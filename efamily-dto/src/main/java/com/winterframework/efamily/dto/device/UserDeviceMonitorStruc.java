/**   
* @Title: DeviceStepStruc.java 
* @Package com.winterframework.efamily.dto.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-8-4 下午2:34:31 
* @version V1.0   
*/
package com.winterframework.efamily.dto.device;

import java.util.Date;

/** 
* @ClassName: UserDeviceMonitorStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author denny 
* @date 2015-11-19 下午2:34:31 
*  
*/
public class UserDeviceMonitorStruc {
	
	private Long deviceUserId;
	private Date startTime;
	private Date endTime;
	private Long userId;
	private Long deviceId;
	private Integer status;
	public Long getDeviceUserId() {
		return deviceUserId;
	}
	public void setDeviceUserId(Long deviceUserId) {
		this.deviceUserId = deviceUserId;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
