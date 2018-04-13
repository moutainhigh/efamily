package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfCustomerAlarmSetting;

public interface IEfCustomerAlarmSettingService extends IBaseService<EfCustomerAlarmSetting> {
	EfCustomerAlarmSetting getByCustomerIdAndAlarmType(Long customerId,Integer alarmType) throws BizException;
	EfCustomerAlarmSetting getValidByCustomerIdAndAlarmType(Long customerId,Integer alarmType) throws BizException;
}
