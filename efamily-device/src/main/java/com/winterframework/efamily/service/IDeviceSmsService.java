package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.DeviceSignalRecord;

public interface IDeviceSmsService{
	
	void connect(Long userId,Long deviceId,List<DeviceSignalRecord> signalList) throws BizException;
}
