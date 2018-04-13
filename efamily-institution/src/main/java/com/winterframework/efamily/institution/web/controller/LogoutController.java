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
import com.winterframework.efamily.institution.service.IMainPageManageService;
import com.winterframework.efamily.utils.HttpUtil;


@Controller("logoutController")
@RequestMapping(value = "/logout")
public class LogoutController {

	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@Resource(name = "mainPageManageServiceImpl")
	private IMainPageManageService mainPageManageServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(LogoutController.class);
	
	
	@RequestMapping("/logout.do")
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView view = new ModelAndView("pc/mainpage");
		String orgId = request.getParameter("orgId").toString();
		Map<String,String> map = new HashMap<String,String>();
		try{
			request.getSession().setAttribute("orgId", 1);
			
			map.put("resultCode", "0");

			map.put("orgId", "1");
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}
		view.addObject(JsonUtils.toJson(map));
		return view;
	}
	
}

