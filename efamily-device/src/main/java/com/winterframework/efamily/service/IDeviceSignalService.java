package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.DeviceSignalRecord;

public interface IDeviceSignalService extends IDeviceSignalRecordService{
	
	 
	/**
	 * 保存设备信号数据
	 * @param userId
	 * @param deviceId
	 * @param signalList
	 * @return
	 * @throws BizException
	 */
	void save(Context ctx,Long userId,Long deviceId,List<DeviceSignalRecord> signalList) throws BizException;
}
