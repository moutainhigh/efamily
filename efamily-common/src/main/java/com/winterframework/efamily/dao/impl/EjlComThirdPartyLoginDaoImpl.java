package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComThirdPartyLoginDao;
import com.winterframework.efamily.entity.EjlThirdPartyLogin;
import com.winterframework.efamily.entity.ThirdPartyLoginStruc;
@Repository("ejlComThirdPartyLoginDaoImpl")
public class EjlComThirdPartyLoginDaoImpl <E extends EjlThirdPartyLogin> extends BaseDaoImpl<EjlThirdPartyLogin> implements IEjlComThirdPartyLoginDao{

	
	public List<ThirdPartyLoginStruc> getThirdPartyLoginStruc(Long userId){
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getThirdPartyLoginStruc"), userId);
	}
	
}
