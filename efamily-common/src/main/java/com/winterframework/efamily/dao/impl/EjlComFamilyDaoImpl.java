package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComFamilyDao;
import com.winterframework.efamily.entity.EjlFamily;

@Repository("ejlComFamilyDaoImpl")
public class EjlComFamilyDaoImpl<E extends EjlFamily> extends BaseDaoImpl<EjlFamily> implements IEjlComFamilyDao {
	public Long getEjlFamilyCodeId(){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlFamilyCodeId"));
	}
	
	public EjlFamily getFamilyByParm(EjlFamily ejlFamily){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getFamilyByParm"),ejlFamily);
	}
	
}
