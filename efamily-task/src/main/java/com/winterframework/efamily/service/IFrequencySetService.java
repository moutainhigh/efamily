package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.DeviceSignalRecord;

public interface IFrequencySetService {
	public void batterySet(DeviceBatteryRecord battery) throws BizException;
	
	public void signalSet(DeviceSignalRecord signal) throws BizException;
}
