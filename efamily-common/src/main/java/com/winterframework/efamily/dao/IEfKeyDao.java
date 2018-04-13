package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfKey;

public interface IEfKeyDao extends IBaseDao<EfKey> {

	EfKey getByKey(String key) throws Exception;
}
