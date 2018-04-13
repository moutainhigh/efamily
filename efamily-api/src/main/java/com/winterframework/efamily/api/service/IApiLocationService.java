package com.winterframework.efamily.api.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.service.IEjlComLocationService;

public interface IApiLocationService extends IEjlComLocationService{

	/**
	 * 获取后续的数据（按设备和业务时间）
	 * @param ctx
	 * @param imei
	 * @param bizTime
	 * @return
	 * @throws BizException
	 */
	List<EjlLocation> getListAfterByImeiAndTime(Context ctx,String imei,Date bizTime) throws BizException;
	
	
	
	/**
	 * 获取时间范围内的数据（按设备和业务时间）
	 * @param deviceId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BizException
	 */
	List<EjlLocation> getListBetweenByDeviceIdAndTime(Context ctx,String imei,Date startTime, Date endTime) throws BizException;
}
