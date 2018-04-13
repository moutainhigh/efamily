package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfLocationWifiDao;
import com.winterframework.efamily.entity.EfLocationWifi;
 
@Repository("efLocationWifiDaoImpl")
public class EfLocationWifiDaoImpl<E extends EfLocationWifi> extends BaseDaoImpl<EfLocationWifi> implements IEfLocationWifiDao{

	@Override
	public List<EfLocationWifi> getByOpTag(Long opTag,Long beginTime,Long endTime,Long userId,Long deviceId) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opTag", opTag); 
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return sqlSessionTemplate.selectList(getQueryPath("getByOpTag"), map);
	}

	@Override
	public Map<Date, Date> getUnhandledMaxMinTime(Long time) throws BizException{
		return this.sqlSessionTemplate.selectOne("getMaxMinTimeWift", time);
	}

	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>(); 
		map.put("timeFrom", DateUtils.convertDate2Long(timeFrom));
		map.put("timeTo", DateUtils.convertDate2Long(timeTo));
		return this.sqlSessionTemplate.selectList(getQueryPath("getDeviceIdListByFlags"), map);
	}
}
