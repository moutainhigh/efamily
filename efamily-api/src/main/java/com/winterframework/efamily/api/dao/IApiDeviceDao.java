package com.winterframework.efamily.api.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;

public interface IApiDeviceDao extends IEjlComDeviceDao {

	/**
	 * 获取绑定/解绑设备列表（按客户ID 状态  时间）
	 * @param customerId
	 * @param status
	 * @param time
	 * @return
	 * @throws Exception
	 */
	List<EjlDevice> getListByCustomerIdAndStatusAndTime(Long customerId,Integer status,Date time) throws Exception;
 
}
