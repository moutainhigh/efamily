package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IHelloDao;
import com.winterframework.efamily.entity.DeviceHello;
import com.winterframework.efamily.service.IHelloService;

@Service("helloServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class HelloServiceImpl  extends BaseServiceImpl<IHelloDao,DeviceHello> implements IHelloService {
	@Resource(name="helloDaoImpl")
	private IHelloDao helloDao;
	@Override
	protected IHelloDao getEntityDao() {
		// TODO Auto-generated method stub
		return helloDao;
	}
}
