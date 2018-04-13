package com.winterframework.efamily.web.controller;

import java.util.Map;

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
import com.winterframework.efamily.dto.WatchDetailRequest;
import com.winterframework.efamily.dto.WatchDetailResponse;
import com.winterframework.efamily.service.IEjlDeviceConfigService;

/**
 * 获取手表详情handler
 * @author david
 *
 */
@Controller("watchDetailController")
@RequestMapping("/server")
public class WatchDetailController  {
	private static final Logger log = LoggerFactory.getLogger(WatchDetailController.class); 
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@RequestMapping("/watchDetail")
	@ResponseBody
	protected Response<WatchDetailResponse> doHandle(@RequestBody Request<WatchDetailRequest> request) throws BizException {
		Response<WatchDetailResponse> fmlResponse = new Response<WatchDetailResponse>(request);
		WatchDetailResponse watchDetailResponse = new WatchDetailResponse();
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		String userId = request.getData().getUserId();
		String watchId = request.getData().getWatchId();
		if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(watchId)){
			Map<String,String> map= deviceConfigServiceImpl.getWatchDeviceParaAll(Long.valueOf(watchId), Long.valueOf(userId));
			watchDetailResponse.setMap(map);
			fmlResponse.setData(watchDetailResponse);
		}else{
			log.error("WatchDetailController获取设备参数时，参数不完整，获取失败。 userId = "+userId +" ; watchId = "+watchId+" ;  ");
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			
		}
		return fmlResponse;
	}
}
