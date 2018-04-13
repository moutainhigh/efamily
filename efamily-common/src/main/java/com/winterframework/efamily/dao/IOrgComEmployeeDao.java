package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;

public interface IOrgComEmployeeDao   extends IBaseDao<OrgEmployee>{ 

	public List<OrgEmployeeBaseInfo> getOrgEmployeeBaseInfoPage(Long orgId,Integer start,Integer pagesize,EmployeeManageQueryConditionInfo queryConditionInfo);
	
	public List<AccountNumberBaseInfo> getAccountNumberBaseInfoPage(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber);

}
