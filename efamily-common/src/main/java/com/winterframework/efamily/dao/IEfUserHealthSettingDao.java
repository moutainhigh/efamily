package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfUserHealthSetting;

public interface IEfUserHealthSettingDao  extends IBaseDao<EfUserHealthSetting>{
	EfUserHealthSetting getByUserId(Long userId) throws Exception;
}
