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
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfUserHealthSetting;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEfUserHealthSettingService;
import com.winterframework.efamily.service.ITskDeviceAlarmService;
import com.winterframework.efamily.service.ITskHealthHeartRateService;


/**
 * 心率告警任务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月21日
 */
public class HeartRateAlarmTask {

	private Logger log = LoggerFactory.getLogger(HeartRateAlarmTask.class);

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
	
	
	private final int beforeMin=30;
	private final String defaultRateSpan="60~100";
	
	public void execute() throws Exception{
		/**
		 * 1.获取拥有最新心率数据的设备(上个上传时间至今)
		 * 2.获取设备对应的用户健康设置
		 * 3.循环处理各个用户异常心率数据 有则告警
		 */
		String alarmLastTime=redisClient.get(RedisPrefix.HEALTH_HEARTRATE_ALARM_LASTTIME);
		Date lastTime=null;
		Date curTime=DateUtils.currentDate();
		if(null!=alarmLastTime){
			lastTime=DateUtils.getDate(Long.valueOf(alarmLastTime));
		}else{
			lastTime=DateUtils.addMinutes(curTime, -1*beforeMin);
		}
		List<Map<String,Long>> mapList=tskHealthHeartRateService.getLastestUserIdDeviceIdListByCreateTime(lastTime,curTime);
		if(null!=mapList){
			for(Map<String,Long> devmap:mapList){
				Long userId=devmap.get("userId");
				Long deviceId=devmap.get("deviceId");
				EjlUser deviceUser = ejlUserDaoImpl.getById(userId);
				try{
					EfUserHealthSetting userHealthSett= efUserHealthSettingService.getByUserId(userId);
					if(null==userHealthSett){
						userHealthSett=new EfUserHealthSetting();
						userHealthSett.setRateSpan(defaultRateSpan);
					}
					IntegerSpan span=new IntegerSpan(userHealthSett.getRateSpan());
					Integer low=span.getDown();
					Integer high=span.getUp();
					
					List<EjlHealthHeartRate> rateList=tskHealthHeartRateService.getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(userId,deviceId,low,high,lastTime,curTime);
					Context ctx=new Context(userId,deviceId);
					if(null!=rateList){
						for(EjlHealthHeartRate rate:rateList){
							try{
								Long rt=rate.getRate();
								int type=rt.longValue()<low?1:2;
								EfDeviceAlarm deviceAlarm=new EfDeviceAlarm();
								deviceAlarm.setUserId(userId);
								deviceAlarm.setDeviceId(deviceId);
								
								deviceAlarm.setType(EfDeviceAlarm.Type.HEART.getValue());
								Map<String,String> map=new HashMap<String,String>();
								map.put("heartRate", rate.getRate()+"");
								map.put("type", type+"");
								map.put("nickName", deviceUser.getNickName()!=null?deviceUser.getNickName():deviceUser.getPhone());
								map.put("deviceId", deviceId+"");
								map.put("familyId", deviceUser.getFamilyId()+"");
								deviceAlarm.setValue(JsonUtils.toJson(map));
								deviceAlarm.setTime(rate.getToTime());
								deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
								tskDeviceAlarmService.save(ctx, deviceAlarm);
							}catch(Exception e){
								e.printStackTrace();
								log.error("generate device heart rate alarm failed.healthId="+rate.getId(),e.getMessage());
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					log.error("generate device heart rate alarm failed. userId="+userId,e.getMessage());
				}
			}
		}
		redisClient.set(RedisPrefix.HEALTH_HEARTRATE_ALARM_LASTTIME, curTime.getTime()+"");
	}
}
