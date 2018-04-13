package com.winterframework.efamily.dto;

/** 
* @ClassName: HealthyMonitorDataSteps 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午3:12:43 
*  
*/
public class HealthyMonitorDataHeart {

	private Long rate;
	private Long timeSpan;
	
	public Long getRate() {
		return rate;
	}
	public void setRate(Long rate) {
		this.rate = rate;
	}
	public Long getTimeSpan() {
		return timeSpan;
	}
	public void setTimeSpan(Long timeSpan) {
		this.timeSpan = timeSpan;
	}
	
}
