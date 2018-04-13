package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComRoleUrlDao;
import com.winterframework.efamily.entity.OrgRoleUrl;
import com.winterframework.efamily.service.IOrgComRoleUrlService;


@Service("orgComRoleUrlServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComRoleUrlServiceImpl  extends BaseServiceImpl<IOrgComRoleUrlDao,OrgRoleUrl> implements IOrgComRoleUrlService {
	@Resource(name="orgComRoleUrlDaoImpl")
	private IOrgComRoleUrlDao dao;
	@Override
	protected IOrgComRoleUrlDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
