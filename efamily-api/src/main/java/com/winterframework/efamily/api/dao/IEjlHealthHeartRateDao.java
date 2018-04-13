/**   
* @Title: IEjlHealthHeartRateDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午4:18:13 
* @version V1.0   
*/
package com.winterframework.efamily.api.dao;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlComHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlHealthHeartRate;


/** 
* @ClassName: IEjlHealthHeartRateDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:18:13 
*  
*/
public interface IEjlHealthHeartRateDao extends IEjlComHealthHeartRateDao {
	public EjlHealthHeartRate getLastUserHeartRate(Long deviceId,Long userId,Long time) throws BizException;
	 
	public List<EjlHealthHeartRate> getHealthHeartRateByTime(Long deviceId,Long fromTime,Long toTime,Long userId)throws BizException;
}
