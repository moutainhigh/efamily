/**   
* @Title: Pm25.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 上午9:59:21 
* @version V1.0   
*/
package com.winterframework.efamily.institution.dto;

/** 
* @ClassName: Pm25 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 上午9:59:21 
*  
*/
public class Pm25 {

	private String key;
	private String show_desc;
	private String dateTime;
	private String cityName;
	private Pm25Info pm25;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getShow_desc() {
		return show_desc;
	}

	public void setShow_desc(String show_desc) {
		this.show_desc = show_desc;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Pm25Info getPm25() {
		return pm25;
	}

	public void setPm25(Pm25Info pm25) {
		this.pm25 = pm25;
	}
}
