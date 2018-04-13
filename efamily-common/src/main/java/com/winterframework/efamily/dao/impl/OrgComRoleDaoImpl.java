package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComRoleDao;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;


@Repository("orgComRoleDaoImpl")
public class OrgComRoleDaoImpl<E extends OrgRole> extends BaseDaoImpl<OrgRole> implements IOrgComRoleDao{
		
	public List<OrgRoleBaseInfo> getRoleBaseInfoList(Long orgId){
		OrgRole orgRole = new OrgRole();
		orgRole.setOrgId(orgId);
		orgRole.setStatus(1);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getRoleBaseInfoList"), orgRole);
	}
	
	public List<UrlAuthInfo>  getUrlAuthInfoList(Long roleId){
		return  this.sqlSessionTemplate.selectList(this.getQueryPath("getUrlAuthInfoList"), roleId);
	}
}
