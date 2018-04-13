/**   
* @Title: WeatherPushStruc.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 下午7:43:00 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

/** 
* @ClassName: WeatherPushStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 下午7:43:00 
*  
*/
public class WeatherPushStruc {
	private String weather;
	private String temperature;
	private String wind;
	private String dress;
	private String curtemperature;
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getDress() {
		return dress;
	}
	public void setDress(String dress) {
		this.dress = dress;
	}
	public String getCurtemperature() {
		return curtemperature;
	}
	public void setCurtemperature(String curtemperature) {
		this.curtemperature = curtemperature;
	}
	
}
