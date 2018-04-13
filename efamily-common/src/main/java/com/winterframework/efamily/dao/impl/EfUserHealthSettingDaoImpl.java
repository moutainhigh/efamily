package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfUserHealthSettingDao;
import com.winterframework.efamily.entity.EfUserHealthSetting;
 
@Repository("efUserHealthSettingDaoImpl")
public class EfUserHealthSettingDaoImpl<E extends EfUserHealthSetting> extends BaseDaoImpl<EfUserHealthSetting> implements IEfUserHealthSettingDao{
	@Override
	public EfUserHealthSetting getByUserId(Long userId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserId"), map);
	}
}
