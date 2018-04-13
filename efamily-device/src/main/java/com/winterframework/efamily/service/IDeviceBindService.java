package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.device.DeviceBindConfirmRequest;

public interface IDeviceBindService{
	/**
	 * 手表绑定确认
	 * @param bizReq
	 * @throws Exception
	 */
	Integer bind(Context ctx,Long userId,Long deviceId,DeviceBindConfirmRequest bizReq) throws BizException;
	/**
	 * 绑表推送
	 * @param ctx
	 * @param userId
	 * @param status
	 * @throws BizException
	 */
	void pushBind(Context ctx, Long userId, int status) throws BizException;
}
