package com.winterframework.efamily.api.dao;

import java.util.List;

import com.winterframework.efamily.dao.IEjlComHealthStepCountDao;
import com.winterframework.efamily.entity.EjlHealthStepCount;

public interface IEjlHealthStepCountDao extends IEjlComHealthStepCountDao{
	public List<EjlHealthStepCount> getHealthStepCountByTime(Long  deviceId,Long fromTime, Long endTime,Long userId) throws Exception;
	
	public EjlHealthStepCount getLastHealthStepCountByTime(Long deviceId,Long userId) throws Exception;
}
