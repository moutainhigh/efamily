package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.OrgUserFamilyDisease;
import com.winterframework.efamily.entity.OrgUserFamilyDiseaseBaseInfo;

public interface IOrgComUserFamilyDiseaseService  extends IBaseService<OrgUserFamilyDisease> { 

	public List<OrgUserFamilyDiseaseBaseInfo> getOrgDiseaseBaseInfo(Long orgId,Long orgUserId,Integer status);
	
	public void saveOrgDiseaseBaseInfoList(Context ctx,List<OrgUserFamilyDiseaseBaseInfo> orgUserFamilyDiseaseBaseInfoList) throws BizException;
}
