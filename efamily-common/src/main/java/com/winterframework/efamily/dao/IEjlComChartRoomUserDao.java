package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlChartRoomUser;

public interface IEjlComChartRoomUserDao extends IBaseDao<EjlChartRoomUser> {
	List<EjlChartRoomUser> getByRoomId(Long roomId) throws Exception;

}
