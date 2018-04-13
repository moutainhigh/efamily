package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserLoginRecordDao;
import com.winterframework.efamily.entity.EjlUserLoginRecord;

@Repository("ejlComUserLoginRecordDaoImpl")
public class EjlComUserLoginRecordDaoImpl<E extends EjlUserLoginRecord> extends BaseDaoImpl<EjlUserLoginRecord> implements IEjlComUserLoginRecordDao {
}
