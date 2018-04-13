package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlChartRoomUserDao;
import com.winterframework.efamily.entity.EjlChartRoomUser;
import com.winterframework.efamily.service.IEjlChartRoomUserService;

@Service("ejlChartRoomUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlChartRoomUserServiceImpl extends BaseServiceImpl<IEjlChartRoomUserDao,EjlChartRoomUser> implements IEjlChartRoomUserService {
 
	@Resource(name="ejlChartRoomUserDaoImpl")
	private IEjlChartRoomUserDao ejlChartRoomUserDaoImpl;
	@Override
	protected IEjlChartRoomUserDao getEntityDao() { 
		return ejlChartRoomUserDaoImpl;
	}
			
}
