/**   
* @Title: Pm25Info.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 上午10:00:57 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

/** 
* @ClassName: Pm25Info 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 上午10:00:57 
*  
*/
public class Pm25Info {

	private String curPm;
	private String pm25;
	private String pm10;
	private Integer level;
	private String quality;
	private String des;
	public String getCurPm() {
		return curPm;
	}
	public void setCurPm(String curPm) {
		this.curPm = curPm;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public String getPm10() {
		return pm10;
	}
	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
}
