package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlUserChartRoomDao;
import com.winterframework.efamily.entity.EjlUserChartRoom;
import com.winterframework.efamily.service.IEjlUserChartRoomService;

@Service("ejlUserChartRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserChartRoomServiceImpl extends BaseServiceImpl<IEjlUserChartRoomDao,EjlUserChartRoom> implements IEjlUserChartRoomService {
 
	@Resource(name="ejlUserChartRoomDaoImpl")
	private IEjlUserChartRoomDao ejlUserChartRoomDaoImpl;
	@Override
	protected IEjlUserChartRoomDao getEntityDao() { 
		return ejlUserChartRoomDaoImpl;
	}
			
	public EjlUserChartRoom getByUserIdAndChatRoomId(Long userId,Long chartRoomId){
		EjlUserChartRoom ejlUserChartRoom = new EjlUserChartRoom();
		ejlUserChartRoom.setUserId(userId);
		ejlUserChartRoom.setChatRoomId(chartRoomId);
		return ejlUserChartRoomDaoImpl.getByUserIdAndChatRoomId(ejlUserChartRoom);
	}
}