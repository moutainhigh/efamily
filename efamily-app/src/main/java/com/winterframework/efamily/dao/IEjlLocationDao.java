package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.entity.EjlLocation;

public interface IEjlLocationDao extends IEjlComLocationDao {

	List<EjlLocation> getUserLocation(Long userId, Long watchId, Date time);
	
	public List<EjlLocation> getUserLocationBetweenTime(Long userId,
			Long watchId, Date beginTime, Date endTime);
 

}
