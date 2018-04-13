package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserNoticeDao;
import com.winterframework.efamily.entity.EjlUserNotice;

@Repository("ejlComUserNoticeDaoImpl")
public class EjlComUserNoticeDaoImpl<E extends EjlUserNotice>  extends BaseDaoImpl<EjlUserNotice> implements IEjlComUserNoticeDao{

}
