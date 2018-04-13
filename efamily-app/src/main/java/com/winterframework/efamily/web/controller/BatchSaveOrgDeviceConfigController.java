package com.winterframework.efamily.web.controller;



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
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEfComOrgDeviceDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dto.HealthyRequest;
import com.winterframework.efamily.dto.UserHealthlyConfigRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.dto.device.param.DeviceParam;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.converter.ParameterConvertor;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComOrgDeviceService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("batchSaveOrgDeviceConfigController")
@RequestMapping("/server")
public class BatchSaveOrgDeviceConfigController {

	private static final Logger logger = LoggerFactory.getLogger(SaveHealthyUserConfigController.class);
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	@PropertyConfig(value="server.url")
	private String serverUrl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@Resource(name = "ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;

	@Resource(name = "efDeviceSettingServiceImpl")
	private IEfDeviceSettingService efDeviceSettingService;
	
	@Resource(name="efComOrgDeviceServiceImpl")
	private IEfComOrgDeviceService efComOrgDeviceServiceImpl;
	
	@Resource(name="efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	
	
	@RequestMapping("/batchSaveOrgDeviceConfig")
	@ResponseBody
	protected Response batchSaveOrgDeviceConfig(@RequestBody Request<List<DeviceParam>> request) throws Exception {
		Response fmlResponse = new Response(request);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		String key = String.valueOf(request.getCtx().get("orgKey"));
		try{
		EfOrg efo = new EfOrg();
		efo.setIkey(key);
		efo.setStatus(1);
		EfOrg efoEntity = efComOrgServiceImpl.selectOneObjByAttribute(request.getCtx(), efo);
		if(efoEntity == null){
			throw new BizException(StatusBizCode.ORG_KEY_INVALID.getValue());
		}
		for(DeviceParam deviceParam:request.getData()){
			
			EfOrgDevice entity = new EfOrgDevice();
			entity.setImei(deviceParam.getImei());
			entity.setOrgId(efoEntity.getId());
			entity.setStatus(1);
			EfOrgDevice efd = efComOrgDeviceServiceImpl.selectOneObjByAttribute(request.getCtx(), entity);
			if(efd == null){
				throw new BizException(StatusBizCode.IMEI_NOT_BIND_ORG.getValue());
			}
		}
		
		for(DeviceParam deviceParam:request.getData()){		
			
			WatchDeviceManageRequest watchDeviceManageRequest = deviceParam.getParam();
			
			Long userId = ejlUserDeviceServiceImpl.getDeviceUseingUserIdByCode(deviceParam.getImei());
			Long deviceId = ejlUserDeviceServiceImpl.getUserUseingDeviceId(userId);
			//deviceConfigServiceImpl.updateWatchDeviceParaBy(request.getCtx(), deviceId, ParameterConvertor.ParamIndex.LOCATION_ONFF, common.getLocationOnff(), userId);
			deviceConfigServiceImpl.updateWatchDeviceParaBy(request.getCtx(), deviceId, watchDeviceManageRequest.getParameterIndex(), watchDeviceManageRequest.getParameterContext(), userId);
		}}catch(BizException e){
			fmlResponse.setStatus(new Status(e.getCode()));
		}catch(Exception e){
			fmlResponse.setStatus(new Status(StatusBizCode.UNKNOW.getValue()));
		}
		return fmlResponse;
	}
}
