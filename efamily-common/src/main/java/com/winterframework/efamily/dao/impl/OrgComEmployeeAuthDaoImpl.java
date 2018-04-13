package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComEmployeeAuthDao;
import com.winterframework.efamily.entity.OrgEmployeeAuth;

@Repository("orgComEmployeeAuthDaoImpl")
public class OrgComEmployeeAuthDaoImpl<E extends OrgEmployeeAuth> extends BaseDaoImpl<OrgEmployeeAuth> implements IOrgComEmployeeAuthDao{


}
