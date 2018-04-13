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
import com.winterframework.efamily.dto.ScanWatchRequest;
import com.winterframework.efamily.dto.ScanWatchResponse;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlUserDeviceService;

@Controller("scanWatchController")
@RequestMapping("/server")
public class ScanWatchController {
	private static final Logger log = LoggerFactory.getLogger(ScanWatchController.class); 
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;
	
	@RequestMapping("/scanWatch")
	@ResponseBody
	protected Response<ScanWatchResponse> doHandle(@RequestBody Request<ScanWatchRequest> request) throws BizException {
		Response<ScanWatchResponse> fmlResponse = new Response<ScanWatchResponse>(request);
		ScanWatchResponse scanWatchResponse = new ScanWatchResponse();
		Long userId = request.getUserId();
        String watchCode = request.getData().getWatchCode();
        String nickName = request.getData().getNickName();
        String zombieUserId = request.getData().getZombieUserId();//僵尸用户ID
        String phoneNumber = request.getData().getPhoneNumber();
        log.info("【扫描手表接口 开始】:userId = "+userId+" ; watchCode = "+watchCode +" ; nickName = "+nickName+" ; zombieUserId = "+zombieUserId+" ; phoneNumber = "+phoneNumber+" ; ");
    	if(ejlUserDeviceServiceImpl.checkScanWatch(request.getCtx(), userId, watchCode, nickName, phoneNumber, zombieUserId)){
    		scanWatchResponse = ejlUserDeviceServiceImpl.manageScanWatch(request.getCtx(), userId, watchCode, nickName, phoneNumber, zombieUserId);
    	}
        log.info("【扫描手表接口 结束】: familyId = "+scanWatchResponse.getFamilyId());
        fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(scanWatchResponse);
		return fmlResponse;
	}
}