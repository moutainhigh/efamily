package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComChartRoomDao;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.service.IEjlComChartRoomService;

@Service("ejlComChartRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComChartRoomServiceImpl extends BaseServiceImpl<IEjlComChartRoomDao,EjlChartRoom> implements IEjlComChartRoomService {
 
	@Resource(name="ejlComChartRoomDaoImpl")
	private IEjlComChartRoomDao ejlComChartRoomDaoImpl;
	@Override
	protected IEjlComChartRoomDao getEntityDao() { 
		return ejlComChartRoomDaoImpl;
	}
			
}
