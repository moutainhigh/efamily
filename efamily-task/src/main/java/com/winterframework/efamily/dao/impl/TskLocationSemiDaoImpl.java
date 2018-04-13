package com.winterframework.efamily.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.ITskLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationSemi;

@Repository("tskLocationSemiDaoImpl")
public class TskLocationSemiDaoImpl extends EfComLocationSemiDaoImpl<EfLocationSemi> implements ITskLocationSemiDao {

	@Override
	public List<Map<String,Long>> getDeviceIdListByFlag(int flag, Date timeFrom,Date timeTo)
			throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("timeFrom",timeFrom);
		map.put("timeTo",timeTo);
		return this.sqlSessionTemplate.selectList(getQueryPath("getDeviceIdListByFlag"), map);
	}
	@Override
	public List<EfLocationSemi> getSameListById(Long userId,Long deviceId,Long id) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId",deviceId);
		map.put("id",id);
		return this.sqlSessionTemplate.selectList(getQueryPath("getSameListById"), map);
	}
	@Override
	public Map<Date, Date> getMaxMinTimeByDeviceIdAndFlag(Long userId,Long deviceId,
			int flag, Date timeFrom,Date timeTo) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("flag", flag);
		map.put("timeFrom",timeFrom);
		map.put("timeTo",timeTo);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getMaxMinTimeByDeviceIdAndFlag"), map);
	}
	@Override
	public List<EfLocationSemi> getListByDeviceIdAndTimeFromToAndFlag(Long userId,Long deviceId, Date timeFrom, Date timeTo, int flag)
			throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("flag", flag);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByDeviceIdAndTimeFromToAndFlag"), map);
	}
	@Override
	public List<EfLocationSemi> getListNearByDeviceIdAndLatLngAndFlag(Long userId,Long deviceId,
			double longitudeFrom, double longitudeTo, double latitudeFrom,
			double latitudeTo, int flag) throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("longitudeFrom", longitudeFrom);
		map.put("longitudeTo", longitudeTo);
		map.put("latitudeFrom", latitudeFrom);
		map.put("latitudeTo", latitudeTo);
		map.put("flag", flag);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListNearByDeviceIdAndLatLngAndFlag"), map);
	}
	@Override
	public List<EfLocationSemi> getListNearByDeviceIdAndTime(Long userId,Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListNearByDeviceIdAndTime"), map);
	}
	@Override
	public List<EfLocationSemi> getListNearByDeviceIdAndLatLngAndFlags(Long userId,
			Long deviceId, double longitudeFrom, double longitudeTo,
			double latitudeFrom, double latitudeTo, List<Integer> flags)
			throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("longitudeFrom", longitudeFrom);
		map.put("longitudeTo", longitudeTo);
		map.put("latitudeFrom", latitudeFrom);
		map.put("latitudeTo", latitudeTo);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListNearByDeviceIdAndLatLngAndFlags"), map);
	}
	@Override
	public List<EfLocationSemi> getPreNextListByIdAndCountAndFlags(Long userId,Long deviceId,Long id,
			int count, List<Integer> flags) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("id", id);
		map.put("count", count);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectList(getQueryPath("getPreNextListByIdAndCountAndFlags"), map);
	}
	@Override
	public List<EfLocationSemi> getListNearByDeviceIdAndLatLngAndExcludeFlags(Long userId,
			Long deviceId, double longitudeFrom, double longitudeTo,
			double latitudeFrom, double latitudeTo, List<Integer> flags)
			throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("longitudeFrom", longitudeFrom);
		map.put("longitudeTo", longitudeTo);
		map.put("latitudeFrom", latitudeFrom);
		map.put("latitudeTo", latitudeTo);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListNearByDeviceIdAndLatLngAndExcludeFlags"), map);
	}
	@Override
	public List<EfLocationSemi> getListByTimeFromToAndFlag(Date timeFrom,
			Date timeTo, int flag) throws BizException {
		Map<String,Object> map=new HashMap<String, Object>(); 
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("flag", flag);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByTimeFromToAndFlag"), map);
	}
	@Override
	public List<Map<String,Long>> getDeviceIdListByFlags(Date timeFrom, Date timeTo,
			List<Integer> flags) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>(); 
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectList(getQueryPath("getDeviceIdListByFlags"), map);
	}
	
	@Override
	public Map<Date, Date> getMaxMinTimeByDeviceIdAndFlags(Long userId,Long deviceId,
			Date timeFrom, Date timeTo, List<Integer> flags) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("timeFrom",timeFrom);
		map.put("timeTo",timeTo);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getMaxMinTimeByDeviceIdAndFlags"), map);
	}
	@Override
	public EfLocationSemi getFirstByDeviceIdAndFlags(Long userId,Long deviceId, Date timeFrom,
			Date timeTo, List<Integer> flags) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("timeFrom",timeFrom);
		map.put("timeTo",timeTo);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getFirstByDeviceIdAndFlags"), map);
	}
	@Override
	public List<EfLocationSemi> getListByDeviceIdAndTimeFromToAndFlags(Long userId,
			Long deviceId, Date timeFrom, Date timeTo, List<Integer> flags)
			throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("timeFrom", timeFrom);
		map.put("timeTo", timeTo);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByDeviceIdAndTimeFromToAndFlags"), map);
	}
	@Override
	public List<EfLocationSemi> getSameList(Long userId, Long deviceId,
			Integer source, Date time,String remark) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("source", source);
		map.put("time", time);
		map.put("remark", remark);
		return this.sqlSessionTemplate.selectList(getQueryPath("getSameList"), map);
	}
}
