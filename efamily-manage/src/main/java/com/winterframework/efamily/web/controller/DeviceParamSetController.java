/**   
* @Title: DeviceParamSetController.java 
* @Package com.winterframework.efamily.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-18 上午11:03:40 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;

import com.winterframework.efamily.dao.IEjlComUserDeviceDao;

import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.DeviceParamConfig;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.enums.ParameterMap;

import com.winterframework.efamily.service.IEjlComParamConfigService;
import com.winterframework.efamily.utils.HttpUtil;

import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: DeviceParamSetController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-18 上午11:03:40 
*  
*/
@Controller("deviceParamSetController")
@RequestMapping(value = "/paramSet")
@SuppressWarnings("unchecked")
public class DeviceParamSetController {

	@PropertyConfig(value = "server.url")
	private String serverUrl;

	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	
	@Resource(name = "ejlComParamConfigServiceImpl")
	private IEjlComParamConfigService ejlComParamConfigServiceImpl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	private Logger log = LoggerFactory.getLogger(DeviceParamSetController.class);

	@RequestMapping("/toParamSet")
	public ModelAndView addFeedback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("param/paramSet");
		List<DeviceParamConfig> list = ejlComParamConfigServiceImpl.selectListObjByAttribute(null,new DeviceParamConfig());
		if(list != null){
			for(DeviceParamConfig p :list){
				view.addObject(p.getParamKey(), p.getParamValue());
			}
		}
		return view;
	}

	@Deprecated
	@RequestMapping("/setParam")
	public ModelAndView setParam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ModelAndView view = new ModelAndView("param/paramSet");
		List<WatchDeviceManageRequest> list = new ArrayList<WatchDeviceManageRequest>();
		
		try {
			List<DeviceParamConfig> dbList = ejlComParamConfigServiceImpl.selectListObjByAttribute(null,new DeviceParamConfig());
			for (int i = 400001; i <= 400023; i++) {
				String value = request.getParameter(ParameterMap.paraIndexKeyMap.get(i));
				String key = ParameterMap.paraIndexKeyMap.get(i);
				if (value != null && value != "") {
					DeviceParamConfig old = null;
					if(dbList != null){
						for(DeviceParamConfig dbEntity:dbList){
							if(dbEntity.getParamKey().equals(key)){
								old = dbEntity;
								break;
							}
						}
					}
					if(old==null ||!old.getParamValue().equals(value)){
					WatchDeviceManageRequest deviceParam = new WatchDeviceManageRequest();
					deviceParam.setParameterContext(value);
					deviceParam.setParameterIndex(i + "");
					list.add(deviceParam);
					//view.addObject(key, deviceParam.getParameterContext());
					DeviceParamConfig entity = new DeviceParamConfig();
					entity.setParamKey(key);
					entity.setParamValue(deviceParam.getParameterContext());
					Context ctx = new Context();
					ctx.set("userId", -1);
					if(old != null){
						ejlComParamConfigServiceImpl.remove(ctx,old.getId());
					}
					ejlComParamConfigServiceImpl.save(ctx,entity);
				}
			}
			}
			if (!list.isEmpty()) {
				toSetParam(list);
				
			}
		} catch (Exception e) {
			log.error("参数设置错误", e);
		}
		return addFeedback(request,response);
	}
	
	
	private void toSetParam(List<WatchDeviceManageRequest> list){
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setStatus(1l);
		List<EjlUserDevice> userDeviceList = ejlComUserDeviceDaoImpl.getAllByEntity(ejlUserDevice);
		for(EjlUserDevice userDevice:userDeviceList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("deviceUserId", userDevice.getDeviceId()+"");
			map.put("deviceId", userDevice.getUserId()+"");
			for(WatchDeviceManageRequest requestw:list){
				String parameterIndex = requestw.getParameterIndex();//功能参数的索引位置
				String parameterContext = requestw.getParameterContext();//功能参数的内容
				map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue()+"");
				map.put("paramCode", parameterIndex);
				map.put("paramValue", parameterContext);
				try {
					httpUtil.http(serverUrl,"push/device", map, Map.class);
				} catch (BizException e) {
					log.error("参数设置错误", e);
				}
			}
		}
	}

}
