/**   
* @Title: IEfComWeatherService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 下午4:12:51 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfWeather;

/** 
* @ClassName: IEfComWeatherService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 下午4:12:51 
*  
*/
public interface IEfComWeatherService extends IBaseService<EfWeather> {
	public void deleteAll() throws Exception;
	
	public EfWeather getWeatherByCityName(String cityName) throws Exception;
	public int update(EfWeather efWeather) throws Exception;
	public void saveOne(EfWeather efWeather) throws Exception;
	public EfWeather getNewWeatherByCityName(String cityName) throws Exception;
	
	public void saveFutherOne(EfWeather efWeather) throws Exception;
	
	public List<EfWeather> getFutherWeather(String cityName,Date beginDate,Integer limit) throws Exception;
}
