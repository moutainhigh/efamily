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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: DeviceParamSetController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-18 上午11:03:40 
*  
*/
@Controller("manaulPushController")
@RequestMapping(value = "/manualPush")
@SuppressWarnings("unchecked")
public class ManaulPushController {

	@Resource(name = "httpClientImpl")
	protected IHttpClient httpClientImpl;
	
	@PropertyConfig(value = "server.url")
	private String serverUrl;

	@PropertyConfig(value="server.url.action.push")
	private String serverUrlActionPush;


	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
//	@Resource(name = "ejlComParamConfigServiceImpl")
//	private IEjlComParamConfigService ejlComParamConfigServiceImpl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl;
	
	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao ejlComDeviceDaoImpl;

	private Logger log = LoggerFactory.getLogger(ManaulPushController.class);

	@RequestMapping("/toPush")
	public ModelAndView toPush(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("param/manualPush");
//		ejlComUserDaoImpl.getAll();
		return view;
	}
	
	@RequestMapping("/push")
	public ModelAndView push(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("param/manualPush");
		Integer command = Integer.valueOf(request.getParameter("command"));
		try{
			EjlUserDevice  eEjlUserDevice= new EjlUserDevice();
			eEjlUserDevice.setStatus(1l);
			String userId = request.getParameter("userId");
			String deviceId = request.getParameter("deviceId");
			if(!StringUtils.isEmpty(userId)) {
				eEjlUserDevice.setUserId(Long.valueOf(userId));
			}
			if(!StringUtils.isEmpty(deviceId)) {
				eEjlUserDevice.setDeviceId(Long.valueOf(deviceId));
			}
			List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
			for(EjlUserDevice ejlUserDevice:list){
				Notification notification = getPusherParam(ejlUserDevice.getUserId(),ejlUserDevice.getDeviceId(),command);
				httpClientImpl.invokeHttp(serverUrl+serverUrlActionPush, notification,Map.class);
			}
		}catch(Exception e){
			log.error("device query task error", e);
		}
		Map model = new HashMap<String,String>();
		model.put("success", "success");
		view.addAllObjects(model);
		return view;
	}

	@RequestMapping("/findDeviceUser")
	@ResponseBody
	public Object findDeviceUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return JsonUtils.toJson(ejlComUserDaoImpl.findDeviceUser());
	}
	
	@RequestMapping("/findDeviceByUser")
	@ResponseBody
	public Object findDeviceByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EjlDevice  ejlDevice  = new EjlDevice();
		ejlDevice.setUserId(Long.valueOf(request.getParameter("userId")));
		List<EjlDevice> deviceList = ejlComDeviceDaoImpl.getAllByEntity(ejlDevice);
		List<EjlDevice> returnList = new ArrayList<EjlDevice>();
		//通过从device表中查询userId得到的数据和user_device表中得到的数据不一致，device表中有无效数据。过滤数据
		if(deviceList != null && !deviceList.isEmpty()){
			Map<Long,EjlDevice> deviceMap = new HashMap<Long,EjlDevice>();
			for(EjlDevice device : deviceList) {
				deviceMap.put(device.getId(), device);
			}
			EjlUserDevice  eEjlUserDevice= new EjlUserDevice();
			eEjlUserDevice.setStatus(1l);
			eEjlUserDevice.setUserId(Long.valueOf(request.getParameter("userId")));
			List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
			for(EjlUserDevice ejlUserDevice : list) {
				if(deviceMap.containsKey(ejlUserDevice.getDeviceId())) {
					returnList.add(deviceMap.get(ejlUserDevice.getDeviceId()));
				}
			}
		}
		return JsonUtils.toJson(returnList);
	}
	
	public Notification getPusherParam(Long userId,Long deviceId,Integer command)
	{ 
		NotyTarget target=new NotyTarget();
		target.setUserId(userId);
		target.setDeviceId(deviceId);
		
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(command);
		message.setData(getParamData());
		Notification notification=new Notification();
		notification.setTarget(target);
		notification.setMessage(message);
		return notification;
	}
	
	public Map<String, String> getParamData() {
		return new HashMap<String, String>();
	}
	
	

}
