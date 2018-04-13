package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserChartRoomDao;
import com.winterframework.efamily.entity.EjlUserChartRoom;
import com.winterframework.efamily.service.IEjlComUserChartRoomService;

@Service("ejlComUserChartRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserChartRoomServiceImpl extends BaseServiceImpl<IEjlComUserChartRoomDao,EjlUserChartRoom> implements IEjlComUserChartRoomService {
 
	@Resource(name="ejlComUserChartRoomDaoImpl")
	private IEjlComUserChartRoomDao ejlComUserChartRoomDaoImpl;
	@Override
	protected IEjlComUserChartRoomDao getEntityDao() { 
		return ejlComUserChartRoomDaoImpl;
	}
			
}