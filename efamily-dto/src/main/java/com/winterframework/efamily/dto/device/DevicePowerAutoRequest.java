package com.winterframework.efamily.dto.device;

import java.util.List;


/**
 * 设备自动开关机请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DevicePowerAutoRequest{
	private List<String> ons;	//开机时间列表
	private List<String> offs;	//关机时间列表
	
	public List<String> getOns() {
		return ons;
	}
	public void setOns(List<String> ons) {
		this.ons = ons;
	}
	public List<String> getOffs() {
		return offs;
	}
	public void setOffs(List<String> offs) {
		this.offs = offs;
	}
		
}
