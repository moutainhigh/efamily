package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComCustomerDataSendSettingDao;
import com.winterframework.efamily.entity.EfCustomerDataSendSetting;
import com.winterframework.efamily.service.IEfComCustomerDataSendSettingService;


@Service("efComCustomerDataSendSettingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComCustomerDataSendSettingServiceImpl extends BaseServiceImpl<IEfComCustomerDataSendSettingDao,EfCustomerDataSendSetting> implements IEfComCustomerDataSendSettingService {
 
	@Resource(name="efComCustomerDataSendSettingDaoImpl")
	private IEfComCustomerDataSendSettingDao efComCustomerDataSendSettingDaoImpl;
	
	@Override
	protected IEfComCustomerDataSendSettingDao getEntityDao() { 
		return efComCustomerDataSendSettingDaoImpl;
	}

	public EfCustomerDataSendSetting getEfCustomerDataSendSettingBy(Long customerId,Integer dataType) throws BizException{
		EfCustomerDataSendSetting efCustomerDataSendSetting = new EfCustomerDataSendSetting();
		efCustomerDataSendSetting.setCustomerId(customerId);
		efCustomerDataSendSetting.setDataType(dataType);
		return efComCustomerDataSendSettingDaoImpl.selectOneObjByAttribute(efCustomerDataSendSetting);
	}
	
}
