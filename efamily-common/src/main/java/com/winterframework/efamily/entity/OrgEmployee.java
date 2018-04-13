 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.entity;

import java.util.*;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class OrgEmployee extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgEmployee";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_ROLE_ID = "用户ID";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_HEAD_IMG = "头像url资源ID";
	public static final String ALIAS_STATUS = "状态 : 0 初始导入 1 在职 2 请假 9 离职 ";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_SEX = "性别 0男 1女";
	public static final String ALIAS_AGE = "年龄";
	public static final String ALIAS_HEIGHT = "身高 单位cm";
	public static final String ALIAS_WEIGHT = "体重 kg";
	public static final String ALIAS_PHONE_NUMBER = "电话号码";
	public static final String ALIAS_EMERGENCY_CONTACT = "紧急联系人";
	public static final String ALIAS_EMERGENCY_CONTACT_PHONE = "紧急联系人电话";
	public static final String ALIAS_EMERGENCY_CONTACT_RELATION = "紧急联系人关系";
	public static final String ALIAS_NATIONS = "民族";
	public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
	public static final String ALIAS_EDUCATION_DEGREE = "教育程度";
	public static final String ALIAS_POLITICAL_OUTLOOK = "政治面貌";
	public static final String ALIAS_PART = "工作单位";
	public static final String ALIAS_DUTY = "职务";
	public static final String ALIAS_CONTRACT_START_TIME = "合同开始时间";
	public static final String ALIAS_CONTRACT_END_TIME = "合同结束时间";
	public static final String ALIAS_CONTRACT_NUMBER = "合同编号";
	public static final String ALIAS_PAY_RANGE = "薪金范围";
	public static final String ALIAS_HOUSEHOLD_ADDRESS = "户籍地址";
	public static final String ALIAS_LIVE_ADDRESS = "居住地址";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CONTRACT_START_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_CONTRACT_END_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_CONTRACT_NUMBER = DATE_TIME_FORMAT;
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long orgId;
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
	
	//columns END

	public OrgEmployee(){
	}

	public OrgEmployee(
		Long id
	){
		this.id = id;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setOrgId(Long value) {
		this.orgId = value;
	}
	
	public Long getOrgId() {
		return this.orgId;
	}
	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setHeadImg(String value) {
		this.headImg = value;
	}
	
	public String getHeadImg() {
		return this.headImg;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setBirthday(String value) {
		this.birthday = value;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
	public void setSex(Integer value) {
		this.sex = value;
	}
	
	public Integer getSex() {
		return this.sex;
	}
	public void setAge(Integer value) {
		this.age = value;
	}
	
	public Integer getAge() {
		return this.age;
	}
	public void setHeight(Long value) {
		this.height = value;
	}
	
	public Long getHeight() {
		return this.height;
	}
	public void setWeight(Long value) {
		this.weight = value;
	}
	
	public Long getWeight() {
		return this.weight;
	}
	public void setPhoneNumber(String value) {
		this.phoneNumber = value;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setEmergencyContact(String value) {
		this.emergencyContact = value;
	}
	
	public String getEmergencyContact() {
		return this.emergencyContact;
	}
	public void setEmergencyContactPhone(String value) {
		this.emergencyContactPhone = value;
	}
	
	public String getEmergencyContactPhone() {
		return this.emergencyContactPhone;
	}
	public void setEmergencyContactRelation(String value) {
		this.emergencyContactRelation = value;
	}
	
	public String getEmergencyContactRelation() {
		return this.emergencyContactRelation;
	}
	public void setNations(String value) {
		this.nations = value;
	}
	
	public String getNations() {
		return this.nations;
	}
	public void setMaritalStatus(String value) {
		this.maritalStatus = value;
	}
	
	public String getMaritalStatus() {
		return this.maritalStatus;
	}
	public void setEducationDegree(String value) {
		this.educationDegree = value;
	}
	
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
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

	public String getEducationDegree() {
		return this.educationDegree;
	}
	public void setPoliticalOutlook(String value) {
		this.politicalOutlook = value;
	}
	
	public String getPoliticalOutlook() {
		return this.politicalOutlook;
	}
	public void setPart(String value) {
		this.part = value;
	}
	
	public String getPart() {
		return this.part;
	}
	public void setDuty(String value) {
		this.duty = value;
	}
	
	public String getDuty() {
		return this.duty;
	}
	public String getContractStartTimeString() {
		return date2String(getContractStartTime(), FORMAT_CONTRACT_START_TIME);
	}
	public void setContractStartTimeString(String value) {
		setContractStartTime(string2Date(value, FORMAT_CONTRACT_START_TIME,Date.class));
	}
	
	public void setContractStartTime(Date value) {
		this.contractStartTime = value;
	}
	
	public Date getContractStartTime() {
		return this.contractStartTime;
	}
	public String getContractEndTimeString() {
		return date2String(getContractEndTime(), FORMAT_CONTRACT_END_TIME);
	}
	public void setContractEndTimeString(String value) {
		setContractEndTime(string2Date(value, FORMAT_CONTRACT_END_TIME,Date.class));
	}
	
	public void setContractEndTime(Date value) {
		this.contractEndTime = value;
	}
	
	public Date getContractEndTime() {
		return this.contractEndTime;
	}

	public void setPayRange(String value) {
		this.payRange = value;
	}
	
	public String getPayRange() {
		return this.payRange;
	}
	public void setHouseholdAddress(String value) {
		this.householdAddress = value;
	}
	
	public String getHouseholdAddress() {
		return this.householdAddress;
	}
	public void setLiveAddress(String value) {
		this.liveAddress = value;
	}
	
	public String getLiveAddress() {
		return this.liveAddress;
	}

	@Override
	public String toString() {
		return "OrgEmployee [orgId=" + orgId + ", roleId=" + roleId + ", name="
				+ name + ", password=" + password + ", headImg=" + headImg
				+ ", status=" + status + ", loginAuth=" + loginAuth
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

