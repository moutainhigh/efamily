package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfCustomerDataSendSetting;

public interface IEfComCustomerDataSendSettingService extends IBaseService<EfCustomerDataSendSetting>{

	public EfCustomerDataSendSetting getEfCustomerDataSendSettingBy(Long customerId,Integer dataType) throws BizException;
}
