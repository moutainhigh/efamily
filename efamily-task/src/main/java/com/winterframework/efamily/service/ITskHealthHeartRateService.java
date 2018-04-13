package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

public interface ITskHealthHeartRateService extends IEjlComHealthHeartRateService{
	/**
	 * 根据上传时间获取最新的设备信息
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getLastestUserIdDeviceIdListByCreateTime(Date fromTime,Date toTime) throws BizException;
	
	/**
	 * 指定时间区间获取用户异常的心率列表
	 * @param userId
	 * @param deviceId
	 * @param low
	 * @param high
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws BizException
	 */
	List<EjlHealthHeartRate> getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(Long userId,Long deviceId,Integer low,Integer high,Date fromTime,Date toTime) throws BizException;
}
