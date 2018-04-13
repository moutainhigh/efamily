package com.winterframework.efamily.dao.impl;



import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;

import com.winterframework.efamily.dao.IEjlComParamConfigDao;
import com.winterframework.efamily.entity.DeviceParamConfig;


@Repository("eJlComParamConfigDaoImpl")
public class EJlComParamConfigDaoImpl<E extends DeviceParamConfig> extends BaseDaoImpl<DeviceParamConfig> implements IEjlComParamConfigDao{
	
}
