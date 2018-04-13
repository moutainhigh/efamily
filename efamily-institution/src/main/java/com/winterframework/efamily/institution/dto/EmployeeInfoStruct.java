package com.winterframework.efamily.institution.dto;

import java.util.Date;

public class EmployeeInfoStruct {
	
	
	private Long orgId;
	private String orgName;
	private Long orgEmployeeId;
	private Long roleId;
	private String name;
	private String password;
	private String headImg;
	private Integer status;
	private Integer loginAuth;
	private String idNumber;
	private String birthday;
	private Integer sex;
	private Integer age;
	private Long height;
	private Long weight;
	private String phoneNumber;
	private String emergencyContact;
	private String emergencyContactPhone;
	private String emergencyContactRelation;
	private String nations;
	private String maritalStatus;
	private String educationDegree;
	private String politicalOutlook;
	private String part;
	private String duty;
	private Date contractStartTime;
	private Date contractEndTime;
	private String contractNumber;
	private String payRange;
	private String householdAddress;
	private String liveAddress;
	private Date entryTime;//入职时间
	private Date leaveTime;//离职时间
	private Date offWorkStartTime;//请假开始时间
	private Date offWorkEndTime;//请假结束时间
	private String createAuthUser;
	private Date createAuthTime;//入职时间
	
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public Long getOrgEmployeeId() {
		return orgEmployeeId;
	}
	public void setOrgEmployeeId(Long orgEmployeeId) {
		this.orgEmployeeId = orgEmployeeId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLoginAuth() {
		return loginAuth;
	}
	public void setLoginAuth(Integer loginAuth) {
		this.loginAuth = loginAuth;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}
	public Long getWeight() {
		return weight;
	}
	public void setWeight(Long weight) {
		this.weight = weight;
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
	public String getNations() {
		return nations;
	}
	public void setNations(String nations) {
		this.nations = nations;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEducationDegree() {
		return educationDegree;
	}
	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}
	public String getPoliticalOutlook() {
		return politicalOutlook;
	}
	public void setPoliticalOutlook(String politicalOutlook) {
		this.politicalOutlook = politicalOutlook;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Date getContractStartTime() {
		return contractStartTime;
	}
	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	public Date getContractEndTime() {
		return contractEndTime;
	}
	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getPayRange() {
		return payRange;
	}
	public void setPayRange(String payRange) {
		this.payRange = payRange;
	}
	public String getHouseholdAddress() {
		return householdAddress;
	}
	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	public Date getOffWorkStartTime() {
		return offWorkStartTime;
	}
	public void setOffWorkStartTime(Date offWorkStartTime) {
		this.offWorkStartTime = offWorkStartTime;
	}
	public Date getOffWorkEndTime() {
		return offWorkEndTime;
	}
	public void setOffWorkEndTime(Date offWorkEndTime) {
		this.offWorkEndTime = offWorkEndTime;
	}
	public String getCreateAuthUser() {
		return createAuthUser;
	}
	public void setCreateAuthUser(String createAuthUser) {
		this.createAuthUser = createAuthUser;
	}
	public Date getCreateAuthTime() {
		return createAuthTime;
	}
	public void setCreateAuthTime(Date createAuthTime) {
		this.createAuthTime = createAuthTime;
	}
	@Override
	public String toString() {
		return "EmployeeInfoStruct [orgId=" + orgId + ", orgName=" + orgName
				+ ", orgEmployeeId=" + orgEmployeeId + ", roleId=" + roleId
				+ ", name=" + name + ", password=" + password + ", headImg="
				+ headImg + ", status=" + status + ", loginAuth=" + loginAuth
				+ ", idNumber=" + idNumber + ", birthday=" + birthday
				+ ", sex=" + sex + ", age=" + age + ", height=" + height
				+ ", weight=" + weight + ", phoneNumber=" + phoneNumber
				+ ", emergencyContact=" + emergencyContact
				+ ", emergencyContactPhone=" + emergencyContactPhone
				+ ", emergencyContactRelation=" + emergencyContactRelation
				+ ", nations=" + nations + ", maritalStatus=" + maritalStatus
				+ ", educationDegree=" + educationDegree
				+ ", politicalOutlook=" + politicalOutlook + ", part=" + part
				+ ", duty=" + duty + ", contractStartTime=" + contractStartTime
				+ ", contractEndTime=" + contractEndTime + ", contractNumber="
				+ contractNumber + ", payRange=" + payRange
				+ ", householdAddress=" + householdAddress + ", liveAddress="
				+ liveAddress + ", entryTime=" + entryTime + ", leaveTime="
				+ leaveTime + ", offWorkStartTime=" + offWorkStartTime
				+ ", offWorkEndTime=" + offWorkEndTime + ", createAuthUser="
				+ createAuthUser + ", createAuthTime=" + createAuthTime + "]";
	}

}
