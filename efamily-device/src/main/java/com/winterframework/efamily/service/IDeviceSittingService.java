package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.entity.EjlHealthSitting;

public interface IDeviceSittingService{
	
	/**
	 * 保存设备久坐数据
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	StepResponse save(Context ctx,Long userId,Long deviceId,List<EjlHealthSitting> recordList) throws BizException;
	
	public void saveSitSwitch(Context ctx,Long userId,Long deviceId,Integer sitSwitch) throws BizException;
}
