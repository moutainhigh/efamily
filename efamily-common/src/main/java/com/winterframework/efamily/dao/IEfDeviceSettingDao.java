package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfDeviceSetting;

public interface IEfDeviceSettingDao  extends IBaseDao<EfDeviceSetting>{ 
	
	EfDeviceSetting getByUserIdAndDeviceId(Long userId,Long deviceId) throws Exception;
}
