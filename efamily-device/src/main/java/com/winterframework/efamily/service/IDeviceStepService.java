package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.entity.EjlHealthStepCount;

public interface IDeviceStepService{
	
	/**
	 * 保存设备心率数据
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	StepResponse save(Context ctx,Long userId,Long deviceId,List<EjlHealthStepCount> recordList) throws BizException;
}
