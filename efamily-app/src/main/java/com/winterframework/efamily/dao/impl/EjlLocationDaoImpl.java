package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlLocationDao;
import com.winterframework.efamily.entity.EjlLocation;

@Repository("ejlLocationDaoImpl")
public class EjlLocationDaoImpl extends EjlComLocationDaoImpl<EjlLocation> implements IEjlLocationDao {
	
	@Override
	public List<EjlLocation> getUserLocationBetweenTime(Long userId,
			Long watchId, Date beginTime, Date endTime) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("watchId", watchId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList("getUserLocationBetweenTime", map);
	}
	

	
}
