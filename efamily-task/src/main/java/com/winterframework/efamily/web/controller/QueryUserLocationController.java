package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.QueuePrefix;
import com.winterframework.efamily.service.IEfComLocationSemiService;
import com.winterframework.efamily.service.IEfLocationOriginService;
import com.winterframework.efamily.service.ITskLocationSemiServiceNew;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Controller("queryUserLocationController")
@RequestMapping("/task/location")
public class QueryUserLocationController {
	private Logger log = LoggerFactory.getLogger(QueryUserLocationController.class);
	@Resource(name = "efComLocationSemiServiceImpl")
	private IEfComLocationSemiService efComLocationSemiServiceImpl;
	
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;	

	@Resource(name = "RedisClient")
	private RedisClient redisClient;   
	@Resource(name = "redisQueue")
	private RedisQueue redisQueue;   
	@Resource(name = "efLocationOriginServiceImpl")
	private IEfLocationOriginService efLocationOriginServiceImpl;
	
	@Resource(name = "tskLocationSemiServiceImplNew")
	private ITskLocationSemiServiceNew tskLocationSemiServiceNew;
	
	@RequestMapping("/sendUserLocation")
	@ResponseBody
	protected Response<Object> sendUserLocation(@RequestBody Request<Map<String,String>> request) throws Exception {
		/**
		 * 1.定位处理
		 * 2.找到推送目标列表（解锁）
		 * 3.推送
		 */
		Response<Object> resp = new Response<Object>(request);
		Long userId = Long.valueOf(request.getData().get("userId"));
		Long deviceId = Long.valueOf(request.getData().get("deviceId")); 
		
		List<Notification> notificationList=null;
		//业务处理完即刻解锁
		try{
			efLocationOriginServiceImpl.initLocationOrigin(userId, deviceId);
			 
			EfLocationSemi locationSemi = efComLocationSemiServiceImpl.getLastLocation(userId, deviceId);
	    	if(null!=locationSemi){
	    		notificationList=getNotificationList(locationSemi);
	    	}
		}catch(Exception e){
			log.error("handle location failed.userId="+userId+" deviceId="+deviceId);
		}finally{
			String target=userId+Separator.vertical+deviceId;
			redisClient.unlock(QueuePrefix.QUEUE_LOCATION_QUERY_LOCK+target);
		}
		//推送
		notify(notificationList);
		
        return resp;
	}
	private List<Notification> getNotificationList(EfLocationSemi locationSemi){
		Long userId=locationSemi.getUserId();
		Long deviceId=locationSemi.getDeviceId();
		//推送定位信息（不管有没有最新的）
		NotyMessage notyMessage=getNotyMessage(locationSemi);
		Notification notification=null;  
		String target=userId+Separator.vertical+deviceId;
		String value=redisQueue.getUnique(QueuePrefix.QUEUE_LOCATION_QUERY+target);
		List<Notification> notificationList=new ArrayList<Notification>();
		final int expireTime=5*60; 
		while(null!=value){
			String[] params=value.split(Separator.verticalSep);
			Long userIdApp=Long.valueOf(params[0]);
			Long time=Long.valueOf(params[1]);
			
			//请求在有效期内的推送
			if(!DateUtils.isBefore(DateUtils.addSeconds(DateUtils.getDate(time), expireTime), DateUtils.currentDate())){
				notification=new Notification(); 
				notification.setTarget(new NotyTarget(userIdApp,null));  
				notification.setMessage(notyMessage);
				notification.setRealTime(true);
				notificationList.add(notification);
			}
			
			value=redisQueue.getUnique(QueuePrefix.QUEUE_LOCATION_QUERY+target);
        }
		return notificationList;
	}
	private void notify(List<Notification> notificationList){
		if(null!=notificationList){	//查询定位用户较少  没必要并行处理
			for(Notification noty:notificationList){
				try{
					notificationUtil.notification(noty);
				}catch(Exception e){
					log.error("location query notification failed.userId="+noty.getTarget().getUserId());
				}
			}
		}
	}
	private NotyMessage getNotyMessage(EfLocationSemi locationSemi){
		Map<String,String> data=new HashMap<String,String>();
		data.put("userId", locationSemi.getUserId()+"");
		data.put("deviceId", locationSemi.getDeviceId()+"");
		data.put("lat", locationSemi.getLatitude()+"");
		data.put("lng", locationSemi.getLongitude()+"");
		data.put("type", locationSemi.getType()+"");
		data.put("time", locationSemi.getTime().getTime()+"");
		data.put("addressDesc", locationSemi.getAddress());
		data.put("precision", locationSemi.getRadius()+"");
		
		data.put("notyType", NotyMessage.Type.NOTICE.getCode()+"");
		data.put("noticeType", "109006");
		 
		NotyMessage message=new NotyMessage();
		message.setCommand(10005);
		message.setType(NotyMessage.Type.NOTICE);
		message.setData(data);
		
		return message;
	}
}
