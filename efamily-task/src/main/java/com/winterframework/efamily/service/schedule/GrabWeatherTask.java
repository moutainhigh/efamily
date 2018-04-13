/**   
* @Title: GrabWeatherTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-11 上午11:09:49 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;
import java.util.List;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dao.IEjlUserDao;


import com.winterframework.efamily.service.IEfComWeatherService;
import com.winterframework.efamily.service.IEjlComUserExtendInfoService;
import com.winterframework.efamily.service.IWeatherService;


import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GrabWeatherTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-11 上午11:09:49 
*  
*/
public class GrabWeatherTask {
	private Logger log = LoggerFactory.getLogger(GrabWeatherTask.class);
	@Resource(name = "ejlComLocationDaoImpl")
	private IEjlComLocationDao ejlComLocationDaoImpl;
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;

	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "ejlComWeatherServiceImpl")
	private IEfComWeatherService ejlComWeatherServiceImpl; 
	
	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherServiceImpl; 
	@PropertyConfig(value = "juhe.weather.key")
	private String key;
	
	
	@Resource(name ="ejlComUserExtendInfoServiceImpl")
	private IEjlComUserExtendInfoService ejlComUserExtendInfoDaoImpl;

	public void execute() throws Exception {
		/*EjlUserDevice eEjlUserDevice = new EjlUserDevice();
		eEjlUserDevice.setStatus(1l);
		List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
		Map<String, List<EjlUserDevice>> cityDeviceMap = new HashMap<String, List<EjlUserDevice>>();
		for (EjlUserDevice ejlUserDevice : list) {
			EjlUserExtendInfo  entity = new EjlUserExtendInfo();
			entity.setUserId(ejlUserDevice.getUserId());
			Context  ctx = new Context();
			ctx.set("userId", -1);
			EjlUserExtendInfo result = ejlComUserExtendInfoDaoImpl.selectOneObjByAttribute(ctx, entity);
			if (result != null) {
				String city =result.getCurrentCity(); 
				log.debug("weather-userId="+ejlUserDevice.getUserId()+ "city="+city);
				if(city != null){
					if (cityDeviceMap.get(city) == null) {
						List<EjlUserDevice> listUser = new ArrayList<EjlUserDevice>();
						listUser.add(ejlUserDevice);
						cityDeviceMap.put(city, listUser);
					} else {
						cityDeviceMap.get(city).add(ejlUserDevice);
					}
				}

			}
		}
		
		if (!cityDeviceMap.isEmpty()) {
			for(Entry<String, List<EjlUserDevice>> entry:cityDeviceMap.entrySet()){
				weatherServiceImpl.getWeather(entry.getKey());
			}
		}*/
		List<String> allCity = ejlComUserExtendInfoDaoImpl.getAllCityName();
		if(allCity != null && !allCity.isEmpty()){
			for(String cityName :allCity){
				if(cityName != null){
					try{
					weatherServiceImpl.getWeather(cityName);
					}catch(Exception e){
						log.error("get weather error cityName:"+cityName, e);
					}
				}
			}
		}

	}

}
