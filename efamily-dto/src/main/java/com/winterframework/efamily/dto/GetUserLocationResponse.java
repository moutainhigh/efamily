package com.winterframework.efamily.dto;

public class GetUserLocationResponse {

	private Long deviceId;
	private Long userId;
	private String lat;// 纬度
	private String lng;// 经度
	private Integer type;// 坐标类型,1.GPS; 2.AMAP;
	private Integer needWait;// 是否需要等待 1.YES; 2.NO;
	private String time;// 坐标时间
	private String addressDesc;// 坐标地址描述
	private String precision;// 精度
	private String batteryInfo;//电量信息
	private Integer runningMode;//充电模式
	private Integer onLineStatus;//开关机状态
	
	public Integer getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}
	public Integer getRunningMode() {
		return runningMode;
	}
	public void setRunningMode(Integer runningMode) {
		this.runningMode = runningMode;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBatteryInfo() {
		return batteryInfo;
	}
	public void setBatteryInfo(String batteryInfo) {
		this.batteryInfo = batteryInfo;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNeedWait() {
		return needWait;
	}
	public void setNeedWait(Integer needWait) {
		this.needWait = needWait;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddressDesc() {
		return addressDesc;
	}
	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	
	
	
    
}
