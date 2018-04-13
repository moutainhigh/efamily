package com.winterframework.efamily.web.controller;

import java.util.List;

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

import com.winterframework.efamily.entity.EfCustomer;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IQrcodeService;


@Controller("getDownLoaderUrlCottroller")
@RequestMapping("/server")
public class GetDownLoaderUrlCottroller {
	private static final Logger log = LoggerFactory.getLogger(GetDownLoaderUrlCottroller.class);
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl;
	
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerService;

	@RequestMapping("/getDownLoaderUrl")
	@ResponseBody
	protected Response<String> getDownLoaderUrl(@RequestBody Request<String> request)throws BizException {
		Response<String> fmlResponse = new Response<String>(request);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		try{
		Integer customerNumber = qrcodeServiceImpl.getByImei(request.getData()).getCustomerNumber();
		EfCustomer ec = efComCustomerService.getEfCustomerBy(customerNumber);
		fmlResponse.setData(ec.getDownloadUrl());
		}catch(Exception e){
			fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
			log.error("getDownLoaderUrl error imei="+request.getData(), e);
		}
		return fmlResponse;
	}
}
