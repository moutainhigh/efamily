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
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dto.HealthyRequest;
import com.winterframework.efamily.dto.UserHealthlyConfigRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EfUserHealthSetting;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComOrgDeviceService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.efamily.service.IEfUserHealthSettingService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/**
 * 保存设备设置
 * @author floy
 *
 */
@Controller("saveHealthyUserConfigController")
@RequestMapping("/server")
public class SaveHealthyUserConfigController {

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
	@Resource(name="efUserHealthSettingServiceImpl")
	private IEfUserHealthSettingService efUserHealthSettingService;
	
	
	
	@RequestMapping("/batchSaveHealthyUserConfig")
	@ResponseBody
	protected Response batchSaveHealthyUserConfig(@RequestBody Request<List<HealthyRequest>> request) throws Exception {
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
		for(HealthyRequest healthyRequest:request.getData()){
			
			EfOrgDevice entity = new EfOrgDevice();
			entity.setImei(healthyRequest.getImei());
			entity.setOrgId(efoEntity.getId());
			entity.setStatus(1);
			EfOrgDevice efd = efComOrgDeviceServiceImpl.selectOneObjByAttribute(request.getCtx(), entity);
			if(efd == null){
				throw new BizException(StatusBizCode.IMEI_NOT_BIND_ORG.getValue());
			}
		}
		
		for(HealthyRequest healthyRequest:request.getData()){			
			UserHealthlyConfigRequest req = new UserHealthlyConfigRequest();
			req.setUserId(ejlUserDeviceServiceImpl.getDeviceUseingUserIdByCode(healthyRequest.getImei()));
			req.setDeviceId(ejlUserDeviceServiceImpl.getUserUseingDeviceId(req.getUserId()));
			req.setRemindSwitch(healthyRequest.getSitOnff());
			req.setSitTime(healthyRequest.getSitTime()!=null?Float.valueOf(healthyRequest.getSitTime()):null);
			req.setSleepSwitch(healthyRequest.getSleepOnff());
			List<String> sitList = healthyRequest.getSittingSpan();
			List<String> sleepList = healthyRequest.getSleepSpan();
			String sitSpan = null;
			String sleepSpan = null;
			if(sitList!=null){
				sitSpan="";
				for(String sis :sitList){
					sitSpan+=sis+",";
				}
				sitSpan = sitSpan.substring(0, sitSpan.length()-1);
			}
			if(sleepList!=null){
				sleepSpan="";
				for(String sleeps :sleepList){
					sleepSpan+=sleeps+",";
				}
				sleepSpan = sleepSpan.substring(0, sitSpan.length()-1);
			}
			req.setSittingSpan(sitSpan);
			req.setSleepSpan(sleepSpan);
			ejlUserServiceImpl.updateHealthlyUserConfig(request.getCtx(),req);
			UserHealthlyConfigRequest bizReq=req;
			Map<String,String> sendMap =toSetParamNew(request.getCtx(), bizReq);
			EjlDevice device=ejlDeviceServiceImpl.get(bizReq.getDeviceId());
			if(null!=device){
				String version=device.getSoftwareVersion();
				if(null!=version){
					Map<String,String> map = new HashMap<String,String>();
					map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
					map.put("code", EfDeviceSetting.ModuleCode.HEALTH.getValue()+"");
					map.put("value", sendMap.get("healthStr"));
					ejlUserServiceImpl.pushDevice(bizReq.getUserId(), bizReq.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);
					
					Map<String,String> mapCommon = new HashMap<String,String>();
					mapCommon.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
					mapCommon.put("code", EfDeviceSetting.ModuleCode.COMMON.getValue()+"");
					mapCommon.put("value", sendMap.get("commonStr"));
					ejlUserServiceImpl.pushDevice(bizReq.getUserId(), bizReq.getDeviceId(), mapCommon, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);
				}else{	//兼容旧版手表软件
					toSetParam(bizReq);
				}
			}
		}}catch(BizException e){
			fmlResponse.setStatus(new Status(e.getCode()));
		}
		return fmlResponse;
	}
	
	
	@RequestMapping("/changeDevicePre")
	@ResponseBody
	protected Response changeDevicePre(@RequestBody Request<DeviceParamFrequency> request) throws Exception {
		Response fmlResponse = new Response(request);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		Long userId = Long.valueOf(request.getUserId());
		Long deviceId = Long.valueOf(request.getDeviceId());
		DeviceParamFrequency df = request.getData();
		EjlUserDevice eEjlUserDevice = new EjlUserDevice();
		eEjlUserDevice.setStatus(1l);
		eEjlUserDevice.setUserId(userId);
		eEjlUserDevice.setDeviceId(deviceId);
		List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
		for(EjlUserDevice ejlUserDevice:list){
			EjlDevice device=ejlDeviceServiceImpl.get(ejlUserDevice.getDeviceId());
			if(null!=device){
				String version=device.getSoftwareVersion();
				if(null!=version){	
					
					EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(5433l,786l);
					if(deviceSetting == null){
						continue;
					}
					String frequency= deviceSetting.getFrequency();
					if(frequency != null){
						DeviceParamFrequency deviceParamFrequency = JsonUtils.fromJson(frequency, DeviceParamFrequency.class);
						deviceParamFrequency.setLocationUfreq(df==null||df.getLocationUfreq()==null?330:df.getLocationUfreq());
						deviceParamFrequency.setLocationGfreq(df==null||df.getBatteryGfreq()==null?115:df.getLocationGfreq());
						deviceParamFrequency.setWalkUfreq(df==null||df.getWalkUfreq()==null?258:df.getWalkUfreq());
						deviceParamFrequency.setSignalGfreq(df==null||df.getSignalGfreq()==null?3210:df.getSignalGfreq());
						deviceParamFrequency.setSignalUfreq(df==null||df.getSignalUfreq()==null?3650:df.getSignalUfreq());
						deviceParamFrequency.setBatteryGfreq(df==null||df.getBatteryGfreq()==null?180:df.getBatteryGfreq());
						deviceParamFrequency.setBatteryUfreq(df==null||df.getBatteryUfreq()==null?560:df.getBatteryUfreq());
						deviceParamFrequency.setHeartGfreq(df==null||df.getHeartGfreq()==null?30:df.getHeartGfreq());
						deviceParamFrequency.setHeartUfreq(df==null||df.getHeartUfreq()==null?55:df.getHeartUfreq());
						Context ctx = new Context();
						ctx.set("userId", -1);
						
						String frequencyStr=JsonUtils.toJson(deviceParamFrequency);
						deviceSetting.setFrequency(frequencyStr);
						efDeviceSettingService.save(ctx, deviceSetting);
						Map<String,String> map = new HashMap<String,String>();
						map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
						map.put("code", EfDeviceSetting.ModuleCode.FREQUENCY.getValue()+"");
						map.put("value", frequencyStr);
						ejlUserServiceImpl.pushDevice(deviceSetting.getUserId(), deviceSetting.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);
				}
			}
			}
		}
		
		return fmlResponse;
	}
	
	
	
	@RequestMapping("/saveHealthyUserConfig")
	@ResponseBody
	protected Response doHandle(@RequestBody Request<UserHealthlyConfigRequest> request) throws Exception {
		Response fmlResponse = new Response(request);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		ejlUserServiceImpl.updateHealthlyUserConfig(request.getCtx(),request.getData());
		UserHealthlyConfigRequest bizReq=request.getData();
		EjlDevice device=ejlDeviceServiceImpl.get(bizReq.getDeviceId());
		Map<String,String> sendMap =toSetParamNew(request.getCtx(), bizReq);
		if(null!=device){
			String version=device.getSoftwareVersion();
			if(null!=version){
				Map<String,String> map = new HashMap<String,String>();
				map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
				map.put("code", EfDeviceSetting.ModuleCode.HEALTH.getValue()+"");
				map.put("value", sendMap.get("healthStr"));
				ejlUserServiceImpl.pushDevice(bizReq.getUserId(), bizReq.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);
				
				Map<String,String> mapCommon = new HashMap<String,String>();
				mapCommon.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
				mapCommon.put("code", EfDeviceSetting.ModuleCode.COMMON.getValue()+"");
				mapCommon.put("value", sendMap.get("commonStr"));
				ejlUserServiceImpl.pushDevice(bizReq.getUserId(), bizReq.getDeviceId(), mapCommon, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);
			}else{	//兼容旧版手表软件
				toSetParam(bizReq);
			}
		}	
		return fmlResponse;
	}
	private void toSetParam(UserHealthlyConfigRequest struc) throws Exception{
		Map<String,String> map = new HashMap<String,String>();

		if(struc.getSitTime() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400027");
			map.put("value", String.valueOf(struc.getSitTime()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.SETT);
		}
		
		/*if(struc.getHeartSwitch() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400017");
			map.put("value", String.valueOf(struc.getHeartSwitch()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);
		}*/
		if(struc.getStepCountSwitch() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400020");
			map.put("value", String.valueOf(struc.getStepCountSwitch()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);
		}
		if(struc.getRemindSwitch() != null){
			//兼容以前的版本
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400026");
			map.put("value", String.valueOf(struc.getRemindSwitch()==0?1:0));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);	
		}
		
		if(struc.getHeight() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400033");
			map.put("value", String.valueOf(struc.getHeight()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);
		}
		if(struc.getWeight() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400034");
			map.put("value", String.valueOf(struc.getWeight()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);
		}
		if(struc.getStepCount() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400035");
			map.put("value", String.valueOf(struc.getStepCount()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);
		}
		/*if(struc.getClimbSwitch() != null){
			map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
			map.put("code", "400036");
			map.put("value", String.valueOf(struc.getClimbSwitch()));
			ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(), NotyMessage.Type.OPER);
		}*/
	}
	private Map<String,String> toSetParamNew(Context ctx,UserHealthlyConfigRequest struc) throws Exception{
		EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(struc.getUserId(),struc.getDeviceId());
		if(deviceSetting == null){
			deviceSetting = new EfDeviceSetting();
			deviceSetting.setDeviceId(struc.getDeviceId());
			deviceSetting.setUserId(struc.getUserId());
		}
		String healthSett=deviceSetting.getHealth();
		String commonSett=deviceSetting.getCommon();
		DeviceParamHealth paramHealth=null;
		DeviceParamCommon paramCommon=null;
		if(healthSett!= null){
		paramHealth=JsonUtils.fromJson(healthSett, DeviceParamHealth.class);
		}
		if(commonSett!= null){
		paramCommon=JsonUtils.fromJson(commonSett, DeviceParamCommon.class);
		}
		if(paramHealth==null){
			paramHealth=new DeviceParamHealth();
		}
		if(paramCommon==null){
			paramCommon=new DeviceParamCommon();
		}
		if(struc.getHeight()!=null || struc.getWeight() != null|| struc.getAge() != null || struc.getArm() != null){
			EfUserHealthSetting userHealthSett=efUserHealthSettingService.getByUserId(struc.getUserId());
			if(null==userHealthSett){
				throw new BizException(StatusBizCode.USER_HEALTH_SETT_MISSING.getValue());
			}
			if(struc.getAge()!=null){
				userHealthSett.setAge(struc.getAge().intValue());
				paramHealth.setAge(null!=struc.getAge()?struc.getAge().intValue():null);
			}
			if(struc.getHeight()!=null){
				paramHealth.setHeight(Integer.valueOf(struc.getHeight()+""));
				userHealthSett.setHeight(Integer.valueOf(struc.getHeight()+""));
			}
			if(struc.getWeight()!= null){
				paramHealth.setWeight(struc.getWeight());
				userHealthSett.setWeight(struc.getWeight());
			}
			paramHealth.setSex(null!=struc.getSex()?struc.getSex().intValue():null);
			paramHealth.setArm(struc.getArm());
			userHealthSett.setArm(struc.getArm());
			
			efUserHealthSettingService.save(ctx, userHealthSett);
		}
		
		if(struc.getRemindSwitch() != null){
			paramCommon.setSittingOnff(struc.getRemindSwitch().intValue());
			paramHealth.setSittingTime(struc.getSitTime().intValue());
			paramHealth.setSittingSpan(struc.getSittingSpan().split(","));
			
		}
		
		if(struc.getSleepSwitch() != null){
			paramCommon.setSleepOnff(struc.getSleepSwitch().intValue());
			paramHealth.setSleepSpan(struc.getSleepSpan().split(","));
		}
		
		if(struc.getStepCountSwitch() != null){
			//计步开关不提供
		}
		String healthStr=JsonUtils.toJson(paramHealth);
		deviceSetting.setHealth(healthStr);
		String commonStr=JsonUtils.toJson(paramCommon);
		deviceSetting.setCommon(commonStr);
		efDeviceSettingService.save(ctx, deviceSetting);
		Map<String,String> sendMap = new HashMap<String,String>();
		sendMap.put("healthStr", healthStr);
		sendMap.put("commonStr", commonStr);
		return sendMap;
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
		map.put("code", EfDeviceSetting.ModuleCode.HEALTH.getValue()+"");
		map.put("value", healthStr);
		ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);
		
		Map<String,String> mapCommon = new HashMap<String,String>();
		mapCommon.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
		mapCommon.put("code", EfDeviceSetting.ModuleCode.COMMON.getValue()+"");
		mapCommon.put("value", commonStr);
		ejlUserServiceImpl.pushDevice(struc.getUserId(), struc.getDeviceId(), mapCommon, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT);*/
	}
}
