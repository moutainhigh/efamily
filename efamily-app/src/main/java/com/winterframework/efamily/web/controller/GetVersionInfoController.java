package com.winterframework.efamily.web.controller;

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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.GetVersionInfoRequest;
import com.winterframework.efamily.dto.GetVersionInfoResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GetVersionInfoHandler 
* @Description: 获取app版本信息
* @author david 
* @date 2015-6-24 上午10:21:29 
*  
*/
@Controller("getVersionInfoController")
@RequestMapping("/server")
public class GetVersionInfoController{
	private static final Logger logger = LoggerFactory.getLogger(GetVersionInfoController.class);
	
	@PropertyConfig(value="versionNumber")
	private String versionNumber;
	
	@PropertyConfig(value="versionDescribe")
	private String versionDescribe;
	
	@PropertyConfig(value="updateFlag")
	private String updateFlag;
	
	@PropertyConfig(value="downloadUrl")
	private String downloadUrl;
	
	
	@RequestMapping("/getVersionInfo")
	@ResponseBody
	protected Response<GetVersionInfoResponse> doHandle(@RequestBody Request<GetVersionInfoRequest> request) throws BizException {
		Response<GetVersionInfoResponse> fmlResponse = new Response<GetVersionInfoResponse>(request);
		GetVersionInfoResponse getVersionInfoResponse = new GetVersionInfoResponse();
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		
        byte clinetType= request.getData().getClientType();
        
		String[] downloadUrlArr = downloadUrl.split(EfamilyConstant.SPLIT_DEVICE_FLAG);
		String[] updateFlagArr = updateFlag.split(EfamilyConstant.SPLIT_DEVICE_FLAG);
		String[] versionDescribeArr = versionDescribe.split(EfamilyConstant.SPLIT_DEVICE_FLAG);
		String[] versionNumberArr = versionNumber.split(EfamilyConstant.SPLIT_DEVICE_FLAG);
		
		getVersionInfoResponse.setDownloadUrl(downloadUrlArr[clinetType-1]);
		getVersionInfoResponse.setUpdateFlag(updateFlagArr[clinetType-1]);
		getVersionInfoResponse.setVersionDescribe(versionDescribeArr[clinetType-1]);
		getVersionInfoResponse.setVersionNumber(versionNumberArr[clinetType-1]);

		fmlResponse.setData(getVersionInfoResponse);
		return fmlResponse;
	}
}