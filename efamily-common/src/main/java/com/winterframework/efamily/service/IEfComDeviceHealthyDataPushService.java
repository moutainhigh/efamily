package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfDeviceHealthyDataPush;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

public interface IEfComDeviceHealthyDataPushService  extends IBaseService<EfDeviceHealthyDataPush> {

	public List<EfDeviceHealthyDataPush> getDeviceHealthyDataPushList(Date fromTime, Date toTime, int status) throws Exception;
	
	public void saveBloodPressureToDataPush(Long userId,Long deviceId,String imei,List<EjlHealthBloodPressure> bloodList) throws BizException;
	
	public void saveHeartToDataPush(Long userId,Long deviceId,String imei,List<EjlHealthHeartRate> heartList) throws BizException;
}
