package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfDeviceAlarmTarget;

public interface IEfDeviceAlarmTargetService extends IBaseService<EfDeviceAlarmTarget> { 
	List<EfDeviceAlarmTarget> getByStatus(Integer status) throws BizException;
}
