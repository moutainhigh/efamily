package com.winterframework.efamily.dto;


/** 
* @ClassName: HealthyMonitorDataStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午2:42:18 
*  
*/
public class HealthyMonitorDataUnitStruc{
	private Long userId;
	private Long startTime;
	private Long endTime;
	private Object content;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		return startTime.hashCode() + endTime.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		HealthyMonitorDataUnitStruc o = (HealthyMonitorDataUnitStruc) obj;
		return this.startTime.equals(o.getStartTime()) && this.endTime.equals(o.getEndTime());
	}
}
