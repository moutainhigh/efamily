package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlRoleFunctionDao;
import com.winterframework.efamily.entity.EjlRoleFunction;

@Repository("ejlRoleFunctionDaoImpl")
public class EjlRoleFunctionDaoImpl extends EjlComRoleFunctionDaoImpl<EjlRoleFunction> implements IEjlRoleFunctionDao {
}
