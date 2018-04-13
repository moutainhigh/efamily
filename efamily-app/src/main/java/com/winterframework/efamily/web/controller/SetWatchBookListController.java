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

import com.winterframework.efamily.dto.WatchDeviceManageRequest;

import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceService;

import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 设置手表的参数  handler
 * @author david
 *
 */
@Controller("setWatchBookListController")
@RequestMapping("/server")
public class SetWatchBookListController{
	
	private static final Logger log = LoggerFactory.getLogger(WatchDeviceManageController.class); 
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@RequestMapping("/setWatchBookList")
	@ResponseBody
	protected Response<Object> doHandle(@RequestBody Request<List<WatchDeviceManageRequest>> request) throws NumberFormatException, Exception {
		Response<Object> fmlResponse = new Response<Object>(request);
		log.info("手表通讯录开始 : "+request.toString()+" ; ");
		try{
		deviceConfigServiceImpl.setWatchAdderssBookList(request.getCtx(), request.getData());
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		}catch(BizException e){
			fmlResponse.setStatus(new Status(e.getCode()));
		}catch(Exception e){
			fmlResponse.setStatus(new Status(StatusBizCode.UNKNOW.getValue()));
		}
		
		log.info("手表通讯录结束 : "+request.toString()+" ; ");
		
		return fmlResponse;
	}
	
}
