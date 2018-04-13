/**   
* @Title: IEjlHealthHeartRateDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午4:18:13 
* @version V1.0   
*/
package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthBloodPressureAlarmPram;
import com.winterframework.efamily.entity.EjlHealthHeartRate;


/** 
* @ClassName: IEjlHealthHeartRateDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:18:13 
*  
*/
public interface IEjlHealthBloodPressureDao extends IEjlComHealthBloodPressureDao {
	public List<EjlHealthBloodPressure> getNoticeUserBloodPressure(Long userId, Long startTime,Long systolicPressureGt,Long systolicPressureLt,Long diastolicPressureGt,Long diastolicPressureLt,Long toTime) throws BizException;
	
	public List<EjlHealthBloodPressure> getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(EjlHealthBloodPressureAlarmPram ejlHealthBloodPressureAlarmPram) throws BizException ;

	public List<Map<String, Long>> getLastestUserIdDeviceIdListByCreateTime(Date fromTime,Date toTime) throws Exception ;
	
}
