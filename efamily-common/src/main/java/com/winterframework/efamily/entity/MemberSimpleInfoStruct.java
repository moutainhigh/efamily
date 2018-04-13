package com.winterframework.efamily.entity;

public class MemberSimpleInfoStruct {

	private Long orgUserId;//成员ID
	private Long orgId;//机构ID
	private Long userId;//亿家联用户ID
	private String name;//成员名称
	private String idNumber;//身份证编码
	private Integer status;//成员状态
	private Integer sex;//成员性别
	private Integer age;//成员年龄
	private String phoneNumber;//成员手机号码
	private String emergencyContact;//紧急联系人
	private String emergencyContactPhone;//紧急联系人号码
	private String emergencyContactRelation;//紧急联系人关系
	
	
	public Long getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}
	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}
	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}
	@Override
	public String toString() {
		return "MemberSimpleInfoStruct [orgUserId=" + orgUserId + ", orgId="
				+ orgId + ", userId=" + userId + ", name=" + name
				+ ", idNumber=" + idNumber + ", status=" + status + ", sex="
				+ sex + ", age=" + age + ", phoneNumber=" + phoneNumber
				+ ", emergencyContact=" + emergencyContact
				+ ", emergencyContactPhone=" + emergencyContactPhone
				+ ", emergencyContactRelation=" + emergencyContactRelation
				+ "]";
	}


}
