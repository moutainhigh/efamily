package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlFamilyDao;
import com.winterframework.efamily.entity.EjlFamily;

@Repository("ejlFamilyDaoImpl")
public class EjlFamilyDaoImpl extends EjlComFamilyDaoImpl<EjlFamily> implements IEjlFamilyDao {
	public Long getEjlFamilyCodeId(){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlFamilyCodeId"));
	}
	
	public EjlFamily getFamilyByParm(EjlFamily ejlFamily){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getFamilyByParm"),ejlFamily);
	}
	
}
