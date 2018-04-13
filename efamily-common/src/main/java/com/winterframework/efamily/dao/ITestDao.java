package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.Test;

public interface ITestDao  extends IBaseDao<Test>{ 
	void test(Long userId);
}
