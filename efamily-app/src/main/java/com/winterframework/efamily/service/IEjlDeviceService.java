package com.winterframework.efamily.service;

import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareQueryResponse;
import com.winterframework.efamily.dto.app.AppDeviceSoftwareUpdateResponse;

public interface IEjlDeviceService extends IEjlComDeviceService{
	
	public Map<String, String> getBaseDeviceParamMapBy(Long id);
	
	
	AppDeviceSoftwareQueryResponse querySoftware(Context ctx,Long deviceId) throws BizException;
	AppDeviceSoftwareUpdateResponse updateSoftware(Context ctx,Long deviceId) throws BizException;
}
