package com.winterframework.efamily.dto.device;




/**
 * 设备天气设置请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceWeatherSettingRequest{
	private String mode;	//模式
	private String content;	//内容
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
