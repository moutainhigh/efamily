package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IDeviceSittingService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("deviceOperationOnffStrategy")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationOnffStrategy extends AbstractDeviceOperationStrategy{
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;

	@Resource(name = "deviceSittingServiceImpl")
	private IDeviceSittingService deviceSittingService;
	@Resource(name = "ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	@Resource(name="ejlComMessageServiceImpl")
	private IEjlComMessageService ejlComMessageService;
	@Resource(name="propertyUtil")
	private PropertyUtil propertyUtil;
	@Resource(name="notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Override
	protected void doBiz(Context ctx, Integer code, Long time, Integer status)
			throws BizException {
		Long deviceId = ctx.getDeviceId();
		Long userId = ctx.getUserId(); 
		
		EjlDevice device=ejlComDeviceService.get(deviceId);
		if(null!=device){
			device.setOnlineStatus(status);
			if(status.intValue()==YesNo.NO.getValue()){
				device.setSleeplockStatus(YesNo.NO.getValue());
				deviceSittingService.saveSitSwitch(ctx, userId, deviceId, YesNo.NO.getValue());
			}
			ejlComDeviceService.save(ctx, device);
		} 
		
		try{
			EjlUser userWatch =  ejlComUserService.get(userId);
			String nickName = userWatch.getNickName()==null?userWatch.getPhone():userWatch.getNickName();
			String content = "";
			NoticeType noticeType = null;
			Map<String,String> data = new HashMap<String,String>();
			EjlDevice ejlDevice = new EjlDevice();
			ejlDevice.setId(deviceId);
			ejlDevice.setUpdateTime(DateUtils.convertLong2Date(time));
			
			if(status.intValue()==1){
				content = propertyUtil.getProperty("notice.watch.power.no").replace("@_0_@", nickName);
			}else{
				content = propertyUtil.getProperty("notice.watch.power.off").replace("@_0_@", nickName);
			} 
			data.put("userId",  userId+"");
			data.put("deviceId",  deviceId+"");
			data.put("type", status+"");
			data.put("nickName", nickName);
			data.put("time", time+"");
	        
			//*** 推送给相应用户【只推送给家庭中的APP用户】
			List<EjlUser> userList = ejlComUserService.getUserByFamilyAndType(userWatch.getFamilyId(), EfamilyConstant.USER_TYPE_APP);
			if(null!=userList){
				Map<String,String> data2 = new HashMap<String,String>();
				data2.put("noticeType", UserNotice.NoticeType.DEVICE_ONOFF.getValue()+"");
				data2.putAll(data);
				for(EjlUser user:userList){
					try{
						notificationUtil.notification(user.getId(), null, EfamilyConstant.PUSH, NotyMessage.Type.NOTICE, data2, false);
					}catch(Exception e){
						log.error("power onff push failed.userId="+userId);
					}
				}
			}
			log.info("userList : "+userList.toString());
			
			
	        //*** 新增一条消息发送到家庭聊天组   
			EjlMessage entity=new EjlMessage(); 
			entity.setSendUserId( userId);
			entity.setReceiveUserId(userWatch.getFamilyId());
			entity.setChatRoomId(userWatch.getFamilyId());
			entity.setContentType(Long.valueOf(EjlMessage.ContentType.TIPS.getCode())); 
			entity.setChatType(new Long(EjlMessage.ChatType.FML_GROUP.getValue()));
			entity.setContent(content);
			entity.setContentTime(0L);
			entity.setAppSendTime(Long.valueOf(DateUtils.format(new Date(),"yyyyMMddHHmmssSSS")));
			entity.setStatus(0);
			ejlComMessageService.save(ctx,entity); 
		}catch(Exception e){
			log.error("error occurs when device onff.deviceId="+deviceId,e);
		}
		
	}
	 
}
