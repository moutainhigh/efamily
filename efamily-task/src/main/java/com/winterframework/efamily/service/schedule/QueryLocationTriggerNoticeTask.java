package com.winterframework.efamily.service.schedule;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEfComLocationSemiService;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;


public class QueryLocationTriggerNoticeTask {

	private Logger log = LoggerFactory.getLogger(QueryLocationTriggerNoticeTask.class);

	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name = "efComLocationSemiServiceImpl")
	private IEfComLocationSemiService efComLocationSemiServiceImpl;
	
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;	

	@Resource(name = "RedisClient")
	private RedisClient redisClient;   
	
	@PropertyConfig("location.query.notice.forward.seconds")
	private Long forwardSeconds; 
	
	public void execute() throws Exception {
		/*log.debug("---begin QueryLocationTriggerNoticeTask----");
		Set<String> userListSet = redisClient.keys(EfamilyConstant.LOCATION_QUERY+"*");
		if(userListSet!=null && userListSet.size()>0){
			//*** 遍历缓存的查询用户列表
			for(String userListStr:userListSet){
				String userListQueryInfo = redisClient.get(userListStr);
				Long deviceId = Long.valueOf(userListQueryInfo.split("#")[0]);
				Long deviceUserId = Long.valueOf(userListQueryInfo.split("#")[1]);
				Date date = DateUtils.getDate(Long.valueOf(userListQueryInfo.split("#")[2])-forwardSeconds*1000);
				String queryLocationUserListStr = userListQueryInfo.split("#")[3];
		        EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getNewestLocationForQueryNotice(deviceId,deviceUserId, date);
		        if(efLocationSemi==null){
		        	continue;
		        }
		        //*** 存在最新定位信息则清除查询用户缓存
		        redisClient.del(EfamilyConstant.LOCATION_QUERY+deviceId);
		        
				EjlUser deviceUser = ejlUserDaoImpl.getUserByDeviceId(deviceId);
				if(deviceUser != null){
					List<EjlUser> userList = ejlUserDaoImpl.getUserByDeviceAndApp(deviceUser.getFamilyId());
					List<EjlUser> attentionList =  ejlUserDaoImpl.getAttentionUserByFamilyId(deviceUser.getFamilyId());
		            if(attentionList != null){
		            	userList.addAll(attentionList);
		            }		
		            for(EjlUser user : userList){
		            	Long userId = user.getId();
		            	if(queryLocationUserListStr.contains(","+userId+",")){
							Map<String,String> data=new HashMap<String,String>();
							data.put("userId", efLocationSemi.getUserId()+"");
							data.put("deviceId", efLocationSemi.getDeviceId()+"");
							data.put("lat", efLocationSemi.getLatitude()+"");
							data.put("lng", efLocationSemi.getLongitude()+"");
							data.put("type", efLocationSemi.getType()+"");
							data.put("time", efLocationSemi.getTime().getTime()+"");
							data.put("addressDesc", efLocationSemi.getAddress());
							data.put("precision", efLocationSemi.getRadius()+"");
							
							data.put("notyType", NotyMessage.Type.NOTICE.getCode()+"");
							data.put("noticeType", "109006");
							
							NotyTarget target=new NotyTarget(userId,null);
							NotyMessage message=new NotyMessage();
							message.setCommand(10005);
							message.setType(NotyMessage.Type.NOTICE);
							message.setData(data);
							Notification notification=new Notification();
							notification.setTarget(target);
							notification.setMessage(message);
							notification.setRealTime(true);
							notificationUtil.notification(notification);
		            	}
		            }
				}
			}
		}
		
		log.debug("---end QueryLocationTriggerNoticeTask----");*/
	}

}
