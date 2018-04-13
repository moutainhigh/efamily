package com.winterframework.efamily.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlDeviceDao;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareQueryResponse;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareUpdateResponse;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfPlatformDeviceVersionService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("ejlDeviceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlDeviceServiceImpl extends EjlComDeviceServiceImpl implements IEjlDeviceService {

	@Resource(name = "ejlDeviceDaoImpl")
	private IEjlDeviceDao ejlDeviceDaoImpl;

	@Resource(name = "efPlatformDeviceVersionServiceImpl")
	private IEfPlatformDeviceVersionService efPlatformDeviceVersionService;
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeService;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Override
	protected IEjlDeviceDao getEntityDao() {
		return ejlDeviceDaoImpl;
	}

	@Override
	public Map<String, String> getBaseDeviceParamMapBy(Long id) {
		return getEntityDao().getBaseDeviceParamMapBy(id);
	}
	
	@Override
	public AppDeviceSoftwareQueryResponse querySoftware(Context ctx,Long deviceId) throws BizException {
		EjlDevice device=get(deviceId);
		if(null==device){
			throw new BizException(StatusBizCode.DATA_NOT_EXIST.getValue());
		}
		Qrcode qrcode=qrcodeService.getByImei(device.getCode());
		if(null==qrcode){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		Integer deviceType=qrcode.getType();
		String deviceModel=qrcode.getModel();
		Integer customerNumber=qrcode.getCustomerNumber();
		String deviceVersion=device.getSoftwareVersion();
		
		AppDeviceSoftwareQueryResponse bizRes=new AppDeviceSoftwareQueryResponse();
		bizRes.setVersion(device.getSoftwareVersion());
		
		EfPlatformDeviceVersion platformDeviceVersion=efPlatformDeviceVersionService.getLatestByDeviceTypeAndDeviceModelAndCustomer(deviceType, deviceModel,customerNumber);
		if(null==platformDeviceVersion){
			throw new BizException(StatusBizCode.APP_DEVICE_PLATFORM_VERSION_MISSING.getValue());
		}
		if(!platformDeviceVersion.getOpenVersion().equals(deviceVersion)){
			bizRes.setHasNew(YesNo.YES.getValue());
		}else{
			bizRes.setHasNew(YesNo.NO.getValue());
		}
		return bizRes;
	}
	
	@Override
	public AppDeviceSoftwareUpdateResponse updateSoftware(Context ctx,Long deviceId)
			throws BizException {
		EjlDevice device=get(deviceId);
		if(null==device){
			throw new BizException(StatusBizCode.APP_DEVICE_UNBOUND.getValue());
		}
		if(null!=device.getSoftwareStatus() && device.getSoftwareStatus().intValue()==EjlDevice.SoftwareStatus.DOING.getValue()){
			throw new BizException(StatusBizCode.APP_DEVICE_SOFTWARE_ING.getValue());
		}
		Qrcode qrcode=qrcodeService.getByImei(device.getCode());
		if(null==qrcode){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		Integer deviceType=qrcode.getType();
		String deviceModel=qrcode.getModel();
		Integer customerNumber=qrcode.getCustomerNumber();
		
		EfPlatformDeviceVersion platformDeviceVersion=efPlatformDeviceVersionService.getLatestByDeviceTypeAndDeviceModelAndCustomer(deviceType, deviceModel,customerNumber);
		if(null==platformDeviceVersion || platformDeviceVersion.getOpenVersion().equals(device.getSoftwareVersion())){
			throw new BizException(StatusBizCode.APP_DEVICE_NO_NEW_VERSION.getValue());
		}
		AppDeviceSoftwareUpdateResponse bizRes=new AppDeviceSoftwareUpdateResponse();
		device.setSoftwareStatus(EjlDevice.SoftwareStatus.DOING.getValue());
		device.setSoftwareUpdatorId(ctx.getUserId());
		device.setSoftwareUpdateTime(DateUtils.currentDate());
		save(ctx, device);
		
		notificationUtil.notification(device.getUserId(), deviceId,20121, NotyMessage.Type.OPER, null, true);
		return bizRes;
	}
	
	

}