package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlHealthSleep;

public interface IDeviceSleepService extends IEjlComHealthSleepService{
	
	/**
	 * 保存设备睡眠数据
	 * @param userId
	 * @param deviceId
	 * @param heartList
	 * @return
	 * @throws BizException
	 */
	void save(Context ctx,Long userId,Long deviceId,List<EjlHealthSleep> sleepList) throws BizException;
	
	
	public void notifyForSleepStart(Long userId,Long deviceId,int type) throws BizException;
}
