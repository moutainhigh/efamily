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
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;
import com.winterframework.efamily.institution.dto.EmployeeInfoStruct;
import com.winterframework.efamily.institution.dto.EmployeeOffWorkAndLeaveInfo;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.institution.service.IRoleAndAuthMangeService;
import com.winterframework.efamily.utils.HttpUtil;

@Controller("employeeManageController")
@RequestMapping(value = "/employee")
public class EmployeeManageController {

	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@Resource(name = "employeeManageServiceImpl")
	private IEmployeeManageService employeeManageServiceImpl;
	
	@Resource(name = "roleAndAuthMangeServiceImpl")
	private IRoleAndAuthMangeService roleAndAuthMangeServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(EmployeeManageController.class);
	
	@RequestMapping("/getOrgEmployeeBaseInfoList.do")
	@ResponseBody
	public Object getOrgEmployeeBaseInfoList(HttpServletRequest request, HttpServletResponse response,EmployeeManageQueryConditionInfo queryConditionInfo) throws Exception {
		
        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Integer start = Integer.valueOf(request.getParameter("start").toString())-1;
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<OrgEmployeeBaseInfo> orgEmployeeBaseInfoList = employeeManageServiceImpl.getOrgEmployeeBaseInfoList(orgId, start ,queryConditionInfo);
			map.put("resultCode", orgEmployeeBaseInfoList != null?"0":"10");
			map.put("orgEmployeeBaseInfoList",orgEmployeeBaseInfoList);
			//*** 角色类型
			map.put("roleList",employeeManageServiceImpl.getOrgRoleList());
			map.put("start",start);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("getOrgEmployeeBaseInfoList : 获取员工列表出现异常： queryConditionInfo = "+queryConditionInfo.toString()+" ; orgId = "+orgId+" ; start = "+start+" ; ");
			map.put("resultCode", "9999");
		}

		return map;
	}
			
	@RequestMapping("/getOrgEmployeeBaseInfo.do")
	@ResponseBody
	public Object getOrgEmployeeBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        //Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgEmployeeId = Long.valueOf(request.getParameter("orgEmployeeId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			EmployeeInfoStruct employeeInfoStruct = employeeManageServiceImpl.getOrgEmployeeBaseInfo(orgEmployeeId);
			map.put("resultCode", employeeInfoStruct != null?"0":"10");
			map.put("employeeInfoStruct",employeeInfoStruct);
			map.put("orgEmployeeId",orgEmployeeId);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}

		return map;
	}
	
	@RequestMapping("/saveOrUpdateOrgEmployee.do")
	@ResponseBody
	public Object saveOrUpdateOrgEmployee(HttpServletRequest request, HttpServletResponse response,EmployeeInfoStruct employeeInfoStruct) throws Exception {

        //Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgEmployeeId = Long.valueOf(request.getParameter("orgEmployeeId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(employeeManageServiceImpl.saveOrUpdateOrgEmployee(employeeInfoStruct)){
				map.put("resultCode", "0");
				map.put("orgEmployeeId",orgEmployeeId);
				map.put("time",DateUtils.getCurTime());
			}else{
				map.put("resultCode", "9999");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}

		return map;
	}
	
	
	@RequestMapping("/offWorkOrLeaveForEmployee.do")
	@ResponseBody
	public Object offWorkOrLeaveForEmployee(HttpServletRequest request, HttpServletResponse response,EmployeeOffWorkAndLeaveInfo updateInfo) throws Exception {

        //Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgEmployeeId = Long.valueOf(request.getParameter("orgEmployeeId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(employeeManageServiceImpl.offWorkOrLeaveForEmployee(updateInfo)){
				map.put("resultCode", "0");
				map.put("orgEmployeeId",orgEmployeeId);
				map.put("time",DateUtils.getCurTime());
			}else{
				map.put("resultCode", "9999");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}

		return map;
	}
	
	@RequestMapping("/getOpenAccountInfo.do")
	@ResponseBody
	public Object getOpenAccountInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
	
	@RequestMapping("/openAccountForEmployee.do")
	@ResponseBody
	public Object openAccountForEmployee(HttpServletRequest request, HttpServletResponse response,EmployeeOffWorkAndLeaveInfo updateInfo) throws Exception {

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
