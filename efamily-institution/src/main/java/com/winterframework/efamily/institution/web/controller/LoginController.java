package com.winterframework.efamily.institution.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.EmployeeLoginInfo;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.institution.service.IMainPageManageService;
import com.winterframework.efamily.service.IOrgComUserService;
import com.winterframework.efamily.utils.HttpUtil;


@Controller("loginController")
@RequestMapping(value = "/platform")
public class LoginController {

	
/*	@PropertyConfig(value = "server.url")
	private String serverUrl;*/


	
	@Resource(name = "employeeManageServiceImpl")
	private IEmployeeManageService employeeManageServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/toLogin.do")
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("toLogin");

		return view;
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phoneNumber = request.getParameter("phoneNumber").toString();
		String password = request.getParameter("password").toString();
		
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			//返回登录   ：  有多少个老人院   单位  等信息
			
			EmployeeLoginInfo employeeLoginInfo = employeeManageServiceImpl.employeeLogin(phoneNumber, password);
			request.getSession().setAttribute("loginedUser", employeeLoginInfo);
			map.put("resultCode", "0");
			map.put("orgId", employeeLoginInfo.getOrgId());
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("用户登录失败： phoneNumber = "+phoneNumber);
			map.put("resultCode", "9999");
		}
		return map;
	}
	
}
