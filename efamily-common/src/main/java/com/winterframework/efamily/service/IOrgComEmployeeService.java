package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;

public interface IOrgComEmployeeService  extends IBaseService<OrgEmployee> { 

	public void updateEmployeeRole(Long orgEmployeeId,Long roleId) throws BizException;
	
	public List<OrgEmployeeBaseInfo> getOrgEmployeeBaseInfoPage(Long orgId,Integer start,Integer pagesize,EmployeeManageQueryConditionInfo queryConditionInfo);
	
	public List<AccountNumberBaseInfo> getAccountNumberBaseInfoPage(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber);
	
}
