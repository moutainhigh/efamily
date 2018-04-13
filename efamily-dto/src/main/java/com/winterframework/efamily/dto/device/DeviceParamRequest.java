package com.winterframework.efamily.dto.device;

public class DeviceParamRequest{
	private int code;	//编码
	private String value;	//参数值
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}
