package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.GetWatchBindInfoRequest;
import com.winterframework.efamily.dto.GetWatchBindInfoResponse;
import com.winterframework.efamily.dto.WatchBindInfo;
import com.winterframework.efamily.service.IEjlDeviceConfigService;

@Controller("getWatchBindInfoController")
@RequestMapping("/server")
public class GetWatchBindInfoController{
	private static final Logger log = LoggerFactory.getLogger(GetWatchBindInfoController.class); 
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@RequestMapping("/getWatchBindInfo")
	@ResponseBody
	protected Response<GetWatchBindInfoResponse> doHandle(@RequestBody Request<GetWatchBindInfoRequest> request) throws Exception {
		Response<GetWatchBindInfoResponse> fmlResponse = new Response<GetWatchBindInfoResponse>(request);
		GetWatchBindInfoResponse getWatchBindInfoResponse = new GetWatchBindInfoResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		String deviceCode = request.getData().getDeviceCode();//手表ID
		String phoneNumber = "";
		if( StringUtils.isNotBlank(deviceCode)){
			//  绑定状态：0  未绑定 ； 1 绑定 ；2 未激活；3 未入库（设备不存在）
			WatchBindInfo watchBindInfo= deviceConfigServiceImpl.queryWatchBindInfo(deviceCode);
			phoneNumber = deviceConfigServiceImpl.getDevicePhoneBy(deviceCode);
			getWatchBindInfoResponse.setPhoneNumber(phoneNumber);
			getWatchBindInfoResponse.setBindWatchStatus(String.valueOf(watchBindInfo.getBindWatchType()));
			getWatchBindInfoResponse.setFamilyCode(watchBindInfo.getFamilyCode());
			getWatchBindInfoResponse.setFamilyName(watchBindInfo.getFamilyName());
			
			fmlResponse.setData(getWatchBindInfoResponse);
		}else{
			log.error("获取设备参数时，参数不完整，获取失败。 userId = "+request.getUserId() +" ; deviceCode = "+deviceCode+" ;  ");
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
}