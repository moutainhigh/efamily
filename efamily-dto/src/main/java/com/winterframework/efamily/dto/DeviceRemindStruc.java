/**   
* @Title: DeviceRemindStruc.java 
* @Package com.winterframework.efamily.server.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-7 上午10:57:18 
* @version V1.0   
*/
package com.winterframework.efamily.dto;

/** 
* @ClassName: DeviceRemindStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-7 上午10:57:18 
*  
*/
public class DeviceRemindStruc {

	private String type;
	private String content;
	private String name;
	private Long volume;
	private int periodType;
	private Long time;
	private Long endTime;
	private Long id;
	private Long userId;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	public int getPeriodType() {
		return periodType;
	}
	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
