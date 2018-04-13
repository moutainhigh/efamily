package com.winterframework.efamily.server.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.NotyTaskRequest;
import com.winterframework.efamily.dto.device.NotyTaskResponse;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.IPushService;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.modules.utils.SpringContextHolder;

public class NotificationProcessor implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationProcessor.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	private final IPushService pushService=SpringContextHolder.getBean("pushServiceImpl");
	private final HttpUtil httpUtil=SpringContextHolder.getBean("httpUtil");
	private final PropertyUtil propertyUtil=SpringContextHolder.getBean("propertyUtil");
	private final RedisClient redisClient = SpringContextHolder.getBean("RedisClient");
	 
	private NotyTask notyTask;
	public NotificationProcessor(String dataStr){
		notyTask=JsonUtils.fromJson(dataStr, NotyTask.class);
	}
	@Override
	public void run() {
		//log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	}
	
	@SuppressWarnings("static-access")
	private void process(){
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
		final String token = TokenManager.getToken(userId, deviceId);
		boolean isHsn=rule.isHsn();
		//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
		if(notyTask.isOffline()){
			data.put("isOffline", YesNo.YES.getValue()+"");
		}			
		boolean isConnected=ChannelManager.isConnected(userId, deviceId);
		if(userId.longValue()==839){
			log.info("-------");
		}
		/*if(notyTask.getNotyType().getCode()==0){
			log.info("the msg collection is: " +isConnected + " this deviceId is:" + deviceId);
		}*/
		
		//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->isConnected:"+isConnected);
		//未连接 & 离线推送 & 未失效    则放入优先级5队列中以待重发
		if(!isConnected && rule.isOffline() && (expireTime==-1 || ((DateUtils.getCurTime()-taskTime)/1000/60<=expireTime))){
			long time1=DateUtils.getCurTime();
			isOffline=true;
			//手表终端 需要连线后继续推送
			//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
			//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
			if(null!=deviceId){
				//log.info("this is add queue->deviceId:"+deviceId);
				addQueue(notyTask,isOffline);//加入离线推送队列
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
							iosMsg.append(data.get("senderName")+" 给你发送了一条消息");
						}else if(notyType.equals(NotyMessage.Type.REMIND)){
							RemindTaskStruc remind=JsonUtils.fromJson(data.get("remind"), RemindTaskStruc.class);
							extraData.put("sendUserId", remind.getUserId()+"");
							extraData.put("name", remind.getUserName());
							extraData.put("sendTime", DateUtils.convertDate2Long(DateUtils.currentDate())+"");
							iosMsg.append(remind.getUserName() + "给你发了一条提醒");
						} else if(notyType.equals(NotyMessage.Type.ALARM)){
							extraData.put("userId", data.get("userId"));
							extraData.put("nickName", data.get("nickName"));
							if(data.get("noticeType").equals(UserNotice.NoticeType.BATTERY_WARNING.getValue()+"")){
								extraData.put("battery", data.get("battery"));
								extraData.put("batteryTime", data.get("batteryTime"));
								iosMsg.append(data.get("nickName") + "的手表电量剩余"+data.get("battery")+"%");
							}else if(data.get("noticeType").equals(UserNotice.NoticeType.ELECTRONIC_WARNING.getValue()+"")){
								extraData.put("time", data.get("time"));
								extraData.put("type", data.get("type"));
								iosMsg.append(data.get("nickName") + (data.get("type").equals("1")?"进入":"离开") +"围栏范围" );
							}
							else if(data.get("noticeType").equals(UserNotice.NoticeType.RATE_WARNING.getValue()+"")){
								extraData.put("time", data.get("time"));
								extraData.put("type", data.get("type"));
								extraData.put("configValue", data.get("configValue"));
								extraData.put("value", data.get("value"));
								iosMsg.append(data.get("nickName") + (data.get("type").equals("1")?"进入":"离开") +"围栏范围" );
							}
						}
						//可改进：独立服务器处理第三方推送
						try{
							//redisClient.set("JPushClient"+notyTask.getId(), notyTask.getId()+"");
                            boolean flag = true;
							if(data.get("noticeType")==null&&String.valueOf(Command.CHAT_MESSAGE).equals(data.get("noticeType"))){
								if(data.get("contentType").equals("5")){
									flag = false;
								}
							}
							List<String> noPushNoticeType = new ArrayList<String>();
							noPushNoticeType.add(String.valueOf(NoticeType.BATTERY_WARNING));
							noPushNoticeType.add(String.valueOf(NoticeType.ELECTRONIC_WARNING));
							noPushNoticeType.add(String.valueOf(NoticeType.DEVICE_ONOFF));
							if(flag && (data.get("noticeType")==null || !noPushNoticeType.contains(data.get("noticeType")))){
								//JPushClientUtil.sendPushBy(token+"",iosMsg.toString(),extraData);
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
					addQueue(notyTask,isOffline);//加入离线推送队列
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
				
				
				NotyTaskRequest bizReq=new NotyTaskRequest();
				bizReq.setId(notyTask.getId());
				bizReq.setStatus(NotyTask.Status.SENT.getCode());
				Context ctx=new Context();
				ctx.set("userId", -1+"");	//系统
				Response<NotyTaskResponse> bizRes= httpUtil.http(serverUrl,urlPath,ctx,bizReq,NotyTaskResponse.class);
				/*if(notyTask.getNotyType().getCode()==0){
					log.info("the msg send status save: " +isConnected + " this deviceId is:" + deviceId);
				}*/
				if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
					throw new ServerException(bizRes.getStatus().getCode());
				}
			} catch (ServerException e) {
				log.error(e.getMessage(),e);
				log.error("send mq error and rollback->error notyTask:"+notyTask.getId());
				addQueue(notyTask,false);
			}
			log.debug("notification online cost time:"+(DateUtils.getCurTime()-time1));
		}
		data=null;
		notyTask=null;
	}
	/**
	 * 离线推送队列
	 * @param notyTask
	 */
	private void addQueue(NotyTask notyTask,boolean isOffline){
		redisQueue.add(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId(),isOffline).getName(), JsonUtils.toJson(notyTask));
	}
	/*private void addQueue(NotyTask notyTask){
		NotyRule rule=notyTask.getRule();
		Long expireTime=Long.valueOf(rule.getExpireTime());
		Long taskTime=notyTask.getTaskTime();
		Long waitTime=(DateUtils.getCurTime()-taskTime)/1000/60;
		Long rate = waitTime*100/expireTime;
		if(rate<10){
			//log.error("wwwwwwwwwwwwwwwwwwwwwwwwwww->add:"+rate+" waitTime:"+waitTime+ " expireTime:"+expireTime);
			redisQueue.add(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId()).getName(), JsonUtils.toJson(notyTask));
		}else if(rate<50){
			//log.error("wwwwwwwwwwwwwwwwwwwwwwwwwww->add:"+rate+" waitTime:"+waitTime+ " expireTime:"+expireTime);
			redisQueue.add(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId()).getName()+"_offline", JsonUtils.toJson(notyTask));
		}else{
			//log.error("wwwwwwwwwwwwwwwwwwwwwwwwwww->add:"+rate+" waitTime:"+waitTime+ " expireTime:"+expireTime);
			redisQueue.add(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId()).getName()+"_offline_1", JsonUtils.toJson(notyTask));
		}*/
		/*NotyRule rule=notyTask.getRule(); 
		Long taskTime=notyTask.getTaskTime();
		if((DateUtils.getCurTime()-taskTime)/1000/60<=30){
			redisQueue.add(NotyQueue.getQueue(rule.getPriority()).getName()+"_offline", JsonUtils.toJson(notyTask));  
		}else{
			redisQueue.add(NotyQueue.getQueue(rule.getPriority()).getName()+"_offline_1", JsonUtils.toJson(notyTask));  
		}
	}*/
	
	
}
