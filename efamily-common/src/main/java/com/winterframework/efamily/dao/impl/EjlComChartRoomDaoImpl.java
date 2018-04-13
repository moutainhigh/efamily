package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComChartRoomDao;
import com.winterframework.efamily.entity.EjlChartRoom;


@Repository("ejlComChartRoomDaoImpl")
public class EjlComChartRoomDaoImpl<E extends EjlChartRoom> extends BaseDaoImpl<EjlChartRoom>  implements IEjlComChartRoomDao {
  
}
