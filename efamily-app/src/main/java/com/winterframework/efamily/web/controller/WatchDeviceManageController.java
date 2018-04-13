package com.winterframework.efamily.web.controller;


import java.util.ArrayList;
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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.dto.WatchDeviceManageResponse;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 设置手表的参数  handler
 * @author david
 *
 */
@Controller("watchDeviceManageController")
@RequestMapping("/server")
public class WatchDeviceManageController{
	
	private static final Logger log = LoggerFactory.getLogger(WatchDeviceManageController.class); 
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;

	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@RequestMapping("/watchDeviceManage")
	@ResponseBody
	protected Response<WatchDeviceManageResponse> doHandle(@RequestBody Request<WatchDeviceManageRequest> request) throws NumberFormatException, Exception {
		Response<WatchDeviceManageResponse> fmlResponse = new Response<WatchDeviceManageResponse>(request);
		WatchDeviceManageResponse watchDeviceManageResponse = new WatchDeviceManageResponse();
		
		log.info("设置参数开始 : "+request.toString()+" ; ");
		//*** 参数检查 
		String userId = request.getUserId()+"";//操作员ID
		if(request.getData().getFamilyId() == null){
			Long familyId = ejlUserServiceImpl.get(request.getUserId()).getFamilyId();
			request.getData().setFamilyId(String.valueOf(familyId));
		}
		if(!deviceConfigServiceImpl.checkParam( request.getData(),request.getUserId())){
			log.error("WatchDeviceManageController设备参数管理时，参数检测出现异常： userId = "+request.getUserId()+" ; request.data = "+request.getData().toString());
			throw new BizException(StatusCode.UNKNOW.getValue());
		}
		String familyId = request.getData().getFamilyId();//家庭ID
		String watchId = request.getData().getWatchId();//手表ID
		String parameterIndex = request.getData().getParameterIndex();//功能参数的索引位置
		String parameterContext = request.getData().getParameterContext();//功能参数的内容
		Long oprType = request.getData().getOprType();
		
		EjlDeviceParmConfig ejlDeviceParmConfig = null;
		Long usingDeviceUserId = null;
		int operdateResult = StatusCode.OK.getValue();
		//*******************  手表设备参数： 偶数获取  奇数设置/操作     *******************
	    usingDeviceUserId = ejlUserDeviceServiceImpl.getDeviceUseingUserId(Long.valueOf(watchId));
		
		if(EfamilyConstant.OPRTYPE_GET == oprType.longValue()){
			ejlDeviceParmConfig = deviceConfigServiceImpl.getWatchDeviceParaBy(Long.valueOf(watchId), parameterIndex,parameterContext);
			parameterContext = ejlDeviceParmConfig.getParamValue();
		}else{
			//****  只有家庭成员可以修改设备
			List<EjlUser> userList = new ArrayList<EjlUser>();
			if(EfamilyConstant.APP_DEVICE_UN_BIND.equals(parameterIndex)){
                //***** 解绑设备时 通知APP用户
				userList = ejlUserServiceImpl.getEjlUserFamilyAndAttentionList(request.getUserId());
			}
			if(deviceConfigServiceImpl.updateWatchDeviceParaBy(request.getCtx(),Long.valueOf(watchId),parameterIndex,parameterContext,request.getUserId())){
				if(EfamilyConstant.APP_DEVICE_UN_BIND.equals(parameterIndex)){
                    //***** 解绑设备时 通知APP用户
					deviceConfigServiceImpl.notifyAppForUnbindDevice(request.getUserId(),usingDeviceUserId, Long.valueOf(watchId),userList);
				}
			}else{
				operdateResult = StatusCode.FAILED.getValue();
			}
		}
		
		fmlResponse.setStatus(new Status(operdateResult));
		watchDeviceManageResponse.setFamilyId(familyId);
		watchDeviceManageResponse.setParameterContext(parameterContext);
		watchDeviceManageResponse.setParameterIndex(parameterIndex);
		watchDeviceManageResponse.setUserId(userId);
		watchDeviceManageResponse.setWatchId(watchId);
		watchDeviceManageResponse.setUsingDeviceUserId(usingDeviceUserId+"");
		fmlResponse.setData(watchDeviceManageResponse);
		log.info("设置参数结束 : "+request.toString()+" ; ");
		
		return fmlResponse;
	}
	
}
