package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlDevice;

public interface IEjlComDeviceService extends IBaseService<EjlDevice>{
	EjlDevice getByImei(String imei) throws BizException;
	List<EjlDevice> getByType(Integer type) throws BizException;
	/**
	 * 更新软件版本号
	 * @param userId
	 * @param deviceId
	 * @param version
	 * @throws BizException
	 */
	void updateSoftwareVersion(Context ctx,Long userId,Long deviceId,String version) throws BizException;
	/**
	 * 功能：绑定确认时，绑定失败的处理
	 * @param ctx
	 * @param userId
	 * @param deviceId
	 * @throws BizException
	 */
	public void bindDeviceFailForConfirm(Context ctx,Long userId,Long deviceId)throws BizException;

	///////////////////////基本行为方法//////////////////////////////
	/**
	 * 绑定
	 * @throws BizException
	 */
	void bind() throws BizException;
	/**
	 * 解绑
	 * @throws BizException
	 */
	void unbind() throws BizException;
	
	public List<EjlDevice> getDeviceByTypeAndStatus(Integer type,Integer status) throws BizException;
}
