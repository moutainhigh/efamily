package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlFamily;

public interface IEjlComFamilyDao extends IBaseDao<EjlFamily> {
 
	public Long getEjlFamilyCodeId();
	
	public EjlFamily getFamilyByParm(EjlFamily ejlFamily);
}
