package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.GetUserLocationResponse;
import com.winterframework.efamily.dto.LocationStruc;


public interface IEjlLocationService extends IEjlComLocationService {
	
	
	/**
	 * 
	 * @param userId
	 * @param deviceId
	 * @param 请求时间
	 * @return
	 * @throws Exception
	 */
	public GetUserLocationResponse getUserLastLocation(Context ctx,Long userId, Long deviceId,Long time) throws Exception;
	/**
	 * @param userId
	 * @param watchId
	 * @param recentHourType // 查询多少小时内的轨迹
	 * @param queryType 1 定位 2 轨迹
	 * @return
	 */
	public List<LocationStruc> getUserLocation(Long userId, Long watchId, Long recentHourType, Long queryType) throws Exception;
	
	
	
	/**
	 * @param userId
	 * @param watchId
	 * @param recentHourType // 查询多少小时内的轨迹
	 * @param queryType 1 定位 2 轨迹
	 * @return
	 */
	public List<LocationStruc> getUserLocationBetweenTime(Long userId, Long watchId, Date beginTime, Date endTime);
	
}
