package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfPlatformDeviceVersionService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("deviceOperationSoftwareStrategy")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationSoftwareStrategy extends AbstractDeviceOperationStrategy { 
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService; 
	@Resource(name = "efPlatformDeviceVersionServiceImpl")
	private IEfPlatformDeviceVersionService efPlatformDeviceVersionService;
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeService;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Override
	protected void doBiz(Context ctx, Integer code, Long time, Integer status)
			throws BizException {
		if(null!=status){
			Long deviceId=ctx.getDeviceId();
			EjlDevice device=ejlComDeviceService.get(deviceId);
			if(null==device){
				throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
			}
			Long appUserId=device.getSoftwareUpdatorId();
			String version="";
			Integer resultCode;
			if(status.intValue()==YesNo.YES.getValue()){
				resultCode=YesNo.NO.getValue();	//成功
				Qrcode qrcode=qrcodeService.getByImei(device.getCode());
				if(null==qrcode){
					throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
				}
				Integer deviceType=qrcode.getType();
				String deviceModel=qrcode.getModel();
				Integer customerNumber=qrcode.getCustomerNumber();
				EfPlatformDeviceVersion platformDeviceVersion=efPlatformDeviceVersionService.getLatestByDeviceTypeAndDeviceModelAndCustomer(deviceType,deviceModel,customerNumber);
				if(null==platformDeviceVersion){
					throw new BizException(StatusBizCode.APP_DEVICE_PLATFORM_VERSION_MISSING.getValue());
				}
				version=platformDeviceVersion.getOpenVersion();
				device.setSoftwareStatus(EjlDevice.SoftwareStatus.SUCCESS.getValue());
				device.setSoftwareVersion(version);
				device.setSoftwareFinishTime(DateUtils.currentDate());
			}else{
				resultCode=status;
				device.setSoftwareStatus(EjlDevice.SoftwareStatus.FAILED.getValue());
				device.setSoftwareFinishTime(DateUtils.currentDate());
			}
			ejlComDeviceService.save(ctx, device);
			
			try{
				String deviceNickName=null==ctx.get("nickName")?"":(String)ctx.get("nickName");
				Map<String,String> data=new HashMap<String,String>(); 
				data.put("noticeType", UserNotice.NoticeType.APP_DEVICE_SOFTWARE_UPDATE_PUSH.getValue()+""); 
				data.put("deviceId", deviceId+"");
				data.put("nickName", deviceNickName);
				data.put("resultCode", resultCode+"");
				data.put("version", version); 
				notificationUtil.notification(appUserId, null, EfamilyConstant.PUSH, NotyMessage.Type.NOTICE, data, false);
			}catch(Exception e){
				log.error("notification of software upgrade failed. deviceId="+deviceId+" appUserId="+appUserId+" resultCode="+status,e);
			}
		}
	}

}
