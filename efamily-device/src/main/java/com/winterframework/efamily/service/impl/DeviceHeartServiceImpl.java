package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dto.HeartRequest;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.DeviceHeartFinishRequest;
import com.winterframework.efamily.dto.device.DeviceHeartStartRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceHeartService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.ISoftwareVersionService;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("deviceHeartServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceHeartServiceImpl extends EjlComHealthHeartRateServiceImpl implements IDeviceHeartService{

	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl; 	
	@Resource(name="httpClientImpl")
	protected IHttpClient httpClientImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	@Resource(name = "softwareVersionServiceImpl")
	private ISoftwareVersionService softwareVersionService;
	
	@Override
	public void save(Context ctx,Long userId,Long deviceId,List<EjlHealthHeartRate> heartList) throws BizException {
		/**
		 * 1.insert设备心率表
		 */	
		log.debug("welcome to heart service."); 
		if(null!=heartList){
			save(ctx,heartList);
		}
	}
	
	@Override
	public void start(Context ctx, Long userId, Long deviceId,
			DeviceHeartStartRequest bizReq) throws BizException {
		if(!softwareVersionService.gt(deviceId, "v2.0")){
			throw new BizException(StatusBizCode.DEVICE_NOT_SUPPORT.getValue());
		}
		EjlDevice device=ejlComDeviceService.get(deviceId);
		if(device!=null){
			if(YesNo.NO.getValue()==bizReq.getStatus().intValue()){
				device.setOperStatus(EjlDevice.OperStatus.START.getValue());
			}else{
				device.setOperStatus(EjlDevice.OperStatus.FINISH.getValue());
				device.setOperFinishTime(DateUtils.getDate(bizReq.getTime()));
			}
			ejlComDeviceService.save(ctx, device);
			
			if(null!=device.getOperUserId()){
				try{
					Map<String,String> data=new HashMap<String, String>();
					data.put("noticeType",UserNotice.NoticeType.APP_DEVICE_HEALTH_HEART_START.getValue()+"");
					data.put("resultCode", bizReq.getStatus()+"");
					data.put("deviceId", device.getId()+"");
					data.put("nickName", device.getName());
					notificationUtil.notification(device.getOperUserId(), null, EfamilyConstant.PUSH,NotyMessage.Type.NOTICE, data, false);
				}catch(Exception e){
					log.error("heart start push failed.operUserId="+device.getOperUserId()+" deviceId="+device.getId(),e);
				}
			}
		}
	}
	@Override
	public void finish(Context ctx, Long userId, Long deviceId,
			DeviceHeartFinishRequest bizReq) throws BizException {
		EjlDevice device=ejlComDeviceService.get(deviceId);
		if(device!=null){
			device.setOperStatus(EjlDevice.OperStatus.FINISH.getValue());
			device.setOperFinishTime(DateUtils.getDate(bizReq.getTime()));
			ejlComDeviceService.save(ctx, device);
			
			if(null!=device.getOperUserId()){
				try{
					Map<String,String> data=new HashMap<String, String>();
					data.put("noticeType",UserNotice.NoticeType.APP_DEVICE_HEALTH_HEART_FINISH.getValue()+"");
					data.put("resultCode", bizReq.getStatus()+"");
					data.put("deviceId", device.getId()+"");
					data.put("nickName", device.getName());
					notificationUtil.notification(device.getOperUserId(), null, EfamilyConstant.PUSH,NotyMessage.Type.NOTICE, data, false);
				}catch(Exception e){
					log.error("heart start push failed.operUserId="+device.getOperUserId()+" deviceId="+device.getId(),e);
				}
			}
		}
	}
	public void notifyForHeartUpdate(Context ctx,Long userId,List<HeartRequest> bizReqList) throws BizException{
		Integer count = getLastEjlHealthHeartRate(bizReqList);
		if(count == null){
			return;
		}
		Map<String,String> data = new HashMap<String,String>();
		data.put("currentHeartRate", count+"");
		data.put("updateType", EfamilyConstant.UPDATE_HEALTHY_TYPE_RATE +"");
		data.put("userId",  userId+"");
		data.put("deviceId", ctx.getDeviceId()+"");
		EjlUser userWatch =  ejlComUserDaoImpl.getById(userId);
		EjlUser user = new EjlUser();
		user.setFamilyId(userWatch.getFamilyId());
		user.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> userList = ejlComUserDaoImpl.selectListObjByAttribute(user);
		notifyUser(data,userList,userId,NoticeType.UPDATE_HEALTHY,null);
	}
	
	public Integer getLastEjlHealthHeartRate(List<HeartRequest> bizReqList){
		Integer count = null;
		Long fromTime = 0L;
		if(bizReqList!=null &&bizReqList.size()>0){
			for(int i=0;i<bizReqList.size();i++){
				HeartRequest heartRequestTemp =  bizReqList.get(i);
				if(heartRequestTemp.getCount()<=0){
					continue;
				}
				if(heartRequestTemp.getFromTime()>fromTime){
					count = heartRequestTemp.getCount();
					fromTime = heartRequestTemp.getFromTime();
				}
			}
		}
		return count;
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
