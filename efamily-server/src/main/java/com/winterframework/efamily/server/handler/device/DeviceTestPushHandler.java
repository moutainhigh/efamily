package com.winterframework.efamily.server.handler.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceBindRequest;
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.dto.device.DeviceContactsRequest;
import com.winterframework.efamily.dto.device.DeviceLunarInitRequest;
import com.winterframework.efamily.dto.device.DeviceLunarRequest;
import com.winterframework.efamily.dto.device.DevicePowerAutoRequest;
import com.winterframework.efamily.dto.device.DeviceRemindStruc;
import com.winterframework.efamily.dto.device.DeviceWeatherInitRequest;
import com.winterframework.efamily.dto.device.DeviceWeatherRequest;
import com.winterframework.efamily.dto.device.DeviceWeatherSettingRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.core.IDeviceService;
import com.winterframework.efamily.server.core.INoticeService;
import com.winterframework.efamily.server.enums.TestPusher;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
 
/**
 * 设备绑定确认
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceTestPushHandler")
public class DeviceTestPushHandler extends AbstractHandler{	
	
	@Resource(name="notificationServiceImpl")
	private INotificationService notificationService;
	@Resource(name="deviceServiceImpl")
	private IDeviceService deviceService;
	@Resource(name="batteryGetPusher")
	private AbstractPusher batteryGetPusher;	
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException{
		/*System.out.println(bizNumberUtils.getNumber(BizNumberUtils.Type.EFAMILY_NUMBER));
		System.out.println(bizNumberUtils.getNumber(BizNumberUtils.Type.CHAT_GROUP_NUMBER));*/
		log.info("Test push",req.getValue("command"));  
		NotyTarget t=new NotyTarget();
		t.setUserId(Long.valueOf(req.getData().remove("pushUserId"))); 
		String deviceIdStr=req.getData().remove("pushDeviceId"); 
		if(null!=deviceIdStr && !"".equals(deviceIdStr)){
			t.setDeviceId(Long.valueOf(deviceIdStr));
		}		
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(Integer.valueOf(req.getData().remove("command")));
		message.setData(req.getData());
		
		message.setType(message.getCommand()%2==0?NotyMessage.Type.MSG:NotyMessage.Type.OPER);
		Notification notification=new Notification();
		notification.setTarget(t);
		notification.setMessage(message);
		 
		notificationService.notify(notification);
		return new FmlResponse(req);
		/*
		if(req.getValue("pushId").equals(TestPusher.param_get)){ 
			return paramGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.param_set)){
			return paramSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.chat_set)){
			return chatSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.chat_get)){
			return chatGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.chat_push)){
			return chatPush(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.ctt_get)){
			return cttGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.ctt_set)){
			return cttSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.sigl_get)){
			return signalGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.sigl_max_get)){
			return signalMaxGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.location_get)){
			return locationGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.heart_get)){
			return heartGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.software_get)){
			return softwareGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.bind_push)){
			return bindPush(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.power_onff)){
			return powerOnff(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.lbs_get)){
			return lbsGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.wifi_get)){
			return wifiGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.power_onff_set)){
			return powerOnffSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.power_onff_get)){
			return powerOnffGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.time_set)){
			return timeSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.time_get)){
			return timeGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.satellite_get)){
			return satelliteGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.weather_init)){
			return weatherInit(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.weather_set)){
			return weatherSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.weather_push)){
			return weatherPush(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.lunar_init)){
			return lunarInit(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.lunar_set)){
			return lunarSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.lunar_push)){
			return lunarPush(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.battery_get)){
			return batteryGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.remind_add)){
			return remindAdd(ctx,req);
		}
		else if(req.getValue("pushId").equals(TestPusher.remind_edit)){
			return remindEdit(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.remind_del)){
			return remindDelete(ctx,req);
		}
		else if(req.getValue("pushId").equals(TestPusher.remind_set)){
			return remindSet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.remind_get)){
			return remindGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.walk_remote)){
			return stepRemote(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.walk_get)){
			return stepGet(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.monitor)){
			return monitor(ctx,req);
		}else if(req.getValue("pushId").equals(TestPusher.sleep_get)){
			return sleepGet(ctx,req);
		}else{
			return paramSet(ctx,req);
		}*/
	}
	
	private FmlResponse batteryGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();    
		Map<String,String> data=new HashMap<String,String>(); 
		batteryGetPusher.push(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse lunarPush(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_LUNAR_PUSH.getCode()+"");
		DeviceLunarRequest bizReq=new DeviceLunarRequest(); 
		bizReq.setDate(DateUtils.format(DateUtils.currentDate()));
		//bizReq.setWeek(Lunar.Week.MONDAY.getValue());
		bizReq.setWeek("星期一");
		bizReq.setLunar("三月初九");
		bizReq.setGanzhi("乙未年庚辰月癸酉日");
		//bizReq.setZodiac(Lunar.Zodiac.SHEEP.getValue());
		bizReq.setZodiac("sheep");
		bizReq.setFitavoid("祭祀 祈福-开市 出火");
		data.put("data", JsonUtils.toJson(bizReq));
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse lunarSet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_LUNAR_SET.getCode()+"");
		data.put("value","111001");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse lunarInit(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_LUNAR_INIT.getCode()+"");
		DeviceLunarInitRequest bizReq=new DeviceLunarInitRequest(); 
		Map<String,String> zodiac=new HashMap<String,String>();
		//zodiac.put(Lunar.Zodiac.RAT.getValue(), "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		zodiac.put("rat", "2e9147b4e1974c2d81468d5a8bc37180");
		//zodiac.put(Lunar.Zodiac.OX.getValue(), "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		zodiac.put("ox", "abfe3870cdcb4a9c9208eb8f6c4bfc7b");
		
		bizReq.setZodiac(zodiac);
		data.put("data", JsonUtils.toJson(bizReq));
		FmlResponse res=new FmlResponse(req);  
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse weatherPush(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_WEATHER_PUSH.getCode()+"");
		DeviceWeatherRequest bizReq=new DeviceWeatherRequest(); 
		//bizReq.setWeather(Weather.SUNNY.getValue());
		bizReq.setWeather("晴");
		bizReq.setTemperature("20~30");
		//bizReq.setWind(Weather.Wind.WEAK.getValue());
		bizReq.setWind("微风");
		bizReq.setCurtemperature("25");
		//bizReq.setDress(Weather.Dress.SPRING.getValue());
		bizReq.setDress("炎热");
		bizReq.setAirQuality("优");
		data.put("data", JsonUtils.toJson(bizReq));
		FmlResponse res=new FmlResponse(req);  
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse weatherSet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_WEATHER_SET.getCode()+"");
		DeviceWeatherSettingRequest bizReq=new DeviceWeatherSettingRequest(); 
		bizReq.setMode("1");
		bizReq.setContent("111001");
		FmlResponse res=new FmlResponse(req);  
		data.put("data", JsonUtils.toJson(bizReq));
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse weatherInit(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_WEATHER_INIT.getCode()+"");
		DeviceWeatherInitRequest bizReq=new DeviceWeatherInitRequest(); 
		Map<String,String> weather=new HashMap<String,String>();
		Map<String,String> wind=new HashMap<String,String>();
		Map<String,String> dress=new HashMap<String,String>();
		weather.put("sunny", "a2fd2809e53d4a629cbfd3735780bf7d");
		weather.put("overcast", "c162924c7b20479bab45da129416eac3");
		weather.put("rainy", "e96701c3083d423d85b1549595483640");
		weather.put("cloud", "385420ba97f24592afb8151f05257fdd");
		weather.put("thunder", "60c975a73020421aa343631aa1173b49");
		
		/*wind.put("weak", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		wind.put("middle", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		wind.put("strong", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		
		dress.put("summer", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		dress.put("spring", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		dress.put("winter", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
		dress.put("cold", "aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");*/
		
		bizReq.setWeather(weather);
		/*bizReq.setWind(wind);
		 * 		ctx.set("userId", 621L);
		ctx.set("deviceId", 248L);
		bizReq.setDress(dress);*/
		data.put("data", JsonUtils.toJson(bizReq));
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse satelliteGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_LOCATION_SATELLITE_QUERY.getCode()+"");
		FmlResponse res=new FmlResponse(req);  
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse timeGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_TIME_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse timeSet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_TIME_SET.getCode()+"");
		data.put("value",DateUtils.currentDate().getTime()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse powerOnffGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_POWER_AUTO_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse powerOnffSet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_POWER_AUTO_SET.getCode()+"");
		
		DevicePowerAutoRequest bizReq=new DevicePowerAutoRequest();  
		List<String> ons=new ArrayList<String>(); 
		List<String> offs=new ArrayList<String>();
		ons.add("08:00");
		ons.add("14:00");
		bizReq.setOns(ons);
		offs.add("12:00");
		offs.add("21:00");
		bizReq.setOffs(offs);
		data.put("data", JsonUtils.toJson(bizReq));
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse wifiGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_LOCATION_WIFI_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse lbsGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_MOBILE_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse powerOnff(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_POWER_ONFF.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse bindPush(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_BIND_PUSH.getCode()+"");
		List<DeviceBindRequest> bizReqList=new ArrayList<DeviceBindRequest>();
		for(int i=0;i<2;i++){
			DeviceBindRequest bizReq=new DeviceBindRequest();
			bizReq.setUserId(1003L+i);
			bizReq.setNickName("friend"+i);
			bizReq.setPhoneNumber("1861234567"+i);
			bizReqList.add(bizReq);
		}
		data.put("data",JsonUtils.toJson(bizReqList));
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse softwareGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_SOFTWARE_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse heartGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_HEART_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse locationGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_LOCATION_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse signalMaxGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_SIGNAL_MAX_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse signalGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_SIGNAL_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse cttGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_CONTACTS_GET.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse cttSet(Context ctx, FmlRequest req) throws ServerException{  
		FmlResponse res=new FmlResponse(req);
		NotyTarget t=new NotyTarget();
		t.setUserId(ctx.getUserId());
		t.setDeviceId(ctx.getDeviceId());
		 
		Map<String,String> data=new HashMap<String,String>();		
		List<DeviceContactsRequest> bizReqList=new ArrayList<DeviceContactsRequest>();
		for(int i=0;i<2;i++){
			DeviceContactsRequest bizReq=new DeviceContactsRequest();
			bizReq.setUserId(1006L+i);
			bizReq.setNickName("xiao h"+i);
			bizReq.setPhoneNumber("1881234123"+i);
			bizReq.setHeadImage("aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
			bizReq.setIsSos(YesNo.YES.getValue());
			bizReqList.add(bizReq);
		} 
		data.put("userName",ctx.getUserId()+"");
		data.put("data",JsonUtils.toJson(bizReqList));
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(Command.DEVICE_CONTACTS_SET.getCode());
		message.setData(data);
		Notification notification=new Notification();
		notification.setTarget(t);
		notification.setMessage(message);
		 
		notificationService.notify(notification);
		return res; 
	} 
	private FmlResponse cttSet_bk(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_CONTACTS_SET.getCode()+"");
		List<DeviceContactsRequest> bizReqList=new ArrayList<DeviceContactsRequest>();
		for(int i=0;i<2;i++){
			DeviceContactsRequest bizReq=new DeviceContactsRequest();
			bizReq.setUserId(1006L+i);
			bizReq.setNickName("xiao h"+i);
			bizReq.setPhoneNumber("1881234123"+i);
			bizReq.setHeadImage("aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
			bizReq.setIsSos(YesNo.YES.getValue());
			bizReqList.add(bizReq);
		} 
		data.put("userId",ctx.getUserId()+"");
		data.put("data",JsonUtils.toJson(bizReqList));
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req);  
		res=getFmlResponse(res, null);
		return res;
	} 
	private FmlResponse chatPush(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Long userId=ctx.getUserId();//Long.valueOf(req.getValue("userId"));   
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_CHAT_PUSH.getCode()+"");
		List<DeviceChatMessageRequest> bizReqList=new ArrayList<DeviceChatMessageRequest>();
		for(int i=0;i<2;i++){
			DeviceChatMessageRequest bizReq=new DeviceChatMessageRequest();
			bizReq.setType(ResourceType.AUDIO); 
			bizReq.setSenderId(1003L+i);
			bizReq.setReceiverId(userId);
			bizReq.setContent("aW1hZ2V8MjAxNTA3Mjh8NTBkMWMwmmM=");
			bizReq.setTime(DateUtils.currentDate().getTime());
			bizReqList.add(bizReq);
		}
		
		data.put("data",JsonUtils.toJson(bizReqList));
		FmlResponse res=new FmlResponse(req);  
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	}
	
	private FmlResponse chatSet(Context ctx, FmlRequest req) throws ServerException{ 
		req.getData().clear();
		log.error("chat set start......");
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_CHAT_SET.getCode()+"");
		data.put("audio", "http://103.17.117.63/audios/tip1.mp3"); 
		data.put("volume", "6"); 
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse chatGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_CHAT_GET.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	
	private FmlResponse paramSet(Context ctx, FmlRequest req) throws ServerException{
		//req.getData().clear();

		//Long userId=613L;//Long.valueOf(req.getValue("userId"));
		//ctx=new Context();
		//ctx.set("userId", userId); 

		//Long userId=100001L;//Long.valueOf(req.getValue("userId"));
		//ctx=new Context();ctx.set("userId", userId); 

		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_PARAM_SET.getCode()+"");
		/*data.put("parameterIndex", 400001+"");
		data.put("parameterContext", 10+"");
		data.put("userId", 2001+"");*/
		
		data.put("paramCode", req.getData().get("paramCode")+"");
		data.put("paramValue", req.getData().get("paramValue")+"");
		
		log.error("---------------参数设置： paramCode = "+req.getData().get("paramValue")+" : parameterContext = "+req.getData().get("parameterContext"));
		deviceService.operation(ctx, data);
		
		req.getData().clear();
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	private FmlResponse paramGet(Context ctx, FmlRequest req) throws ServerException{ 
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_PARAM_GET.getCode()+"");
		data.put("paramCode",req.getData().get("paramCode")+""); 
		//data.put("fromId", 2001+"");
		req.getData().clear();
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	
	
	
	private FmlResponse remindSet(Context ctx, FmlRequest req) throws ServerException{ 
		req.getData().clear();
		log.error("remind set start......"); 
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_REMIND_SET.getCode()+""); 
		data.put("audio", "http://103.17.117.63/audios/tip1.mp3"); 
		data.put("volume", "6");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	
	
	private FmlResponse remindAdd(Context ctx, FmlRequest req) throws ServerException{ 
		req.getData().clear();
		log.error("remind add start......");
		List<DeviceRemindStruc> list = new ArrayList<DeviceRemindStruc>();
		DeviceRemindStruc struc = new DeviceRemindStruc();
		struc.setContent("http://103.17.117.63/audios/tip1.mp3");
		struc.setType("audio");
		struc.setPeriodType(1);
		struc.setName("testAddRemind");
		struc.setTime(DateUtils.convertDate2Long(DateUtils.currentDate()));
		struc.setVolume(6l);
		struc.setEndTime(DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), 3)));
		struc.setId(1l);struc.setUserId(1111l);
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_REMIND_ADD.getCode()+"");
		DeviceRemindStruc struc1 = new DeviceRemindStruc();
		struc1.setContent("http://103.17.117.63/audios/tip1.mp3");
		struc1.setType("audio");
		struc1.setPeriodType(1);
		struc1.setName("testAddRemind");
		struc1.setTime(DateUtils.convertDate2Long(DateUtils.currentDate()));
		struc1.setVolume(6l);
		struc1.setEndTime(DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), 3)));
		struc1.setId(2l);struc1.setUserId(1111l);
		list.add(struc);
		list.add(struc1);
		
		data.put("remind", JsonUtils.toJson(list));
		FmlResponse res=new FmlResponse(req); 
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	}
	
	private FmlResponse remindEdit(Context ctx, FmlRequest req) throws ServerException{ 
		req.getData().clear();
		log.error("remind add start......");
		List<DeviceRemindStruc> list = new ArrayList<DeviceRemindStruc>();
		DeviceRemindStruc struc = new DeviceRemindStruc();
		struc.setContent("http://103.17.117.63/audios/tip1.mp3");
		struc.setType("audio");
		struc.setPeriodType(1);
		struc.setName("testEditRemind");
		struc.setTime(DateUtils.convertDate2Long(DateUtils.currentDate()));
		struc.setVolume(6l);
		struc.setEndTime(DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), 3)));
		ctx=new Context();ctx.set("userId", ctx.getUserId()); 
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_REMIND_UPDATE.getCode()+"");
		list.add(struc);
		data.put("remind", JsonUtils.toJson(list));
		FmlResponse res=new FmlResponse(req); 
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	}
	
	
	private FmlResponse remindDelete(Context ctx, FmlRequest req) throws ServerException{ 
		req.getData().clear();
		log.error("remind add start......");
		List<Long> list = new ArrayList<Long>();
		/*DeviceRemindStruc struc = new DeviceRemindStruc();
		struc.setContent("http://103.17.117.63/audios/tip1.mp3");
		struc.setType("audio");
		struc.setPeriodType(1);
		struc.setName("testEditRemind");
		struc.setTime(DateUtils.convertDate2Long(DateUtils.currentDate()));
		struc.setVolume(6l);
		struc.setEndTime(DateUtils.convertDate2Long(DateUtils.addDays(DateUtils.currentDate(), 3)));
		struc.setId(1l);
		Map<String,String> data=new HashMap<String,String>();
		data.put("audio", "http://103.17.117.63/audios/tip1.mp3"); 
		data.put("volume", "6");*/ 
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_REMIND_DELETE.getCode()+"");
		list.add(1l);
		data.put("remind", JsonUtils.toJson(list));
		FmlResponse res=new FmlResponse(req); 
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	}
	

	
	private FmlResponse remindGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear(); 
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_REMIND_QUERY.getCode()+"");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	
	private FmlResponse stepRemote(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear(); 
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_STEP_REMOTE.getCode()+"");
		data.put("value", "30");
		deviceService.operation(ctx, data);
		FmlResponse res=new FmlResponse(req); 
		res=getFmlResponse(res, null);
		return res;
	}
	
	private FmlResponse stepGet(Context ctx, FmlRequest req) throws ServerException{  
		req.getData().clear();
		Map<String,String> data=new HashMap<String,String>();
		data.put("command", Command.DEVICE_STEP_QUERY.getCode()+"");
		FmlResponse res=new FmlResponse(req); 
		deviceService.operation(ctx, data);
		res=getFmlResponse(res, null);
		return res;
	}
	
	private FmlResponse monitor(Context ctx, FmlRequest req) throws ServerException{
		FmlResponse res=new FmlResponse(req);
		NotyTarget t=new NotyTarget();
		t.setUserId(ctx.getUserId());
		t.setDeviceId(ctx.getDeviceId());
		 
		Map<String,String> data=new HashMap<String,String>();
		data.put("phoneNumber", req.getValue("phoneNumber")); 
		
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(Command.DEVICE_MONITOR.getCode());
		message.setData(data);
		Notification notification=new Notification();
		notification.setTarget(t);
		notification.setMessage(message);
		 
		notificationService.notify(notification);
		return res;
	}
	private FmlResponse sleepGet(Context ctx, FmlRequest req) throws ServerException{
		FmlResponse res=new FmlResponse(req);
		NotyTarget t=new NotyTarget();
		t.setUserId(ctx.getUserId());
		t.setDeviceId(ctx.getDeviceId());
		  
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(Command.DEVICE_SLEEP_QUERY.getCode());
		Notification notification=new Notification();
		notification.setTarget(t);
		notification.setMessage(message);
		 
		notificationService.notify(notification);
		return res;
	}
	
	
	
}