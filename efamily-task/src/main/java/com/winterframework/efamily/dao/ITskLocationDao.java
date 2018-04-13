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

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlLocation;


 
public interface ITskLocationDao extends IEjlComLocationDao {
	/**
	 * 根据用户获取时间点之后的定位数据
	 * @param userId
	 * @param deviceId
	 * @param time
	 * @return
	 * @throws BizException
	 */
	List<EjlLocation> getListByUserIdDeviceIdAfterTime(Long userId,Long deviceId,Date time) throws Exception;
}
