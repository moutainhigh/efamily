package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;


public interface ITskDeviceAlarmTargetService extends IEfDeviceAlarmTargetService {
	void doProcess() throws BizException;
}
