/**   
* @Title: EjlHealthHeartRateDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午4:25:53 
* @version V1.0   
*/
package com.winterframework.efamily.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.api.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.base.exception.BizException;

import com.winterframework.efamily.dao.impl.EjlComHealthHeartRateDaoImpl;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

/** 
* @ClassName: EjlHealthHeartRateDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:25:53 
*  
*/
@Repository("ejlHealthHeartRateDaoImpl")
public class EjlHealthHeartRateDaoImpl extends EjlComHealthHeartRateDaoImpl<EjlHealthHeartRate> implements IEjlHealthHeartRateDao {

	@Override
	public EjlHealthHeartRate getLastUserHeartRate(Long deviceId,Long userId,Long time) throws BizException {
		Map map = new HashMap();
		map.put("deviceId", deviceId);
		map.put("userId", userId);
		map.put("time", time);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastUserHeartRate"), map);
	}

	@Override
	public List<EjlHealthHeartRate> getHealthHeartRateByTime(Long deviceId,
			Long fromTime, Long toTime,Long userId) throws BizException {
		Map map = new HashMap();
		map.put("deviceId", deviceId);
		map.put("fromTime", fromTime);
		map.put("userId", userId);
		if(toTime != null){
			map.put("toTime", toTime);
		}
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getHealthHeartRateByTime"), map);
	}
}
