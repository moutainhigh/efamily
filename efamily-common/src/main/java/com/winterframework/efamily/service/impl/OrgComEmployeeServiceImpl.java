package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComEmployeeDao;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;
import com.winterframework.efamily.service.IOrgComEmployeeService;


@Service("orgComEmployeeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComEmployeeServiceImpl  extends BaseServiceImpl<IOrgComEmployeeDao,OrgEmployee> implements IOrgComEmployeeService {
	@Resource(name="orgComEmployeeDaoImpl")
	private IOrgComEmployeeDao dao;
	@Override
	protected IOrgComEmployeeDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<OrgEmployeeBaseInfo> getOrgEmployeeBaseInfoPage(Long orgId,Integer start,Integer pagesize,EmployeeManageQueryConditionInfo queryConditionInfo){
		return dao.getOrgEmployeeBaseInfoPage(orgId, start, pagesize, queryConditionInfo);
	}
	
	public List<AccountNumberBaseInfo> getAccountNumberBaseInfoPage(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber){

		return dao.getAccountNumberBaseInfoPage(orgId, start, pagesize,name,phoneNumber);
	}
	
	public void updateEmployeeRole(Long orgEmployeeId,Long roleId) throws BizException{
		OrgEmployee orgEmployee = new OrgEmployee();
		orgEmployee.setId(orgEmployeeId);
		orgEmployee.setRoleId(roleId);
		Context ctx = new Context();
		ctx.set("userId", -1);
		save(ctx, orgEmployee);
	}
}
