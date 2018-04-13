package com.winterframework.efamily.service;


import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlUserDevice;

public interface IEjlComUserDeviceService extends IBaseService<EjlUserDevice>{
	/**
	 * 获取有效记录
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	EjlUserDevice getValidByUserId(Long userId) throws BizException;
	/**
	 * 功能：绑定设备时，确认以后修改相应状态等信息
	 * @param userId
	 * @param deviceId
	 * @throws BizException
	 */
	public void bindDeviceForConfirm(Context ctx,Long userId,Long deviceId) throws BizException;
	/**
	 * 功能：解除绑定设备时，确认以后修改相应状态等信息
	 * @param userId
	 * @param deviceId
	 * @throws BizException
	 */
	public void unbindDeviceForConfirm(Context ctx,Long userId,Long deviceId) throws BizException;


}
