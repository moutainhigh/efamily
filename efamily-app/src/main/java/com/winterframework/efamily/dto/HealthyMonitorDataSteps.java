package com.winterframework.efamily.dto;

/** 
* @ClassName: HealthyMonitorDataSteps 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午3:12:43 
*  
*/
public class HealthyMonitorDataSteps {
	private long steps;
	private long calorie;
	private long mileage;
	public Long getSteps() {
		return steps;
	}
	public void setSteps(Long steps) {
		this.steps = steps;
	}
	public Long getCalorie() {
		return calorie;
	}
	public void setCalorie(Long calorie) {
		this.calorie = calorie;
	}
	public Long getMileage() {
		return mileage;
	}
	public void setMileage(Long mileage) {
		this.mileage = mileage;
	}
	
}
