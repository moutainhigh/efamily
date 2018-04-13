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

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;

import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.institution.dto.MemberInfoStruct;
import com.winterframework.efamily.institution.service.IMemberManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;


@Controller("memberManageController")
@RequestMapping(value = "/member")
public class MemberManageController {

	
	@Resource(name = "memberManageServiceImpl")
	private IMemberManageService memberManageServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(MemberManageController.class);

	@RequestMapping("/getMemberSimpleInfoStructList.do")
	@ResponseBody
	public Object getMemberSimpleInfoStructList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Integer start = Integer.valueOf(request.getParameter("start").toString())-1;
        String queryValue = DataHandlerUtil.getStringFrom(request.getParameter("queryValue"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<MemberSimpleInfoStruct> memberSimpleInfoStructList = memberManageServiceImpl.getMemberSimpleInfoStructList(orgId, start , queryValue);
			map.put("resultCode", memberSimpleInfoStructList != null?"0":"10");
			map.put("memberSimpleInfoStructList",memberSimpleInfoStructList);
			map.put("start",start);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取成员的简单信息列表出现异常： orgId = "+orgId+" ; start = "+start+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}

	@RequestMapping("/getMemberInfoStruct.do")
	@ResponseBody
	public Object getMemberInfoStruct(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgUserId = Long.valueOf(request.getParameter("orgUserId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			MemberInfoStruct memberInfoStruct = memberManageServiceImpl.getMemberInfoStruct(orgUserId);
			map.put("resultCode", memberInfoStruct != null?"0":"10");
			map.put("memberInfoStruct",memberInfoStruct);
			map.put("orgUserId",orgUserId);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取会员基本信息出现异常： orgId = "+orgId+" ; orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/saveOrUpdateMemberInfo.do")
	@ResponseBody
	public Object saveOrUpdateMemberInfo(HttpServletRequest request, HttpServletResponse response,MemberInfoStruct memberInfoStruct) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Context ctx = new Context();
			ctx.set("userId", -1);
			memberManageServiceImpl.saveOrUpdateMemberInfo(ctx, memberInfoStruct);
			map.put("resultCode", "0");
			map.put("memberInfoStruct",memberInfoStruct);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("保存或者修改会员基本信息出现异常： memberInfoStruct = "+memberInfoStruct+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
		
	}
	
	@RequestMapping("/deleteMemberInfo.do")
	@ResponseBody
	public Object deleteMemberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();
		Long orgUserId = Long.valueOf(request.getParameter("orgUserId").toString());
		try{
			Context ctx = new Context();
			ctx.set("userId", -1);
			memberManageServiceImpl.deleteMemberInfo(ctx, orgUserId);
			map.put("resultCode", "0");
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("删除会员基本信息出现异常： orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}

}

