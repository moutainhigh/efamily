package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlFunctionDao;
import com.winterframework.efamily.entity.EjlFunction;

@Repository("ejlFunctionDaoImpl")
public class EjlFunctionDaoImpl extends EjlComFunctionDaoImpl<EjlFunction> implements IEjlFunctionDao {
}
