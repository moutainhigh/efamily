package com.winterframework.efamily.entity.converter;

import java.util.List;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.dto.HeartRequest;
import com.winterframework.efamily.dto.LocationRequest;
import com.winterframework.efamily.dto.LocationWifiRequest;
import com.winterframework.efamily.dto.RemindListStruc;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.dto.SleepRequest;
import com.winterframework.efamily.dto.TestRequest;
import com.winterframework.efamily.dto.UpdateUserLoginRecordRequest;
import com.winterframework.efamily.dto.UserBaseInfoStruc;
import com.winterframework.efamily.dto.device.DeviceBatteryRecordRequest;
import com.winterframework.efamily.dto.device.DeviceBloodPressureRequest;
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.dto.device.DeviceContactsRequest;
import com.winterframework.efamily.dto.device.DeviceLocationSatelliteRequest;
import com.winterframework.efamily.dto.device.DeviceMobileRequest;
import com.winterframework.efamily.dto.device.DeviceParamRequest;
import com.winterframework.efamily.dto.device.DeviceSignalRecordRequest;
import com.winterframework.efamily.dto.device.NotyTaskRequest;
import com.winterframework.efamily.dto.device.ResourceDownloadResponse;
import com.winterframework.efamily.dto.device.ResourceUploadRequest;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.DeviceLocationSatellite;
import com.winterframework.efamily.entity.DeviceMobile;
import com.winterframework.efamily.entity.DeviceSignalRecord;
import com.winterframework.efamily.entity.EfDeviceOperation;
import com.winterframework.efamily.entity.EfLocationGps;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.EjlHealthSleep;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserLoginRecord;
import com.winterframework.efamily.entity.FmlResource;
import com.winterframework.efamily.entity.NotificationTask;
import com.winterframework.efamily.entity.Test;
import com.winterframework.efamily.enums.DeviceParameter;
import com.winterframework.efamily.utils.HealthUtil;

public class DTOConvert {

	public static Test convert2Test(TestRequest req){
		if(null==req) return null;
		Test test=new Test(); 
		test.setId(req.getId());
		
		return test;
	}
	public static EjlDeviceParmConfig toDeviceParamConfig(Long userId,Long deviceId,DeviceParamRequest req){
		if(null==req) return null;
		EjlDeviceParmConfig entity=new EjlDeviceParmConfig(); 
		entity.setParamKey(DeviceParameter.get(req.getCode()).getValue());
		entity.setParamValue(req.getValue());
		return entity;
	}
	
	public static EjlDeviceAddressList toDeviceContacts(Long userId,Long deviceId,DeviceContactsRequest req){
		if(null==req) return null;
		EjlDeviceAddressList entity=new EjlDeviceAddressList(); 
		entity.setUserId(req.getUserId());
		entity.setName(req.getNickName());
		entity.setHeadImage(req.getHeadImage());
		entity.setPhoneNumber(req.getPhoneNumber());
		entity.setCreatorId(userId);		
		return entity;
	}
	public static DeviceBatteryRecord toDeviceBatteryRecord(Long deviceId,DeviceBatteryRecordRequest req){
		if(null==req) return null;
		DeviceBatteryRecord entity=new DeviceBatteryRecord(); 
		entity.setDeviceId(deviceId);
		entity.setValue(req.getValue());
		entity.setTime(req.getTime());		
		return entity;
	}
	public static DeviceSignalRecord toDeviceSignalRecord(Long deviceId,DeviceSignalRecordRequest req){
		if(null==req) return null;
		DeviceSignalRecord entity=new DeviceSignalRecord(); 
		entity.setDeviceId(deviceId);
		entity.setLevel(req.getValue());
		entity.setTime(req.getTime());
		
		return entity;
	}
	public static DeviceLocationSatellite toDeviceLocationSatellite(Long deviceId,DeviceLocationSatelliteRequest req){
		if(null==req) return null;
		DeviceLocationSatellite entity=new DeviceLocationSatellite(); 
		entity.setDeviceId(deviceId);
		entity.setSateNumber(req.getCount());
		entity.setRate(req.getRate());
		entity.setTime(req.getTime());
		return entity;
	}
	public static EfLocationGps toLocation(Long userId,Long deviceId,LocationRequest req){
		if(null==req) return null;
		EfLocationGps entity=new EfLocationGps();  
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		entity.setLocation(req.getLatitude()+Separator.comma+req.getLongitude());
		entity.setTime(DateUtils.getDate(req.getTime()));

		return entity;
	}
	public static EfLocationWifi toLocationWifi(Long userId,Long deviceId,LocationWifiRequest req){
		if(null==req) return null;
		EfLocationWifi entity=new EfLocationWifi();  
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		entity.setMac1(req.getMac1());
		entity.setMac2(req.getMac2());
		entity.setMac3(req.getMac3());
		entity.setMac4(req.getMac4());
		entity.setMac5(req.getMac5());
		entity.setMacName1(req.getMacName1());
		entity.setMacName2(req.getMacName2());
		entity.setMacName3(req.getMacName3());
		entity.setMacName4(req.getMacName4());
		entity.setMacName5(req.getMacName5());
		entity.setSignal1(req.getSignal1());
		entity.setSignal2(req.getSignal2());
		entity.setSignal3(req.getSignal3());
		entity.setSignal4(req.getSignal4());
		entity.setSignal5(req.getSignal5());
		entity.setTime(req.getTime());
		return entity;
	}
	
