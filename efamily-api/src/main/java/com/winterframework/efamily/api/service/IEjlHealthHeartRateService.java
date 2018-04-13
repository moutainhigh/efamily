package com.winterframework.efamily.api.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.IEjlComHealthHeartRateService;

public interface IEjlHealthHeartRateService extends IEjlComHealthHeartRateService{
	public List<EjlHealthHeartRate> getHealthHeartRateByTime(String imei,Long fromTime,Long toTime) throws BizException;
	
	public EjlHealthHeartRate getLastUserHeartRate(String imei) throws BizException;;
}
