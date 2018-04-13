package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlUserLoginRecordDao;
import com.winterframework.efamily.entity.EjlUserLoginRecord;

@Repository("ejlUserLoginRecordDaoImpl")
public class EjlUserLoginRecordDaoImpl extends EjlComUserLoginRecordDaoImpl<EjlUserLoginRecord> implements IEjlUserLoginRecordDao {
}
