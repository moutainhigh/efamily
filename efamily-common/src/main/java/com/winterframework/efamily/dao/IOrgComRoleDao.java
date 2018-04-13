package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;

public interface IOrgComRoleDao extends IBaseDao<OrgRole>{ 

	public List<OrgRoleBaseInfo> getRoleBaseInfoList(Long orgId);
	
	public List<UrlAuthInfo>  getUrlAuthInfoList(Long roleId);
	
}
