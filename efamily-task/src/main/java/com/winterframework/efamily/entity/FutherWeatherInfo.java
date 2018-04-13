/**   
* @Title: FutherWeatherInfo.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 上午9:55:19 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

import java.util.List;

/** 
* @ClassName: FutherWeatherInfo 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 上午9:55:19 
*  
*/
public class FutherWeatherInfo {

	private List dawn;
	private List day;
	private List night;
	public List getDawn() {
		return dawn;
	}
	public void setDawn(List dawn) {
		this.dawn = dawn;
	}
	public List getDay() {
		return day;
	}
	public void setDay(List day) {
		this.day = day;
	}
	public List getNight() {
		return night;
	}
	public void setNight(List night) {
		this.night = night;
	}

	

}
