/**   
* @Title: EjlHealthHeartRateDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午4:25:53 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
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
	public List<EjlHealthHeartRate> getNoticeUserHeartRate(Long userId,
			Long startTime, Long gtValue, Long ltValue,Long toTime) throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("startTime", startTime);
		map.put("gtValue", gtValue);
		map.put("ltValue", ltValue);
		map.put("toTime", toTime);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getNoticeUserHeartRate"), map);
	}

	
}
