package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.entity.EjlMessage;

public interface IDeviceChatService{
	
	/**
	 * 上传设备聊天设置
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	void settingUpload(Context ctx,DeviceChatSettingRequest bizReq) throws BizException;
	/**
	 * 接收设备聊天消息
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	void receive(Context ctx,Long userId,Long deviceId,EjlMessage message) throws BizException;
}
