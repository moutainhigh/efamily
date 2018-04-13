package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.OrgUserFamilyDisease;
import com.winterframework.efamily.entity.OrgUserFamilyDiseaseBaseInfo;

public interface IOrgComUserFamilyDiseaseDao  extends IBaseDao<OrgUserFamilyDisease>{ 

	public List<OrgUserFamilyDiseaseBaseInfo> getOrgDiseaseBaseInfo(Long orgId,Long orgUserId,Integer status);
	
}
