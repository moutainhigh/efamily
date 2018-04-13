package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.GetUserHealthlyConfigRequest;
import com.winterframework.efamily.dto.UserHealthlyConfigStruc;
import com.winterframework.efamily.entity.EfModule;
import com.winterframework.efamily.service.IEfComModuleService;
import com.winterframework.efamily.service.IEjlUserService;

@Controller("getHealthyModuleContorller")
@RequestMapping("/server")
public class GetHealthyModuleContorller {

	private static final Logger logger = LoggerFactory.getLogger(GetHealthyUserConfigController.class);
	@Resource(name = "efComModuleServiceImpl")
	private IEfComModuleService efComModuleServiceImpl;
	
	@RequestMapping("/getHealthyModule")
	@ResponseBody
	protected Response<Map> doHandle(@RequestBody Request<Long> request) throws BizException {
		Response<Map> fmlResponse = new Response<Map>(request);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		List<EfModule> list = efComModuleServiceImpl.getEfModuleList();
		Map<String,List<Integer>> map = new HashMap<String,List<Integer>>();
		if(list!=null&&!list.isEmpty()){
			for(EfModule e:list){
				String key = e.getCustomerId()+"_"+e.getGlevel();
				if(map.containsKey(key)){
					map.get(key).add(e.getNumber());
				}else{
					List<Integer> mList = new ArrayList<Integer>();
					mList.add(e.getNumber());
					map.put(key, mList);
				}
			}
		}
		fmlResponse.setData(map);
		return fmlResponse;
	}
}
