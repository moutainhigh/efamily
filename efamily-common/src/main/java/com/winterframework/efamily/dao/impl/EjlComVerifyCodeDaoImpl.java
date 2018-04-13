package com.winterframework.efamily.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComVerifyCodeDao;
import com.winterframework.efamily.entity.EjlVerifyCode;

@Repository("ejlComVerifyCodeDaoImpl")
public class EjlComVerifyCodeDaoImpl<E extends EjlVerifyCode> extends BaseDaoImpl<EjlVerifyCode> implements IEjlComVerifyCodeDao {
	@Override
	public EjlVerifyCode getEffectiveVerifyCode(String phoneNumber,int type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("type", type);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEffectiveVerifyCode"), map);
	}
}
