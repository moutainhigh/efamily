package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComMessageDao;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.service.IEjlComMessageService;

@Service("ejlComMessageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComMessageServiceImpl extends BaseServiceImpl<IEjlComMessageDao, EjlMessage> implements IEjlComMessageService {

	@Resource(name = "ejlComMessageDaoImpl")
	private IEjlComMessageDao ejlComMessageDaoImpl;

	@Override
	protected IEjlComMessageDao getEntityDao() {
		return ejlComMessageDaoImpl;
	}


}
