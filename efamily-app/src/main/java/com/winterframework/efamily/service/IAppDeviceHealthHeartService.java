package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

public interface IAppDeviceHealthHeartService extends IEjlComHealthHeartRateService{
	
	/**
	 * 设备心率测量开始
	 * @param userId
	 * @param deviceId
	 * @param heartList
	 * @return
	 * @throws BizException
	 */
	void start(Context ctx,Long userId,Long deviceId) throws BizException;
	
	/**
	 * 设备心率测量结束
	 * @param ctx
	 * @param userId
	 * @param deviceId
	 * @throws BizException
	 */
	void stop(Context ctx,Long userId,Long deviceId) throws BizException;
	
	/**
	 * 查询最新心率数据
	 * @param ctx
	 * @param userId
	 * @param deviceId
	 * @return
	 * @throws BizException
	 */
	EjlHealthHeartRate  queryLatest(Context ctx,Long userId,Long deviceId) throws BizException;
}
