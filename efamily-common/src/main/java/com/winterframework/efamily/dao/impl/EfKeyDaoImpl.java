package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfKeyDao;
import com.winterframework.efamily.entity.EfKey;

@Repository("efKeyDaoImpl")
public class EfKeyDaoImpl<E extends EfKey> extends BaseDaoImpl<EfKey> implements IEfKeyDao {
	@Override
	public EfKey getByKey(String key) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ukey", key); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByKey"), map);
	}
}
