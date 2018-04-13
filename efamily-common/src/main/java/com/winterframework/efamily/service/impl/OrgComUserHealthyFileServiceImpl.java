package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComUserHealthyFileDao;
import com.winterframework.efamily.entity.OrgUserHealthyFile;
import com.winterframework.efamily.service.IOrgComUserHealthyFileService;


@Service("orgComUserHealthyFileServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComUserHealthyFileServiceImpl  extends BaseServiceImpl<IOrgComUserHealthyFileDao,OrgUserHealthyFile> implements IOrgComUserHealthyFileService {
	@Resource(name="orgComUserHealthyFileDaoImpl")
	private IOrgComUserHealthyFileDao dao;
	@Override
	protected IOrgComUserHealthyFileDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
