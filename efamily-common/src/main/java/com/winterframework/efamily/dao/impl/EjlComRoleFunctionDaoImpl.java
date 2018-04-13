package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComRoleFunctionDao;
import com.winterframework.efamily.entity.EjlRoleFunction;

@Repository("ejlComRoleFunctionDaoImpl")
public class EjlComRoleFunctionDaoImpl<E extends EjlRoleFunction> extends BaseDaoImpl<EjlRoleFunction> implements IEjlComRoleFunctionDao {
}
