package com.winterframework.efamily.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComChartRoomUserDao;
import com.winterframework.efamily.entity.EjlChartRoomUser;

@Repository("ejlComChartRoomUserDaoImpl")
public class EjlComChartRoomUserDaoImpl<E extends EjlChartRoomUser> extends BaseDaoImpl<EjlChartRoomUser> implements IEjlComChartRoomUserDao {
	@Override
	public List<EjlChartRoomUser> getByRoomId(Long roomId) throws Exception { 
		return sqlSessionTemplate.selectList(getQueryPath("getByRoomId"), roomId);
	}
	
}
