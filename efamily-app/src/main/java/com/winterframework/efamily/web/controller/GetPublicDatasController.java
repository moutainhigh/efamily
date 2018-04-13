package com.winterframework.efamily.web.controller;


import javax.annotation.Resource;

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
import com.winterframework.efamily.dto.GetPublicDatasRequest;
import com.winterframework.efamily.dto.GetPublicDatasResponse;
import com.winterframework.efamily.entity.EjlPublicData;
import com.winterframework.efamily.service.IEjlPublicDataService;

/** 
* @ClassName: GetPublicDatasHandler 
* @Description: 获取公共数据配置接口
* @author david 
* @date 2015-6-24 上午10:21:29 
*  
*/
@Controller("getPublicDatasController")
@RequestMapping("/server")
public class GetPublicDatasController{
	private static final Logger logger = LoggerFactory.getLogger(GetPublicDatasController.class);

	@Resource(name = "ejlPublicDataServiceImpl")
	private IEjlPublicDataService ejlPublicDataServiceImpl;
	
	@RequestMapping("/getPublicDatas")
	@ResponseBody
	protected Response<GetPublicDatasResponse> doHandle(@RequestBody Request<GetPublicDatasRequest> request) throws BizException {
		Response<GetPublicDatasResponse> fmlResponse = new Response<GetPublicDatasResponse>(request);
		GetPublicDatasResponse getPublicDatasResponse = new GetPublicDatasResponse();
		
		byte clinetType= request.getData().getClientType();
		String appVersion= request.getData().getAppVersion();
		
		EjlPublicData ejlPublicData = new EjlPublicData();
		ejlPublicData.setDeviceType(Integer.valueOf(clinetType));
		ejlPublicData = ejlPublicDataServiceImpl.selectOneObjByAttribute(request.getCtx(), ejlPublicData);
		
		if(ejlPublicData != null){
			getPublicDatasResponse.setDownloadUrl(ejlPublicData.getDownloadUrl());
			getPublicDatasResponse.setVersionDescribe(ejlPublicData.getVersionDescribe());
			getPublicDatasResponse.setVersionNumber(ejlPublicData.getVersionNumber());
			
			getPublicDatasResponse.setLogoUrl(ejlPublicData.getLogoUrl());
			getPublicDatasResponse.setWeChat(ejlPublicData.getWeChat());
			getPublicDatasResponse.setWeiboName(ejlPublicData.getWeiboName());
			getPublicDatasResponse.setWeiboUrl(ejlPublicData.getWeiboUrl());
			getPublicDatasResponse.setPhoneNumber(ejlPublicData.getPhoneNumber());
			getPublicDatasResponse.setVerifyCodeLength(ejlPublicData.getVerifyCodeLength()+"");
			
			//*** 计算是否强制更新
			String updateFlag = ejlPublicDataServiceImpl.getUpdateFlagByAppVersion(appVersion,ejlPublicData.getUpdateFlag(), clinetType);
			getPublicDatasResponse.setUpdateFlag(updateFlag);
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}else{
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
		}
		fmlResponse.setData(getPublicDatasResponse);
		return fmlResponse;
	}
}