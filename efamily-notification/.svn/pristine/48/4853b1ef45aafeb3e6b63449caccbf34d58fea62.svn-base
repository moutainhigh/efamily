package com.winterframework.efamily.notification.daemon;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.dto.RemindTaskStruc;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.notification.NotificationInner;
import com.winterframework.efamily.notification.enums.PushStatus;
import com.winterframework.efamily.notification.enums.QueuePrefix;
import com.winterframework.efamily.notification.model.NotyQueue;
import com.winterframework.efamily.notification.model.NotyRule;
import com.winterframework.efamily.notification.model.NotyTask;
import com.winterframework.efamily.notification.service.IConnectService;
import com.winterframework.efamily.notification.service.INotificationTpService;
import com.winterframework.modules.utils.SpringContextHolder;

public class NotificationProcessorNew2 implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationProcessorNew2.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	private final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	private final IConnectService connectService=SpringContextHolder.getBean("connectServiceImpl");
	private final INotificationTpService notificationTpService=SpringContextHolder.getBean("notificationTpServiceImpl");
	
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
					for(;index<length;index++){
						String dataStr=redisQueue.get(queueName, index);	
						if(null==dataStr) continue;
						notyTask=JsonUtils.fromJson(dataStr, NotyTask.class);
						//System.out.println(Thread.currentThread().getName()+"QQQQQQQQQQQQQQQQQQQQQQQQQQQ:"+queueName+ "  index="+index+" dataStr="+dataStr);
						boolean isFinished=doProcess(notyTask);
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
	private boolean doProcess(NotyTask notyTask) {
		/**
		 * 获取目标服务器IP
		 * 调用推送http
		 * isOK则删除队列
		 * isDisconnect则离线推送
		 * isFailed则保留队列
		 */
		boolean isOffline=false;
		NotyTarget target=notyTask.getTarget();
		NotyRule rule=notyTask.getRule();
		Map<String,String> data=notyTask.getData();
		Long taskTime=notyTask.getTaskTime();
		
		int command=notyTask.getCommand();
		Long userId=target.getUserId();
		Long deviceId=target.getDeviceId(); 
		int expireTime=rule.getExpireTime();
		int sendType=rule.getSendType();
		String offlineTitle=notyTask.getOfflineTitle();	//离线推送消息标题
		Map<String,String> offlineData=notyTask.getOfflineData();	//离线推送消息体内容
		
		//不管是否离线在线 过期消息 一律先过滤
		if(expireTime!=-1 && ((DateUtils.getCurTime()-taskTime)/1000/60>expireTime)){
			return true;
		}
		
		//离线发送过 则标注
		if(notyTask.isOffline()){
			data.put("isOffline", YesNo.YES.getValue()+"");
		}
		NotificationInner notificationInner=new NotificationInner();
		notificationInner.setUserId(userId);
		notificationInner.setDeviceId(deviceId);
		notificationInner.setCommand(command);
		notificationInner.setData(data);
		try{
			PushStatus pushStatus=connectService.push(notificationInner);
			if(pushStatus.equals(PushStatus.SUCCESS)){
				return true;
			}else if(pushStatus.equals(PushStatus.OFFLINE)){
				
				if(deviceId!=null){
					//*** 设备不发送离线推送
					return false;
				}
				
				/**离线处理逻辑：
				 * 设备的消息保留
				 * APP的离线推送
				 */
				if(isOfflineSend(sendType)){
					isOffline=true;
					//手表终端 需要连线后继续推送
					//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
					//log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX->userId:"+userId+"  deviceId:"+deviceId);
					final NotyMessage.Type notyType=notyTask.getNotyType();  
					if (!notyType.equals(NotyMessage.Type.OPER)&&!notyType.equals(NotyMessage.Type.NOTICE)){
						//手机终端离线推送（离线在线都需要推送的  离线推送过的就不再推送）
						if(!notyTask.isSentOffline()){
							Map<String,String> extraData=new HashMap<String,String>();
							extraData.put("notyType", data.get("notyType"));
							if(data.get("noticeType")!=null){
								extraData.put("noticeType", data.get("noticeType"));
							}
                            if(offlineData!=null){
                            	extraData.putAll(offlineData);
                            }
							//可改进：独立服务器处理第三方推送 
							try{
								notificationTpService.jpush(userId, deviceId, offlineTitle, extraData);	//亿家联APP
							}catch(Exception e){
								log.error("JPush failed:"+extraData.toString(),e);
							} 
							notyTask.setSentOffline(true);
							extraData=null;
						}
					}
				}
				
				//在线推送（告警离线推了 在线 需要再推）
				if(isOnlineSend(sendType)){
					if(!notyTask.isOffline()){
						notyTask.setOffline(true);
					}
					//addQueueHead(notyTask);//加入用户队列队首
					return false;
				}
			}else{
				//推送失败 再推送
				return false;
			}
		}catch(Exception e){
			log.error("push failed.userId="+userId+" deviceId="+deviceId,e);
			return false;
		}
		return true;
 	}
	private boolean isOfflineSend(int sendType){
		return sendType==NotyMessage.SendType.OFF_LINE.getValue() || sendType==NotyMessage.SendType.ONFF_LINE.getValue();
	}
	private boolean isOnlineSend(int sendType){
		return sendType==NotyMessage.SendType.ON_LINE.getValue() || sendType==NotyMessage.SendType.ONFF_LINE.getValue();
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
