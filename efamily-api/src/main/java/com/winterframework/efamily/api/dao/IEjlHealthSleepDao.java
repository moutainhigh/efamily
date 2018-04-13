package com.winterframework.efamily.api.dao;

import java.util.List;

import com.winterframework.efamily.dao.IEJLComHealthSleepDao;
import com.winterframework.efamily.entity.EjlHealthSleep;

public interface IEjlHealthSleepDao extends IEJLComHealthSleepDao{

	public List<EjlHealthSleep> getSleepsByTime(EjlHealthSleep ejlHealthSleep) throws Exception;
}
