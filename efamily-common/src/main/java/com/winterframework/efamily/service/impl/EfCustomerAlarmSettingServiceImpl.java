package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfCustomerAlarmSettingDao;
import com.winterframework.efamily.entity.EfCustomerAlarmSetting;
import com.winterframework.efamily.service.IEfCustomerAlarmSettingService;

@Service("efCustomerAlarmSettingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfCustomerAlarmSettingServiceImpl  extends BaseServiceImpl<IEfCustomerAlarmSettingDao,EfCustomerAlarmSetting> implements IEfCustomerAlarmSettingService {
	@Resource(name="efCustomerAlarmSettingDaoImpl")
	private IEfCustomerAlarmSettingDao dao;
	
	@Override
	protected IEfCustomerAlarmSettingDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	} 
	 
	@Override
	public EfCustomerAlarmSetting getByCustomerIdAndAlarmType(Long customerId,
			Integer alarmType) throws BizException {
		try{
			return dao.getByCustomerIdAndAlarmType(customerId, alarmType);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfCustomerAlarmSetting getValidByCustomerIdAndAlarmType(
			Long customerId, Integer alarmType) throws BizException {
		EfCustomerAlarmSetting alarmSett=getByCustomerIdAndAlarmType(customerId, alarmType);
		if(null!=alarmSett && alarmSett.getStatus().intValue()==YesNo.YES.getValue()){
			return alarmSett;
		}
		return null;
	}
}
