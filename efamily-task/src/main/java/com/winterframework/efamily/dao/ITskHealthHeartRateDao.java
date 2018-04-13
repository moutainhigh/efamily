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
import com.winterframework.efamily.entity.EjlHealthHeartRate;


 
public interface ITskHealthHeartRateDao extends IEjlComHealthHeartRateDao {
	/**
	 * 根据上传时间获取最新的设备信息
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getLastestUserIdDeviceIdListByCreateTime(Date fromTime,Date toTime) throws Exception;
	/**
	 * 指定时间区间获取用户异常的心率列表
	 * @param userId
	 * @param deviceId
	 * @param low
	 * @param high
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws BizException
	 */
	List<EjlHealthHeartRate> getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(Long userId,Long deviceId,Integer low,Integer high,Date fromTime,Date toTime) throws Exception;
}
