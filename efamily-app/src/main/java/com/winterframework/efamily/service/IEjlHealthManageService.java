/**   
* @Title: IEjlHealthManageService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:01:35 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.HealthyMonitorDataStruc;
import com.winterframework.efamily.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.dto.HealthyProfileStruc;
import com.winterframework.efamily.dto.QueryMonitorDataRequest;
import com.winterframework.efamily.entity.EjlHealthStepCount;

/** 
* @ClassName: IEjlHealthManageService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:01:35 
*  
*/
public interface IEjlHealthManageService extends IBaseService<EjlHealthStepCount>{
	public HealthyProfileStruc getHealthyProfilesByFamilyId(Long familyId,Long deviceId)
			throws BizException;

	public void deleteHealthyProfileByUserId(String ownerMobileNumber, Long userId,Long deviceId) throws BizException;

	public HealthyMonitorDataStruc getMonitorDataById(QueryMonitorDataRequest request) throws Exception;
	
	public HealthyMonitorDataStruc getMonitorHeartDataById(QueryMonitorDataRequest request) throws Exception;
	
	public Long getAllDayStepsByUser(Long userId,Date queryDate) throws BizException;
	
	public HealthyMonitorDateSleepResponse getMonitorDataSleepById(QueryMonitorDataRequest request) throws Exception;
	
	public Integer getSleepLockStatus(Long userId,Long deviceId) throws BizException;
}
