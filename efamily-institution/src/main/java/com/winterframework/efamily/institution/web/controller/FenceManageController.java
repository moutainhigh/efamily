package com.winterframework.efamily.institution.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.OrgFence;
import com.winterframework.efamily.institution.dto.FenceUserIdAndNameInfo;
import com.winterframework.efamily.institution.dto.UserFenceBaseInfo;
import com.winterframework.efamily.institution.dto.UserFenceNameAndIdInfo;
import com.winterframework.efamily.institution.service.IFenceManageService;

@Controller("fenceManageController")
@RequestMapping(value = "/fence")
public class FenceManageController {
	
	@Resource(name = "fenceManageServiceImpl")
	private IFenceManageService fenceManageServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(FenceManageController.class);
	
	
	@RequestMapping("/addFence.do")
	@ResponseBody
	public Object addFence(HttpServletRequest request, HttpServletResponse response,UserFenceBaseInfo userFenceBaseInfo,String userIds) throws Exception {

		//Long orgId = Long.valueOf(request.getParameter("orgId"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Context ctx = new Context();
			ctx.set("userId", 9999);
			map.put("resultCode", "0");
			//********** 新增围栏 
			OrgFence orgFence = fenceManageServiceImpl.saveUserFenceBaseInfo(ctx, userFenceBaseInfo);
			//********** 增加围栏中的人员
			if(StringUtils.isNotBlank(userIds)){
				fenceManageServiceImpl.saveFenceIdAndUserId(ctx, orgFence, userIds);
			}
			
			System.out.println("fenceUserIdAndNameInfoList : "+userIds);
			map.put("fenceId", orgFence.getId());
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("新增围栏时，出现异常：userFenceBaseInfo = "+userFenceBaseInfo.toString(),e.getMessage());
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	
	@RequestMapping("/deleteFence.do")
	@ResponseBody
	public Object deleteFence(HttpServletRequest request, HttpServletResponse response,UserFenceBaseInfo userFenceBaseInfo,String userIdsAddFence,String userIdsSubFence) throws Exception {

		//Long orgId = Long.valueOf(request.getParameter("orgId"));
		Long fenceId = Long.valueOf(request.getParameter("fenceId"));
		Context ctx = new Context();
		ctx.set("userId", 9999);
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map.put("resultCode", "0");
			fenceManageServiceImpl.deleteFence(ctx, fenceId);
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("删除围栏时出现异常： fenceId = "+fenceId,e.getMessage());
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/updateFence.do")
	@ResponseBody
	public Object updateFence(HttpServletRequest request, HttpServletResponse response,UserFenceBaseInfo userFenceBaseInfo,String userIdsAddFence,String userIdsSubFence) throws Exception {

		Long fenceId = userFenceBaseInfo.getFenceId();
		Integer isFenceBaseFlag = Integer.valueOf(request.getParameter("isFenceBaseFlag"));
		Context ctx = new Context();
		ctx.set("userId", 9999);
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map.put("resultCode", "0");
			if(isFenceBaseFlag.intValue() == 1){
				fenceManageServiceImpl.saveUserFenceBaseInfo(ctx, userFenceBaseInfo);
			}
			
			if(StringUtils.isNotBlank(userIdsAddFence)){
				fenceManageServiceImpl.addUserToFence(ctx, fenceId, userIdsAddFence);
			}
			
			if(StringUtils.isNotBlank(userIdsSubFence)){
				fenceManageServiceImpl.subUserFromFence(ctx, fenceId, userIdsSubFence);
			}

			//map.put("fenceUserIdAndNameInfoList ", fenceManageServiceImpl.getUserIdAndNameAllBindInfoList(orgId));
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("修改围栏时出现异常：fenceId = "+fenceId,e.getMessage());
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getFence.do")
	@ResponseBody
	public Object getFence(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long orgId = Long.valueOf(request.getParameter("orgId"));
		Long fenceId = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try{

			List<UserFenceNameAndIdInfo> userFenceNameAndIdInfoList = fenceManageServiceImpl.getUserFenceNameAndIdList(orgId);
			if(userFenceNameAndIdInfoList!=null && userFenceNameAndIdInfoList.size()>0){
				
				if(StringUtils.isNotBlank(request.getParameter("fenceId"))){
					fenceId = Long.valueOf(request.getParameter("fenceId"));
				}else{
					fenceId = userFenceNameAndIdInfoList.get(0).getFenceId();
				}
				map.put("resultCode", "0");
				map.put("userFenceNameAndIdInfoList", userFenceNameAndIdInfoList);

				map.put("userIdAndNameInFence", fenceManageServiceImpl.getUserIdAndNameInFence(fenceId));
				map.put("userIdAndNameAllBindInfo", fenceManageServiceImpl.getUserIdAndNameAllBindInfoList(orgId));
			}else{
				map.put("resultCode", "10");//*** 数据为空
			}

			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取围栏时出现异常: fenceId="+fenceId+" ; orgId = "+orgId,e.getMessage());
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getUserIdAndNameInFence.do")
	@ResponseBody
	public Object getUserIdAndNameInFence(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		Long fenceId = Long.valueOf(request.getParameter("fenceId"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<FenceUserIdAndNameInfo> fenceUserIdAndNameInfoList = fenceManageServiceImpl.getUserIdAndNameInFence(fenceId);
			if(fenceUserIdAndNameInfoList !=null && fenceUserIdAndNameInfoList.size()>0){
				map.put("resultCode", "0");
				map.put("fenceId", fenceId);
				map.put("userIdAndNameInFence", fenceUserIdAndNameInfoList);
			}else{
				map.put("resultCode", "10");
			} 
			
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取围栏中的人员时出现异常: fenceId = "+fenceId,e.getMessage());
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	
	@RequestMapping("/getOrgBindAllMember.do")
	@ResponseBody
	public Object getOrgBindAllMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		Long orgId = Long.valueOf(request.getParameter("orgId"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map.put("resultCode", "0");
			map.put("userIdAndNameAllBindInfo", fenceManageServiceImpl.getUserIdAndNameAllBindInfoList(orgId));
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取机构所有绑定的成员时出现异常",e.getMessage());
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	public static void main(String[] args){
		List<FenceUserIdAndNameInfo> fenceUserIdAndNameInfoList = new ArrayList<FenceUserIdAndNameInfo>();
		FenceUserIdAndNameInfo fenceUserIdAndNameInfo = new FenceUserIdAndNameInfo();
		fenceUserIdAndNameInfo.setName("张三疯");
		fenceUserIdAndNameInfo.setUserId(1L);
		fenceUserIdAndNameInfoList.add(fenceUserIdAndNameInfo);
		fenceUserIdAndNameInfo = new FenceUserIdAndNameInfo();
		fenceUserIdAndNameInfo.setName("沈金兵");
		fenceUserIdAndNameInfo.setUserId(2L);		
		fenceUserIdAndNameInfoList.add(fenceUserIdAndNameInfo);
		System.out.println(JsonUtils.toJson(fenceUserIdAndNameInfoList));
	}
	
}
