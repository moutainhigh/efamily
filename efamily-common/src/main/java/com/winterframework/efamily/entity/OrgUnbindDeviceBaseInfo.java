package com.winterframework.efamily.entity;

public class OrgUnbindDeviceBaseInfo {

	private Long orgDeviceId;//机构设备对应的ID
	private String imei;//设备编码
	private Integer type;//1 手表  2 手环
	private Long status;//设备状态
	
	
	public Long getOrgDeviceId() {
		return orgDeviceId;
	}
	public void setOrgDeviceId(Long orgDeviceId) {
		this.orgDeviceId = orgDeviceId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
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
	@Override
	public String toString() {
		return "OrgUnbindDeviceBaseInfo [orgDeviceId=" + orgDeviceId
				+ ", imei=" + imei + ", type=" + type + ", status=" + status
				+ "]";
	}
	
}
