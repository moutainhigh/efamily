 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;



import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class OrgUserHealthyFile  extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "OrgUserHealthyFile";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_EMPLOYEE_ID = "用户ID";
	public static final String ALIAS_HEAD_IMG = "头像url资源ID";
	public static final String ALIAS_STATUS = "状态 : 0 初始导入 1 在职 2 请假 9 离职 ";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_SEX = "性别 0男 1女";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_AGE = "年龄";
	public static final String ALIAS_HEIGHT = "身高 单位cm";
	public static final String ALIAS_WEIGHT = "体重 kg";
	public static final String ALIAS_BLOOD_TYPE = "血型";
	public static final String ALIAS_PHONE_NUMBER = "电话号码";
	public static final String ALIAS_ID_NUMBER = "身份证编码";
	public static final String ALIAS_VISION_RIGHT = "视力右";
	public static final String ALIAS_VISION_LEFT = "视力左";
	public static final String ALIAS_HEARING_RIGHT = "听力右";
	public static final String ALIAS_HEARING_LEFT = "听力左";
	public static final String ALIAS_SLEEP_STATUS = "睡眠情况";
	public static final String ALIAS_DRUG_ALLERGY = "药物过敏";
	public static final String ALIAS_DRUG_USE_RECORD = "药物使用记录";
	public static final String ALIAS_EXPOSURE_HISTORY = "暴露史";
	public static final String ALIAS_OPERATION_HISTORY = "手术史";
	public static final String ALIAS_DISEASE = "疾病";
	public static final String ALIAS_FAMILY_DISEASE_HISTORY = "家庭疾病史";
	public static final String ALIAS_EXERCISE_FREQUENCY = "锻炼频率";
	public static final String ALIAS_EXERCISE_TIME = "锻炼时间";
	public static final String ALIAS_EXERCISE_CONTENT = "锻炼内容";
	public static final String ALIAS_SMOKE_FREQUENCY = "吸烟频率";
	public static final String ALIAS_SMOKE_STATUS = "吸烟状况";
	public static final String ALIAS_SMOKE_AMOUNT_PER_DAY = "日吸烟量";
	public static final String ALIAS_SMOKE_LENGTH = "烟龄";
	public static final String ALIAS_DRINK_FREQUENCY = "饮酒频率";
	public static final String ALIAS_DRINK_TYPE = "饮酒类型";
	public static final String ALIAS_DRINK_AMOUNT_PER_DAY = "日饮酒量";
	public static final String ALIAS_DRINK_LENGTH = "酒龄";
	public static final String ALIAS_DIET_FREQUENCY = "饮食频率";
	public static final String ALIAS_DIET_HABITS = "饮食习惯";
	public static final String ALIAS_DIET_TIME = "饮食时间";
	public static final String ALIAS_HEART_RATE_DIAGNOSTIC_SUMMARY = "心率诊断总结";
	public static final String ALIAS_HEART_RATE_INTERVENTION_PROGRAM = "心率干预计划";
	public static final String ALIAS_BLOOD_PRESSURE_DIAGNOSTIC_SUMMARY = "血压诊断总结";
	public static final String ALIAS_BLOOD_PRESSURE_INTERVENTION_PROGRAM = "血压干预计划";
	public static final String ALIAS_BLOOD_SUGAR_DIAGNOSTIC_SUMMARY = "血糖诊断总结";
	public static final String ALIAS_BLOOD_SUGAR_INTERVENTION_PROGRAM = "血糖干预计划";
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
	private Long employeeId;
	private String headImg;
	private Integer status;
	private String birthday;
	private Integer sex;
	private String name;
	private Integer age;
	private Long height;
	private Long weight;
	private String bloodType;
	private String phoneNumber;
	private String idNumber;
	private String visionRight;
	private String visionLeft;
	private String hearingRight;
	private String hearingLeft;
	private String sleepStatus;
	private String drugAllergy;
	private String drugUseRecord;
	private String exposureHistory;
	private String operationHistory;
	private String disease;
	private String familyDiseaseHistory;
	private String exerciseFrequency;
	private String exerciseTime;
	private String exerciseContent;
	private String smokeFrequency;
	private String smokeStatus;
	private String smokeAmountPerDay;
	private String smokeLength;
	private String drinkFrequency;
	private String drinkType;
	private String drinkAmountPerDay;
	private String drinkLength;
	private String dietFrequency;
	private String dietHabits;
	private String dietTime;
	private String heartRateDiagnosticSummary;
	private String heartRateInterventionProgram;
	private String bloodPressureDiagnosticSummary;
	private String bloodPressureInterventionProgram;
	private String bloodSugarDiagnosticSummary;
	private String bloodSugarInterventionProgram;

	//columns END

	public OrgUserHealthyFile(){
	}

	public OrgUserHealthyFile(
		Long id
	){
		this.id = id;
	}

	public void setOrgId(Long value) {
		this.orgId = value;
	}
	
	public Long getOrgId() {
		return this.orgId;
	}
	public void setEmployeeId(Long value) {
		this.employeeId = value;
	}
	
	public Long getEmployeeId() {
		return this.employeeId;
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
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
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
	public void setBloodType(String value) {
		this.bloodType = value;
	}
	
	public String getBloodType() {
		return this.bloodType;
	}
	public void setPhoneNumber(String value) {
		this.phoneNumber = value;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setIdNumber(String value) {
		this.idNumber = value;
	}
	
	public String getIdNumber() {
		return this.idNumber;
	}
	public void setVisionRight(String value) {
		this.visionRight = value;
	}
	
	public String getVisionRight() {
		return this.visionRight;
	}
	public void setVisionLeft(String value) {
		this.visionLeft = value;
	}
	
	public String getVisionLeft() {
		return this.visionLeft;
	}
	public void setHearingRight(String value) {
		this.hearingRight = value;
	}
	
	public String getHearingRight() {
		return this.hearingRight;
	}
	public void setHearingLeft(String value) {
		this.hearingLeft = value;
	}
	
	public String getHearingLeft() {
		return this.hearingLeft;
	}
	public void setSleepStatus(String value) {
		this.sleepStatus = value;
	}
	
	public String getSleepStatus() {
		return this.sleepStatus;
	}
	public void setDrugAllergy(String value) {
		this.drugAllergy = value;
	}
	
	public String getDrugAllergy() {
		return this.drugAllergy;
	}
	public void setDrugUseRecord(String value) {
		this.drugUseRecord = value;
	}
	
	public String getDrugUseRecord() {
		return this.drugUseRecord;
	}
	public void setExposureHistory(String value) {
		this.exposureHistory = value;
	}
	
	public String getExposureHistory() {
		return this.exposureHistory;
	}
	public void setOperationHistory(String value) {
		this.operationHistory = value;
	}
	
	public String getOperationHistory() {
		return this.operationHistory;
	}
	public void setDisease(String value) {
		this.disease = value;
	}
	
	public String getDisease() {
		return this.disease;
	}
	public void setFamilyDiseaseHistory(String value) {
		this.familyDiseaseHistory = value;
	}
	
	public String getFamilyDiseaseHistory() {
		return this.familyDiseaseHistory;
	}
	public void setExerciseFrequency(String value) {
		this.exerciseFrequency = value;
	}
	
	public String getExerciseFrequency() {
		return this.exerciseFrequency;
	}
	public void setExerciseTime(String value) {
		this.exerciseTime = value;
	}
	
	public String getExerciseTime() {
		return this.exerciseTime;
	}
	public void setExerciseContent(String value) {
		this.exerciseContent = value;
	}
	
	public String getExerciseContent() {
		return this.exerciseContent;
	}
	public void setSmokeFrequency(String value) {
		this.smokeFrequency = value;
	}
	
	public String getSmokeFrequency() {
		return this.smokeFrequency;
	}
	public void setSmokeStatus(String value) {
		this.smokeStatus = value;
	}
	
	public String getSmokeStatus() {
		return this.smokeStatus;
	}
	public void setSmokeAmountPerDay(String value) {
		this.smokeAmountPerDay = value;
	}
	
	public String getSmokeAmountPerDay() {
		return this.smokeAmountPerDay;
	}
	public void setSmokeLength(String value) {
		this.smokeLength = value;
	}
	
	public String getSmokeLength() {
		return this.smokeLength;
	}
	public void setDrinkFrequency(String value) {
		this.drinkFrequency = value;
	}
	
	public String getDrinkFrequency() {
		return this.drinkFrequency;
	}
	public void setDrinkType(String value) {
		this.drinkType = value;
	}
	
	public String getDrinkType() {
		return this.drinkType;
	}
	public void setDrinkAmountPerDay(String value) {
		this.drinkAmountPerDay = value;
	}
	
	public String getDrinkAmountPerDay() {
		return this.drinkAmountPerDay;
	}
	public void setDrinkLength(String value) {
		this.drinkLength = value;
	}
	
	public String getDrinkLength() {
		return this.drinkLength;
	}
	public void setDietFrequency(String value) {
		this.dietFrequency = value;
	}
	
	public String getDietFrequency() {
		return this.dietFrequency;
	}
	public void setDietHabits(String value) {
		this.dietHabits = value;
	}
	
	public String getDietHabits() {
		return this.dietHabits;
	}
	public void setDietTime(String value) {
		this.dietTime = value;
	}
	
	public String getDietTime() {
		return this.dietTime;
	}
	public void setHeartRateDiagnosticSummary(String value) {
		this.heartRateDiagnosticSummary = value;
	}
	
	public String getHeartRateDiagnosticSummary() {
		return this.heartRateDiagnosticSummary;
	}
	public void setHeartRateInterventionProgram(String value) {
		this.heartRateInterventionProgram = value;
	}
	
	public String getHeartRateInterventionProgram() {
		return this.heartRateInterventionProgram;
	}
	public void setBloodPressureDiagnosticSummary(String value) {
		this.bloodPressureDiagnosticSummary = value;
	}
	
	public String getBloodPressureDiagnosticSummary() {
		return this.bloodPressureDiagnosticSummary;
	}
	public void setBloodPressureInterventionProgram(String value) {
		this.bloodPressureInterventionProgram = value;
	}
	
	public String getBloodPressureInterventionProgram() {
		return this.bloodPressureInterventionProgram;
	}
	public void setBloodSugarDiagnosticSummary(String value) {
		this.bloodSugarDiagnosticSummary = value;
	}
	
	public String getBloodSugarDiagnosticSummary() {
		return this.bloodSugarDiagnosticSummary;
	}
	public void setBloodSugarInterventionProgram(String value) {
		this.bloodSugarInterventionProgram = value;
	}
	
	public String getBloodSugarInterventionProgram() {
		return this.bloodSugarInterventionProgram;
	}

	@Override
	public String toString() {
		return "OrgUserHealthyFile [orgId=" + orgId + ", employeeId="
				+ employeeId + ", headImg=" + headImg + ", status=" + status
				+ ", birthday=" + birthday + ", sex=" + sex + ", name=" + name
				+ ", age=" + age + ", height=" + height + ", weight=" + weight
				+ ", bloodType=" + bloodType + ", phoneNumber=" + phoneNumber
				+ ", idNumber=" + idNumber + ", visionRight=" + visionRight
				+ ", visionLeft=" + visionLeft + ", hearingRight="
				+ hearingRight + ", hearingLeft=" + hearingLeft
				+ ", sleepStatus=" + sleepStatus + ", drugAllergy="
				+ drugAllergy + ", drugUseRecord=" + drugUseRecord
				+ ", exposureHistory=" + exposureHistory
				+ ", operationHistory=" + operationHistory + ", disease="
				+ disease + ", familyDiseaseHistory=" + familyDiseaseHistory
				+ ", exerciseFrequency=" + exerciseFrequency
				+ ", exerciseTime=" + exerciseTime + ", exerciseContent="
				+ exerciseContent + ", smokeFrequency=" + smokeFrequency
				+ ", smokeStatus=" + smokeStatus + ", smokeAmountPerDay="
				+ smokeAmountPerDay + ", smokeLength=" + smokeLength
				+ ", drinkFrequency=" + drinkFrequency + ", drinkType="
				+ drinkType + ", drinkAmountPerDay=" + drinkAmountPerDay
				+ ", drinkLength=" + drinkLength + ", dietFrequency="
				+ dietFrequency + ", dietHabits=" + dietHabits + ", dietTime="
				+ dietTime + ", heartRateDiagnosticSummary="
				+ heartRateDiagnosticSummary
				+ ", heartRateInterventionProgram="
				+ heartRateInterventionProgram
				+ ", bloodPressureDiagnosticSummary="
				+ bloodPressureDiagnosticSummary
				+ ", bloodPressureInterventionProgram="
				+ bloodPressureInterventionProgram
				+ ", bloodSugarDiagnosticSummary="
				+ bloodSugarDiagnosticSummary
				+ ", bloodSugarInterventionProgram="
				+ bloodSugarInterventionProgram + "]";
	}

}

