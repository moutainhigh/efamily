package com.winterframework.efamily.service.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEfComOrgDeviceDao;
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComUserNoticeService;
import com.winterframework.efamily.service.ITskDeviceAlarmService;


public class HeartRateNoticeOrgTask {

	private Logger log = LoggerFactory.getLogger(HeartRateNoticeOrgTask.class);

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	@Resource(name = "ejlComUserNoticeServiceImpl")
	private IEjlComUserNoticeService ejlComUserNoticeServiceImpl;

	@Resource(name = "ejlHealthHeartRateDaoImpl")
	private IEjlHealthHeartRateDao ejlHealthHeartRateDaoImpl;
	
	@Resource(name="efComOrgDeviceDaoImpl")
	private IEfComOrgDeviceDao efComOrgDeviceDaoImpl;
	
	
	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;
	
	@Resource(name = "tskDeviceAlarmServiceImpl")
	private ITskDeviceAlarmService tskDeviceAlarmService;
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	public void execute()throws Exception{
		EfOrgDevice entity = new EfOrgDevice();
		entity.setStatus(1);
		List<EfOrgDevice> orgDeList = efComOrgDeviceDaoImpl.selectListObjByAttribute(entity);
		for(EfOrgDevice ejlUserNotice:orgDeList){
			try{
				EjlDevice device=ejlComDeviceService.getByImei(ejlUserNotice.getImei());
			if(null==device || device.getStatus().longValue()==EjlDevice.STATUS.UNBIND.getValue()) continue;
			String startTime = redisClient.get(EfamilyConstant.LASTHEARTRATENOTICE+"_org_"+device.getId()+"_"+device.getUserId());
			Long initLt = 60l;
			Long initGt=100l;
			Long toTime = null;
			List<EjlHealthHeartRate> rateList  =ejlHealthHeartRateDaoImpl.getNoticeUserHeartRate(device.getUserId(), startTime==null?null:Long.valueOf(startTime), 
					initGt, initLt,toTime);
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
				if(ejlHealthHeartRate.getRate()>initGt){
						gtValue = ejlHealthHeartRate.getRate();		
						fromTimeGt = ejlHealthHeartRate.getFromTime();
				}
				
				if(ejlHealthHeartRate.getRate()<initLt){
					ltValue = ejlHealthHeartRate.getRate();
					fromTimeLt = ejlHealthHeartRate.getFromTime();
				}
				beginTime = ejlHealthHeartRate.getFromTime();
			}
			if(fromTimeGt != null||fromTimeLt!=null){
				
				Context ctx = new Context();
				ctx.set("userId", device.getUserId());
				log.debug("heart org alarm.userId="+device.getUserId()+" device_id="+device.getId());
				EfDeviceAlarm deviceAlarm=new EfDeviceAlarm();
				deviceAlarm.setUserId(device.getUserId());
				deviceAlarm.setDeviceId(device.getId());
				deviceAlarm.setType(EfDeviceAlarm.Type.HEART.getValue());
				if(fromTimeGt != null){
					Map<String,String> map=new HashMap<String,String>();
					map.put("heartRate", gtValue+"");
					map.put("type", "2");
					deviceAlarm.setValue(JsonUtils.toJson(map));
					deviceAlarm.setTime(fromTimeGt);
					deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
					tskDeviceAlarmService.save(ctx, deviceAlarm);
				}
				
				
				if(fromTimeLt != null){
					Map<String,String> map=new HashMap<String,String>();
					map.put("heartRate", ltValue+"");
					map.put("type", "1");
					deviceAlarm.setValue(JsonUtils.toJson(map));
					deviceAlarm.setTime(fromTimeLt);
					deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
					tskDeviceAlarmService.save(ctx, deviceAlarm);
				}
			}
			if(beginTime != null){
			redisClient.set(EfamilyConstant.LASTHEARTRATENOTICE+"_org_"+device.getId()+"_"+device.getUserId(),String.valueOf(beginTime));
			}
			}catch(Exception e){
				log.error("send org heart error", e);
			}
			
		}
	}
}
