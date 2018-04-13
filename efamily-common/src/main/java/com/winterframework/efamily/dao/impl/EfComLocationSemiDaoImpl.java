package com.winterframework.efamily.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationSemi;

@Repository("efComLocationSemiDaoImpl")
public class EfComLocationSemiDaoImpl<E extends EfLocationSemi> extends BaseDaoImpl<EfLocationSemi> implements IEfComLocationSemiDao {

	@Override
	public EfLocationSemi getPrevious(Long userId,Long deviceId,Long id,List<Integer> flags)
			throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("id", id);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getPrevious"), map);
	}
	@Override
	public EfLocationSemi getNext(Long userId,Long deviceId,Long id,List<Integer> flags)
			throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("id", id);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getNext"), map);
	}
	
	
	
	
	
	@Override
	public EfLocationSemi getPreviousByTime(Long userId, Long deviceId,
			Date time, List<Integer> flags) throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("time", time);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getPreviousByTime"), map);
	}
	@Override
	public EfLocationSemi getNextByTime(Long userId, Long deviceId, Date time,
			List<Integer> flags) throws BizException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("time", time);
		map.put("flags", flags);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getNextByTime"), map);
	}
	@Override
	public List<EfLocationSemi> getDeviceLocationSemiByStatus(Integer count,
			Long deviceId, int status) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("flag", status);
		map.put("count", count);
		map.put("deviceId", deviceId);
		return this.sqlSessionTemplate.selectList("getDeviceLocationSemiByStatus", map);
	}
	
	@Override
	public Integer updateStatus(Long id,Integer status ) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("id", id);
		return this.sqlSessionTemplate.update("updateStatus", map);
	}
	@Override
	public EfLocationSemi getLast(Long deviceId, Date time) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("time", time);
		map.put("flag", EfLocationSemi.Flag.UNHANDLE.getValue());
		map.put("notFlag", EfLocationSemi.Flag.DISPOSE.getValue());
		map.put("notFlag1", EfLocationSemi.Flag.DELETE.getValue());
		return this.sqlSessionTemplate.selectOne("getLast", map);
	}
	
	
	
	
	@Override
	public List<EfLocationSemi> getAllListByNewUnhander(Long deviceId)
			throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("flag", EfLocationSemi.Flag.UNHANDLE.getValue());
		map.put("notFlag", EfLocationSemi.Flag.DISPOSE.getValue());
		map.put("notFlag1", EfLocationSemi.Flag.DELETE.getValue());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAllListByNewUnhander"), map);
	}
	@Override
	public List<EfLocationSemi> getFirstLocationByNewUnhanderTime(Long deviceId,Date endTime,Integer time)
			throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("flag", EfLocationSemi.Flag.UNHANDLE.getValue());
		map.put("notFlag", EfLocationSemi.Flag.DISPOSE.getValue());
		map.put("notFlag1", EfLocationSemi.Flag.DELETE.getValue());
		map.put("endTime", endTime);
		map.put("beginTime", DateUtils.addMinutes(endTime, -time));
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getFirstLocationByNewUnhanderTime"), map);
	}
	
	@Override
	public EfLocationSemi getLastLocation(Long userId, Long deviceId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getLastLocation"), map);
	}
	
	@Override	
	public EfLocationSemi getNewestLocationForQueryNotice(Long deviceId,Long deviceUserId, Date date) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", DateUtils.format(date,DateUtils.DATETIME_FORMAT_PATTERN));
		map.put("deviceId", deviceId);
		map.put("userId", deviceUserId);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getNewestLocationForQueryNotice"), map);
	}
	
}
