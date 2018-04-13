package com.winterframework.efamily.api.service;

import java.util.List;

import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.service.IEjlComHealthStepCountService;

public interface IEjlHealthStepCountService extends IEjlComHealthStepCountService{
	public List<EjlHealthStepCount> getHealthStepCountByTime(String imei,Long fromTime,Long endTime) throws Exception;
	
	public EjlHealthStepCount getLastHealthStepCountByTime(String imei) throws Exception;
}
