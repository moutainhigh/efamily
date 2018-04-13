/**   
* @Title: DeviceBatteryWarningTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-9 下午4:37:56 
* @version V1.0   
*/
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
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.service.IDeviceBatteryRecordService;
import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.service.IFrequencySetService;
import com.winterframework.efamily.service.ITskDeviceAlarmService;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.efamily.utils.NotifyUserUtil;

/** 
* @ClassName: DeviceBatteryWarningTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-9 下午4:37:56 
*  
*/
public class DeviceBatteryWarningTask {
	private Logger log = LoggerFactory.getLogger(NoticeDisposableTask.class); 
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;

	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	@Resource(name = "deviceBatteryRecordServiceImpl")
	private IDeviceBatteryRecordService deviceBatteryRecordService;

	@Resource(name = "RedisClient")
	private RedisClient redisClient; 
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil; 
	
	@Resource(name = "ejlComMessageServiceImpl")
	private IEjlComMessageService ejlComMessageServiceImpl;
	@Resource(name = "tskDeviceAlarmServiceImpl")
	private ITskDeviceAlarmService tskDeviceAlarmService;
	@Resource(name = "propertyUtil")
	private PropertyUtil propertyUtil; 
	
	
	@Resource(name = "frequencySetServiceImpl")
	private IFrequencySetService frequencySetServiceImpl;

	public void execute() throws Exception {
		Long time=DateUtils.addMinutes(DateUtils.currentDate(), -30).getTime(); //1小时前数据
		EjlUserDevice eEjlUserDevice = new EjlUserDevice();
		eEjlUserDevice.setStatus(1l);
		List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
		for (EjlUserDevice userDevice : list) {
			//String maxBattery = redisClient.get(EfamilyConstant.MAXBATTERY + userDevice.getDeviceId());
			String lastNotice = redisClient.get(EfamilyConstant.LASTBATTERY + userDevice.getDeviceId());
			//if (maxBattery != null) {
			try{
				DeviceBatteryRecord deviceBatteryRecord = deviceBatteryRecordService
						.getLastDeviceBatteryRecordByDeviceId(userDevice.getDeviceId(),time);
				//frequencySetServiceImpl.batterySet(deviceBatteryRecord);
				if (deviceBatteryRecord!=null&&Integer.valueOf(deviceBatteryRecord.getValue()) <= 20) {
					Integer level = getBatteryLevel(deviceBatteryRecord.getValue());
					deviceBatteryRecord.setLevel(level);
					EjlUser user = ejlUserDaoImpl.getUserByUserId(userDevice.getUserId());
					EjlUser userSelect = new EjlUser();
					userSelect.setFamilyId(user.getFamilyId());
					userSelect.setType(EfamilyConstant.USER_TYPE_APP);
					List<EjlUser> userList = ejlUserDaoImpl.getEjlUserByAttribute(userSelect);
					Map<String, String> paramMap = new HashMap<String, String>();

					if (lastNotice != null) {
						DeviceBatteryRecord lastDeviceBatteryRecord = JsonUtils.fromJson(lastNotice,
								DeviceBatteryRecord.class);
						/*if (lastDeviceBatteryRecord.getCreateTime().compareTo(deviceBatteryRecord.getCreateTime()) == 0) {
							continue;
						}*/
						if(lastDeviceBatteryRecord.getTime()==null){
							lastDeviceBatteryRecord.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-125).getTime());
						}
						if (DateUtils.addMinutes(new Date(lastDeviceBatteryRecord.getTime()), 30).compareTo(
								new Date(deviceBatteryRecord.getTime())) > 0
								&& Double.valueOf(deviceBatteryRecord.getValue()) > Double
										.valueOf(lastDeviceBatteryRecord.getValue())) {
							continue;
						}
						//2小时内，同级别定量不重复告警
						if(DateUtils.addMinutes(new Date(lastDeviceBatteryRecord.getTime()), 120).compareTo(
								new Date(deviceBatteryRecord.getTime())) > 0){
							if(lastDeviceBatteryRecord.getLevel()!=null&&lastDeviceBatteryRecord.getLevel().equals(deviceBatteryRecord.getLevel())){
								continue;
							}
						}
						
					}
					paramMap.put("userId", userDevice.getUserId() + "");
					paramMap.put("watchId", userDevice.getDeviceId() + "");
					paramMap.put("nickName", user.getNickName());
					paramMap.put("icon", user.getHeadImg());
					paramMap.put("battery", this.getBatteryLevel(deviceBatteryRecord.getValue())+"");
					paramMap.put("batteryTime", deviceBatteryRecord.getTime() + "");
					NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.BATTERY_WARNING, null,notificationUtil,false);
					
					//***** 新增一条消息发送到家庭聊天组  电量低于多少  ***********
					EjlMessage entity=new EjlMessage(); 
					entity.setSendUserId( user.getId());
					entity.setReceiveUserId(user.getFamilyId());
					entity.setChatRoomId(user.getFamilyId());
					entity.setContentType(Long.valueOf(EjlMessage.ContentType.TIPS.getCode())); 
					entity.setChatType(new Long(EjlMessage.ChatType.FML_GROUP.getValue()));
					String nickName = user.getNickName()==null?user.getPhone():user.getNickName();
					String content = propertyUtil.getProperty("notice.watch.battery").replace("@_0_@", nickName).replace("@_1_@", String.valueOf(level));
					entity.setContent(content);
					entity.setContentTime(0L);
					entity.setAppSendTime(Long.valueOf(DateUtils.format(new Date(),"yyyyMMddHHmmssSSS")));
					entity.setStatus(0);
					Context ctx = new Context();
					ctx.set("userId", user.getId());
					ejlComMessageServiceImpl.save(ctx,entity);
					redisClient.set(EfamilyConstant.LASTBATTERY + userDevice.getDeviceId(),
							JsonUtils.toJson(deviceBatteryRecord));
					
					log.debug("battery generate device alarm.userId="+userDevice.getUserId()+" device_id="+userDevice.getDeviceId());
					EfDeviceAlarm deviceAlarm=new EfDeviceAlarm();
					deviceAlarm.setUserId(userDevice.getUserId());
					deviceAlarm.setDeviceId(userDevice.getDeviceId());
					deviceAlarm.setType(EfDeviceAlarm.Type.BATTERY.getValue());
					Map<String,String> map=new HashMap<String,String>();
					map.put("battery", this.getBatteryLevel(deviceBatteryRecord.getValue())+"");
					deviceAlarm.setValue(JsonUtils.toJson(map));
					deviceAlarm.setTime(deviceBatteryRecord.getTime());
					deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
					tskDeviceAlarmService.save(ctx, deviceAlarm);
				}
			}catch(Exception e){
				log.error("set device notice error for use:"+userDevice.getUserId(), e);
			}

		}
	}
	
	public int getBatteryLevel(Integer value){
		int battery = Integer.valueOf(value);
		if(battery>10&&battery<=20){
			return 20;
		}else 
			return 10;
	}
}
