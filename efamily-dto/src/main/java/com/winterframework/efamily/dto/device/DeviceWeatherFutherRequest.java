package com.winterframework.efamily.dto.device;

import java.util.List;




/**
 * 设备天气请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceWeatherFutherRequest{
	private String curtemperature;	//当前气温	
	private String weather;	//天气
	private String temperature;	//温度范围
	private String wind;	//风力范围
	private String airQuality;	//空气质量
	//private String wetness;	//湿度范围
	private String dress;	//穿衣指数
	private String city;	//城市
	private List<DeviceWeatherRequest> futherList;
	


	public String getCurtemperature() {
	return curtemperature;
	}
	public void setCurtemperature(String curtemperature) {
		this.curtemperature = curtemperature;
	}
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
	/*public String getWetness() {
		return wetness;
	}
	public void setWetness(String wetness) {
		this.wetness = wetness;
	}*/
	public String getDress() {
		return dress;
	}
	public void setDress(String dress) {
		this.dress = dress;
	}
	public String getAirQuality() {
		return airQuality;
	}
	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<DeviceWeatherRequest> getFutherList() {
		return futherList;
	}
	public void setFutherList(List<DeviceWeatherRequest> futherList) {
		this.futherList = futherList;
	}
}
