package com.winterframework.efamily.dao;


import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;

import com.winterframework.efamily.entity.EjlHealthSleep;

public interface IEJLComHealthSleepDao  extends IBaseDao<EjlHealthSleep>{ 
	public EjlHealthSleep getLastSleepByAttribute(EjlHealthSleep ejlHealthSleep) throws BizException;
	public List<EjlHealthSleep> getSleepsByAttribute(EjlHealthSleep ejlHealthSleep) throws BizException;
}
