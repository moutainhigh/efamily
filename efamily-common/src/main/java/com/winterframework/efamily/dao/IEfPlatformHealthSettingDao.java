package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfPlatformHealthSetting;

public interface IEfPlatformHealthSettingDao  extends IBaseDao<EfPlatformHealthSetting>{
	EfPlatformHealthSetting getByAgeLevel(Integer ageLevel) throws Exception;
}
