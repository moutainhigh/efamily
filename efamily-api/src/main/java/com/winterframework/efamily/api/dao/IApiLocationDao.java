package com.winterframework.efamily.api.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.entity.EjlLocation;

public interface IApiLocationDao extends IEjlComLocationDao {

	/**
	 * 获取后续的数据（按设备和业务时间）
	 * @param deviceId
	 * @param bizTime
	 * @return
	 * @throws BizException
	 */
	List<EjlLocation> getListAfterByDeviceIdAndTime(Long deviceId,Date bizTime,Long userId) throws Exception;
	
	
	/**
	 * 获取时间范围内的数据（按设备和业务时间）
	 * @param deviceId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BizException
	 */
	List<EjlLocation> getListBetweenByDeviceIdAndTime(Long deviceId,Date startTime,Date endTime,Long userId) throws Exception;
 
}
