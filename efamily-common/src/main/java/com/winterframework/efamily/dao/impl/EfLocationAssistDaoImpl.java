package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfLocationAssistDao;
import com.winterframework.efamily.entity.EfLocationAssist;

@Repository("efLocationAssistDaoImpl")
public class EfLocationAssistDaoImpl<E extends EfLocationAssist> extends BaseDaoImpl<EfLocationAssist> implements IEfLocationAssistDao {
	@Override
	public EfLocationAssist getByLocationId(Long locationId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("locationId", locationId); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByLocationId"), map);
	}
}
