package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.device.DeviceLunarFutherRequest;
import com.winterframework.efamily.dto.device.DeviceLunarRequest;
import com.winterframework.efamily.entity.EfLunar;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComLunarService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.ISoftwareVersionService;
import com.winterframework.efamily.service.IWeatherService;
import com.winterframework.efamily.service.schedule.LunarDevicePushTask;
import com.winterframework.efamily.utils.NotificationUtil;

@Controller("sendUserLunarController")
@RequestMapping("/task/lunar")
public class SendUserLunarController {
	private Logger log = LoggerFactory.getLogger(SendUserLunarController.class);

	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;

	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	@Resource(name = "ejlComLunarServiceImpl")
	private IEfComLunarService ejlComLunarServiceImpl; 
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil; 
	
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Resource(name="softwareVersionServiceImpl")
	private ISoftwareVersionService softwareVersionService;
	
	
	@RequestMapping("/sendUserLunar")
	@ResponseBody
	protected Response<Object> sendUserLunar(@RequestBody Request<Map<String,Long>> request) throws Exception {
		log.debug("reset send user lunar begin userId="+request.getData().get("userId"));
		Response<Object> resp = new Response<Object>(request);
		Date date = DateUtils.parse(DateUtils.format(new Date()));
		EfLunar efLunar = ejlComLunarServiceImpl.getEfLunarByDate(date);
		Long deviceId = request.getData().get("deviceId");
		Long userId = request.getData().get("userId");
		if(efLunar != null){
		DeviceLunarRequest struc = new DeviceLunarRequest();
		struc.setDate(DateUtils.format(efLunar.getSolarDate()));
		struc.setFitavoid(efLunar.getSuit() + "-" + efLunar.getAvoid());
		struc.setGanzhi(efLunar.getGanzhi());
		struc.setLunar(efLunar.getLunarDate());struc.setWeek(efLunar.getWeek()+"");
		struc.setZodiac(efLunar.getZodiac());
		struc.setWeek(efLunar.getWeek()==1?"星期一":efLunar.getWeek()==2?"星期二":efLunar.getWeek()==3?"星期三":
				efLunar.getWeek()==4?"星期四":efLunar.getWeek()==5?"星期五":efLunar.getWeek()==6?"星期六":
					efLunar.getWeek()==7?"星期日":null);
		DeviceLunarFutherRequest futherStruc = getFutherLunar(efLunar);
		List<DeviceLunarRequest> futherList = new ArrayList<DeviceLunarRequest>();
		futherList.add(struc);
		futherList.addAll(futherStruc.getFutherLunars());
		try{
			Map<String, String> data = new HashMap<String, String>(); 
			EjlDevice device = ejlComDeviceService.get(deviceId);
			data.put("data", softwareVersionService.gt(device.getId(), "v2.0")?JsonUtils.toJson(futherList):JsonUtils.toJson(struc));
			NotyTarget target=new NotyTarget(userId,deviceId);
			NotyMessage message=new NotyMessage();
			message.setCommand(28973);
			message.setType(NotyMessage.Type.NOTICE);
			message.setData(data);
			Notification notification=new Notification();
			notification.setTarget(target);
			notification.setMessage(message);
			notification.setRealTime(false);
			notificationUtil.notification(notification);
			}catch(Exception e){
				log.error("send Lunar errro deviceId="+deviceId, e);
			}
		}
		resp.setStatus(new Status(StatusBizCode.OK.getValue()));
		log.debug("reset send user lunar end userId="+request.getData().get("userId"));
		return resp;
	}
		
		private DeviceLunarFutherRequest getFutherLunar(EfLunar efLunar){
			DeviceLunarFutherRequest struc = new DeviceLunarFutherRequest();
			struc.setDate(DateUtils.format(efLunar.getSolarDate()));
			struc.setFitavoid(efLunar.getSuit() + "-" + efLunar.getAvoid());
			struc.setGanzhi(efLunar.getGanzhi());
			struc.setLunar(efLunar.getLunarDate());struc.setWeek(efLunar.getWeek()+"");
			struc.setZodiac(efLunar.getZodiac());
			struc.setWeek(efLunar.getWeek()==1?"星期一":efLunar.getWeek()==2?"星期二":efLunar.getWeek()==3?"星期三":
					efLunar.getWeek()==4?"星期四":efLunar.getWeek()==5?"星期五":efLunar.getWeek()==6?"星期六":
						efLunar.getWeek()==7?"星期日":null);
			List<DeviceLunarRequest> futherList = new ArrayList<DeviceLunarRequest>();
			for(int i=1;i<=2;i++){
				try{
					EfLunar futherLunar = ejlComLunarServiceImpl.getEfLunarByDate(DateUtils.addDays(efLunar.getSolarDate(), i));
					DeviceLunarRequest futherStruc = new DeviceLunarRequest();
					futherStruc.setDate(DateUtils.format(futherLunar.getSolarDate()));
					futherStruc.setFitavoid(futherLunar.getSuit() + "-" + futherLunar.getAvoid());
					futherStruc.setGanzhi(futherLunar.getGanzhi());
					futherStruc.setLunar(futherLunar.getLunarDate());struc.setWeek(futherLunar.getWeek()+"");
					futherStruc.setZodiac(futherLunar.getZodiac());
					futherStruc.setWeek(futherLunar.getWeek()==1?"星期一":futherLunar.getWeek()==2?"星期二":futherLunar.getWeek()==3?"星期三":
						futherLunar.getWeek()==4?"星期四":futherLunar.getWeek()==5?"星期五":futherLunar.getWeek()==6?"星期六":
							futherLunar.getWeek()==7?"星期日":null);
					futherList.add(futherStruc);
				}catch(Exception e){
					log.error("getFutherLunar error", e);
				}
			}
			struc.setFutherLunars(futherList);
			return struc;
		}
}
