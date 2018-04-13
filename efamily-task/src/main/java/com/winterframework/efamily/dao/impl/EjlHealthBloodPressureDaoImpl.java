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
import com.winterframework.efamily.dao.IEjlHealthBloodPressureDao;
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthBloodPressureAlarmPram;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

/** 
* @ClassName: EjlHealthHeartRateDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:25:53 
*  
*/
@Repository("ejlHealthBloodPressureDaoImpl")
public class EjlHealthBloodPressureDaoImpl extends EjlComHealthBloodPressureDaoImpl<EjlHealthBloodPressure> implements IEjlHealthBloodPressureDao {
	@Override
	public List<EjlHealthBloodPressure> getNoticeUserBloodPressure(Long userId,
			Long startTime, Long systolicPressureGt, Long systolicPressureLt,
			Long diastolicPressureGt, Long diastolicPressureLt,Long toTime)
			throws BizException {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("startTime", startTime);
		map.put("toTime", toTime);
		map.put("systolicPressureGt", systolicPressureGt);
		map.put("systolicPressureLt", systolicPressureLt);
		map.put("diastolicPressureGt", diastolicPressureGt);
		map.put("diastolicPressureLt", diastolicPressureLt);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getNoticeUserBloodPressure"), map);
	}
	
	public List<EjlHealthBloodPressure> getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(EjlHealthBloodPressureAlarmPram ejlHealthBloodPressureAlarmPram)
			throws BizException {

		return this.sqlSessionTemplate.selectList(this.getQueryPath("getSortListByUserIdAndDeviceIdAndSpanAndCreateTime"), ejlHealthBloodPressureAlarmPram);
	}
	
	
	public List<Map<String, Long>> getLastestUserIdDeviceIdListByCreateTime(Date fromTime,Date toTime) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("fromTime", fromTime);
		map.put("toTime", toTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getLastestUserIdDeviceIdListByCreateTime"), map);
	}
	

}
