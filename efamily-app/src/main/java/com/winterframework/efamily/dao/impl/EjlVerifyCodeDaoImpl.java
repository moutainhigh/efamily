package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlVerifyCodeDao;
import com.winterframework.efamily.entity.EjlVerifyCode;

@Repository("ejlVerifyCodeDaoImpl")
public class EjlVerifyCodeDaoImpl extends EjlComVerifyCodeDaoImpl<EjlVerifyCode> implements IEjlVerifyCodeDao {

	/**
	* Title: getInvitedUserByFamily
	* Description:
	* @param familyId
	* @return
	* @throws BizException; 
	* @see com.winterframework.efamily.dao.IEjlVerifyCodeDao#getInvitedUserByFamily(java.lang.Long) 
	*/
	@Override
	public List<EjlVerifyCode> getInvitedUserByFamily(Long familyId) throws BizException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("familyId", familyId);
		map.put("type", 2);
		map.put("sortColumns", "create_time desc");
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlVerifyCodeList"), map);
	}

	@Override
	public EjlVerifyCode getVerifyCode(String phoneNumber, String verifyCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("verifyCode", verifyCode);
		map.put("type", 1);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getVerifyCode"), map);
	}

	
	
}
