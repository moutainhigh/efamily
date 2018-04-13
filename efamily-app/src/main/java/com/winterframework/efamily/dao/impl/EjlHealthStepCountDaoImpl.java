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

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlHealthStepCountDao;
import com.winterframework.efamily.dto.QueryMonitorDataRequest;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: EjlHealthHeartRateDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:25:53 
*  
*/
@Repository("ejlHealthStepCountDaoImpl")
public class EjlHealthStepCountDaoImpl extends EjlComHealthStepCountDaoImpl<EjlHealthStepCount> implements IEjlHealthStepCountDao {

	@Override
	public EjlHealthStepCount getLastUserStepCount(Long userId,Long deviceId) throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastUserStepCount"), map);
	}

	/**
	* Title: deleteHealthStepCount
	* Description:
	* @param userId
	* @throws BizException 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#deleteHealthStepCount(java.lang.Long) 
	*/
	@Override
	public void deleteUserHealthStepCount(Long userId,Long deviceId) throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		this.sqlSessionTemplate.delete(this.getQueryPath("deleteUserHealthStepCount"), map);
		
	}

	/**
	* Title: getEjlHealthStepCountsByUserAndDate
	* Description:
	* @param pageRequest
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#getEjlHealthStepCountsByUserAndDate(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public List<EjlHealthStepCount> getEjlHealthStepCountsByUserAndDate(PageRequest<QueryMonitorDataRequest> pageRequest,boolean ispage)
			throws BizException {
		
		QueryMonitorDataRequest request = pageRequest.getSearchDo();
		Map map = new HashMap();
		map.put("userId", request.getUserId());
		map.put("deviceId", request.getDeviceId());
		if(ispage){
		map.put("startNo", (int) ((pageRequest.getPageNo() - 1) * pageRequest.getPageSize()));
		map.put("endNo", pageRequest.getPageSize() + ((pageRequest.getPageNo() - 1) * pageRequest.getPageSize()));}
		else{
			map.put("startNo", 0);
			map.put("endNo",Long.MAX_VALUE);
		}
		map.put("sortColumns", request.getSortColumns());
		map.put("monitorDataType", request.getMonitorDataType());
		map.put("startQueryTime", request.getStartQueryTime());
		map.put("endQueryTime", request.getEndQueryTime());
		return  this.sqlSessionTemplate.selectList(this.getQueryPath("getHealthStepPage"), map);
		
	}

	/**
	* Title: getAllDayStepsByUser
	* Description:
	* @param userId
	* @param queryDate
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#getAllDayStepsByUser(java.lang.Long, java.util.Date) 
	*/
	@Override
	public Long getAllDayStepsByUser(Long userId, Date queryDate,Long deviceId,int type) throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("queryEndDate", queryDate);
		map.put("queryStartDate", DateUtils.getStartTimeOfDate(queryDate));
		map.put("deviceId", deviceId);
		map.put("type", type);
		if(type==3){
			return this.sqlSessionTemplate.selectOne("getAllDayClimbsByUser",map);
		}else{
			return this.sqlSessionTemplate.selectOne("getAllDayStepsByUser",map);
		}
	}
	
}
