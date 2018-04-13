package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComUrlDao;
import com.winterframework.efamily.entity.OrgUrl;
import com.winterframework.efamily.service.IOrgComUrlService;


@Service("orgComUrlServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComUrlServiceImpl  extends BaseServiceImpl<IOrgComUrlDao,OrgUrl> implements IOrgComUrlService {
	@Resource(name="orgComUrlDaoImpl")
	private IOrgComUrlDao dao;
	@Override
	protected IOrgComUrlDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
