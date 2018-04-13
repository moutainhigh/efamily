package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;

public interface IDeviceBloodService extends IComEjlHealthBloodPressureService{
	
	/**
	 * 保存设备心率数据
	 * @param userId
	 * @param deviceId
	 * @param heartList
	 * @return
	 * @throws BizException
	 */
	void save(Context ctx,Long userId,Long deviceId,List<EjlHealthBloodPressure> bloodList) throws BizException;
	
	/**
	 * 心跳更新调整推送
	 * @param userId
	 * @param familyId
	 * @param bizReqList
	 * @throws BizException
	 */
	public void notifyForHeartUpdate(Context ctx,Long userId,List<EjlHealthBloodPressure> bloodList) throws BizException;
}
