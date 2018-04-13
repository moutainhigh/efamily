package com.winterframework.efamily.server.notification;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyRule;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.CharsetFactory;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JPushClientUtil;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.dto.RemindTaskStruc;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.device.NotyTaskRequest;
import com.winterframework.efamily.dto.device.NotyTaskResponse;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.IPushService;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.efamily.server.utils.QueuePrefix;
import com.winterframework.modules.utils.SpringContextHolder;

public class NotificationProcessorNew2 implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationProcessorNew2.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	private final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	private final IPushService pushService=SpringContextHolder.getBean("pushServiceImpl");
	private final HttpUtil httpUtil=SpringContextHolder.getBean("httpUtil");
	private final PropertyUtil propertyUtil=SpringContextHolder.getBean("propertyUtil");
	 
	private String queueName; 
	public NotificationProcessorNew2(String queueName){
		this.queueName=queueName;
	}
	@Override
	public void run() {
		//log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	}
	
	private void process(){
		//synchronized(queueName){	//防止并发 必须要加锁 
			//System.out.println(Thread.currentThread().getName()+"LLLLLLLLLLLLLLLLLIIIIIIIIIIIIIIIIIIIIIIIIII:"+queueName);
			try{
				int length=redisQueue.len(queueName);
				if(length>0){
					NotyTask notyTask=null;
					int index=0;
					NotyTarget target=getTarget(queueName);
					Long userId=target.getUserId();
					Long deviceId=target.getDeviceId();
					boolean hasOffline=false;
					boolean isDisconnected=!ChannelManager.isConnected(userId, deviceId);
					if(isDisconnected){
						String notyIndexStr=redisClient.get(QueuePrefix.NOTY_INDEX+queueName);
						if(null!=notyIndexStr){
							index=Integer.valueOf(notyIndexStr);
						}
					}
					for(;index<length;index++){
						boolean isConnected=ChannelManager.isConnected(userId, deviceId);
						if(isConnected){  //如果在线  则从队首开始处理
							index=0;
						}
						String dataStr=redisQueue.get(queueName, index);	
						if(null==dataStr) continue;
						notyTask=JsonUtils.fromJson(dataStr, NotyTask.class);
						//System.out.println(Thread.currentThread().getName()+"QQQQQQQQQQQQQQQQQQQQQQQQQQQ:"+queueName+ "  index="+index+" dataStr="+dataStr);
						boolean isFinished=doProcess(notyTask,isConnected);
						if(isFinished){
							redisQueue.del(queueName, dataStr);
							index--;
							length--;
						}else{
							redisQueue.set(queueName, index, JsonUtils.toJson(notyTask));
							hasOffline=true;
						}
					}
					if(hasOffline){
						redisQueue.add(NotyQueue.QUEUE_USER_OFFLINE, queueName);
					}
					redisClient.set(QueuePrefix.NOTY_INDEX+queueName, index+""); 	//记录用户队列下次处理的index
					//System.out.println(Thread.currentThread().getName()+"LLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO:"+queueName);
				}
			}catch(Exception e){
				log.error("process notification error. queueName="+queueName,e);
			}finally{
				redisClient.unlock(QueuePrefix.NOTY_LOCK+queueName);
			}
		//}
	}
	
	/**
	 * @param notyTask
	 * @param isOfflineQueue
	 * @return 是否用户已经断线（断线则下次再处理）
	 */
	private boolean doProcess(NotyTask notyTask,boolean isConnected) {
		//?????优化：查询队列 ->判断用户是否在线->是否推送消息、删除队列
		/*RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
		IPushService pushService=SpringContextHolder.getBean("pushServiceImpl");*/    
		boolean isOffline=false;
		NotyTarget target=notyTask.getTarget();
		NotyRule rule=notyTask.getRule();
		Map<String,String> data=notyTask.getData();
		Long taskTime=notyTask.getTaskTime();
		
		int command=notyTask.getCommand();
		Long userId=target.getUserId();
		Long deviceId=target.getDeviceId(); 
		int expireTime=rule.getExpireTime();
		boolean isHsn=rule.isHsn();
		//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
		if(notyTask.isOffline()){
			data.put("isOffline", YesNo.YES.getValue()+"");
		}
		//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->isConnected:"+isConnected);
		if(!isConnected && rule.isOffline() && ((DateUtils.getCurTime()-taskTime)/1000/60>expireTime)){
			return true;
		}
		//未连接 & 离线推送 & 未失效    则放入优先级5队列中以待重发
		if(!isConnected && rule.isOffline() && (expireTime==-1 || ((DateUtils.getCurTime()-taskTime)/1000/60<=expireTime))){
			long time1=DateUtils.getCurTime();
			isOffline=true;
			//手表终端 需要连线后继续推送
			//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
			//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
			if(null!=deviceId){
				//log.info("this is add queue->deviceId:"+deviceId);
				//addQueueHead(notyTask);//加入离线推送队列
				return false;
			}else{
				final NotyMessage.Type notyType=notyTask.getNotyType();  
				if (!notyType.equals(NotyMessage.Type.OPER)&&!notyType.equals(NotyMessage.Type.NOTICE)){
					//手机终端离线推送
					if(!notyTask.isSentOffline()){
						Map<String,String> extraData=new HashMap<String,String>();
						extraData.put("notyType", data.get("notyType"));
						if(data.get("noticeType")!=null){
							extraData.put("noticeType", data.get("noticeType"));
						}
						StringBuffer iosMsg = new StringBuffer("");
						if(notyType.equals(NotyMessage.Type.MSG)){
							extraData.put("sendUserId", data.get("sendUserId"));
							extraData.put("name", data.get("senderName"));
							extraData.put("chatRoomId", data.get("chatGroupId"));
							extraData.put("chatType", data.get("chatType"));
							extraData.put("contentType", data.get("contentType"));
							extraData.put("content", data.get("content"));
							extraData.put("sendTime", data.get("sendTime"));
							if(data.get("contentType")!=null && data.get("contentType").equals("6")){//contentType=6 提示类型的聊天内容
								iosMsg.append(data.get("content"));
							}else{
								iosMsg.append(propertyUtil.getProperty("notice.send.message").replace("@_0_@", data.get("senderName")));
							}
							
						}else if(notyType.equals(NotyMessage.Type.REMIND)){
							RemindTaskStruc remind=JsonUtils.fromJson(data.get("remind"), RemindTaskStruc.class);
							extraData.put("sendUserId", remind.getUserId()+"");
							extraData.put("name", remind.getUserName());
							extraData.put("sendTime", DateUtils.convertDate2Long(DateUtils.currentDate())+"");
							iosMsg.append(propertyUtil.getProperty("notice.send.remind").replace("@_0_@", remind.getUserName()));
						} else if(notyType.equals(NotyMessage.Type.ALARM)){
							extraData.put("userId", data.get("userId"));
							extraData.put("nickName", data.get("nickName"));
							if(data.get("noticeType").equals(UserNotice.NoticeType.BATTERY_WARNING.getValue()+"")){
								extraData.put("battery", data.get("battery"));
								extraData.put("batteryTime", data.get("batteryTime"));
								iosMsg.append(propertyUtil.getProperty("notice.watch.battery").replace("@_0_@", data.get("nickName")).replace("@_1_@", data.get("battery")));
							}else if(data.get("noticeType").equals(UserNotice.NoticeType.ELECTRONIC_WARNING.getValue()+"")){
								extraData.put("time", data.get("time"));
								extraData.put("type", data.get("type"));
								if(data.get("type").equals("1")){
									iosMsg.append(propertyUtil.getProperty("notice.watch.fence.in").replace("@_0_@", data.get("nickName")).replace("@_1_@", data.get("remark")));
								}else{
									iosMsg.append(propertyUtil.getProperty("notice.watch.fence.out").replace("@_0_@", data.get("nickName")).replace("@_1_@", data.get("remark")));
								}
							}
							else if(data.get("noticeType").equals(UserNotice.NoticeType.RATE_WARNING.getValue()+"")){
								extraData.put("time", data.get("time"));
								extraData.put("type", data.get("type"));
								extraData.put("configValue", data.get("configValue"));
								extraData.put("value", data.get("value"));
								
								extraData.put("deviceId", data.get("deviceId"));
								extraData.put("familyId", data.get("familyId"));
								iosMsg.append("异常警报：" + data.get("nickName") + "心率数据异常！" );
							}else if(data.get("noticeType").equals(UserNotice.NoticeType.BLOOOD_PRESSURE.getValue()+"")){
								extraData.put("time", data.get("time"));
								extraData.put("type", data.get("type"));
								extraData.put("configValue", data.get("configValue"));
								extraData.put("value", data.get("value"));
								extraData.put("bloodPressureType", data.get("bloodPressureType"));
								extraData.put("deviceId", data.get("deviceId"));
								iosMsg.append("异常警报：" + data.get("nickName") + "血压数据异常！" );
							}
						}
						//可改进：独立服务器处理第三方推送
						try{
							String token =TokenManager.getToken(userId, deviceId);
							Context ctx = TokenManager.getTokenContext(token);
							if (ctx!=null && ctx.get("appType")!=null) {
								JPushClientUtil.sendPushBy(token + "", iosMsg.toString(), extraData,ctx.get("appType").toString());
							}
						}catch(Exception e){
							log.error("JPush failed:",e);
							log.error("JPush failed:"+extraData.toString());
						} 
						notyTask.setSentOffline(true);
						extraData=null;
					}
					/*new Thread(){
						public void run() {								
							JPushClientUtil.sendPushBy(token+"","您有一条新"+notyType.getValue(),extraData);
						};
					}.start();*/
				}
				if(notyType.equals(NotyMessage.Type.NOTICE) || notyType.equals(NotyMessage.Type.OPER)||notyType.equals(NotyMessage.Type.ALARM)){
					if(!notyTask.isOffline()){
						notyTask.setOffline(true);
					}
					//addQueueHead(notyTask);//加入用户队列队首
					data=null;
					return false;
				}
			}
			log.debug("notification offline cost time:"+(DateUtils.getCurTime()-time1));
		}
		
		if(isConnected){
			long time1=DateUtils.getCurTime();
			log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->sssssssssss:"+userId+" deviceId:"+deviceId);
			//正常连接
			FmlResponse response=new FmlResponse();
			response.setEncode((byte) CharsetFactory.CharsetEnum.UTF8.getCode());
			response.setEncrypt((byte) YesNo.NO.getValue());
	        response.setVersion((byte)1);
	        response.setClinetType((byte)-1);
	        response.setExtend((byte)0);
	        response.setSessionId(DateUtils.getCurTime());
	        response.setCommand(command); //消息推送
			response.setStatus(null!=deviceId?StatusCode.REQUEST.getValue():StatusCode.OK.getValue());  //默认0，ok 
			//res.setStatus(StatusCode.REQUEST.getValue()); 
			response.setToken(TokenManager.getToken(userId,deviceId)); 
			response.setData(data);
			
			try {
				/*if(notyTask.getNotyType().getCode()==0){
					log.info("the msg send: " +isConnected + " this deviceId is:" + deviceId);
				}*/
				pushService.push(userId, deviceId, response);
				//更新状态：已发送   --可以改进：独立服务器处理消息状态
				String serverUrl=propertyUtil.getProperty("server.url.app");
				String urlPath=propertyUtil.getProperty("noty.task.save");
				
				//*** 暂时屏蔽掉notytask状态更新
/*				NotyTaskRequest bizReq=new NotyTaskRequest();
				bizReq.setId(notyTask.getId());
				bizReq.setStatus(NotyTask.Status.SENT.getCode());
				Context ctx=new Context();
				ctx.set("userId", -1+"");	//系统
				Response<NotyTaskResponse> bizRes= httpUtil.http(serverUrl,urlPath,ctx,bizReq,NotyTaskResponse.class);
				if(notyTask.getNotyType().getCode()==0){
					log.info("the msg send status save: " +isConnected + " this deviceId is:" + deviceId);
				}
				if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
					throw new ServerException(bizRes.getStatus().getCode());
				}*/
			} catch (ServerException e) {
				log.error("send mq error and rollback->error notyTask:"+notyTask.getId(),e);
				//addQueueHead(notyTask,true,isOfflineQueue);
				data=null;
				return false;
			}
			log.debug("notification online cost time:"+(DateUtils.getCurTime()-time1));
		}
		data=null;
		return true;
	}
	/**
	 * 离线推送队列
	 * @param notyTask
	 */
	private void addQueue(NotyTask notyTask){
		redisQueue.add(queueName, JsonUtils.toJson(notyTask));
	}
	private void addQueueHead(NotyTask notyTask){
		redisQueue.addHead(queueName, JsonUtils.toJson(notyTask));
	}
	private void addQueue(NotyTask notyTask,boolean isOffline,boolean isOfflineQueue){
	
		/*
		 * if 离线 then
		 *   if 来自离线队列的处理 then
		 *      入离线队首
		 *   else 来自在线队列的处理
		 *      入离线队尾
		 *   end 
		 *  else 在线
		 *      入在线队首
		 *  end
		 */
		redisQueue.add(NotyQueue.QUEUE_USER, queueName);
		if(isOffline){
			String offlineQueueName=getOfflineQueueName(queueName);
			if(isOfflineQueue){
				redisQueue.addHead(offlineQueueName, JsonUtils.toJson(notyTask));
			}else{
				redisQueue.add(offlineQueueName, JsonUtils.toJson(notyTask));
			}
		}else{
			redisQueue.addHead(queueName, JsonUtils.toJson(notyTask));
		}
	}
	private String getOfflineQueueName(String queueName){
		return queueName+Separator.underline+QueuePrefix.OFFLINE;
	}
	public static NotyTarget getTarget(String queueNameTarget){
		if(null==queueNameTarget) return null;
		String targetStr=queueNameTarget.split(Separator.escape+Separator.underline)[1]; 
		String[] targetArr=targetStr.split(Separator.escape+Separator.vertical);
		Long userId=Long.valueOf(targetArr[0]);
		Long deviceId=targetArr.length==2?Long.valueOf(targetArr[1]):null;
		
		return new NotyTarget(userId,deviceId);
	}
}
