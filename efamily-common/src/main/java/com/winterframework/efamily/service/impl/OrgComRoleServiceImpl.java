package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComRoleDao;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.service.IOrgComRoleService;


@Service("orgComRoleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComRoleServiceImpl  extends BaseServiceImpl<IOrgComRoleDao,OrgRole> implements IOrgComRoleService {
	@Resource(name="orgComRoleDaoImpl")
	private IOrgComRoleDao dao;
	@Override
	protected IOrgComRoleDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<OrgRoleBaseInfo> getRoleBaseInfoList(Long orgId){
		return dao.getRoleBaseInfoList(orgId);
	}
	
	public List<UrlAuthInfo>  getUrlAuthInfoList(Long roleId){
		return dao.getUrlAuthInfoList(roleId);
	}
	
}
