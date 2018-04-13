package com.winterframework.efamily.dto;

public class HealthlyUserRequest {

	private Long userId;
	private Long deviceType;
	private String icon;
	private String name;
	private Long deviceId;
	private Long familyId;
	private String familyName;
	private String moduleType;
	private Integer orgTag;
	private String code;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public Integer getOrgTag() {
		return orgTag;
	}
	public void setOrgTag(Integer orgTag) {
		this.orgTag = orgTag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
