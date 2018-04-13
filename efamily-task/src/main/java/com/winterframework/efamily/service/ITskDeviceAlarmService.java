package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;


public interface ITskDeviceAlarmService extends IEfDeviceAlarmService {
	void doProcess() throws BizException;
}
