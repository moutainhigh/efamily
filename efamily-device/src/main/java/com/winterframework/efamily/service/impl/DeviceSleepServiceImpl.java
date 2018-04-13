package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dto.HeartRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlHealthSleep;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IDeviceSleepService;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("deviceSleepServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceSleepServiceImpl extends EjlComHealthSleepServiceImpl implements IDeviceSleepService{
	
	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl; 	
	@Resource(name="httpClientImpl")
	protected IHttpClient httpClientImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	@Override
	public void save(Context ctx,Long userId,Long deviceId,List<EjlHealthSleep> sleepList) throws BizException {
		/**
		 * 1.insert设备睡眠表
		 */	
		log.debug("welcome to heart service."); 
		if(null!=sleepList){
			save(ctx,sleepList);
		}
	}
	
	@Override
	public void notifyForSleepStart(Long userId,Long deviceId,int type) throws BizException{
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("userId",  userId+"");
		data.put("deviceId",  deviceId+"");
		data.put("type", type+"");
		EjlUser userWatch =  ejlComUserDaoImpl.getById(userId);
		EjlUser user = new EjlUser();
		user.setFamilyId(userWatch.getFamilyId());
		user.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> userList = ejlComUserDaoImpl.selectListObjByAttribute(user);
		notifyUser(data,userList,userId,NoticeType.SLEEP_START,null);
	}
	
	public void notifyUser(Map<String,String> data,List<EjlUser> userList,Long userId,NoticeType noticeType,Long notNoticeUserId){
    	if(userList == null || userList.size()==0){
			log.info("需要推送的用户为空。 userId = "+userId+" ; noticeType = "+noticeType);
			return;
    	}
   		for(EjlUser userTemp : userList){
   			if(notNoticeUserId!=null && notNoticeUserId.longValue() == userTemp.getId().longValue()){
   				continue;
   			} 
   			try { 
				NotyTarget t=new NotyTarget(userTemp.getId(),null); 
				
				//Map<String,String> paramMap=userNotice.getParamMap();
				Map<String,String> data2=new HashMap<String,String>(); 
				data2.put("noticeType", noticeType.getValue()+"");
				data2.putAll(data);
				
				/*String content=propertyUtil.getProperty(userNotice.getNoticeType().getValue()+"");
				data.put("content", replaceParam(content,paramMap));*/
				
				NotyMessage message=new NotyMessage();
				message.setId(null);
				message.setType(NotyMessage.Type.NOTICE);
				message.setCommand(EfamilyConstant.PUSH);
				message.setData(data2);
				Notification notification=new Notification();
				notification.setTarget(t);
				notification.setMessage(message);
				 
				notificationUtil.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("推送[notifyUser]时出现异常：  userId = "+userTemp.getId());
			}
   		}
     }
}
