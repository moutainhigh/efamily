package com.winterframework.efamily.dto.device;

public class DeviceHardwareRequest{
	private String imei;	//IMEI
	private String imsi;	//IMSI
	private String model;	//型号
	private Integer type;    //设备类型(1手表 2手环...)
	private Integer simStatus;	//是否有卡(0无 1有)
	
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSimStatus() {
		return simStatus;
	}
	public void setSimStatus(Integer simStatus) {
		this.simStatus = simStatus;
	}	
	
}
