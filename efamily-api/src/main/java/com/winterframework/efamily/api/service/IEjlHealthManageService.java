package com.winterframework.efamily.api.service;

import java.util.List;

import com.winterframework.efamily.api.dto.HealthyMonitorDateSleepResponse;

public interface IEjlHealthManageService {
	public List<HealthyMonitorDateSleepResponse> getMonitorDataSleepById(String imei,Long fromTime,Long toTime) throws Exception;
	
	public HealthyMonitorDateSleepResponse getLastSleepBetweenTime(String imei) throws Exception;
}
