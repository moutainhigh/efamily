/**   
* @Title: HealthStepCountStruc.java 
* @Package com.winterframework.efamily.dto.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-8-12 下午6:56:46 
* @version V1.0   
*/
package com.winterframework.efamily.dto.device;

import java.util.Date;

/** 
* @ClassName: HealthStepCountStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-8-12 下午6:56:46 
*  
*/
public class HealthStepCountStruc {

	private Long userId;
	private Long steps;
	private Date begintime;
	private Date endtime;
	private Long deviceId;
	private Integer type;
	private Long height;
	private Long calorie;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSteps() {
		return steps;
	}

	public void setSteps(Long steps) {
		this.steps = steps;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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
