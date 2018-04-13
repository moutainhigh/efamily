package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfKeyApi;

public interface IEfKeyApiDao extends IBaseDao<EfKeyApi> {

	EfKeyApi getByKey(String key) throws Exception;
}
