/**   
* @Title: IDeviceBatteryRecordDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午3:40:52 
* @version V1.0   
*/
package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.EfWeather;

/** 
* @ClassName: IDeviceBatteryRecordDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午3:40:52 
*  
*/
public interface IEfComWeatherDao extends IBaseDao<EfWeather>{
	public void deleteAll() throws Exception;
	public EfWeather getWeatherByCityName(String cityName) throws Exception;
	public EfWeather getNewWeatherByCityName(String cityName) throws Exception;
	List<EfWeather> getFutherWeather(String cityName, Date beginDate,Integer limit) throws Exception;
}
