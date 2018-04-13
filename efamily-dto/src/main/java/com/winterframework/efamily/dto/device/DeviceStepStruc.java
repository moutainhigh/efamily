/**   
* @Title: DeviceStepStruc.java 
* @Package com.winterframework.efamily.dto.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-8-4 下午2:34:31 
* @version V1.0   
*/
package com.winterframework.efamily.dto.device;

/** 
* @ClassName: DeviceStepStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-8-4 下午2:34:31 
*  
*/
public class DeviceStepStruc {
	private Long count;
	private Long fromTime;
	private Long toTime;
	private Integer type;
	private Long height;
	private Long calorie;
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getFromTime() {
		return fromTime;
	}
	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}
	public Long getToTime() {
		return toTime;
	}
	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}
	public Long getCalorie() {
		return calorie;
	}
	public void setCalorie(Long calorie) {
		this.calorie = calorie;
	}
	
}
