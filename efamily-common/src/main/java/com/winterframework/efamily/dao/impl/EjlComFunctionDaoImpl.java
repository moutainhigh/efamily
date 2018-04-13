package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComFunctionDao;
import com.winterframework.efamily.entity.EjlFunction;

@Repository("ejlComFunctionDaoImpl")
public class EjlComFunctionDaoImpl<E extends EjlFunction> extends BaseDaoImpl<EjlFunction> implements IEjlComFunctionDao {
}
