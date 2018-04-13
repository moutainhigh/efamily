package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComDeviceModuleDao;
import com.winterframework.efamily.entity.EfDeviceModule;
@Repository("efComDeviceModuleDaoImpl")
public class EfComDeviceModuleDaoImpl<E extends EfDeviceModule> extends BaseDaoImpl<EfDeviceModule> implements IEfComDeviceModuleDao{

}
