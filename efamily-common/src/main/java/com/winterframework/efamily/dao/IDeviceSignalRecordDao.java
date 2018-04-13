package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceSignalRecord;

public interface IDeviceSignalRecordDao  extends IBaseDao<DeviceSignalRecord>{ 
	List<DeviceSignalRecord> getByDeviceId(Long deviceId) throws BizException;
	int deleteByDeviceId(Long deviceId) throws BizException;
}
