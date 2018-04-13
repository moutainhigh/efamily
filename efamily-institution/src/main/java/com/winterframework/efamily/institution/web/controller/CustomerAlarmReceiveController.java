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

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfDeviceAlarmLast;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEfDeviceAlarmLastService;
import com.winterframework.efamily.service.IEjlComDeviceService;


@Controller("customerAlarmReceiveController")
@RequestMapping(value = "/receive")
public class CustomerAlarmReceiveController {

	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	
	@Resource(name = "efDeviceAlarmLastServiceImpl")
	private IEfDeviceAlarmLastService efDeviceAlarmLastServiceImpl;

	
	private Logger log = LoggerFactory.getLogger(CustomerAlarmReceiveController.class);
	
	@RequestMapping("/toHeartRateAlarm.do")
	@ResponseBody
	public Object toHeartRateAlarm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = request.getParameter("orgId");
		Map<String,String> map = new HashMap<String,String>();
		try{
			//****** 接收心率
			alarmReceiveHandler( request , EfDeviceAlarm.Type.HEART.getValue());
			map.put("resultCode", "0");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/toBloodPressureAlarm.do")
	@ResponseBody
	public Object toBloodPressureAlarm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = request.getParameter("orgId");
		Map<String,String> map = new HashMap<String,String>();
		try{
			//****** 接收血压
			alarmReceiveHandler( request , EfDeviceAlarm.Type.BLOOD.getValue());
			map.put("resultCode", "0");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/toSosAlarm.do")
	@ResponseBody
	public Object toSosAlarm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = request.getParameter("orgId");
		Map<String,String> map = new HashMap<String,String>();
		try{
			//****** 接收SOS
			alarmReceiveHandler( request , EfDeviceAlarm.Type.SOS.getValue());
			map.put("resultCode", "0");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/toFenceAlarm.do")
	@ResponseBody
	public Object toFenceAlarm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = request.getParameter("orgId");
		Map<String,String> map = new HashMap<String,String>();
		try{
			//****** 接收围栏
			alarmReceiveHandler( request , EfDeviceAlarm.Type.FENCE.getValue());
			map.put("resultCode", "0");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	@RequestMapping("/toBloodSugar.do")
	@ResponseBody
	public Object toBloodSugar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = request.getParameter("orgId");
		Map<String,String> map = new HashMap<String,String>();
		try{
			//****** 接收血糖
			alarmReceiveHandler( request , EfDeviceAlarm.Type.BLOODSUGAR.getValue());
			map.put("resultCode", "0");
		}catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
		}
		return map;
	}
	
	public void alarmReceiveHandler(HttpServletRequest request,Integer alarmType) throws BizException{
		String queryString =  request.getQueryString();
		String imei = request.getParameter("imei");
		
		log.info("接收报警信息： imei = "+imei+" ; queryString = "+queryString+" ; ");

		EjlDevice device = ejlComDeviceServiceImpl.getByImei(imei);
		EfDeviceAlarmLast efDeviceAlarmLast = efDeviceAlarmLastServiceImpl.getByUserIdAndType(device.getUserId(), alarmType);
		if(efDeviceAlarmLast == null){
			efDeviceAlarmLast = new EfDeviceAlarmLast();
		}
		String value = "alarmType="+alarmType+"&"+queryString;
		efDeviceAlarmLast.setUserId(device.getUserId());
		efDeviceAlarmLast.setDeviceId(device.getId());
		efDeviceAlarmLast.setStatus(EfDeviceAlarmLast.Status.UNHANDLED.getValue());
		efDeviceAlarmLast.setTime(DateUtils.getCurTime());
		efDeviceAlarmLast.setType(alarmType);
		efDeviceAlarmLast.setValue(value);
		
		Context ctx = new Context();
		ctx.set("userId", -1);
		efDeviceAlarmLastServiceImpl.save(ctx, efDeviceAlarmLast);
	}
	
	
	
}
