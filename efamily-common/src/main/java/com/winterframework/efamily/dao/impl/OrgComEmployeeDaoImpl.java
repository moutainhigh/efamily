package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComEmployeeDao;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;


@Repository("orgComEmployeeDaoImpl")
public class OrgComEmployeeDaoImpl<E extends OrgEmployee> extends BaseDaoImpl<OrgEmployee> implements IOrgComEmployeeDao{
		
	 
	
	
	public List<OrgEmployeeBaseInfo> getOrgEmployeeBaseInfoPage(Long orgId,Integer start,Integer pagesize ,EmployeeManageQueryConditionInfo queryConditionInfo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("start", start);
		map.put("pagesize", pagesize);
		
		map.put("name", queryConditionInfo.getName());
		map.put("phoneNumber", queryConditionInfo.getPhoneNumber());
		map.put("roleId", queryConditionInfo.getRoleId());
		map.put("status", queryConditionInfo.getStatus());
		map.put("entryTimeStart", queryConditionInfo.getEntryTimeStart());
		map.put("entryTimeEnd", queryConditionInfo.getEntryTimeEnd());
		
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getOrgEmployeeBaseInfoPage"), map);
	}
	
	public List<AccountNumberBaseInfo> getAccountNumberBaseInfoPage(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("start", start);
		map.put("pagesize", pagesize);
		map.put("name", name);
		map.put("phoneNumber", phoneNumber);
		
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAccountNumberBaseInfoPage"), map);
	}
	
	
}
