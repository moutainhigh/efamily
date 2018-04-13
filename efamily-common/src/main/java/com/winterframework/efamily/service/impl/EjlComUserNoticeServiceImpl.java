package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;

import com.winterframework.efamily.dao.IEjlComUserNoticeDao;

import com.winterframework.efamily.entity.EjlUserNotice;

import com.winterframework.efamily.service.IEjlComUserNoticeService;

@Service("ejlComUserNoticeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserNoticeServiceImpl extends BaseServiceImpl<IEjlComUserNoticeDao,EjlUserNotice> implements IEjlComUserNoticeService{

	@Resource(name="ejlComUserNoticeDaoImpl")
	private IEjlComUserNoticeDao dao;
	@Override
	protected IEjlComUserNoticeDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
