package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.DeviceLocationSatellite;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.entity.EjlLocation;

public interface IDeviceLocationService extends IEjlComLocationService{
 
	/**
	 * 保存设备定位数据
	 * @param userId
	 * @param deviceId
	 * @param locationList
	 * @return
	 * @throws BizException
	 */
	void save(Context ctx,Long userId,Long deviceId,List<EjlLocation> locationList) throws BizException;
	/**
	 * WIFI定位数据上传
	 * @param userId
	 * @param deviceId
	 * @param locationList
	 * @throws BizException
	 */
	void wifiUpload(Context ctx,List<EfLocationWifi> locationList) throws BizException;
	/**
	 * 上传定位卫星数
	 * @param ctx
	 * @param locationSateList
	 * @throws BizException
	 */
	void uploadSatellite(Context ctx,List<DeviceLocationSatellite> locationSateList) throws BizException;
}
