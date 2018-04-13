package com.winterframework.efamily.institution.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.OrgRoleUrl;
import com.winterframework.efamily.entity.OrgUrl;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.institution.dto.EmployeeRoleAuthInfo;
import com.winterframework.efamily.institution.dto.OrgRoleDto;
import com.winterframework.efamily.institution.service.IRoleAndAuthMangeService;
import com.winterframework.efamily.service.IOrgComEmployeeService;
import com.winterframework.efamily.service.IOrgComRoleService;
import com.winterframework.efamily.service.IOrgComRoleUrlService;
import com.winterframework.efamily.service.IOrgComUrlService;

@Service("roleAndAuthMangeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class RoleAndAuthMangeServiceImpl implements IRoleAndAuthMangeService{

	
	@Resource(name="orgComEmployeeServiceImpl")
	private IOrgComEmployeeService orgComEmployeeServiceImpl;
	
	@Resource(name="orgComRoleServiceImpl")
	private IOrgComRoleService orgComRoleServiceImpl;
	
	@Resource(name="orgComRoleUrlServiceImpl")
	private IOrgComRoleUrlService orgComRoleUrlServiceImpl;
	
	@Resource(name="orgComUrlServiceImpl")
	private IOrgComUrlService orgComUrlServiceImpl;
	
	public List<OrgRoleBaseInfo> getOrgRoleList(Long orgId){
		return orgComRoleServiceImpl.getRoleBaseInfoList(orgId);
	}
	
	public OrgRole addOrUpdateOrgRoleDto(OrgRoleDto orgRoleDto) throws BizException{
		OrgRole orgRole = new OrgRole();
		orgRole.setId(orgRoleDto.getOrgRoleId());
		orgRole.setOrgId(orgRoleDto.getOrgId());
		orgRole.setRemark(orgRoleDto.getRemark());
		orgRole.setName(orgRoleDto.getName());
		orgRole.setStatus(1);
		Context ctx = new Context();
		ctx.set("userId", -1);
		orgComRoleServiceImpl.save(ctx, orgRole);
		return orgRole;
	}
	
	
	public EmployeeRoleAuthInfo getEmployeeRoleAuthInfo(Long orgRoleId) throws BizException{
		EmployeeRoleAuthInfo employeeRoleAuthInfo = new EmployeeRoleAuthInfo();
		OrgRole orgRole = orgComRoleServiceImpl.get(orgRoleId);
		if(orgRole != null){
			String createUser = "系统创建";
			if(orgRole.getCreatorId()>0){
				OrgEmployee orgEmployee = orgComEmployeeServiceImpl.get(orgRole.getCreatorId());
				if(orgEmployee != null){
					createUser = orgEmployee.getName();
				}
			}
			employeeRoleAuthInfo.setCreateTime(DateUtils.format(orgRole.getCreateTime(), DateUtils.DATETIME_FORMAT_PATTERN) );
			employeeRoleAuthInfo.setCreateUser(createUser);
			employeeRoleAuthInfo.setName(orgRole.getName());
			employeeRoleAuthInfo.setOrgId(orgRole.getOrgId());
			employeeRoleAuthInfo.setRemark(orgRole.getRemark());
			employeeRoleAuthInfo.setOrgRoleId(orgRoleId);
			List<UrlAuthInfo> urlAuthInfoList = orgComRoleServiceImpl.getUrlAuthInfoList(orgRoleId);
			if(urlAuthInfoList != null){
				employeeRoleAuthInfo.setUrlAuthInfoList(urlAuthInfoList);
			}
		}
		return employeeRoleAuthInfo;
	}
	
	public List<UrlAuthInfo> getUrlAuthInfoList() throws BizException{
		List<UrlAuthInfo> urlAuthInfoList = new ArrayList<UrlAuthInfo>();
        List<OrgUrl> orgUrlList = orgComUrlServiceImpl.selectListObjByAttribute(new Context(), new OrgUrl());
        for(OrgUrl orgUrl:orgUrlList){
        	UrlAuthInfo urlAuthInfo = new UrlAuthInfo();
        	urlAuthInfo.setName(orgUrl.getName());
        	urlAuthInfo.setStatus(0);
        	urlAuthInfo.setAuthId(orgUrl.getId());
        	urlAuthInfoList.add(urlAuthInfo);
        }
		return urlAuthInfoList;
	}
	
	public void addOrgRoleUrlList(Long orgRoleId,String authIds) throws BizException{
		String[] authIdsArr = authIds.split(",");
		for(String urlIdStr:authIdsArr){
			OrgRoleUrl orgRoleUrl = new OrgRoleUrl();
			orgRoleUrl.setRoleId(orgRoleId);
			orgRoleUrl.setStatus(1);
			orgRoleUrl.setUrlId(Long.valueOf(urlIdStr));
			Context ctx = new Context();
			ctx.set("userId", -1);
			orgComRoleUrlServiceImpl.save(ctx, orgRoleUrl);
		}
		
	}
	
	
	
}
