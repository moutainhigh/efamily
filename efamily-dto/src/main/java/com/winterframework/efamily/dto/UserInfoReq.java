package com.winterframework.efamily.dto;

public class UserInfoReq {

	private String imei;
	private String devicePhone;
	private String name;
	private Integer sex;
	private Double weight;
	private Integer height;
	private Integer age;
	private String guardianPhone;
	private Integer guardianRelation;
	private Integer operType;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getDevicePhone() {
		return devicePhone;
	}
	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGuardianPhone() {
		return guardianPhone;
	}
	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}
	public Integer getGuardianRelation() {
		return guardianRelation;
	}
	public void setGuardianRelation(Integer guardianRelation) {
		this.guardianRelation = guardianRelation;
	}
	public Integer getOperType() {
		return operType;
	}
	public void setOperType(Integer operType) {
		this.operType = operType;
	}
	
	
}
