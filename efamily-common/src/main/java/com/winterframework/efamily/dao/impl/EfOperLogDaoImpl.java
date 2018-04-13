package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfOperLogDao;
import com.winterframework.efamily.entity.EfOperLog;
 
@Repository("efOperLogDaoImpl")
public class EfOperLogDaoImpl<E extends EfOperLog> extends BaseDaoImpl<EfOperLog> implements IEfOperLogDao{

}
