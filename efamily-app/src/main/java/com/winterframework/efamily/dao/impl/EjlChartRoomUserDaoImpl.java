package com.winterframework.efamily.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlChartRoomUserDao;
import com.winterframework.efamily.entity.EjlChartRoomUser;
import com.winterframework.efamily.entity.EjlUser;

@Repository("ejlChartRoomUserDaoImpl")
public class EjlChartRoomUserDaoImpl extends EjlComChartRoomUserDaoImpl<EjlChartRoomUser> implements IEjlChartRoomUserDao {
	
	@Override
	public int updateStatusByUserIdAndChartId(EjlChartRoomUser ejlChartRoomUser) throws BizException {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateStatusByUserIdAndChartId"), ejlChartRoomUser);
	}
	
	
	public List<EjlUser> getChartRoomUserByRoomIdAndStatus(EjlChartRoomUser ejlChartRoomUser) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getChartRoomUserByRoomIdAndStatus"), ejlChartRoomUser);
	}
	
	
}
