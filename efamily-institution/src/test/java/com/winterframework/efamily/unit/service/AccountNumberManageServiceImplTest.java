package com.winterframework.efamily.unit.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.entity.WatchParamBaseInfo;
import com.winterframework.efamily.institution.dto.EmployeeRoleAuthInfo;
import com.winterframework.efamily.institution.dto.OrgRoleDto;
import com.winterframework.efamily.institution.service.IDeviceManageService;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.institution.service.IRoleAndAuthMangeService;
import com.winterframework.modules.test.BaseTestCase;



public class AccountNumberManageServiceImplTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(AccountNumberManageServiceImplTest.class);

	@Resource(name = "employeeManageServiceImpl")
	private IEmployeeManageService employeeManageServiceImpl;

	@Resource(name = "roleAndAuthMangeServiceImpl")
	private IRoleAndAuthMangeService roleAndAuthMangeServiceImpl;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		Long orgId = 100L;
		Integer start = 0;
		String queryValue = "";
		String imei = "";
		Long orgUserId = 1L;
		Long orgDeviceId = 3L;
		Long orgEmployeeId = 1L;
			
		System.out.println("**************************START******************************");
		
		
/*		List<AccountNumberBaseInfo> accountNumberBaseInfoList = employeeManageServiceImpl.getAccountNumberBaseInfoPage(orgId, start, queryValue);
        System.out.println("accountNumberBaseInfoList : "+JsonUtils.toJson(accountNumberBaseInfoList));*/
		
        
/*      AccountNumberBaseInfo accountNumberBaseInfo = employeeManageServiceImpl.getAccountNumberBaseInfo(orgEmployeeId);
        System.out.println("accountNumberBaseInfo : "+JsonUtils.toJson(accountNumberBaseInfo));
		*/
        
        List<OrgRoleBaseInfo> orgRoleBaseInfoList = roleAndAuthMangeServiceImpl.getOrgRoleList(orgId);
        System.out.println("orgRoleBaseInfoList : "+JsonUtils.toJson(orgRoleBaseInfoList));
       
        
/*		EmployeeRoleAuthInfo employeeRoleAuthInfo = roleAndAuthMangeServiceImpl.getEmployeeRoleAuthInfo(1L);
        System.out.println("employeeRoleAuthInfo : "+JsonUtils.toJson(employeeRoleAuthInfo));
       */
        
/*        OrgRoleDto orgRoleDto = new OrgRoleDto();
        orgRoleDto.setAuthIds("1,2,3");
        orgRoleDto.setName("测试权限11");
        orgRoleDto.setOrgId(orgId);
        orgRoleDto.setOrgRoleId(null);
        orgRoleDto.setRemark("测试权限12");
		OrgRole orgRole = roleAndAuthMangeServiceImpl.addOrUpdateOrgRoleDto(orgRoleDto);
		roleAndAuthMangeServiceImpl.addOrgRoleUrlList(orgRole.getId(), orgRoleDto.getAuthIds());
        System.out.println("orgRole : "+JsonUtils.toJson(orgRole));*/
        
        
/*        List<UrlAuthInfo> urlAuthInfoList = roleAndAuthMangeServiceImpl.getUrlAuthInfoList();
        System.out.println("urlAuthInfoList : "+JsonUtils.toJson(urlAuthInfoList));
        */
        
		System.out.println("**************************END******************************");
		
	}
	
	
	
	
	
	
}

