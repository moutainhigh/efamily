package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComRoleDao;
import com.winterframework.efamily.entity.EjlRole;

@Repository("ejlComRoleDaoImpl")
public class EjlComRoleDaoImpl<E extends EjlRole> extends BaseDaoImpl<EjlRole> implements IEjlComRoleDao {
}
