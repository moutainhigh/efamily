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
import com.winterframework.efamily.entity.EjlHealthStepCount;

/** 
* @ClassName: IEjlHealthManageService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:01:35 
*  
*/
public interface IEjlComHealthStepCountService extends IBaseService<EjlHealthStepCount>{
	List<EjlHealthStepCount> getListByUserIdDeviceIdBetweenTime(Long userId,Long deviceId,Date fromTime,Date toTime) throws BizException;
	
}
