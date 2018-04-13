package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfKeyApiDao;
import com.winterframework.efamily.entity.EfKeyApi;

@Repository("efKeyApiDaoImpl")
public class EfKeyApiDaoImpl<E extends EfKeyApi> extends BaseDaoImpl<EfKeyApi> implements IEfKeyApiDao {
	@Override
	public EfKeyApi getByKey(String key) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ukey", key); 
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByKey"), map);
	}
}
