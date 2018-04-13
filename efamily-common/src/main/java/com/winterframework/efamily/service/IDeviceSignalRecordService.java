package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.DeviceSignalRecord;

public interface IDeviceSignalRecordService extends IBaseService<DeviceSignalRecord> {
	List<DeviceSignalRecord> queryByDeviceId(Long deviceId) throws BizException;
	int removeByDeviceId(Long deviceId) throws BizException;
}
