package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComMessageDao;
import com.winterframework.efamily.entity.EjlMessage;

@Repository("ejlComMessageDaoImpl")
public class EjlComMessageDaoImpl<E extends EjlMessage> extends BaseDaoImpl<EjlMessage> implements IEjlComMessageDao {
}
