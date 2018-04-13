/**   
* @Title: Location.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-11 下午5:58:01 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

/** 
* @ClassName: Location 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-11 下午5:58:01 
*  
*/
public class Location {
	private String lng;
	private String lat;
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
}
