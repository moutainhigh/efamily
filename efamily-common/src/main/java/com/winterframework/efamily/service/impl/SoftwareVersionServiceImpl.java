package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.utils.StringUtils;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.ISoftwareVersionService;

@Service("softwareVersionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class SoftwareVersionServiceImpl  implements ISoftwareVersionService {
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Override
	public boolean gt(Long deviceId, String version) throws BizException {
		EjlDevice device=ejlComDeviceService.get(deviceId);
		if(null==device){
			throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
		}
		if(StringUtils.isEmpty(device.getSoftwareVersion())){
			return false;
		}
		return version.compareTo(device.getSoftwareVersion())<=0;
		
	}
	@Override
	public boolean gt(String imei, String version) throws BizException {
		EjlDevice device=ejlComDeviceService.getByImei(imei);
		if(null==device){
			throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
		}
		if(StringUtils.isEmpty(device.getSoftwareVersion())){
			return false;
		}
		return version.compareTo(device.getSoftwareVersion())>=0;
	}
	
}
