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
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.WatchParamBaseInfo;
import com.winterframework.efamily.institution.service.IDeviceManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;

@Controller("deviceMangeController")
@RequestMapping(value = "/device")
public class DeviceMangeController {

	@Resource(name = "deviceManageServiceImpl")
	private IDeviceManageService deviceManageServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(DeviceMangeController.class);
	
	
	@RequestMapping("/getOrgBindDeviceBaseInfo.do")
	@ResponseBody
	public Object getOrgBindDeviceBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Integer start = Integer.valueOf(request.getParameter("start").toString())-1;
        String queryValue = DataHandlerUtil.getStringFrom(request.getParameter("queryValue"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<OrgBindDeviceBaseInfo> orgBindDeviceBaseInfo = deviceManageServiceImpl.getOrgBindDeviceBaseInfo(orgId, start,queryValue);
			map.put("resultCode", orgBindDeviceBaseInfo != null?"0":"10");
			map.put("orgBindDeviceBaseInfo",orgBindDeviceBaseInfo);
			map.put("start",start);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取机构已绑定设备列表出现异常： orgId = "+orgId+" ; start = "+start+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getOrgUnbindDeviceBaseInfo.do")
	@ResponseBody
	public Object getOrgUnbindDeviceBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Integer start = Integer.valueOf(request.getParameter("start").toString())-1;
        String queryValue = DataHandlerUtil.getStringFrom(request.getParameter("queryValue"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<OrgUnbindDeviceBaseInfo> orgUnbindDeviceBaseInfo = deviceManageServiceImpl.getOrgUnbindDeviceBaseInfo(orgId, start, queryValue);
			map.put("resultCode", orgUnbindDeviceBaseInfo != null?"0":"10");
			map.put("orgUnbindDeviceBaseInfo",orgUnbindDeviceBaseInfo);
			map.put("start",start);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取没有绑定的设备列表出现异常： orgId = "+orgId+" ; start = "+start+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getOrgUnbindUserBaseInfo.do")
	@ResponseBody
	public Object getOrgUnbindUserBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Integer start = Integer.valueOf(request.getParameter("start").toString())-1;
        String queryValue = DataHandlerUtil.getStringFrom(request.getParameter("queryValue"));

		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<OrgUnbindUserBaseInfo> orgUnbindUserBaseInfo = deviceManageServiceImpl.getOrgUnbindUserBaseInfo(orgId, start, queryValue);
			map.put("resultCode", orgUnbindUserBaseInfo != null?"0":"10");
			map.put("orgUnbindUserBaseInfo",orgUnbindUserBaseInfo);
			map.put("start",start);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取没有绑定设备的用户列表出现异常： orgId = "+orgId+" ; start = "+start+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getWatchParamBaseInfo.do")
	@ResponseBody
	public Object getWatchParamBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgUserId = Long.valueOf(request.getParameter("orgUserId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			WatchParamBaseInfo watchParamBaseInfo = deviceManageServiceImpl.getWatchParamBaseInfo(orgUserId);
			map.put("resultCode", watchParamBaseInfo != null?"0":"10");
			map.put("watchParamBaseInfo",watchParamBaseInfo);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取设备编辑基本信息出现异常： orgId = "+orgId+" ; orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/saveWatchParamBaseInfo.do")
	@ResponseBody
	public Object saveWatchParamBaseInfo(HttpServletRequest request, HttpServletResponse response ,WatchParamBaseInfo watchParamBaseInfo) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId").toString());
        Long orgUserId = Long.valueOf(request.getParameter("orgUserId").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			deviceManageServiceImpl.saveWatchParamBaseInfo(watchParamBaseInfo);
			map.put("resultCode", watchParamBaseInfo != null?"0":"10");
			map.put("watchParamBaseInfo",watchParamBaseInfo);
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("保存设备编辑基本信息出现异常： orgId = "+orgId+" ; orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	
	@RequestMapping("/bindOrgDevice.do")
	@ResponseBody
	public Object bindOrgDevice(HttpServletRequest request, HttpServletResponse response ,WatchParamBaseInfo watchParamBaseInfo) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId"));
        Long orgUserId = Long.valueOf(request.getParameter("orgUserId"));
        Long orgDeviceId = Long.valueOf(request.getParameter("orgDeviceId"));
        //Integer operType = Integer.valueOf(request.getParameter("operType").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			
			//*** 根据  orgUserId 去查询 userId 并创建家庭  并把紧急联系人创建userId 
			//*** 发起绑定
			//*** 绑定成功修改 机构这边的绑定状态
			if(deviceManageServiceImpl.bindOrUnbindDevice( orgId, orgUserId, orgDeviceId, 1)){
				map.put("resultCode", "0");
			}else{
				map.put("resultCode", "9999");
			}
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取设备编辑基本信息出现异常： orgId = "+orgId+" ; orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/unbindOrgDevice.do")
	@ResponseBody
	public Object unbindOrgDevice(HttpServletRequest request, HttpServletResponse response ,WatchParamBaseInfo watchParamBaseInfo) throws Exception {

        Long orgId = Long.valueOf(request.getParameter("orgId"));
        Long orgUserId = Long.valueOf(request.getParameter("orgUserId"));
        Long orgDeviceId = Long.valueOf(request.getParameter("orgDeviceId"));
        //Integer operType = Integer.valueOf(request.getParameter("operType").toString());
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			//*** 根据  orgUserId 去查询 userId 并创建家庭  并把紧急联系人创建userId 
			//*** 发起绑定
			//*** 绑定成功修改 机构这边的绑定状态
			if(deviceManageServiceImpl.bindOrUnbindDevice( orgId, orgUserId, orgDeviceId, 0)){
				map.put("resultCode", "0");
			}else{
				map.put("resultCode", "9999");
			}
			map.put("time",DateUtils.getCurTime());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取设备编辑基本信息出现异常： orgId = "+orgId+" ; orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
}


