package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComMessageMarkDao;
import com.winterframework.efamily.entity.EjlMessageMark;

@Repository("ejlComMessageMarkDaoImpl")
public class EjlComMessageMarkDaoImpl<E extends EjlMessageMark> extends BaseDaoImpl<EjlMessageMark> implements IEjlComMessageMarkDao {
	
}