	public static EjlHealthHeartRate toHeartRate(Long userId,Long deviceId,HeartRequest req){
		if(null==req) return null;
		EjlHealthHeartRate entity=new EjlHealthHeartRate();  
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		entity.setRate(new Long(req.getCount()));
		entity.setTimeSpan(new Long(HealthUtil.getTimeSpan(req.getToTime(),req.getFromTime())));
		entity.setFromTime(req.getFromTime());
		entity.setToTime(req.getToTime());
		return entity;
	}
	
	public static EjlHealthBloodPressure toBloodPressure(Long userId,Long deviceId,DeviceBloodPressureRequest bizReq){
		if(null==bizReq) return null;
		EjlHealthBloodPressure entity=new EjlHealthBloodPressure();  
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		entity.setHigh(bizReq.getHigh());
		entity.setLow(bizReq.getLow());
		entity.setFromTime(bizReq.getFromTime());
		entity.setToTime(bizReq.getToTime());
		entity.setRate(bizReq.getRate());
		return entity;
	}
	
	public static EjlHealthSleep toSleep(Long userId,Long deviceId,SleepRequest req){
		if(null==req) return null;
		EjlHealthSleep entity=new EjlHealthSleep();
		String total=req.getTotal();
		List<String> deepList=req.getDeep();
		List<String> wakeList=req.getWake();
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		if(null!=total){
			String[] deeps=total.split(Separator.horizontal);
			entity.setFromTime(DateUtils.getDate(Long.valueOf(deeps[0])));
			entity.setToTime(DateUtils.getDate(Long.valueOf(deeps[1])));
		}		
		if(null!=deepList){			
			entity.setDeepStruc(JsonUtils.toJson(deepList));
		}
		if(null!=wakeList){			
			entity.setWakeStruc(JsonUtils.toJson(wakeList));
		}
		return entity;
	}
	public static EjlMessage toMessage(DeviceChatMessageRequest req){
		if(null==req) return null;
		EjlMessage entity=new EjlMessage(); 
		entity.setSendUserId(req.getSenderId());
		entity.setReceiveUserId(req.getReceiverId());
		//*** 单人聊天的时候 此处的chatRoomId 就是聊天对方的userId
		entity.setChatRoomId(req.getChatRoomId());
		entity.setContentType(new Long(EjlMessage.ContentType.get(req.getType()).getCode())); 
		//*** 此处做了一个版本兼容 
		entity.setChatType(new Long(EjlMessage.ChatType.FML_GROUP.getValue()));
		//entity.setChatType(req.getChatType()==null?new Long(EjlMessage.ChatType.FML.getValue()):req.getChatType());
		entity.setContent(req.getContent());
		entity.setContentTime(req.getContentTime());
		entity.setAppSendTime(Long.valueOf(DateUtils.format(req.getTime(),"yyyyMMddHHmmssSSS")));
		return entity;
	}
	public static DeviceMobile toDeviceMobile(Long userId,Long deviceId,DeviceMobileRequest req){
		if(null==req) return null;
		DeviceMobile entity=new DeviceMobile(); 
		entity.setUserId(userId);
		entity.setDeviceId(deviceId);
		entity.setServerIp(null==req.getServerIp()?"127.0.0.1":req.getServerIp());
		entity.setNetwork(null==req.getNetwork()?"-1":req.getNetwork());
		entity.setMcc(req.getMcc());
		entity.setMnc(req.getMnc());
		entity.setLac1(req.getLac1());
		entity.setCi1(req.getCi1());
		entity.setRssi1(req.getRssi1());
		entity.setLac2(req.getLac2());
		entity.setCi2(req.getCi2());
		entity.setRssi2(req.getRssi2());
		entity.setLac3(req.getLac3());
		entity.setCi3(req.getCi3());
		entity.setRssi3(req.getRssi3());
		entity.setLac4(req.getLac4());
		entity.setCi4(req.getCi4());
		entity.setRssi4(req.getRssi4());
		entity.setLac5(req.getLac5());
		entity.setCi5(req.getCi5());
		entity.setRssi5(req.getRssi5());
		entity.setTime(req.getTime());
		return entity;
	}
	public static FmlResource toFmlResource(ResourceUploadRequest req){
		if(null==req) return null;
		FmlResource bizRes=new FmlResource();
		bizRes.setResourceId(req.getResourceId());
		bizRes.setType(req.getType());
		bizRes.setExtType(req.getExtType());
		bizRes.setFilePath(req.getFilePath());
		bizRes.setIsMulti(req.getIsMulti());
		bizRes.setRemark(req.getRemark());
		
		return bizRes;
	}
	public static NotificationTask toNotificationTask(NotyTaskRequest req){
		if(null==req) return null;
		NotificationTask task=new NotificationTask();
		task.setId(req.getId());
		task.setNotyType(req.getNotyType());
		task.setMessageId(req.getMessageId());
		task.setUserId(req.getUserId());
		task.setDeviceId(req.getDeviceId());
		task.setStatus(req.getStatus());
		task.setRemark(req.getRemark());
		return task;
	}
	public static ResourceDownloadResponse toResourceDownloadResponse(FmlResource resource){
		if(null==resource) return null;
		ResourceDownloadResponse bizRes=new ResourceDownloadResponse();
		bizRes.setResourceId(resource.getResourceId());
		bizRes.setType(resource.getType());
		bizRes.setExtType(resource.getExtType());
		bizRes.setFilePath(resource.getFilePath());
		bizRes.setIsMulti(resource.getIsMulti());
		bizRes.setRemark(resource.getRemark());
		
		return bizRes;
	}
	
