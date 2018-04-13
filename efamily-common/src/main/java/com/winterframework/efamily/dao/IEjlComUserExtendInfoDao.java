package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlUserExtendInfo;

public interface IEjlComUserExtendInfoDao extends IBaseDao<EjlUserExtendInfo>{
	public List<String> getAllCityName() throws Exception;

	EjlUserExtendInfo getByUserId(Long userId) throws Exception;

}
