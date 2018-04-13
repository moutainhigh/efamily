package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.DeviceLocationSatellite;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.service.IDeviceLocationSatelliteService;
import com.winterframework.efamily.service.IDeviceLocationService;
import com.winterframework.efamily.service.IEfLocationWifiService;
 

@Service("deviceLocationServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceLocationServiceImpl  extends EjlComLocationServiceImpl  implements IDeviceLocationService{
	@Resource(name="deviceLocationSatelliteServiceImpl")
	private IDeviceLocationSatelliteService deviceLocationSatelliteService;
	@Resource(name="efLocationWifiServiceImpl")
	private IEfLocationWifiService efLocationWifiService;
	
	@Override
	public void save(Context ctx,Long userId,Long deviceId,List<EjlLocation> locationList) throws BizException {
		/**
		 * 1.insert定位数据表
		 */		 
		log.debug("welcome to location service.");  
		if(null!=locationList){
			save(ctx,locationList);
		}
	}
	@Override
	public void wifiUpload(Context ctx,List<EfLocationWifi> locationList) throws BizException {
		efLocationWifiService.save(ctx,locationList);
	}
	@Override
	public void uploadSatellite(Context ctx,
			List<DeviceLocationSatellite> locationSateList) throws BizException {
		if(null!=locationSateList){
			deviceLocationSatelliteService.save(ctx,locationSateList);
		}
	}
	
	
}
