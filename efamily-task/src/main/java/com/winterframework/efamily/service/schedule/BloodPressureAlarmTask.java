package com.winterframework.efamily.service.schedule;

import java.util.Date;
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
import com.winterframework.efamily.common.IntegerSpan;
import com.winterframework.efamily.common.RedisPrefix;
import com.winterframework.efamily.dao.IEjlHealthBloodPressureDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfUserHealthSetting;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthBloodPressureAlarmPram;
import com.winterframework.efamily.service.IEfUserHealthSettingService;
import com.winterframework.efamily.service.ITskDeviceAlarmService;
import com.winterframework.efamily.service.ITskHealthHeartRateService;


/**
 * 血压告警任务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月21日
 */
public class BloodPressureAlarmTask {

	private Logger log = LoggerFactory.getLogger(BloodPressureAlarmTask.class);

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	@Resource(name = "tskHealthHeartRateServiceImpl")
	private ITskHealthHeartRateService tskHealthHeartRateService; 
	
	@Resource(name = "efUserHealthSettingServiceImpl")
	private IEfUserHealthSettingService efUserHealthSettingService; 
	
	@Resource(name = "tskDeviceAlarmServiceImpl")
	private ITskDeviceAlarmService tskDeviceAlarmService; 
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name = "ejlHealthBloodPressureDaoImpl")
	private IEjlHealthBloodPressureDao ejlHealthBloodPressureDaoImpl;
	
	
	private final int beforeMin=30;
	private final String defaultBloodHighSpan="90~139";
	private final String defaultBloodLowSpan="60~89";
	
	public void execute() throws Exception{
		/**
		 * 1.获取拥有最新血压数据的设备(上个上传时间至今)
		 * 2.获取设备对应的用户健康设置
		 * 3.循环处理各个用户异常血压数据 有则告警
		 */
		String alarmLastTime=redisClient.get(RedisPrefix.HEALTH_BLOODPRESSURE_ALARM_LASTTIME);
		Date lastTime=null;
		Date curTime=DateUtils.currentDate();
		if(null!=alarmLastTime){
			lastTime=DateUtils.getDate(Long.valueOf(alarmLastTime));
		}else{
			lastTime=DateUtils.addMinutes(curTime, -1*beforeMin);
		}
		//List<Map<String,Long>> mapList=tskHealthHeartRateService.getLastestUserIdDeviceIdListByCreateTime(lastTime,curTime);
		
		List<Map<String,Long>> mapList=ejlHealthBloodPressureDaoImpl.getLastestUserIdDeviceIdListByCreateTime(lastTime, curTime);
		
		if(null!=mapList){
			for(Map<String,Long> devmap:mapList){
				Long userId=devmap.get("userId");
				Long deviceId=devmap.get("deviceId");
				//EjlUser deviceUser = ejlUserDaoImpl.getById(userId);
				try{
					EfUserHealthSetting userHealthSett= efUserHealthSettingService.getByUserId(userId);
					if(null==userHealthSett){
						userHealthSett=new EfUserHealthSetting();
						userHealthSett.setBloodHighSpan(defaultBloodHighSpan);
						userHealthSett.setBloodLowSpan(defaultBloodLowSpan);
					}
					IntegerSpan spanBloodHigh=new IntegerSpan(userHealthSett.getBloodHighSpan());
					Integer bloodHighMin=spanBloodHigh.getDown();
					Integer bloodHighMax=spanBloodHigh.getUp();
					
					IntegerSpan spanBloodLow=new IntegerSpan(userHealthSett.getBloodLowSpan());
					Integer bloodLowMin=spanBloodLow.getDown();
					Integer bloodLowMax=spanBloodLow.getUp();
					
					EjlHealthBloodPressureAlarmPram ejlHealthBloodPressureAlarmPram = EjlHealthBloodPressureAlarmPram.getEjlHealthBloodPressureAlarmPram(userId, deviceId, lastTime, curTime, userHealthSett);
					
					List<EjlHealthBloodPressure> ejlHealthBloodPressureList = ejlHealthBloodPressureDaoImpl.getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(ejlHealthBloodPressureAlarmPram);
					
					Context ctx=new Context(userId,deviceId);
					if(null!=ejlHealthBloodPressureList){
						for(EjlHealthBloodPressure bloodPressure:ejlHealthBloodPressureList){
							try{
								
								/**
								 
                                    imei:设备号码
									systolicPressure ：收缩压
									diastolicPressure：舒张压
									Time:时间
									sysType:收缩压类型（1低 2高）
									diaType:舒张压类型（1低 2高）

								 */
								
								
								int sysType=0;
								if(bloodPressure.getHigh().intValue()>bloodHighMax.intValue()){
									sysType=2;
								}else if(bloodPressure.getHigh().intValue()< bloodHighMin.intValue()){
									sysType=1;
								}
								
								int diaType=0;
								if(bloodPressure.getLow().intValue()>bloodLowMax.intValue()){
									diaType=2;
								}else if(bloodPressure.getLow().intValue()< bloodLowMin.intValue()){
									diaType=1;
								}
								EfDeviceAlarm deviceAlarm=new EfDeviceAlarm();
								deviceAlarm.setUserId(userId);
								deviceAlarm.setDeviceId(deviceId);
								deviceAlarm.setType(EfDeviceAlarm.Type.BLOOD.getValue());
								
								Map<String,String> map=new HashMap<String,String>();
								
								map.put("systolicPressure", bloodPressure.getHigh()+"");
								map.put("diastolicPressure", bloodPressure.getLow()+"");
								
								map.put("sysType", sysType+"");
								map.put("diaType", diaType+"");
								
								map.put("userId", userId+"");
								map.put("deviceId", deviceId+"");
								//map.put("time", bloodPressure.getToTime()+"");
								
								deviceAlarm.setValue(JsonUtils.toJson(map));
								deviceAlarm.setTime(bloodPressure.getToTime());
								deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
								tskDeviceAlarmService.save(ctx, deviceAlarm);
								
							}catch(Exception e){
								log.error("generate device blood pressure alarm failed="+bloodPressure.toString());
							}
						}
					}
				}catch(Exception e){
					log.error("generate device blood pressure alarm failed. userId="+userId);
				}
			}
		}
		redisClient.set(RedisPrefix.HEALTH_BLOODPRESSURE_ALARM_LASTTIME, curTime.getTime()+"");
	}
}
