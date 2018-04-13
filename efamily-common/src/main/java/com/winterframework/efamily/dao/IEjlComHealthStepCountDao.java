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

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlHealthStepCount;

/** 
* @ClassName: IEjlHealthHeartRateDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:18:13 
*  
*/
public interface IEjlComHealthStepCountDao extends IBaseDao<EjlHealthStepCount> {
	List<EjlHealthStepCount> getListByUserIdDeviceIdBetweenTime(Long userId,Long deviceId,Date fromTime,Date toTime) throws Exception;
}
