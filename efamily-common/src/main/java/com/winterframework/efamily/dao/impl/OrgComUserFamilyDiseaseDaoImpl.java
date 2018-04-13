package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComUserFamilyDiseaseDao;
import com.winterframework.efamily.entity.OrgUserFamilyDisease;
import com.winterframework.efamily.entity.OrgUserFamilyDiseaseBaseInfo;


@Repository("orgComUserFamilyDiseaseDaoImpl")
public class OrgComUserFamilyDiseaseDaoImpl<E extends OrgUserFamilyDisease> extends BaseDaoImpl<OrgUserFamilyDisease> implements IOrgComUserFamilyDiseaseDao{
		
	
	public List<OrgUserFamilyDiseaseBaseInfo> getOrgDiseaseBaseInfo(Long orgId,Long orgUserId,Integer status) {
		OrgUserFamilyDisease orgUserFamilyDisease = new OrgUserFamilyDisease();
		orgUserFamilyDisease.setOrgId(orgId);
		orgUserFamilyDisease.setOrgUserId(orgUserId);
		orgUserFamilyDisease.setStatus(status);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserHeartRateAndBloodPressureList"), orgUserFamilyDisease);
	}
	
}
