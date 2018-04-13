package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;

public interface IDeviceQrcodeService extends IQrcodeService{ 
	int upload(Context ctx,String imei,String imsi,String model,Integer type,Integer simStatus) throws BizException;
	
	public String getQrcodeUrl(String imei) throws BizException;
	
	public int updateQrcodeResourceIdBy(Context ctx,String imei,String resourceId) throws BizException;
}
