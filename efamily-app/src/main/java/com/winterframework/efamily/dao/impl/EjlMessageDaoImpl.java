package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlMessageDao;
import com.winterframework.efamily.entity.EjlMessage;

@Repository("ejlMessageDaoImpl")
public class EjlMessageDaoImpl extends EjlComMessageDaoImpl<EjlMessage> implements IEjlMessageDao {
}
