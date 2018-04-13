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
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;

import com.winterframework.efamily.entity.EjlHealthHeartRate;

import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.EjlUserNotice;

import com.winterframework.efamily.service.IEjlComUserNoticeService;

import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.thirdparty.sms.ISmsService;

import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.efamily.utils.NotifyUserUtil;

public class HeartRateNoticeTask {

	private Logger log = LoggerFactory.getLogger(ElectronicFenceNoticeTask.class);

	

	
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

	@Resource(name = "ejlHealthHeartRateDaoImpl")
	private IEjlHealthHeartRateDao ejlHealthHeartRateDaoImpl;
	public void execute()throws Exception{
		EjlUserNotice entity = new EjlUserNotice();
		entity.setRateStatus(1);
		Context ctx  = new Context();
		ctx.set("userId", -1);
		List<EjlUserNotice> list = ejlComUserNoticeServiceImpl.selectListObjByAttribute(ctx, entity);
		for(EjlUserNotice ejlUserNotice:list){
			try{
			EjlUser deviceUser = ejlUserDaoImpl.getById(ejlUserNotice.getDeviceUserId());
			EjlUser user =  ejlUserDaoImpl.getById(ejlUserNotice.getUserId());
			String startTime = redisClient.get(EfamilyConstant.LASTHEARTRATENOTICE +"_"+ejlUserNotice.getUserId()+"_"+ejlUserNotice.getDeviceUserId());
			EjlUserDevice eud = new EjlUserDevice();
			eud.setStatus(1l);
			eud.setUserId(deviceUser.getId());
			EjlUserDevice ejlUserDevice = ejlUserDeviceDaoImpl.getEjlUserDevice(eud);
			if(ejlUserDevice == null){
				continue;
			}
			Long toTime = null;
			List<EjlHealthHeartRate> rateList  =ejlHealthHeartRateDaoImpl.getNoticeUserHeartRate(ejlUserNotice.getDeviceUserId(), startTime==null?null:Long.valueOf(startTime), 
					Long.valueOf(ejlUserNotice.getRateRangeGt()), Long.valueOf(ejlUserNotice.getRateRangeLt()),toTime);
			
			Long gtValue = null;
			Long ltValue = null;
			Long fromTimeGt = null;
			Long fromTimeLt = null;
			Long beginTime = null;
			if(rateList == null){
				return;
			}
			for(EjlHealthHeartRate ejlHealthHeartRate:rateList)
			{
				if(ejlHealthHeartRate.getRate()>ejlUserNotice.getRateRangeGt()){
						gtValue = ejlHealthHeartRate.getRate();		
						fromTimeGt = ejlHealthHeartRate.getFromTime();
				}
				
				if(ejlHealthHeartRate.getRate()<ejlUserNotice.getRateRangeLt()){
					ltValue = ejlHealthHeartRate.getRate();
					fromTimeLt = ejlHealthHeartRate.getFromTime();
				}
				beginTime = ejlHealthHeartRate.getFromTime();
			}
			if(fromTimeGt != null||fromTimeLt!=null){
				
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userId", deviceUser.getId() + "");
				paramMap.put("nickName", deviceUser.getNickName()!=null?deviceUser.getNickName():deviceUser.getPhone());
				paramMap.put("deviceId", ejlUserDevice.getDeviceId()+"");
				paramMap.put("familyId", deviceUser.getFamilyId()+"");
				List<EjlUser> userList = new ArrayList<EjlUser>();
				userList.add(user);
				if(fromTimeGt != null){
					paramMap.put("configValue", ejlUserNotice.getRateRangeGt()+"");
					paramMap.put("value", gtValue+"");
					paramMap.put("time", fromTimeGt+"");
					paramMap.put("type", "2");
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.RATE_WARNING, null,notificationUtil,false);
				}
				
				
				if(fromTimeLt != null){
					paramMap.put("configValue", ejlUserNotice.getRateRangeLt()+"");
					paramMap.put("value", ltValue+"");
					paramMap.put("time", fromTimeLt+"");
					paramMap.put("type", "1");
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.RATE_WARNING, null,notificationUtil,false);
				}
			}
			if(beginTime!= null){
			redisClient.set(EfamilyConstant.LASTHEARTRATENOTICE +"_"+ejlUserNotice.getUserId()+"_"+ejlUserNotice.getDeviceUserId(),String.valueOf(beginTime));
			}
			}catch(Exception e){
				log.error("send heart error", e);
			}
			
		}
	}
}
