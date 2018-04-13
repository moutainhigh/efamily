package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlChartRoomDao;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.service.IEjlChartRoomService;

@Service("ejlChartRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlChartRoomServiceImpl extends BaseServiceImpl<IEjlChartRoomDao,EjlChartRoom> implements IEjlChartRoomService {
 
	@Resource(name="ejlChartRoomDaoImpl")
	private IEjlChartRoomDao ejlChartRoomDaoImpl;
	@Override
	protected IEjlChartRoomDao getEntityDao() { 
		return ejlChartRoomDaoImpl;
	}
			
}
