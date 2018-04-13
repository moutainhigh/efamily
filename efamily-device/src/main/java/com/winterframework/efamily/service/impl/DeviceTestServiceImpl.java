package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.dao.IDeviceTestDao;
import com.winterframework.efamily.entity.Test;
import com.winterframework.efamily.service.IDeviceTestService;
 

@Service("deviceTestServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceTestServiceImpl  extends TestServiceImpl implements IDeviceTestService{
	private static final Logger log= LoggerFactory.getLogger(DeviceTestServiceImpl.class);
	
	@Resource(name="deviceTestDaoImpl")
	private IDeviceTestDao deviceTestDao; 
	@Override
	public void test(Long userId) {
		Test t=new Test();
		t.setId(userId);
		//deviceTestDao.insert(t);
		deviceTestDao.test(userId);
	}
}
