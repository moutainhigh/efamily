package com.winterframework.efamily.institution.service.impl;

import javax.annotation.Resource;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.OrgUserFamilyDisease;
import com.winterframework.efamily.entity.OrgUserHealthyFile;
import com.winterframework.efamily.entity.OrgUserHealthyFileBaseInfo;
import com.winterframework.efamily.institution.service.IHealthyFileManageService;
import com.winterframework.efamily.service.IOrgComUserFamilyDiseaseService;
import com.winterframework.efamily.service.IOrgComUserHealthyFileService;


public class HealthyFileManageServiceImpl implements IHealthyFileManageService{

	@Resource(name="orgComUserHealthyFileServiceImpl")
	private IOrgComUserHealthyFileService orgComUserHealthyFileServiceImpl;
	
	@Resource(name="orgComUserFamilyDiseaseServiceImpl")
	private IOrgComUserFamilyDiseaseService orgComUserFamilyDiseaseServiceImpl;

	//*** 增加用户健康档案
	public void add(Context ctx,OrgUserHealthyFile orgUserHealthyFile,OrgUserFamilyDisease orgUserFamilyDisease) throws BizException{
		
		orgUserHealthyFile.setStatus(1);
		orgUserFamilyDisease.setUpdateTime(null);
		orgUserFamilyDisease.setUpdatorId(null);
		orgComUserHealthyFileServiceImpl.save(ctx, orgUserHealthyFile);
		
		orgUserFamilyDisease.setStatus(1);
		orgUserFamilyDisease.setUpdateTime(null);
		orgUserFamilyDisease.setUpdatorId(null);
	    orgComUserFamilyDiseaseServiceImpl.save(ctx, orgUserFamilyDisease);
		
		
	}
	

	//*** 删除用户健康档案
	public void delete(){
		
	}

	//*** 修改用户健康档案
	public void update(){
		
	}
	
	//*** 查询用户健康档案
	public void get(){
		
	}
	
	
	public OrgUserHealthyFile baseInfoToOrgUserHealthyFile(OrgUserHealthyFileBaseInfo baseInfo){
		OrgUserHealthyFile healthyFile = new OrgUserHealthyFile();
		healthyFile.setOrgId(baseInfo.getOrgId());
		healthyFile.setEmployeeId(baseInfo.getEmployeeId());
		healthyFile.setHeadImg(baseInfo.getHeadImg());
		healthyFile.setStatus(baseInfo.getStatus());
		healthyFile.setBirthday(baseInfo.getBirthday());
		healthyFile.setSex(baseInfo.getSex());
		healthyFile.setName(baseInfo.getName());
		healthyFile.setAge(baseInfo.getAge());
		healthyFile.setHeight(baseInfo.getHeight());
		healthyFile.setWeight(baseInfo.getWeight());
		healthyFile.setBloodType(baseInfo.getBloodType());
		healthyFile.setPhoneNumber(baseInfo.getPhoneNumber());
		healthyFile.setIdNumber(baseInfo.getIdNumber());
		healthyFile.setVisionRight(baseInfo.getVisionRight());
		healthyFile.setVisionLeft(baseInfo.getVisionLeft());
		healthyFile.setHearingRight(baseInfo.getHearingRight());
		healthyFile.setHearingLeft(baseInfo.getHearingLeft());
		healthyFile.setSleepStatus(baseInfo.getSleepStatus());
		healthyFile.setDrugAllergy(baseInfo.getDrugAllergy());
		healthyFile.setDrugUseRecord(baseInfo.getDrugUseRecord());
		healthyFile.setExposureHistory(baseInfo.getExposureHistory());
		healthyFile.setOperationHistory(baseInfo.getOperationHistory());
		healthyFile.setDisease(baseInfo.getDisease());
		healthyFile.setFamilyDiseaseHistory(baseInfo.getFamilyDiseaseHistory());
		healthyFile.setExerciseFrequency(baseInfo.getExerciseFrequency());
		healthyFile.setExerciseTime(baseInfo.getExerciseTime());
		healthyFile.setExerciseContent(baseInfo.getExerciseContent());
		healthyFile.setSmokeFrequency(baseInfo.getSmokeFrequency());
		healthyFile.setSmokeStatus(baseInfo.getSmokeStatus());
		healthyFile.setSmokeAmountPerDay(baseInfo.getSmokeAmountPerDay());
		healthyFile.setSmokeLength(baseInfo.getSmokeLength());
		healthyFile.setDrinkFrequency(baseInfo.getDrinkFrequency());
		healthyFile.setDrinkType(baseInfo.getDrinkType());
		healthyFile.setDrinkAmountPerDay(baseInfo.getDrinkAmountPerDay());
		healthyFile.setDrinkLength(baseInfo.getDrinkLength());
		healthyFile.setDietFrequency(baseInfo.getDietFrequency());
		healthyFile.setDietHabits(baseInfo.getDietHabits());
		healthyFile.setDietTime(baseInfo.getDietTime());
		healthyFile.setHeartRateDiagnosticSummary(baseInfo.getHeartRateDiagnosticSummary());
		healthyFile.setHeartRateInterventionProgram(baseInfo.getHeartRateInterventionProgram());
		healthyFile.setBloodPressureDiagnosticSummary(baseInfo.getBloodPressureDiagnosticSummary());
		healthyFile.setBloodPressureInterventionProgram(baseInfo.getBloodPressureInterventionProgram());
		healthyFile.setBloodSugarDiagnosticSummary(baseInfo.getBloodSugarDiagnosticSummary());
		healthyFile.setBloodSugarInterventionProgram(baseInfo.getBloodSugarInterventionProgram());
		
		return healthyFile;
	}
	
}
