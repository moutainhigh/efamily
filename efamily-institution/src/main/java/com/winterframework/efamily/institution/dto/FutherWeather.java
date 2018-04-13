/**   
* @Title: FutherWeather.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 上午9:52:27 
* @version V1.0   
*/
package com.winterframework.efamily.institution.dto;

/** 
* @ClassName: FutherWeather 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 上午9:52:27 
*  
*/
public class FutherWeather {

	private String date;
	private String week;
	private String nongli;
	private FutherWeatherInfo info;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getNongli() {
		return nongli;
	}
	public void setNongli(String nongli) {
		this.nongli = nongli;
	}
	public FutherWeatherInfo getInfo() {
		return info;
	}
	public void setInfo(FutherWeatherInfo info) {
		this.info = info;
	}
	
	
}
