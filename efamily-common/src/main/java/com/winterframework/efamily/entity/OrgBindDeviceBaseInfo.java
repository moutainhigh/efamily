package com.winterframework.efamily.entity;

import java.util.Date;

public class OrgBindDeviceBaseInfo {

	private Long orgUserId;//机构用户ID
	private Long userId;//亿家联用户ID
	private String orgUserName;//机构会员名称
	private String deviceName;//设备名称
	private Integer type;//设备类型
	private Long status;//设备状态
	private String imei;//设备编号
	private String battery;//设备电量
	private String  phoneNumber;//设备手机号码
	private Date bindOnOffTime;//设备绑定解绑时间
	public Long getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrgUserName() {
		return orgUserName;
	}
	public void setOrgUserName(String orgUserName) {
		this.orgUserName = orgUserName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getBindOnOffTime() {
		return bindOnOffTime;
	}
	public void setBindOnOffTime(Date bindOnOffTime) {
		this.bindOnOffTime = bindOnOffTime;
	}
	@Override
	public String toString() {
		return "OrgBindDeviceBaseInfo [orgUserId=" + orgUserId + ", userId="
				+ userId + ", orgUserName=" + orgUserName + ", deviceName="
				+ deviceName + ", type=" + type + ", status=" + status
				+ ", imei=" + imei + ", battery=" + battery + ", phoneNumber="
				+ phoneNumber + ", bindOnOffTime=" + bindOnOffTime + "]";
	}
	
}
