package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IHelloDao;
import com.winterframework.efamily.entity.DeviceHello;
 
@Repository("helloDaoImpl")
public class HelloDaoImpl<E extends DeviceHello> extends BaseDaoImpl<DeviceHello> implements IHelloDao{ 
	
}