	public static EjlUserLoginRecord  toUserLoginRecord(Long userId,Long deviceId,UpdateUserLoginRecordRequest request){
		EjlUserLoginRecord loginRecord=new EjlUserLoginRecord();
		loginRecord.setUserId(userId);
		loginRecord.setToken(request.getToken());
		loginRecord.setStatus(request.getStatus());
		loginRecord.setRemark(request.getRemark());
		return loginRecord;
	}
	public static RemindStruc convertEntityToRemindStruc(EjlRemind entity,String userName) {
		if (entity != null) {
			RemindStruc struc = new RemindStruc();
			struc.setRemindId(entity.getId());
			struc.setDeliverDeadTime(DateUtils.convertDate2Long(entity.getSendTimeEnd()));
			struc.setDeliverTime(entity.getReceiveTime()==null?DateUtils.convertDate2Long(entity.getSendTime()):DateUtils.convertDate2Long(entity.getReceiveTime()));
			struc.setDeliverUserId(entity.getUserId());
			struc.setRemindContent(entity.getContent());
			struc.setRemindName(entity.getName());
			struc.setRemindRepeatType(entity.getSendPeriod());
			struc.setRemindType(entity.getSendMethod());
			struc.setDurationTime(entity.getDurationTime()==null?0l:entity.getDurationTime());
			struc.setDeliverUserName(userName);
			return struc;
		}
		return null;
	}

	public static RemindListStruc convertEntityToRemindListStruc(EjlRemind entity,Long type) {
		if (entity != null) {
			RemindListStruc struc = new RemindListStruc();
			struc.setRemindId(entity.getId());
			struc.setDeliverDeadTime(DateUtils.convertDate2Long(entity.getSendTimeEnd()));
			
			struc.setRemindState(entity.getRemindState());
			struc.setRemindName(entity.getName());
			struc.setRemindType(entity.getSendMethod());
			struc.setType(type);
			struc.setTime(type==1l?DateUtils.convertDate2Long(entity.getCreateTime()):DateUtils.convertDate2Long(entity.getReceiveTime()));
			struc.setSendStatus(entity.getSentStatus());
			return struc;
		}
		return null;
	}

	public static UserBaseInfoStruc convertEntityToUserBaseInfoStruc(EjlUser entity) {
		if (entity != null) {
			UserBaseInfoStruc struc = new UserBaseInfoStruc();
			struc.setAge(entity.getAge() == null ? 0 : entity.getAge());
			struc.setHeadImgUrl(entity.getHeadImg() == null ? "" : entity.getHeadImg());
			struc.setNickName(entity.getNickName() == null ? "" : entity.getNickName());
			struc.setSex(entity.getSex() == null ? 0 : Long.valueOf(entity.getSex()));
			struc.setUserId(entity.getId());
			struc.setUserType(entity.getType());
			struc.setSignature(entity.getSignature() == null?"":entity.getSignature());
			struc.setFamilyId(entity.getFamilyId());
			struc.setPhoneNumber(entity.getPhone() == null?"":entity.getPhone());
			return struc;
		}
		return null;
	}

	public static EfDeviceOperation toDeviceOperation(Long userId,Long deviceId,DeviceOperationRequest req){
		EfDeviceOperation deviceOperation=new EfDeviceOperation();
		deviceOperation.setUserId(userId);
		deviceOperation.setDeviceId(deviceId);
		deviceOperation.setCode(req.getCode());
		deviceOperation.setStatus(req.getStatus());
		deviceOperation.setTime(req.getTime());
		return deviceOperation;
	}
}
