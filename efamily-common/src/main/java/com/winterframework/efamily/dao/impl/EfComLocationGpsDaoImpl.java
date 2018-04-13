package com.winterframework.efamily.dao.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComLocationGpsDao;
import com.winterframework.efamily.entity.EfLocationGps;

 
@Repository("efComLocationGpsDaoImpl")
public class EfComLocationGpsDaoImpl<E extends EfLocationGps> extends BaseDaoImpl<EfLocationGps> implements IEfComLocationGpsDao{
	


	@Override
	public List<EfLocationGps> getByOpTag(Long opTag,Date beginDate,Date endDate,Long userId,Long deviceId) throws BizException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("opTag", opTag);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return sqlSessionTemplate.selectList(getQueryPath("getByOpTag"), map);
	}

	@Override
	public Map<Date, Date> getUnhandledMaxMinTime(int days) throws BizException{
		return this.sqlSessionTemplate.selectOne("getMaxMinTimeGps", days);
	}

	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>(); 
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		return this.sqlSessionTemplate.selectList(getQueryPath("getDeviceIdListByFlags"), map);
	}
	@Override
	public List<String> getGpsOff() throws Exception {
		return this.sqlSessionTemplate.selectList(getQueryPath("getGpsOff"));
	}
}