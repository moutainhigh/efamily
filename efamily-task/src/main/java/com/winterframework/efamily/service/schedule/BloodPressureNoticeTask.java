package com.winterframework.efamily.service.schedule;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;

import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserBarrierDao;
import com.winterframework.efamily.dao.IEjlHealthBloodPressureDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;


import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.EjlUserNotice;

import com.winterframework.efamily.service.IEjlComUserNoticeService;

import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.thirdparty.sms.ISmsService;

import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.efamily.utils.NotifyUserUtil;

public class BloodPressureNoticeTask {

	private Logger log = LoggerFactory.getLogger(BloodPressureNoticeTask.class);

	

	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;

	
	@Resource(name = "ejlComMessageServiceImpl")
	private IEjlComMessageService ejlComMessageServiceImpl;
	
	@Resource(name = "ejlComUserBarrierDaoImpl")
	private IEjlComUserBarrierDao ejlComUserBarrierDaoImpl;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "notificationUtil")
	protected NotificationUtil notificationUtil;
	
	@Resource(name="smsServiceImpl")
	private ISmsService smsService;
	
	
	@Resource(name = "ejlComUserNoticeServiceImpl")
	private IEjlComUserNoticeService ejlComUserNoticeServiceImpl;
	
	
	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;

	@Resource(name = "ejlHealthBloodPressureDaoImpl")
	private IEjlHealthBloodPressureDao ejlHealthBloodPressureDaoImpl;
	public void execute()throws Exception{
		EjlUserNotice entity = new EjlUserNotice();
		entity.setBloodStatus(1);
		Context ctx  = new Context();
		ctx.set("userId", -1);
		List<EjlUserNotice> list = ejlComUserNoticeServiceImpl.selectListObjByAttribute(ctx, entity);
		for(EjlUserNotice ejlUserNotice:list){
			EjlUser deviceUser = ejlUserDaoImpl.getById(ejlUserNotice.getDeviceUserId());
			EjlUser user =  ejlUserDaoImpl.getById(ejlUserNotice.getUserId());
			String startTime = redisClient.get(EfamilyConstant.LASTBLOODPRESSURENOTICE +"_"+ejlUserNotice.getUserId()+"_"+ejlUserNotice.getDeviceUserId());
			EjlUserDevice eud = new EjlUserDevice();
			eud.setStatus(1l);
			eud.setUserId(deviceUser.getId());
			EjlUserDevice ejlUserDevice = ejlUserDeviceDaoImpl.getEjlUserDevice(eud);
			if(ejlUserDevice == null){
				continue;
			}
			Long toTime = null;
			List<EjlHealthBloodPressure> bloodList  =ejlHealthBloodPressureDaoImpl.getNoticeUserBloodPressure(ejlUserNotice.getDeviceUserId(), startTime==null?null:Long.valueOf(startTime), 
					ejlUserNotice.getSystolicRangeGt()==null?null:Long.valueOf(ejlUserNotice.getSystolicRangeGt()), ejlUserNotice.getSystolicRangeLt()==null?null:Long.valueOf(ejlUserNotice.getSystolicRangeLt()),ejlUserNotice.getDiastolicRangeGt()==null?null:Long.valueOf(ejlUserNotice.getDiastolicRangeGt()),ejlUserNotice.getDiastolicRangeLt()==null?null:Long.valueOf(ejlUserNotice.getDiastolicRangeLt()),toTime);
			Integer systolicPressureGt = null;
			Integer systolicPressureLt = null;
			Integer diastolicPressureGt = null;
			Integer diastolicPressureLt = null;
			Long fromTime= null;
			Long beginTime = null;
			if(bloodList == null){
				return;
			}
			for(EjlHealthBloodPressure ejlHealthBloodPressure:bloodList)
			{
				if(ejlUserNotice.getSystolicRangeGt()!=null){
					if(ejlHealthBloodPressure.getHigh()>ejlUserNotice.getSystolicRangeGt()){
						systolicPressureGt = ejlHealthBloodPressure.getHigh();		
						fromTime = ejlHealthBloodPressure.getFromTime();
					}
				}
				if(ejlUserNotice.getSystolicRangeLt()!=null){
					if(ejlHealthBloodPressure.getHigh()<ejlUserNotice.getSystolicRangeLt()){
						systolicPressureLt = ejlHealthBloodPressure.getHigh();
						fromTime = ejlHealthBloodPressure.getFromTime();
					}
				}
				if(ejlUserNotice.getDiastolicRangeGt()!=null){
					if(ejlHealthBloodPressure.getLow()>ejlUserNotice.getDiastolicRangeGt()){
						diastolicPressureGt = ejlHealthBloodPressure.getLow();		
							fromTime = ejlHealthBloodPressure.getFromTime();
					}
				}
				if(ejlUserNotice.getDiastolicRangeLt()!=null){
					if(ejlHealthBloodPressure.getLow()<ejlUserNotice.getDiastolicRangeLt()){
						diastolicPressureLt = ejlHealthBloodPressure.getLow();
						fromTime = ejlHealthBloodPressure.getFromTime();
					}
				}
				beginTime = ejlHealthBloodPressure.getFromTime();
			}
			if(systolicPressureGt != null||systolicPressureLt!=null||diastolicPressureGt != null||diastolicPressureLt!=null){
				
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userId", deviceUser.getId() + "");
				paramMap.put("deviceId", ejlUserDevice.getDeviceId() + "");
				paramMap.put("nickName", deviceUser.getNickName()!=null?deviceUser.getNickName():deviceUser.getPhone());
				List<EjlUser> userList = new ArrayList<EjlUser>();
				userList.add(user);
				if(systolicPressureGt != null){
					paramMap.put("configValue", ejlUserNotice.getSystolicRangeGt()+"");
					paramMap.put("value", systolicPressureGt+"");
					paramMap.put("time", fromTime+"");
					paramMap.put("type", "2");
					paramMap.put("bloodPressureType", "1");//收缩压
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.BLOOOD_PRESSURE, null,notificationUtil,false);
				}
				
				
				if(systolicPressureLt != null){
					paramMap.put("configValue", ejlUserNotice.getSystolicRangeLt()+"");
					paramMap.put("value", systolicPressureLt+"");
					paramMap.put("time", fromTime+"");
					paramMap.put("type", "1");
					paramMap.put("bloodPressureType", "1");//收缩压
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.BLOOOD_PRESSURE, null,notificationUtil,false);
				}
				
				if(diastolicPressureGt != null){
					paramMap.put("configValue", ejlUserNotice.getDiastolicRangeGt()+"");
					paramMap.put("value", diastolicPressureGt+"");
					paramMap.put("time", fromTime+"");
					paramMap.put("type", "2");
					paramMap.put("bloodPressureType", "2");//舒张压
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.BLOOOD_PRESSURE, null,notificationUtil,false);
				}
				
				
				if(diastolicPressureLt != null){
					paramMap.put("configValue", ejlUserNotice.getDiastolicRangeLt()+"");
					paramMap.put("value", diastolicPressureLt+"");
					paramMap.put("time", fromTime+"");
					paramMap.put("type", "1");
					paramMap.put("bloodPressureType", "2");//舒张压
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.BLOOOD_PRESSURE, null,notificationUtil,false);
				}
				
				
			}
			if(beginTime != null){
				redisClient.set(EfamilyConstant.LASTBLOODPRESSURENOTICE +"_"+ejlUserNotice.getUserId()+"_"+ejlUserNotice.getDeviceUserId(),String.valueOf(beginTime));
			}
		}
	}
}
