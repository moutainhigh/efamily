package com.winterframework.efamily.institution.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dto.EmployeeLoginInfo;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.institution.dto.EmployeeInfoStruct;
import com.winterframework.efamily.institution.dto.EmployeeOffWorkAndLeaveInfo;

public interface IEmployeeManageService {

	public List<EfOrg> getEmployeeCanOptOrgList(Long orgEmployeeId) throws BizException;
	
	public List<OrgEmployeeBaseInfo> getOrgEmployeeBaseInfoList(Long orgId,Integer start,EmployeeManageQueryConditionInfo queryConditionInfo);
	
	public List<AccountNumberBaseInfo> getAccountNumberBaseInfoPage(Long orgId,Integer start,String queryValue);
	
 	public AccountNumberBaseInfo getAccountNumberBaseInfo(Long orgEmployeeId) throws BizException;
	
	public OrgEmployee getOrgEmployee(Long orgEmployeeId) throws BizException;
 	
	public List<OrgRole> getOrgRoleList() throws BizException;
	
	public String getRoleNameBy(Long roleId) throws BizException;
	
 	public boolean updateEmployeePassword(Long employeeId,String passwordOld,String passwordNew) throws BizException;
 	
	public EmployeeInfoStruct getOrgEmployeeBaseInfo(Long orgEmployeeId) throws BizException;
	
	public OrgEmployee transferFromQueryData(EmployeeInfoStruct employeeInfoStruct);
	
	public boolean saveOrUpdateOrgEmployee(EmployeeInfoStruct employeeInfoStruct) throws BizException;
	
	public boolean offWorkOrLeaveForEmployee(EmployeeOffWorkAndLeaveInfo updateInfo) throws BizException;
	
	public OrgEmployee getEmployeeByPhoneNumber(String phoneNumber) throws BizException;
	
	public EmployeeLoginInfo employeeLogin(String phoneNumber,String password) throws BizException;
	
	public void openAccountForEmployee(Long orgEmployeeId,Long roleId,String openOrgIds) throws BizException;
	
	public List<EfOrg> getEmployeeCanOptOrgByAddressList(Long orgEmployeeId,String province,String city,String county) throws BizException;
	
}
