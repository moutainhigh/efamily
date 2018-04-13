package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfPlatformHealthSettingDao;
import com.winterframework.efamily.entity.EfPlatformHealthSetting;
 
@Repository("efPlatformHealthSettingDaoImpl")
public class EfPlatformHealthSettingDaoImpl<E extends EfPlatformHealthSetting> extends BaseDaoImpl<EfPlatformHealthSetting> implements IEfPlatformHealthSettingDao{
	@Override
	public EfPlatformHealthSetting getByAgeLevel(Integer ageLevel)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ageLevel", ageLevel); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByAgeLevel"), map);
	}
}
