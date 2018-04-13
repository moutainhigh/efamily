package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.DeviceBatteryRecord;

public interface IDeviceBatteryRecordService extends IBaseService<DeviceBatteryRecord> {
	List<DeviceBatteryRecord> queryByDeviceId(Long deviceId) throws BizException;
	int removeByDeviceId(Long deviceId) throws BizException;
	DeviceBatteryRecord getLastDeviceBatteryRecordByDeviceId(Long deviceId,Long time) throws BizException;
}