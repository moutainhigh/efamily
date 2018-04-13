package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlDeviceAddressList;

public interface IDeviceContactsService extends IEjlComDeviceAddressListService{
	
	/**
	 * 上传设备通讯录
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	void upload(Context ctx,List<EjlDeviceAddressList> cttList) throws BizException;
}
