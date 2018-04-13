package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlLocation;

public interface ITskLocationService extends IEjlComLocationService{
	/**
	 * 根据用户获取时间点之后的定位数据
	 * @param userId
	 * @param deviceId
	 * @param time
	 * @return
	 * @throws BizException
	 */
	List<EjlLocation> getListByUserIdDeviceIdAfterTime(Long userId,Long deviceId,Date time) throws BizException;
	
}
