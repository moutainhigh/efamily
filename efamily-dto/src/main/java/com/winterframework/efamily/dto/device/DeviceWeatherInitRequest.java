package com.winterframework.efamily.dto.device;

import java.util.List;
import java.util.Map;



/**
 * 设备天气初始化请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceWeatherInitRequest{
	private Map<String,String> weather;
	/*private Map<String,String> wind;
	private Map<String,String> dress;*/
	public Map<String, String> getWeather() {
		return weather;
	}
	public void setWeather(Map<String, String> weather) {
		this.weather = weather;
	}
	/*public Map<String, String> getWind() {
		return wind;
	}
	public void setWind(Map<String, String> wind) {
		this.wind = wind;
	}
	public Map<String, String> getDress() {
		return dress;
	}
	public void setDress(Map<String, String> dress) {
		this.dress = dress;
	}*/
		
	
}
