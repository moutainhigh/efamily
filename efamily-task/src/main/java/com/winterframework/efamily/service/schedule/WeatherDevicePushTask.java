/**   
* @Title: GrabWeatherTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-11 上午11:09:49 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Context;

import com.winterframework.efamily.entity.EfUserWeather;
import com.winterframework.efamily.entity.EfWeather;

import com.winterframework.efamily.service.IEfComUserWeatherService;
import com.winterframework.efamily.service.IEfComWeatherService;

import com.winterframework.efamily.service.IWeatherService;


/** 
* @ClassName: GrabWeatherTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-11 上午11:09:49 
*  
*/
public class WeatherDevicePushTask {
	private Logger log = LoggerFactory.getLogger(WeatherDevicePushTask.class);

	@Resource(name = "ejlComWeatherServiceImpl")
	private IEfComWeatherService ejlComWeatherServiceImpl;

	@Resource(name ="efComUserWeatherServiceImpl")
	private IEfComUserWeatherService efComUserWeatherServiceImpl;
	
	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherServiceImpl; 
	

	
	
	public void execute() throws Exception {
		
		/*EfUserWeather entity = new EfUserWeather();
		entity.setStatus(0);
		List<EfUserWeather> list = efComUserWeatherServiceImpl.selectListObjByAttribute(ctx, entity);
		if(list==null||list.isEmpty()){
			return;
		}
		
		this.efUserWeatherListSort(list);
		List<EfUserWeather> resultList = new ArrayList<EfUserWeather>();
		for(EfUserWeather efUserWeather:list){
			if(resultList.contains(efUserWeather)){
				efUserWeather.setStatus(2);
				efComUserWeatherServiceImpl.save(ctx, efUserWeather);
				log.debug("this user weather is repeat user weather id is:"+efUserWeather.getId());
				continue;
			}else{
				resultList.add(efUserWeather);
			}
		}
		Map<String, List<EfUserWeather>> cityDeviceMap = new HashMap<String, List<EfUserWeather>>();
		
		for (EfUserWeather efUserWeather : resultList) {
				String city =efUserWeather.getCityName();
				if(city != null){
					if (cityDeviceMap.get(city) == null) {
						List<EfUserWeather> listUser = new ArrayList<EfUserWeather>();
						listUser.add(efUserWeather);
						cityDeviceMap.put(city, listUser);
					} else {
						cityDeviceMap.get(city).add(efUserWeather);
					}
				}
		}

		if (!cityDeviceMap.isEmpty()) {

			for (Entry<String, List<EfUserWeather>> entry : cityDeviceMap.entrySet()) {
				String cityName = entry.getKey();
				
				//当前城市还没有生成过天气，生成天气数据
				EfWeather isExistWeather = ejlComWeatherServiceImpl.getNewWeatherByCityName(cityName);
				log.debug("wait weather  city is :"+cityName);
				if(isExistWeather != null){
					weatherServiceImpl.sendWeather(entry.getValue(), isExistWeather);
					log.debug("wait weather  city is :"+cityName + " weather id is"+isExistWeather.getId());
				}else{
					log.error("wait send weather city is not presend", cityName);
				}
			}
		}*/
		
		Context ctx = new Context();
		ctx.set("userId", -1);
		
		List<String> cityList = efComUserWeatherServiceImpl.getUnSendCityList();
		if(cityList != null && !cityList.isEmpty()){
			for(String cityName : cityList){
				try{
				EfWeather isExistWeather = ejlComWeatherServiceImpl.getNewWeatherByCityName(cityName);
				EfUserWeather query = new EfUserWeather();
				query.setStatus(0);
				query.setCityName(cityName);
				List<EfUserWeather> list = efComUserWeatherServiceImpl.selectListObjByAttribute(ctx, query);
				log.debug("wait weather  city is :"+cityName);
				if(isExistWeather != null){
					weatherServiceImpl.sendWeather(list, isExistWeather);
					log.debug("wait weather  city is :"+cityName + " weather id is"+isExistWeather.getId());
				}else{
					log.error("wait send weather city is not present", cityName);
				}
				}catch(Exception e){
					log.error("send weather error cityName="+cityName, e);
				}
			}
		}
	}
}
