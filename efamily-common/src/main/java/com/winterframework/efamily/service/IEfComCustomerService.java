package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfCustomer;

public interface IEfComCustomerService extends IBaseService<EfCustomer> {
	
	/**
	 * 功能：根据number获取客户信息
	 * @param number
	 * @return
	 * @throws BizException
	 */
	public EfCustomer getEfCustomerBy(Integer number) throws BizException;
}
