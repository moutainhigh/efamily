package com.winterframework.efamily.dao;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfCustomerAlarmSetting;

public interface IEfCustomerAlarmSettingDao  extends IBaseDao<EfCustomerAlarmSetting>{ 
	EfCustomerAlarmSetting getByCustomerIdAndAlarmType(Long customerId,Integer alarmType) throws BizException;
}
