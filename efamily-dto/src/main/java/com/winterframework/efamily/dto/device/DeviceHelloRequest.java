package com.winterframework.efamily.dto.device;

public class DeviceHelloRequest{
	private String imei;	//IMEI
	private String imsi;	//IMSI
	private Integer type;	//设备类型
	private String token;   //访问令牌
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
