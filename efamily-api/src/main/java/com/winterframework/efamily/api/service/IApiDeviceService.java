package com.winterframework.efamily.api.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEjlComDeviceService;

public interface IApiDeviceService extends IEjlComDeviceService{

	
	/**
	 * 获取绑定/解绑设备列表（按客户ID 状态  时间）
	 * @param ctx
	 * @param customerId
	 * @param status
	 * @param time
	 * @return
	 * @throws BizException
	 */
	List<EjlDevice> getListByCustomerIdAndStatusAndTime(Context ctx,Long customerId,Integer status,Date time) throws BizException;
}
