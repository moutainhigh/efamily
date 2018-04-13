package com.winterframework.efamily.dto.device;

import java.util.Map;



/**
 * 设备农历初始化请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceLunarInitRequest{
	private Map<String,String> zodiac;

	public Map<String, String> getZodiac() {
		return zodiac;
	}

	public void setZodiac(Map<String, String> zodiac) {
		this.zodiac = zodiac;
	}

}
