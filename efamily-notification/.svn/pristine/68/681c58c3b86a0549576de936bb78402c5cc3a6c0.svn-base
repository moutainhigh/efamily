package com.winterframework.efamily.notification.daemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.JPushClientUtil;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EfAppJpushSetting;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.notification.model.JPushParameter;
import com.winterframework.efamily.service.IEfComAppJpushSettingService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.modules.utils.SpringContextHolder;
public class NotificationJPushProcessor implements Runnable{ 
	private static final Logger log= LoggerFactory.getLogger(NotificationJPushProcessor.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	private final IEfComAppJpushSettingService efComAppJpushSettingService=SpringContextHolder.getBean("efComAppJpushSettingServiceImpl");
	private final IEjlComUserService ejlComUserService=SpringContextHolder.getBean("ejlComUserServiceImpl");
	 
	private String queueName; 
	private String jpushParameterStr; 
	public NotificationJPushProcessor(String queueName,String jpushParameterStr){ 
		this.queueName=queueName;
		this.jpushParameterStr=jpushParameterStr;
	}
	@Override
	public void run() {
		//log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	}
	
	private void process(){
		try{
			JPushParameter jpushParameter=JsonUtils.fromJson(jpushParameterStr, JPushParameter.class);
			doProcess(jpushParameter);
			redisQueue.del(queueName, jpushParameterStr);
		}catch(Exception e){
			log.error("process notification error. queueName="+queueName,e);
		} 
	}
	
	/**
	 * @param notyTask
	 */
	private boolean doProcess(JPushParameter jpushParameter) {
		try {
			Long userId = jpushParameter.getUserId();
			EjlUser user = ejlComUserService.get(userId);
			Integer appType = user.getAppType();
			if(appType==null){
				log.error("send jpush error===>appType is null userId:"+jpushParameter.getUserId()+" title:" + jpushParameter.getAlert());
				return true;
			}
			EfAppJpushSetting efAppJpushSetting = efComAppJpushSettingService.getByAppType(appType);
			if(efAppJpushSetting == null){
				log.error("send jpush error===>appType is setting userId:"+jpushParameter.getUserId()+" title:" + jpushParameter.getAlert()+" appType:"+appType);
				return true;
			}
			JPushClientUtil.sendPushBy(jpushParameter.getAlias(), jpushParameter.getAlert(), jpushParameter.getData(),efAppJpushSetting.getAppKey(),efAppJpushSetting.getMasterSecret());
		} catch (Exception e) {
			log.error("send jpush error userId:"+jpushParameter.getUserId()+" title:" + jpushParameter.getAlert(),e);
			return false;
		}
		return true;
	}
}
