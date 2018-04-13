package com.winterframework.efamily.unit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.EmployeeLoginInfo;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.institution.dto.EmployeeInfoStruct;
import com.winterframework.efamily.institution.dto.EmployeeOffWorkAndLeaveInfo;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.institution.service.IRoleAndAuthMangeService;
import com.winterframework.modules.test.BaseTestCase;



public class EmployeeManageServiceImplTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(EmployeeManageServiceImplTest.class);

	@Resource(name="employeeManageServiceImpl")
	private IEmployeeManageService employeeManageServiceImpl;
	
	@Resource(name = "roleAndAuthMangeServiceImpl")
	private IRoleAndAuthMangeService roleAndAuthMangeServiceImpl;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		Long orgId = 100L;
		Integer start = 1;
		String queryValue = "";
		String imei = "";
		Long orgUserId = 1L;
		Long orgDeviceId = 3L;
		
		System.out.println("**************************START******************************");

		
/*		EmployeeManageQueryConditionInfo queryConditionInfo = new EmployeeManageQueryConditionInfo();
		queryConditionInfo.setQueryValue("珊");
		List<OrgEmployeeBaseInfo> orgEmployeeBaseInfoList = employeeManageServiceImpl.getOrgEmployeeBaseInfoList(orgId, start ,queryConditionInfo);
		System.out.println("orgEmployeeBaseInfoList"+JsonUtils.toJson(orgEmployeeBaseInfoList));*/
		
/*		EmployeeInfoStruct employeeInfoStruct = employeeManageServiceImpl.getOrgEmployeeBaseInfo(1L);
		System.out.println("employeeInfoStruct 11 "+JsonUtils.toJson(employeeInfoStruct));*/
		
/*		employeeInfoStruct.setName("刘珊珊");
		employeeManageServiceImpl.saveOrUpdateOrgEmployee(employeeInfoStruct);
		System.out.println("employeeInfoStruct 22 "+JsonUtils.toJson(employeeInfoStruct));*/
		
/*		EmployeeOffWorkAndLeaveInfo updateInfo = new EmployeeOffWorkAndLeaveInfo();
		updateInfo.setOrgEmployeeId(1L);
		updateInfo.setOptType(1);
		updateInfo.setOffWorkStartTime("2016-11-04");
		updateInfo.setOffWorkEndTime("2016-11-06");
		updateInfo.setContent("回家结婚");
		employeeManageServiceImpl.offWorkOrLeaveForEmployee(updateInfo);*/
		
/*		EmployeeInfoStruct employeeInfoStruct = employeeManageServiceImpl.getOrgEmployeeBaseInfo(1L);
		System.out.println("employeeInfoStruct"+JsonUtils.toJson(employeeInfoStruct));*/
		
		List<EfOrg> efOrgList = employeeManageServiceImpl.getEmployeeCanOptOrgList(1L);
		System.out.println("efOrgList : "+JsonUtils.toJson(efOrgList));
		
		List<UrlAuthInfo>  urlAuthInfoList = roleAndAuthMangeServiceImpl.getUrlAuthInfoList();
		System.out.println("urlAuthInfoList : "+JsonUtils.toJson(urlAuthInfoList));
		
		//*** 开通账户
/*		Long orgEmployeeId = 2L;
        String openOrgIds = "1,2,3";
        Long roleId = 2L;
		employeeManageServiceImpl.openAccountForEmployee(orgEmployeeId, roleId, openOrgIds);*/
		
/*		String province = "广东省";
		String city = "深";
		String county = "龙";
		List<EfOrg> efOrgList = employeeManageServiceImpl.getEmployeeCanOptOrgByAddressList(1L, province, city, county);
		System.out.println("efOrgList"+JsonUtils.toJson(efOrgList));*/
	
		System.out.println("**************************END******************************");
		
	}
	
	
	
	
	
	
}

