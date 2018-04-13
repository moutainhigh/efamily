package com.winterframework.efamily.institution.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.EmployeeLoginInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.OrgRoleBaseInfo;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.institution.dto.EmployeeOffWorkAndLeaveInfo;
import com.winterframework.efamily.institution.dto.EmployeeRoleAuthInfo;
import com.winterframework.efamily.institution.dto.OrgRoleDto;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.institution.service.IRoleAndAuthMangeService;


@Controller("accountNumberManageController")
@RequestMapping(value = "/account")
public class AccountNumberManageController {

	@Resource(name = "employeeManageServiceImpl")
	private IEmployeeManageService employeeManageServiceImpl;

	@Resource(name = "roleAndAuthMangeServiceImpl")
	private IRoleAndAuthMangeService roleAndAuthMangeServiceImpl;
	

	private Logger log = LoggerFactory.getLogger(AccountNumberManageController.class);
	
	@RequestMapping("/getAccountNumberBaseInfoList.do")
	@ResponseBody
	public Object getAccountNumberBaseInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Integer start = Integer.valueOf(request.getParameter("start").toString())-1;
        String queryValue = request.getParameter("queryValue").toString();
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<AccountNumberBaseInfo> accountNumberBaseInfo = employeeManageServiceImpl.getAccountNumberBaseInfoPage(orgId, start, queryValue);
			map.put("resultCode", accountNumberBaseInfo != null?"0":"10");
			map.put("accountNumberBaseInfo",accountNumberBaseInfo);
			map.put("start",start);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("getAccountNumberBaseInfoList: 获取账号列表信息时出现异常  orgId = "+orgId+" ; start = "+start+" ; queryValue = "+queryValue+" ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/getAccountNumberBaseInfo.do")
	@ResponseBody
	public Object getAccountNumberBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        Long orgEmployeeId = Long.valueOf(request.getParameter("orgEmployeeId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			AccountNumberBaseInfo accountNumberBaseInfo = employeeManageServiceImpl.getAccountNumberBaseInfo(orgEmployeeId);
			map.put("resultCode", accountNumberBaseInfo != null?"0":"10");
			map.put("accountNumberBaseInfo",accountNumberBaseInfo);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("getAccountNumberBaseInfo: 获取账号详情时出现异常  orgEmployeeId = "+orgEmployeeId+" ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/getOrgRoleList.do")
	@ResponseBody
	public Object getOrgRoleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<OrgRoleBaseInfo> orgRoleBaseInfoList = roleAndAuthMangeServiceImpl.getOrgRoleList(orgId);
			map.put("resultCode", orgRoleBaseInfoList != null?"0":"10");
			map.put("orgRoleList",orgRoleBaseInfoList);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("getOrgRoleList ： 获取机构角色列表出现异常  orgId = "+orgId+" ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/getOrgRoleInfo.do")
	@ResponseBody
	public Object getOrgRoleInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        Long orgRoleId = Long.valueOf(request.getParameter("orgRoleId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			EmployeeRoleAuthInfo employeeRoleAuthInfo = roleAndAuthMangeServiceImpl.getEmployeeRoleAuthInfo(orgRoleId);
			map.put("resultCode", "0");
			map.put("orgRoleInfo", employeeRoleAuthInfo);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("getOrgRole ： 获取机构角色出现异常  orgRoleId = "+orgRoleId+" ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/addOrUpdateOrgRole.do")
	@ResponseBody
	public Object addOrUpdateOrgRole(HttpServletRequest request, HttpServletResponse response,OrgRoleDto orgRoleDto) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			OrgRole orgRole = roleAndAuthMangeServiceImpl.addOrUpdateOrgRoleDto(orgRoleDto);
			roleAndAuthMangeServiceImpl.addOrgRoleUrlList(orgRole.getId(), orgRoleDto.getAuthIds());
			map.put("resultCode", "0");
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("addOrUpdateOrgRole ： 增加角色  orgRoleDto = "+orgRoleDto.toString()+" ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/getAllAuthInfo.do")
	@ResponseBody
	public Object getAllAuthInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			
			List<UrlAuthInfo> urlAuthInfoList = roleAndAuthMangeServiceImpl.getUrlAuthInfoList();
			map.put("resultCode", urlAuthInfoList != null?"0":"10");
			map.put("urlAuthInfoList",urlAuthInfoList);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("getAllAuthInfo ： 获取机构所有权限出现异常   ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/getaddAccountInfo.do")
	@ResponseBody
	public Object getAddAccountInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgEmployeeId = Long.valueOf(request.getParameter("orgEmployeeId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			OrgEmployee OrgEmployee = employeeManageServiceImpl.getOrgEmployee(orgEmployeeId);
			//*** 
			EmployeeLoginInfo employeeLoginInfo = (EmployeeLoginInfo) request.getSession().getAttribute("loginedUser");
			List<EfOrg> efOrgList = employeeManageServiceImpl.getEmployeeCanOptOrgList(employeeLoginInfo.getOrgEmployeeId());
			
			map.put("efOrgList",efOrgList);
			map.put("urlAuthInfoList",roleAndAuthMangeServiceImpl.getUrlAuthInfoList());
			map.put("resultCode", "0");
			map.put("phoneNumber", OrgEmployee.getPhoneNumber());
			map.put("name", OrgEmployee.getName());
			map.put("orgEmployeeId",orgEmployeeId);
			map.put("time",DateUtils.getCurTime());

		}catch(Exception e){
			e.printStackTrace();
			log.error("getOpenAccountInfo : ");
			map.put("resultCode", "9999");
		}

		return map;
	}
	
	@RequestMapping("/addAccountForEmployee.do")
	@ResponseBody
	public Object addAccountForEmployee(HttpServletRequest request, HttpServletResponse response,EmployeeOffWorkAndLeaveInfo updateInfo) throws Exception {

        //Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgEmployeeId = Long.valueOf(request.getParameter("orgEmployeeId").toString());
        String openOrgIds = request.getParameter("openOrgIds").toString();
        Long roleId = Long.valueOf(request.getParameter("roleId").toString());
        
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			//*** 开通账户
			employeeManageServiceImpl.openAccountForEmployee(orgEmployeeId, roleId, openOrgIds);
			map.put("resultCode", "0");
			map.put("orgEmployeeId",orgEmployeeId);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("openAccountForEmployee : 开通账号时出现异常  orgEmployeeId = "+orgEmployeeId+" ; openOrgIds = "+openOrgIds+" ; roleId = "+roleId+" ; ");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}

		return map;
	}

	@RequestMapping("/queryOrgByAddress.do")
	@ResponseBody
	public Object queryOrgByAddress(HttpServletRequest request, HttpServletResponse response,EmployeeOffWorkAndLeaveInfo updateInfo) throws Exception {

		String province = request.getParameter("province").toString();
		String city = request.getParameter("city").toString();
		String county = request.getParameter("county").toString();
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			EmployeeLoginInfo employeeLoginInfo = (EmployeeLoginInfo) request.getSession().getAttribute("loginedUser");
			Long loginEmployeeOrgId = employeeLoginInfo.getOrgId();
			List<EfOrg> efOrgList = employeeManageServiceImpl.getEmployeeCanOptOrgByAddressList(loginEmployeeOrgId, province, city, county);
			map.put("efOrgList",efOrgList);
			map.put("resultCode", "0");
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}

		return map;
	}
}