package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.HeartRequest;
import com.winterframework.efamily.dto.device.DeviceHeartFinishRequest;
import com.winterframework.efamily.dto.device.DeviceHeartStartRequest;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

public interface IDeviceHeartService extends IEjlComHealthHeartRateService{
	
	/**
	 * 保存设备心率数据
	 * @param userId
	 * @param deviceId
	 * @param heartList
	 * @return
	 * @throws BizException
	 */
	void save(Context ctx,Long userId,Long deviceId,List<EjlHealthHeartRate> heartList) throws BizException;
	/**
	 * 测量开始
	 * @param ctx
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @throws BizException
	 */
	void start(Context ctx,Long userId,Long deviceId,DeviceHeartStartRequest bizReq) throws BizException;
	/**
	 * 测量完成
	 * @param ctx
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @throws BizException
	 */
	void finish(Context ctx,Long userId,Long deviceId,DeviceHeartFinishRequest bizReq) throws BizException;
	/**
	 * 心跳更新调整推送
	 * @param userId
	 * @param familyId
	 * @param bizReqList
	 * @throws BizException
	 */
	public void notifyForHeartUpdate(Context ctx,Long userId,List<HeartRequest> bizReqList) throws BizException;
}
