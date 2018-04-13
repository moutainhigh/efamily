package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComCustomerDao;
import com.winterframework.efamily.entity.EfCustomer;


@Repository("efComCustomerDaoImpl")
public class EfComCustomerDaoImpl<E extends EfCustomer> extends BaseDaoImpl<EfCustomer> implements IEfComCustomerDao{

}
