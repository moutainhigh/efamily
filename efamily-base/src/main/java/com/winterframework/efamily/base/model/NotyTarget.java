package com.winterframework.efamily.base.model;

import com.winterframework.efamily.base.enums.Separator;

/**
 * 消息推送目标
 * @ClassName
 * @Description
 * @author ibm
 * 2015年9月4日
 */
public class NotyTarget {
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	
	public NotyTarget() {
	}
	public NotyTarget(Long userId, Long deviceId) {
		this.userId = userId;
		this.deviceId = deviceId;
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
	
	public String getKey(){
		String key=userId+"";
		if(null!=deviceId){
			key+=Separator.vertical;
			key+=deviceId;
		}
		return key;
	}

}
