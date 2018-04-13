package com.winterframework.efamily.institution.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.institution.dto.MemberInfoStruct;
import com.winterframework.efamily.institution.service.IMainPageManageService;
import com.winterframework.efamily.institution.service.IMemberManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;

import com.winterframework.efamily.service.IEfDeviceAlarmLastService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.utils.HttpUtil;


/** 
* @ClassName: DeviceParamSetController 
* @Description: 
* @author floy 
* @date 2015-9-18 11:03:40 
*  
*/
@Controller("mainPageController")
@RequestMapping(value = "/mainPage")
public class MainPageController {

	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@Resource(name = "mainPageManageServiceImpl")
	private IMainPageManageService mainPageManageServiceImpl;

	@Resource(name = "memberManageServiceImpl")
	private IMemberManageService memberManageServiceImpl;
	
	@Resource(name = "efDeviceAlarmLastServiceImpl")
	private IEfDeviceAlarmLastService efDeviceAlarmLastServiceImpl;

	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	
	
	private Logger log = LoggerFactory.getLogger(MainPageController.class);

	@RequestMapping(value = "/toMainPage.do", method = { RequestMethod.GET,RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object toMainPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		Long orgId = Long.valueOf(request.getParameter("orgId"));
		String queryValue = DataHandlerUtil.getStringFrom(request.getParameter("queryValue"));
		try{
			//***
			map.put("resultCode", "0");
			//*** 
			map.put("memberInfoList", mainPageManageServiceImpl.getMainPageData(orgId,queryValue));
			//***
			map.put("exceptionMemberInfo", mainPageManageServiceImpl.getDeviceAlarmExceptionData(Long.valueOf(orgId),0L));
			//***
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("toMainPage：获取主页信息出现异常  orgId = "+orgId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping(value = "/queryMainPageUser.do", method = { RequestMethod.GET,RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryMainPageUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		Long orgId = Long.valueOf(request.getParameter("orgId"));
		String queryValue = DataHandlerUtil.getStringFrom(request.getParameter("queryValue"));
		try{
			//***
			map.put("resultCode", "0");
			//*** 
			map.put("memberInfoList", mainPageManageServiceImpl.getMainPageData(orgId,queryValue));
			//***
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("toMainPage：查询主页用户信息出现异常  orgId = "+orgId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getDeviceAlarmExceptionData.do")
	@ResponseBody
	public Object getDeviceAlarmExceptionData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String orgId = request.getParameter("orgId");
		String timeStr = request.getParameter("time");
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			//***
			Long time = StringUtils.isBlank(timeStr)?0L:Long.valueOf(timeStr);
			map.put("resultCode", "0");
			//***
			map.put("exceptionUserInfo",mainPageManageServiceImpl.getDeviceAlarmExceptionData(Long.valueOf(orgId),time));
		}catch(Exception e){
			e.printStackTrace();
			log.error("getDeviceAlarmExceptionData: 获取设备报警信息时出现异常  orgId = "+orgId+" ; time = "+timeStr+" ;");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping(value = "/toUserDetail.do", method = { RequestMethod.GET,RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object toUserDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();
		String orgUserId = request.getParameter("orgUserId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try{
			map.put("resultCode", "0");
			//*** 用户成员基本信息
			MemberInfoStruct memberInfoStruct =  memberManageServiceImpl.getMemberInfoStruct(Long.valueOf(orgUserId));
			Long userId = memberInfoStruct.getUserId();
			map.put("userBaseInfo", memberInfoStruct);
			//*** 用户健康数据
			map.put("userHealthInfo", mainPageManageServiceImpl.getUserHealthInfo(userId, startDate, endDate));
			//*** 用户定位数据
			map.put("userLocationInfo", mainPageManageServiceImpl.getLastLocation(userId));
			
			//*** 将该用户的异常状态取消掉
			efDeviceAlarmLastServiceImpl.closeUserExceptionRemind(userId);
			
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取用户详情时，出现异常：orgUserId = "+orgUserId);
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/getLastLocationInfo.do")
	@ResponseBody
	public Object getLastLocationInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String orgUserId = request.getParameter("orgUserId");
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Long userId = memberManageServiceImpl.getUserIdBy(Long.valueOf(orgUserId));
			map.put("resultCode", "0");
			//***
			map.put("lastLocationInfo", mainPageManageServiceImpl.getLastLocation(userId));
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("getLastLocationInfo： 获取用户定位信息出现异常  orgUserId = "+orgUserId+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}	
	
	@ResponseBody
	@RequestMapping("/getLocationInfoList.do")
	public Object getLocationInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String orgUserId = request.getParameter("orgUserId");
		Integer hourRecent = Integer.valueOf(request.getParameter("hourRecent"));
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Long userId = memberManageServiceImpl.getUserIdBy(Long.valueOf(orgUserId));
			map.put("resultCode", "0");
			//***
			map.put("locationList", mainPageManageServiceImpl.getLocationList(Long.valueOf(userId), hourRecent));
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("getLocationInfoList : 获取用户轨迹信息出现异常 orgUserId ="+orgUserId+" ; hourRecent = "+hourRecent+" ; ");
			map.put("resultCode", "9999");
		}
		return map;
	}	
	
	@RequestMapping("/getHealthInfo.do")
	@ResponseBody
	public Object getHealthInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String orgUserId = request.getParameter("orgUserId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Long userId = memberManageServiceImpl.getUserIdBy(Long.valueOf(orgUserId));
			map.put("resultCode", "0");
			//***
			map.put("userHealthInfo", mainPageManageServiceImpl.getUserHealthInfo(userId, startDate, endDate));
			//***
			map.put("time",DateUtils.getCurTime()+"");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}
		return map;
	}	
	
}
