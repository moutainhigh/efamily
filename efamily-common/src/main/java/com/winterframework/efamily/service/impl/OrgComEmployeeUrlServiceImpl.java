package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComEmployeeUrlDao;
import com.winterframework.efamily.entity.OrgEmployeeUrl;
import com.winterframework.efamily.service.IOrgComEmployeeUrlService;



@Service("orgComEmployeeUrlServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComEmployeeUrlServiceImpl  extends BaseServiceImpl<IOrgComEmployeeUrlDao,OrgEmployeeUrl> implements IOrgComEmployeeUrlService {
	@Resource(name="orgComEmployeeUrlDaoImpl")
	private IOrgComEmployeeUrlDao dao;
	@Override
	protected IOrgComEmployeeUrlDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
