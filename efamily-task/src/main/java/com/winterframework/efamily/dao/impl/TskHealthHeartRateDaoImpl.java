/**   
* @Title: EjlHealthHeartRateDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午4:25:53 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.ITskHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

 
@Repository("tskHealthHeartRateDaoImpl")
public class TskHealthHeartRateDaoImpl extends EjlComHealthHeartRateDaoImpl<EjlHealthHeartRate> implements ITskHealthHeartRateDao {
	@Override
	public List<Map<String, Long>> getLastestUserIdDeviceIdListByCreateTime(
			Date fromTime,Date toTime) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("fromTime", fromTime);
		map.put("toTime", toTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getLastestUserIdDeviceIdListByCreateTime"), map);
	}
	
	@Override
	public List<EjlHealthHeartRate> getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(
			Long userId, Long deviceId, Integer low, Integer high,
			Date fromTime, Date toTime) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("low", low);
		map.put("high", high);
		map.put("fromTime", fromTime);
		map.put("toTime", toTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getSortListByUserIdAndDeviceIdAndSpanAndCreateTime"), map);
	}
}

