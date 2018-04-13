package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.dto.device.DeviceBindConfirmRequest;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceBindService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("deviceBindServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceBindServiceImpl implements IDeviceBindService{
	private static final Logger log= LoggerFactory.getLogger(DeviceBindServiceImpl.class);
	
	@Resource(name="ejlComUserDeviceServiceImpl")
	private IEjlComUserDeviceService ejlComUserDeviceService;
	@Resource(name="ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserService;
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	@Resource(name="notificationUtil")
	private NotificationUtil notificationUtil; 
	
	@Override
	public Integer bind(Context ctx,Long userId,Long deviceId,DeviceBindConfirmRequest bizReq) throws BizException {
		//app绑定时创建新手表用户（有无），redis.set绑定请求
		//Long userId=bizReq.getUserId();
		Long toId=bizReq.getToId();
		int status=bizReq.getStatus();
		
		if(YesNo.YES.getValue()==status){
			ejlComUserDeviceService.bindDeviceForConfirm(ctx,userId, deviceId);
		}else{
			ejlComUserDeviceService.unbindDeviceForConfirm(ctx,userId, deviceId);
		}	
		return status; 
	}

	public void pushBind(Context ctx, Long userId, int status) throws BizException {
		//推送所有家庭成员
		EjlFamilyUser userFamily=ejlComFamilyUserService.getEffectiveByUserId(userId);
		if(null==userFamily){
			throw new BizException(StatusBizCode.DEVICE_USER_NO_FAMILY.getValue());
		}
		String deviceUserName=null==ctx.get("nickName")?null:(String)ctx.get("nickName");
		Long familyId=userFamily.getFamilyId();
		List<EjlFamilyUser> familyUserList= ejlComFamilyUserService.getEffectiveByFamilyId(familyId);
		if(null!=familyUserList){
			for(EjlFamilyUser familyUser:familyUserList){
				EjlUser user=ejlComUserService.get(familyUser.getUserId()); 
				try {
					NotyTarget t=new NotyTarget(familyUser.getUserId(),null);  
					Map<String,String> data=new HashMap<String,String>(); 
					String noticeType=(YesNo.YES.getValue()==status?UserNotice.NoticeType.BIND_ACCEPT.getValue():UserNotice.NoticeType.BIND_REFUSED.getValue())+"";
					data.put("noticeType", noticeType); 
					data.put("deviceUserName", deviceUserName);
					data.put("userName", user.getNickName());
					data.put("oprTime", DateUtils.getCurTime()+""); 
					
					NotyMessage message=new NotyMessage();
					message.setId(null);
					message.setType(NotyMessage.Type.NOTICE);
					message.setCommand(EfamilyConstant.PUSH);
					message.setData(data);
					Notification notification=new Notification();
					notification.setTarget(t);
					notification.setMessage(message);
					 
					notificationUtil.notification(notification);
				} catch (Exception e) {
					log.error("push error:",e);
				}
			}
		}
	}
}
