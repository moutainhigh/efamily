/**   
* @Title: WeatherData.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 上午10:50:25 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

import java.util.List;

/** 
* @ClassName: WeatherData 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 上午10:50:25 
*  
*/
public class WeatherData<T> {

	private Realtime realtime;
	private Life life;
	private List<FutherWeather> weather;
	private T pm25;
	private String date;
	private String isForeign;
	public Realtime getRealtime() {
		return realtime;
	}
	public void setRealtime(Realtime realtime) {
		this.realtime = realtime;
	}
	public Life getLife() {
		return life;
	}
	public void setLife(Life life) {
		this.life = life;
	}
	
	public List<FutherWeather> getWeather() {
		return weather;
	}
	public void setWeather(List<FutherWeather> weather) {
		this.weather = weather;
	}
	public T getPm25() {
		return pm25;
	}
	public void setPm25(T pm25) {
		this.pm25 = pm25;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIsForeign() {
		return isForeign;
	}
	public void setIsForeign(String isForeign) {
		this.isForeign = isForeign;
	}
}
