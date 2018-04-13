package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfCustomerAlarmSettingDao;
import com.winterframework.efamily.entity.EfCustomerAlarmSetting;
 
@Repository("efCustomerAlarmSettingDaoImpl")
public class EfCustomerAlarmSettingDaoImpl<E extends EfCustomerAlarmSetting> extends BaseDaoImpl<EfCustomerAlarmSetting> implements IEfCustomerAlarmSettingDao{
	
	@Override
	public EfCustomerAlarmSetting getByCustomerIdAndAlarmType(Long customerId,
			Integer alarmType) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);
		map.put("alarmType", alarmType);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByCustomerIdAndAlarmType"), map);
	}

}
