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


public class OrgUser extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//alias
		public static final String TABLE_ALIAS = "OrgUser";
		public static final String ALIAS_ORG_ID = "机构ID";
		public static final String ALIAS_USER_ID = "用户ID";
		public static final String ALIAS_NAME = "名字";
		public static final String ALIAS_STATUS = "状态 : 0 初始导入 1 可用 2 删除";
		public static final String ALIAS_BIRTHDAY = "生日";
		public static final String ALIAS_SEX = "性别 0男 1女";
		public static final String ALIAS_AGE = "年龄";
		public static final String ALIAS_HEIGHT = "身高 单位cm";
		public static final String ALIAS_WEIGHT = "体重 kg";
		public static final String ALIAS_PHONE_NUMBER = "电话号码";
		public static final String ALIAS_LIVE_ADDRESS = "居住地址";
		public static final String ALIAS_EMERGENCY_CONTACT = "紧急联系人";
		public static final String ALIAS_EMERGENCY_CONTACT_PHONE = "紧急联系人电话";
		public static final String ALIAS_DIET_TABOO = "饮食禁忌";
		public static final String ALIAS_INTERESTS = "兴趣";
		public static final String ALIAS_HOUSEHOLD_ADDRESS = "户籍地址";
		public static final String ALIAS_GUARDIAN_NAME = "监护人";
		public static final String ALIAS_GUARDIAN_PHONE_NUMBER = "监护人号码";
		public static final String ALIAS_NATIONS = "民族";
		public static final String ALIAS_BLOOD_TYPE = "血型";
		public static final String ALIAS_PROFESSION = "职业";
		public static final String ALIAS_RH_NEGATIVE = "rh阴性";
		public static final String ALIAS_WORK_UNIT = "工作单位";
		public static final String ALIAS_EDUCATION_DEGREE = "教育程度";
		public static final String ALIAS_INCOME_SOURCE = "收入来源";
		public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
		public static final String ALIAS_HEALTH_STATUS = "健康状况";
		public static final String ALIAS_BODY_STATUS = "身体状况";
		public static final String ALIAS_REMARK = "备注";
		public static final String ALIAS_CREATOR_ID = "creatorId";
		public static final String ALIAS_CREATE_TIME = "创建时间";
		public static final String ALIAS_UPDATOR_ID = "updatorId";
		public static final String ALIAS_UPDATE_TIME = "更新时间";
		
		//date formats
				public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
				public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
		
		//columns START
		private Long orgId;
		private Long userId;
		private String name;
		private String idNumber;
		private String headImg;
		private Integer status;
		private String birthday;
		private Integer sex;
		private Integer age;
		private Long height;
		private Long weight;
		private String phoneNumber;
		private String liveAddress;
		private String emergencyContact;
		private String emergencyContactPhone;
		private String emergencyContactRelation;
		private String dietTaboo;
		private String interests;
		private String householdAddress;
		private String guardianName;
		private String guardianPhoneNumber;
		private String guardianRelation;
		private String nations;
		private String bloodType;
		private String profession;
		private String rhNegative;
		private String workUnit;
		private String educationDegree;
		private String incomeSource;
		private String maritalStatus;
		private String healthStatus;
		private String bodyStatus;
		private String remark;
		private Long creatorId;
		private Date createTime;
		private Long updatorId;
		private Date updateTime;

	//columns END

	public OrgUser(){
	}

	public OrgUser(
		Long id
	){
		this.id = id;
	}

	public enum Status{
		UNBIND(0),BIND(1),DELETE(9);
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
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
	public void setLiveAddress(String value) {
		this.liveAddress = value;
	}
	
	public String getLiveAddress() {
		return this.liveAddress;
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
	public void setDietTaboo(String value) {
		this.dietTaboo = value;
	}
	
	public String getDietTaboo() {
		return this.dietTaboo;
	}
	public void setInterests(String value) {
		this.interests = value;
	}
	
	public String getInterests() {
		return this.interests;
	}
	public void setHouseholdAddress(String value) {
		this.householdAddress = value;
	}
	
	public String getHouseholdAddress() {
		return this.householdAddress;
	}
	public void setGuardianName(String value) {
		this.guardianName = value;
	}
	
	public String getGuardianName() {
		return this.guardianName;
	}
	public void setGuardianPhoneNumber(String value) {
		this.guardianPhoneNumber = value;
	}
	
	public String getGuardianPhoneNumber() {
		return this.guardianPhoneNumber;
	}
	
	public String getGuardianRelation() {
		return guardianRelation;
	}

	public void setGuardianRelation(String guardianRelation) {
		this.guardianRelation = guardianRelation;
	}

	public void setNations(String value) {
		this.nations = value;
	}
	
	public String getNations() {
		return this.nations;
	}
	public void setBloodType(String value) {
		this.bloodType = value;
	}
	
	public String getBloodType() {
		return this.bloodType;
	}
	public void setProfession(String value) {
		this.profession = value;
	}
	
	public String getProfession() {
		return this.profession;
	}
	public void setRhNegative(String value) {
		this.rhNegative = value;
	}
	
	public String getRhNegative() {
		return this.rhNegative;
	}
	public void setWorkUnit(String value) {
		this.workUnit = value;
	}
	
	public String getWorkUnit() {
		return this.workUnit;
	}
	public void setEducationDegree(String value) {
		this.educationDegree = value;
	}
	
	public String getEducationDegree() {
		return this.educationDegree;
	}
	public void setIncomeSource(String value) {
		this.incomeSource = value;
	}
	
	public String getIncomeSource() {
		return this.incomeSource;
	}
	public void setMaritalStatus(String value) {
		this.maritalStatus = value;
	}
	
	public String getMaritalStatus() {
		return this.maritalStatus;
	}
	public void setHealthStatus(String value) {
		this.healthStatus = value;
	}
	
	public String getHealthStatus() {
		return this.healthStatus;
	}
	public void setBodyStatus(String value) {
		this.bodyStatus = value;
	}
	
	public String getBodyStatus() {
		return this.bodyStatus;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setCreatorId(Long value) {
		this.creatorId = value;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
	}
	public String getCreateTimeString() {
		return date2String(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(string2Date(value, FORMAT_CREATE_TIME,Date.class));
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdatorId(Long value) {
		this.updatorId = value;
	}
	
	public Long getUpdatorId() {
		return this.updatorId;
	}
	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}

	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}

	@Override
	public String toString() {
		return "OrgUser [orgId=" + orgId + ", userId=" + userId + ", name="
				+ name + ", idNumber=" + idNumber + ", headImg=" + headImg
				+ ", status=" + status + ", birthday=" + birthday + ", sex="
				+ sex + ", age=" + age + ", height=" + height + ", weight="
				+ weight + ", phoneNumber=" + phoneNumber + ", liveAddress="
				+ liveAddress + ", emergencyContact=" + emergencyContact
				+ ", emergencyContactPhone=" + emergencyContactPhone
				+ ", emergencyContactRelation=" + emergencyContactRelation
				+ ", dietTaboo=" + dietTaboo + ", interests=" + interests
				+ ", householdAddress=" + householdAddress + ", guardianName="
				+ guardianName + ", guardianPhoneNumber=" + guardianPhoneNumber
				+ ", guardianRelation=" + guardianRelation + ", nations="
				+ nations + ", bloodType=" + bloodType + ", profession="
				+ profession + ", rhNegative=" + rhNegative + ", workUnit="
				+ workUnit + ", educationDegree=" + educationDegree
				+ ", incomeSource=" + incomeSource + ", maritalStatus="
				+ maritalStatus + ", healthStatus=" + healthStatus
				+ ", bodyStatus=" + bodyStatus + ", remark=" + remark
				+ ", creatorId=" + creatorId + ", createTime=" + createTime
				+ ", updatorId=" + updatorId + ", updateTime=" + updateTime
				+ "]";
	}

}

