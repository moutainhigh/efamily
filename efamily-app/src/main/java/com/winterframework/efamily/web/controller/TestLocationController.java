package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.LocationStruc;
import com.winterframework.efamily.dto.TestLocationStruc;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.service.IEfLocationSemiService;
 
/** 根据内容下载二维码
 *QrcodeController
 * @ClassName
 * @Description
 * @author ibm
 * 2015年12月21日
 */
@Controller("testLocationController")
@RequestMapping("/test")
public class TestLocationController {
	private static final Logger log = LoggerFactory.getLogger(TestLocationController.class);
	@Resource(name = "efLocationSemiServiceImpl")
	private IEfLocationSemiService efLocationSemiService;
	 
	@RequestMapping(value="/locationList",produces ="application/json; charset=utf-8" )
	@ResponseBody
	public Object locationList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long userId = Long.valueOf(request.getParameter("userId"));
		Long deviceId = Long.valueOf(request.getParameter("deviceId"));
		Long startTime = Long.valueOf(request.getParameter("startTime"));
		Long endTime = Long.valueOf(request.getParameter("endTime"));
		Integer type1 = Integer.valueOf(request.getParameter("type1"));
		Integer type2 = Integer.valueOf(request.getParameter("type2"));
		Date timeFrom=DateUtils.addDays(DateUtils.getDate(startTime), -1);
		Date timeTo=DateUtils.getDate(startTime);

		Integer type=(type1==1 && type2==1)?3:((type1==1)?2:1);
		
		List<EfLocationSemi> locationSemiList = efLocationSemiService.getListByTimeSpan(userId,deviceId,timeFrom,timeTo,type);
		if(null!=locationSemiList){
			List<TestLocationStruc> locationList =new ArrayList<TestLocationStruc>();	 
			for(EfLocationSemi locationSemi:locationSemiList){
				if(null!=locationSemi){
					TestLocationStruc location=new TestLocationStruc();
					location.setLocation(locationSemi.getLatitude()+","+locationSemi.getLongitude());
					location.setTimeBegin(locationSemi.getTimeBegin());
					location.setTimeEnd(locationSemi.getTimeEnd());
					location.setSource(locationSemi.getSource());
					location.setAddressDesc(locationSemi.getAddress());
					locationList.add(location);
				}
			}
			return JsonUtils.toJson(locationList);
		}
		
		return "";
	}	
}
