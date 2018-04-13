package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComCommandInfoDao;
import com.winterframework.efamily.entity.EfCommandInfo;

@Repository("efComCommandInfoDaoImpl")
public class EfComCommandInfoDaoImpl<E extends EfCommandInfo> extends BaseDaoImpl<EfCommandInfo> implements IEfComCommandInfoDao{
	

}
