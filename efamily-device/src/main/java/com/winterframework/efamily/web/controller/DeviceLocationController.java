package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
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

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEfComLocationGpsDao;
import com.winterframework.efamily.dto.LocationMobileRequest;
import com.winterframework.efamily.dto.LocationMobileResponse;
import com.winterframework.efamily.dto.LocationRequest;
import com.winterframework.efamily.dto.LocationResponse;
import com.winterframework.efamily.dto.LocationWifiRequest;
import com.winterframework.efamily.dto.device.DeviceLocationExceptionRequest;
import com.winterframework.efamily.dto.device.DeviceLocationExceptionResponse;
import com.winterframework.efamily.dto.device.DeviceLocationSatelliteRequest;
import com.winterframework.efamily.dto.device.DeviceLocationSatelliteResponse;
import com.winterframework.efamily.dto.device.DeviceMobileRequest;
import com.winterframework.efamily.entity.DeviceLocationSatellite;
import com.winterframework.efamily.entity.DeviceMobile;
import com.winterframework.efamily.entity.EfLocationGps;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.enums.QueuePrefix;
import com.winterframework.efamily.service.IDeviceLocationService;
import com.winterframework.efamily.service.IDeviceMobileService;
import com.winterframework.efamily.service.IEfComLocationGpsService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
@Controller("deviceLocationController")
@RequestMapping("/device/location")
public class DeviceLocationController {
	private static final Logger log= LoggerFactory.getLogger(DeviceLocationController.class);
	
	@Resource(name = "deviceLocationServiceImpl")
	private IDeviceLocationService deviceLocationService;
	
	@Resource(name = "efComLocationGpsServiceImpl")
	private IEfComLocationGpsService efComLocationGpsService;
	@Resource(name = "deviceMobileServiceImpl")
	private IDeviceMobileService deviceMobileService;
	
	@PropertyConfig(value="task.url")
	private String serverUrl;
	@PropertyConfig(value="task.sendUserLocation")
	private String sendUserLocationUrl;
	@Resource(name="httpUtil")
	private HttpUtil httpUtil;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	@Resource(name = "efComLocationGpsDaoImpl")
	private IEfComLocationGpsDao efComLocationGpsDao; 
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<List<LocationRequest>> req) throws BizException{
		List<LocationRequest> bizReqList=req.getData();
		List<EfLocationGps> locationList=new ArrayList<EfLocationGps>();
		if(null==bizReqList){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		EfLocationGps location=null;
		for(LocationRequest bizReq:bizReqList){
			location=new EfLocationGps();
			location=DTOConvert.toLocation(req.getUserId(),req.getDeviceId(), bizReq);
			locationList.add(location);
		}
		efComLocationGpsService.save(req.getCtx(),locationList);
		Response<LocationResponse> res=new Response<LocationResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/wifi/upload")
	@ResponseBody
	public Object wifiUpload(@RequestBody Request<LocationMobileRequest> req) throws BizException{
		LocationMobileRequest bizReq=req.getData();
		if(null==bizReq){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		List<LocationWifiRequest>  wifiList=bizReq.getWifiList();
		List<DeviceMobileRequest>  towerList=bizReq.getMobileList();
		List<LocationRequest>  gpsList=bizReq.getGpsList();
		if(null!=wifiList){
			List<EfLocationWifi> locationList=new ArrayList<EfLocationWifi>();
			for(LocationWifiRequest wifi:wifiList){
				locationList.add(DTOConvert.toLocationWifi(req.getUserId(),req.getDeviceId(), wifi));
			}
			deviceLocationService.wifiUpload(req.getCtx(),locationList);
		}
		if(null!=towerList){
			List<DeviceMobile> locationList=new ArrayList<DeviceMobile>();
			for(DeviceMobileRequest tower:towerList){
				DeviceMobile deviceMobile = DTOConvert.toDeviceMobile(req.getUserId(),req.getDeviceId(), tower);
				locationList.add(deviceMobile);
			}
			deviceMobileService.save(req.getCtx(),locationList);
		}
		if(null!=gpsList){
			List<EfLocationGps> locationList=new ArrayList<EfLocationGps>();
			List<String> gpsOffs=null;
			try{
				gpsOffs=efComLocationGpsDao.getGpsOff();
			}catch(Exception e){
				
			}
			if(gpsOffs==null || !gpsOffs.contains(req.getDeviceId()+"")){
				for(LocationRequest gps:gpsList){
					EfLocationGps locationGps = DTOConvert.toLocation(req.getUserId(),req.getDeviceId(), gps);
					locationList.add(locationGps);
				}
				efComLocationGpsService.save(req.getCtx(),locationList);
			}
		}
		
		try{
			Long userId=req.getUserId();
			Long deviceId=req.getDeviceId();
			String target=userId+Separator.vertical+deviceId;
			String key = QueuePrefix.QUEUE_LOCATION_QUERY_LOCK+target;
			String value=redisClient.get(key);
			//定位查询请求在forwardSeconds内才处理
			if(null!=value && DateUtils.isBefore(DateUtils.currentDate(),DateUtils.addSeconds(DateUtils.getDate(Long.valueOf(value)), 120)) ){	//如果在处理完成之前 有新数据上传 则会再次触发 进入task之后拿不到锁就不处理了
				final Map<String,String> map = new HashMap<String,String>();
				map.put("userId", userId+"");
				map.put("deviceId",deviceId+"");
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {  
							httpUtil.http(serverUrl,sendUserLocationUrl, map);
						} catch (Exception e) {
							log.error("send user location query error", e);
						} 
					}
				}).start();
			}
		}catch(Exception e){
			log.error("send user location query error", e);
		}
		Response<LocationMobileResponse> res=new Response<LocationMobileResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		
		return res;
	}
	@RequestMapping("/satellite/upload")
	@ResponseBody
	public Object satelliteUpload(@RequestBody Request<List<DeviceLocationSatelliteRequest>> req) throws BizException{
		List<DeviceLocationSatelliteRequest> bizReqList=req.getData(); ;
		if(null==bizReqList){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		List<DeviceLocationSatellite> locationSateList=new ArrayList<DeviceLocationSatellite>();
		for(DeviceLocationSatelliteRequest bizReq:bizReqList){
			locationSateList.add(DTOConvert.toDeviceLocationSatellite(req.getDeviceId(), bizReq));				
		} 
		deviceLocationService.uploadSatellite(req.getCtx(),locationSateList);
		Response<DeviceLocationSatelliteResponse> res=new Response<DeviceLocationSatelliteResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
	@RequestMapping("/exception/upload")
	@ResponseBody
	public Object exceptionUpload(@RequestBody Request<DeviceLocationExceptionRequest> req) throws BizException{
		DeviceLocationExceptionRequest bizReq=req.getData();
		if(null==bizReq){
			throw new BizException(StatusCode.PARAM_INVALID.getValue());
		}
		//暂时只做打印日志处理
		log.error("Locate exception:status="+bizReq.getStatus()+" time="+bizReq.getTime()+"");
		Response<DeviceLocationExceptionResponse> res=new Response<DeviceLocationExceptionResponse>(req);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		return res;
	}
}
