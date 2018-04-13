package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.device.DeviceWeatherFutherRequest;
import com.winterframework.efamily.dto.device.DeviceWeatherRequest;
import com.winterframework.efamily.entity.EfUserWeather;
import com.winterframework.efamily.entity.EfWeather;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.EjlUserExtendInfo;
import com.winterframework.efamily.entity.JuheWeatherResult;
import com.winterframework.efamily.service.IEfComUserWeatherService;
import com.winterframework.efamily.service.IEfComWeatherService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
import com.winterframework.efamily.service.IEjlComUserExtendInfoService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.ISoftwareVersionService;
import com.winterframework.efamily.service.IWeatherService;
import com.winterframework.efamily.utils.JuheGetWeatherUtil;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("weatherServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class WeatherServiceImpl  implements IWeatherService  {
	@Resource(name = "ejlComLocationDaoImpl")
	private IEjlComLocationDao ejlComLocationDaoImpl;
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;

	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	@Resource(name = "ejlComWeatherServiceImpl")
	private IEfComWeatherService ejlComWeatherServiceImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Resource(name ="ejlComUserExtendInfoServiceImpl")
	private IEjlComUserExtendInfoService ejlComUserExtendInfoDaoImpl;

	@Resource(name = "efComUserWeatherServiceImpl")
	private IEfComUserWeatherService efComUserWeatherServiceImpl;
	@Resource(name ="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	@Resource(name = "ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationService;
	@Resource(name = "ejlComUserDeviceServiceImpl")
	private IEjlComUserDeviceService ejlComUserDeviceService;
	@Resource(name = "ejlComUserExtendInfoServiceImpl")
	private IEjlComUserExtendInfoService ejlComUserExtendInfoService;
	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherService;
	
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Resource(name="softwareVersionServiceImpl")
	private ISoftwareVersionService softwareVersionService;
	
	
	private Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);
	
	
	
	@PropertyConfig(value = "juhe.weather.key")
	private String key;
	
	private DeviceWeatherRequest convertWeatherToStruc(EfWeather efWeather){
		DeviceWeatherRequest struc = null;
		if (efWeather != null) {
			struc = new DeviceWeatherRequest();
			struc.setCurtemperature(efWeather.getTemperatureCurrent());
			struc.setDress(efWeather.getDress());
			struc.setTemperature(efWeather.getTemperature());
			struc.setWeather(efWeather.getWeather());
			struc.setWind(efWeather.getWind());
			struc.setAirQuality(efWeather.getPollution());
			struc.setCity(efWeather.getCityName());
			struc.setDate(DateUtils.format(efWeather.getSolarDate()));
		}
		return struc;
	}
	
	
	private DeviceWeatherFutherRequest convertWeatherToFutherStruc(EfWeather efWeather){
		DeviceWeatherFutherRequest struc = null;
		if (efWeather != null) {
			struc = new DeviceWeatherFutherRequest();
			struc.setCurtemperature(efWeather.getTemperatureCurrent());
			struc.setDress(efWeather.getDress());
			struc.setTemperature(efWeather.getTemperature());
			struc.setWeather(efWeather.getWeather());
			struc.setWind(efWeather.getWind());
			struc.setAirQuality(efWeather.getPollution());
			struc.setCity(efWeather.getCityName());
		}
		return struc;
	}

	@Override
	public void sendWeather(List<EfUserWeather> userList, EfWeather efWeather)
			throws Exception {
		if(userList != null && !userList.isEmpty()){
			DeviceWeatherRequest struc = convertWeatherToStruc(efWeather);
			DeviceWeatherFutherRequest futherStruc = getFutherStruc(efWeather);
			List<DeviceWeatherRequest> futherList = new ArrayList<DeviceWeatherRequest>();
			futherList.add(struc);
			futherList.addAll(futherStruc.getFutherList());
			efUserWeatherListSort(userList);
			List<Long> userIdList = new ArrayList<Long>();
			for (EfUserWeather efUserWeather : userList) {
				try{
				EjlUserExtendInfo entity = new EjlUserExtendInfo();
				entity.setUserId(efUserWeather.getUserId());
				Context ctx = new Context();
				ctx.set("userId", -1);
				if(struc!= null){
					if(!userIdList.contains(efUserWeather.getUserId())){
						EjlUserDevice ejlUserDevice = ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(efUserWeather.getUserId());
						if(ejlUserDevice != null){
						EjlDevice device = ejlComDeviceService.get(ejlUserDevice.getDeviceId());
						NotyTarget target=new NotyTarget(ejlUserDevice.getUserId(),ejlUserDevice.getDeviceId());
						Map<String, String> data = new HashMap<String, String>(); 
						data.put("data", softwareVersionService.gt(device.getId(), "v2.0")?JsonUtils.toJson(futherList):JsonUtils.toJson(struc)); 
						NotyMessage message=new NotyMessage();
						message.setCommand(28983);
						message.setType(NotyMessage.Type.NOTICE);
						message.setData(data);
						Notification notification=new Notification();
						notification.setTarget(target);
						notification.setMessage(message);
						notification.setRealTime(false);
						notificationUtil.notification(notification);
						userIdList.add(efUserWeather.getUserId());}
					}
					efUserWeather.setStatus(1);
					efUserWeather.setWeatherId(efWeather.getId());
					efComUserWeatherServiceImpl.save(ctx, efUserWeather);
				}
				}catch(Exception e){
					log.error("sendWeather error userId="+efUserWeather.getUserId(), e);
				}
			}
		}
	}
	
	private DeviceWeatherFutherRequest getFutherStruc(EfWeather efWeather)throws Exception{
		DeviceWeatherFutherRequest struc = convertWeatherToFutherStruc(efWeather);
		Date beginDate = DateUtils.getEndTimeOfDate(efWeather.getSolarDate());
		List<EfWeather> futhers = ejlComWeatherServiceImpl.getFutherWeather(efWeather.getCityName(), beginDate, 7);
		if(futhers != null){
			List<DeviceWeatherRequest> futherWeathers = new ArrayList<DeviceWeatherRequest>();
			for(EfWeather futher:futhers){
				futherWeathers.add(convertWeatherToStruc(futher));
			}
			struc.setFutherList(futherWeathers);
		}
		return struc;
	}

	@Override
	public void getWeather(String cityName) throws Exception {
		EfWeather weather = this.getWeatherForCity(cityName);
		if(weather != null){	
			this.sendUserWeather(weather);
			ejlComWeatherServiceImpl.saveOne(weather);
			this.saveFutherWeather(weather.getWeatherList());
		}
	}
	
	private void saveFutherWeather(List<EfWeather> futherWeathers){
		if(futherWeathers == null){
			return;
		}
		try{
		for(EfWeather efWeather:futherWeathers){
			ejlComWeatherServiceImpl.saveFutherOne(efWeather);
		}}catch(Exception e){
			log.error("saveFutherWeather error", e);
		}
	}
	
	private EfWeather getWeatherForCity(String cityName) throws Exception{
		EfWeather weather = null;
		try{
		 weather = JuheGetWeatherUtil.getJuheWeatherDate(cityName,key);
		}catch(Exception e){
			log.error("get city weather errro cityName:"+cityName, e);
		}
		return weather;
	}
	
	private void saveCurrentCityUserWeather(String cityName) throws Exception{
		EjlUserExtendInfo entity  = new EjlUserExtendInfo();
		entity.setCurrentCity(cityName);
		Context ctx = new Context();
		ctx.set("userId", -1);
		List<EjlUserExtendInfo> list = ejlComUserExtendInfoDaoImpl.selectListObjByAttribute(ctx, entity);
		if(list!=null&&!list.isEmpty()){
			for(EjlUserExtendInfo ejlUserExtendInfo:list){
				this.deleteUserRepeatDate(ejlUserExtendInfo.getUserId());
				EfUserWeather ejlUserWeather = new EfUserWeather();
				ejlUserWeather.setUserId(ejlUserExtendInfo.getUserId());
				ejlUserWeather.setCityName(cityName);
				EjlUserDevice ejlUserDevice = ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(ejlUserExtendInfo.getUserId());
				if(ejlUserDevice!= null){
					efComUserWeatherServiceImpl.save(ctx, ejlUserWeather);
				}
			}
		}
	}
	
	
	private void deleteUserRepeatDate(Long userId) throws BizException{
		Context ctx = new Context();
		ctx.set("userId", -1);
		EfUserWeather weatherEntity = new EfUserWeather();
		weatherEntity.setUserId(userId);
		weatherEntity.setStatus(0);
		List<EfUserWeather> resultList = efComUserWeatherServiceImpl.selectListObjByAttribute(ctx, weatherEntity);
		if(resultList!=null && !resultList.isEmpty()){
			for(EfUserWeather deleteResult : resultList){
				efComUserWeatherServiceImpl.remove(ctx, deleteResult.getId());
			}
		}
		
	}
	
	
	private void sendUserWeather(EfWeather efWeather) throws Exception{
		EfWeather newOne = ejlComWeatherServiceImpl.getNewWeatherByCityName(efWeather
				.getCityName());
		if (newOne != null) {
			String dbDate = DateUtils.format(newOne.getSolarDate());
			String insertDate = DateUtils.format(efWeather.getSolarDate());
			if (dbDate.equals(insertDate)) {
				if (!newOne.getWeather().equals(efWeather.getWeather())
						|| !newOne.getTemperatureCurrent().equals(
								efWeather.getTemperatureCurrent())
						|| !newOne.getTemperature().equals(
								efWeather.getTemperature())
						|| !newOne.getPollution().equals(
								efWeather.getPollution())) {
					
					saveCurrentCityUserWeather(efWeather.getCityName());
				}
			} else {
				saveCurrentCityUserWeather(efWeather.getCityName());
			}
		} else {
			saveCurrentCityUserWeather(efWeather.getCityName());
		}
	}
	
	public void efUserWeatherListSort(List<EfUserWeather> userList){
		Collections.sort(userList,new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				EfUserWeather e1 = (EfUserWeather)o1;
				EfUserWeather e2 = (EfUserWeather)o2;
				return e2.getCreateTime().compareTo(e1.getCreateTime());
			}
		});
	}
	
	@Override
	public void userWeatherCityHander(EjlLocation ejlLocation){
	try{
		String currentCity = ejlLocation.getCity();
		String lastCity = null;
		EjlUserExtendInfo  entity = new EjlUserExtendInfo();
		entity.setUserId(ejlLocation.getUserId());
		Context  ctx = new Context();
		ctx.set("userId", -1);
		EjlUserExtendInfo result = ejlComUserExtendInfoDaoImpl.selectOneObjByAttribute(ctx, entity);
		if(result != null){
			lastCity = result.getCurrentCity();
		}
		if(lastCity == null || !lastCity.equals(currentCity)){
			EfWeather  efWeather = ejlComWeatherServiceImpl.getNewWeatherByCityName(currentCity);
			if(efWeather == null){
				this.getWeather(currentCity);
			}
			else{
				this.deleteUserRepeatDate(ejlLocation.getUserId());
				EfUserWeather efUserWeather = new EfUserWeather();
				efUserWeather.setCityName(currentCity);
				efUserWeather.setUserId(ejlLocation.getUserId());
				efComUserWeatherServiceImpl.save(ctx, efUserWeather);
			}
			if(result == null){
				result = new EjlUserExtendInfo();
				result.setUserId(ejlLocation.getUserId());
			}
			result.setCurrentCity(currentCity);
			ejlComUserExtendInfoDaoImpl.save(ctx, result);
		}
		
		}catch(Exception e){
			log.error("[loation_change_weather]: location = "+ejlLocation.toString(),e.getMessage());
		}
	}
	@Override
	public void cityChange() throws BizException {
		/**
		 * 1.获取有效用户列表
		 * 2.找到并更新用户当前城市
		 * 3.用户当前城市变化 则入天气推送队列
		 */
		List<EjlUser> userList=ejlComUserService.getValidDeviceList();
		if(null!=userList){
			Context ctx=new Context();
			ctx.set("userId",-1);
			for(EjlUser user:userList){
				try{
					weatherService.handleOneUserCityChange(ctx, user);
				}catch(Exception e){
					log.error("handle one user city changed failed.",e);
				}
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class,readOnly = false)
	public void handleOneUserCityChange(Context ctx, EjlUser user) throws BizException {
		Long userId=user.getId();
		EjlUserDevice userDevice=ejlComUserDeviceService.getValidByUserId(userId);
		if(null!=userDevice){
			Date time=DateUtils.addHours(DateUtils.currentDate(), -6);
			EjlLocation location=ejlComLocationService.getUserLatestLocation(user.getId(), userDevice.getDeviceId(),YesNo.YES.getValue(),time);
			if(null!=location && location.getCity()!=null){
				String city=location.getCity();
				EjlUserExtendInfo userExtInfo= ejlComUserExtendInfoService.getByUserId(userId);
				if(null==userExtInfo){
					userExtInfo = new EjlUserExtendInfo();
					userExtInfo.setUserId(userId);
				}else if(userExtInfo.getCurrentCity()!=null && userExtInfo.getCurrentCity().equals(city)){
					return;
				}
				userExtInfo.setCurrentCity(city);
				ejlComUserExtendInfoService.save(ctx, userExtInfo);
				
				try {
					EfWeather isExistWeather = ejlComWeatherServiceImpl.getNewWeatherByCityName(city);
					if(isExistWeather == null){
						EfWeather we = this.getWeatherForCity(city);
						if(we!=null){
							ejlComWeatherServiceImpl.saveOne(we);
							this.saveFutherWeather(we.getWeatherList());
						}
					}
					//推送
					saveUserWeather(city, userId);
				} catch (Exception e) {
					log.error("save user weather error.",e);
					throw new BizException(e);
				}
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class,readOnly = false)
	public void sendUserCityWeather(Context ctx) throws BizException {
		Long userId = ctx.getUserId();
		Long deviceId = ctx.getDeviceId();
		Date time=DateUtils.addHours(DateUtils.currentDate(), -6);
		EjlLocation location=ejlComLocationService.getUserLatestLocation(userId, deviceId,YesNo.YES.getValue(),time);
		if(null!=location && location.getCity()!=null){
			String city=location.getCity();
			try {
				EfWeather isExistWeather = ejlComWeatherServiceImpl.getNewWeatherByCityName(city);
				if(isExistWeather == null){
					EfWeather we = this.getWeatherForCity(city);
					if(we!=null){
						ejlComWeatherServiceImpl.saveOne(we);
						this.saveFutherWeather(we.getWeatherList());
					}
				}
				//推送
				saveUserWeather(city, userId);
			} catch (Exception e) {
				log.error("save user weather error.",e);
				throw new BizException(e);
			}
		}
	}


	@Override
	public void saveUserWeather(String cityName, Long userId) throws BizException {
		this.deleteUserRepeatDate(userId);
		EfUserWeather efUserWeather = new EfUserWeather();
		efUserWeather.setCityName(cityName);
		efUserWeather.setUserId(userId);
		Context  ctx = new Context();
		ctx.set("userId", -1);
		efComUserWeatherServiceImpl.save(ctx, efUserWeather);
	}
	
	
	public static void main(String args[]) throws Exception{
		//WeatherServiceImpl w = new WeatherServiceImpl();
		//EfWeather efWeather =w.getWeatherForCity("深圳市");
		//DeviceWeatherFutherRequest re = w.getFutherStruc(efWeather);
		System.out.print("v2.0".compareTo("v2.01")<=0);
		
	}
}
