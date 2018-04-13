package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComCustomerDao;
import com.winterframework.efamily.entity.EfCustomer;
import com.winterframework.efamily.service.IEfComCustomerService;


@Service("efComCustomerServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComCustomerServiceImpl extends BaseServiceImpl<IEfComCustomerDao,EfCustomer> implements IEfComCustomerService {
 
	@Resource(name="efComCustomerDaoImpl")
	private IEfComCustomerDao efComCustomerDaoImpl;
	@Override
	protected IEfComCustomerDao getEntityDao() { 
		return efComCustomerDaoImpl;
	}
	
	public EfCustomer getEfCustomerBy(Integer number) throws BizException{
		EfCustomer efCustomer = new EfCustomer();
		efCustomer.setNumber(number);
		return selectOneObjByAttribute(null, efCustomer);
	}
}
