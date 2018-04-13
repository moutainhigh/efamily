package com.winterframework.efamily.dao;

import com.winterframework.efamily.entity.EjlFamily;

public interface IEjlFamilyDao extends IEjlComFamilyDao {
 
	public Long getEjlFamilyCodeId();
	
	public EjlFamily getFamilyByParm(EjlFamily ejlFamily);
}
