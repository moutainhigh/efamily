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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfWeather;
import com.winterframework.efamily.institution.service.IMainPageManageService;
import com.winterframework.efamily.institution.util.JuheGetWeatherUtil;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Controller("getWeatherController")
@RequestMapping(value = "/platform")
public class GetWeatherController {

	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	private Logger log = LoggerFactory.getLogger(GetWeatherController.class);
	
	@Resource(name="efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	
	@PropertyConfig(value = "juhe.weather.key")
	private String key;
	
	@RequestMapping("/getWeather.do")
	@ResponseBody
	public Object getWeather(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//*** 根据机构号获取机构地址  按照地址获取天气

		String orgId = request.getParameter("orgId");
		Map<String,String> map = new HashMap<String,String>();
		try{
			map.put("resultCode", "0");
			EfOrg org = efComOrgServiceImpl.get(Long.valueOf(orgId));
			String cityName = org.getCity();
			if(StringUtils.isNotBlank(cityName)){
				EfWeather efWeather = JuheGetWeatherUtil.getJuheWeatherDate(cityName, key);
				if(efWeather != null){
					map.put("resultCode", "0");
					map.put("weather", efWeather.getWeather());
					map.put("temperature", efWeather.getTemperature());
					map.put("week", "星期"+"日一二三四五六".charAt(efWeather.getWeek()));
				}
			}
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

