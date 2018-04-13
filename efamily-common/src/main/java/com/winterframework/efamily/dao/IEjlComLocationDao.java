package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlLocation;

public interface IEjlComLocationDao extends IBaseDao<EjlLocation> {

	EjlLocation getUserLatestLocation(Long userId, Long watchId,Integer status,Date time);

	List<EjlLocation> getUserLocation(Long userId, Long watchId, Date time);
 
	public List<EjlLocation> getUserLocationLatelyCount(Long userId, Long watchId,Date time,Long locationCount);
	
	public List<EjlLocation> getUserLocationDesc(Long userId, Long watchId,Date time);
	
	public Integer updateStatus(Long locationId,Integer status );
	
	public EjlLocation getDeviceLocationLatest(String imei);
	EjlLocation getBySourceId(Long sourceId) throws BizException;
}
