package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;

public interface IOrgComRoleService  extends IBaseService<OrgRole> { 

	public List<OrgRoleBaseInfo> getRoleBaseInfoList(Long orgId);
	
	public List<UrlAuthInfo>  getUrlAuthInfoList(Long roleId);
	
}
