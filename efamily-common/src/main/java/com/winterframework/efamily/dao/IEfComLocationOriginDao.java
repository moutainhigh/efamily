package com.winterframework.efamily.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfLocationOrigin;

public interface IEfComLocationOriginDao extends IBaseDao<EfLocationOrigin> {
	public Integer updateStatus(Long id,Integer status) throws Exception;
	
	public List<EfLocationOrigin> getLocationOriginByDevice(Long userId,Long deviceId) throws Exception;
	
	public List<EfLocationOrigin> getLocationOriginByDevice(Long userId,Long deviceId,Date beginTime ,Date endTime) throws Exception;
	
	public List<Map<String,Long>> getLocationOriginDeviceId() throws Exception;
	
	public List<EfLocationOrigin> getLocationOriginByTime(Date beginTime, Date endTime,Long deviceId) throws Exception;
	
	public List<Map<String,Long>> getLocationOriginDeviceId(Date beginTime,Date endTime) throws Exception;

}
