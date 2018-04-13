package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComEmployeeAuthDao;
import com.winterframework.efamily.entity.OrgEmployeeAuth;
import com.winterframework.efamily.service.IOrgComEmployeeAuthService;

@Service("orgComEmployeeAuthServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComEmployeeAuthServiceImpl  extends BaseServiceImpl<IOrgComEmployeeAuthDao,OrgEmployeeAuth> implements IOrgComEmployeeAuthService {
	@Resource(name="orgComEmployeeAuthDaoImpl")
	private IOrgComEmployeeAuthDao dao;
	@Override
	protected IOrgComEmployeeAuthDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public void openOrgForEmployee(Long orgEmployeeId,String openOrgIds) throws BizException{
		String[] openOrgIdsArr = openOrgIds.split(",");
		for(String orgIdStr : openOrgIdsArr){
			Long openOrgId = Long.valueOf(orgIdStr); 
			OrgEmployeeAuth orgEmployeeAuth = new OrgEmployeeAuth();
			orgEmployeeAuth.setOrgEmployeeId(orgEmployeeId);
			orgEmployeeAuth.setOrgId(openOrgId);
			orgEmployeeAuth.setStatus(1);
			orgEmployeeAuth.setRemark("开通账号");
			Context ctx = new Context();
			ctx.set("userId", -1);
			save(ctx, orgEmployeeAuth);
		}

	}

}
