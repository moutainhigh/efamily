package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlRoleDao;
import com.winterframework.efamily.entity.EjlRole;

@Repository("ejlRoleDaoImpl")
public class EjlRoleDaoImpl extends EjlComRoleDaoImpl<EjlRole> implements IEjlRoleDao {
}
