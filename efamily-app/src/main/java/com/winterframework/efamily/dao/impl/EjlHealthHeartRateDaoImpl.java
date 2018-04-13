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
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.dto.QueryMonitorDataRequest;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.modules.page.PageRequest;

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
	public EjlHealthHeartRate getLastUserHeartRate(Long userId,Long deviceId) throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("startTime", DateUtils.getStartTimeOfCurrentDate().getTime());
		map.put("endTime", DateUtils.getEndTimeOfCurrentDate().getTime());
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastUserHeartRate"), map);
	}

	/**
	* Title: deleteUserHealthHeartRate
	* Description:
	* @param userId
	* @throws BizException; 
	* @see com.winterframework.efamily.dao.IEjlHealthHeartRateDao#deleteUserHealthHeartRate(java.lang.Long) 
	*/
	@Override
	public void deleteUserHealthHeartRate(Long userId,Long deviceId) throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		this.sqlSessionTemplate.delete(this.getQueryPath("deleteUserHealthHeartRate"), map);
	}

	@Override
	public List<EjlHealthHeartRate> getEjlHealthHeartRatesByUserAndDate(PageRequest<QueryMonitorDataRequest> pageRequest)
			throws BizException {
		QueryMonitorDataRequest request = pageRequest.getSearchDo();
		Map map = new HashMap();
		map.put("userId", request.getUserId());
		map.put("deviceId", request.getDeviceId());
		map.put("startNo", (int) ((pageRequest.getPageNo() - 1) * pageRequest.getPageSize()));
		map.put("endNo", pageRequest.getPageSize());
		map.put("sortColumns", "to_time desc");
		map.put("fromTime", request.getStartDateTime());
		map.put("toTime", request.getEndDateTime());
		return  this.sqlSessionTemplate.selectList(this.getQueryPath("getHeartRatePage1"), map);
	}
}
