package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.entity.EjlDeviceMonitor;

public interface IDeviceMonitorService{
	
	/**
	 * 保存用户设备监控数据
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	StepResponse saveOrUpdate(Context ctx,Long userId,Long deviceId,EjlDeviceMonitor record) throws BizException;
}
