package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComUserFamilyDiseaseDao;
import com.winterframework.efamily.entity.OrgUserFamilyDisease;
import com.winterframework.efamily.entity.OrgUserFamilyDiseaseBaseInfo;
import com.winterframework.efamily.service.IOrgComUserFamilyDiseaseService;


@Service("orgComUserFamilyDiseaseServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComUserFamilyDiseaseServiceImpl  extends BaseServiceImpl<IOrgComUserFamilyDiseaseDao,OrgUserFamilyDisease> implements IOrgComUserFamilyDiseaseService {
	@Resource(name="orgComUserFamilyDiseaseDaoImpl")
	private IOrgComUserFamilyDiseaseDao dao;
	@Override
	protected IOrgComUserFamilyDiseaseDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<OrgUserFamilyDiseaseBaseInfo> getOrgDiseaseBaseInfo(Long orgId,Long orgUserId,Integer status){
		return dao.getOrgDiseaseBaseInfo(orgId, orgUserId, status);
	}
	
	public void saveOrgDiseaseBaseInfoList(Context ctx,List<OrgUserFamilyDiseaseBaseInfo> orgUserFamilyDiseaseBaseInfoList) throws BizException{
		List<OrgUserFamilyDisease> orgUserFamilyDiseaseList = new ArrayList<OrgUserFamilyDisease>();
		for(OrgUserFamilyDiseaseBaseInfo baseInfo : orgUserFamilyDiseaseBaseInfoList){
			OrgUserFamilyDisease orgUserFamilyDisease = new OrgUserFamilyDisease();
			orgUserFamilyDisease.setDisease(baseInfo.getDisease());
			orgUserFamilyDisease.setOrgId(baseInfo.getOrgId());
			orgUserFamilyDisease.setRelation(baseInfo.getRelation());
			orgUserFamilyDisease.setStatus(1);
			orgUserFamilyDiseaseList.add(orgUserFamilyDisease);
		}
		save(ctx, orgUserFamilyDiseaseList);
	}
	
}
