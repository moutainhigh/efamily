package com.winterframework.efamily.dto.device;



/**
 * 设备定位异常请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceLocationExceptionRequest{
	private int status;	//异常码
	private Long time;	//时间
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	
	
}
