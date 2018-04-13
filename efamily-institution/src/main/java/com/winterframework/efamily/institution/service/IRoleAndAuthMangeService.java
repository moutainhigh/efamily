package com.winterframework.efamily.institution.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.institution.dto.EmployeeRoleAuthInfo;
import com.winterframework.efamily.institution.dto.OrgRoleDto;

public interface IRoleAndAuthMangeService {

	public List<OrgRoleBaseInfo> getOrgRoleList(Long orgId) ;

	public OrgRole addOrUpdateOrgRoleDto(OrgRoleDto orgRoleDto) throws BizException;
	
	public void addOrgRoleUrlList(Long orgRoleId,String authIds) throws BizException;
	
	public EmployeeRoleAuthInfo getEmployeeRoleAuthInfo(Long orgRoleId) throws BizException;
	
	public List<UrlAuthInfo> getUrlAuthInfoList() throws BizException;
}
