/**   
* @Title: DeviceBatteryRecord.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午3:22:58 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

import java.util.Date;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/** 
* @ClassName: DeviceBatteryRecord 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午3:22:58 
*  
*/
public class DeviceBatteryRecord extends FmlBaseEntity{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1232465767886l;
	
	private Long deviceId;
	private Integer value;
	private Long time;
	private Integer level;
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
