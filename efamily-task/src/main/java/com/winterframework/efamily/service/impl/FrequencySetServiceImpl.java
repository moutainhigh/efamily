package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.DeviceSignalRecord;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceBatteryRecordService;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IFrequencySetService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("frequencySetServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FrequencySetServiceImpl implements IFrequencySetService{
	protected Logger log = LoggerFactory.getLogger(FrequencySetServiceImpl.class); 

	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Resource(name = "efDeviceSettingServiceImpl")
	private IEfDeviceSettingService efDeviceSettingService;
	
	@Resource(name = "deviceBatteryRecordServiceImpl")
	private IDeviceBatteryRecordService deviceBatteryRecordService;
	
	@Resource(name="notificationUtil")
	protected NotificationUtil notificationUtil;
	
	@Override
	public void batterySet(DeviceBatteryRecord battery) throws BizException {
		log.debug("batterySet begin");
		if(battery == null || battery.getValue()==null){
			return;
		}
		try{
		Integer batteryGfreq = 180;
		Integer batteryUfreq = 560;
		if(DateUtils.addMinutes(DateUtils.convertLong2Date(battery.getTime()),30).after(DateUtils.currentDate())){
			if(battery.getValue()>=80){
				batteryGfreq = batteryGfreq*8;
				batteryUfreq = batteryUfreq*8;
			}else if(battery.getValue()>=60){
				batteryGfreq = batteryGfreq*5;
				batteryUfreq = batteryUfreq*5;
			}else if(battery.getValue()>=40){
				batteryGfreq = batteryGfreq*3;
				batteryUfreq = batteryUfreq*3;
			}else{
				batteryGfreq = batteryGfreq/2;
				batteryUfreq = batteryUfreq/2;
			}
			log.debug("batterySet batteryUfreq:" +batteryUfreq +" battery:"+battery.getValue());
			EjlDevice ejlDevice = ejlComDeviceService.get(battery.getDeviceId());
			if(ejlDevice == null || ejlDevice.getUserId() == 0){
				return;
			}
			EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(ejlDevice.getUserId(),battery.getDeviceId());
			if(deviceSetting == null){
				return;
			}
			String frequency= deviceSetting.getFrequency();
			if(frequency != null){
				DeviceParamFrequency deviceParamFrequency = JsonUtils.fromJson(frequency, DeviceParamFrequency.class);
				if(!batteryGfreq.equals(deviceParamFrequency.getBatteryGfreq())||!batteryUfreq.equals(deviceParamFrequency.getBatteryUfreq())){
					EfDeviceSetting deviceSettingNew=new EfDeviceSetting();
					deviceSettingNew.setUserId(ejlDevice.getUserId());
					deviceSettingNew.setDeviceId(battery.getDeviceId());
					DeviceParamFrequency paramFreq=new DeviceParamFrequency();
					paramFreq.setBatteryGfreq(batteryGfreq);
					paramFreq.setBatteryUfreq(batteryUfreq);
					Context ctx = new Context();
					ctx.set("userId", -1);
					
					String frequencyStr=JsonUtils.toJson(paramFreq);
					deviceSettingNew.setFrequency(frequencyStr);
					efDeviceSettingService.save(ctx, deviceSettingNew);
					log.debug("batterySet batteryUfreq:" +frequencyStr+" user:"+deviceSettingNew.getUserId());
					Map<String,String> map = new HashMap<String,String>();
					map.put("command", NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue()+"");
					map.put("code", EfDeviceSetting.ModuleCode.FREQUENCY.getValue()+"");
					map.put("value", frequencyStr);
					pushDevice(ejlDevice.getUserId(), battery.getDeviceId(), map, NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(), NotyMessage.Type.SETT, false);
				}
			}
		}}catch(Exception e){
			log.error("batterySet error", e);
		}
		log.debug("batterySet end");
	}

	public void pushDevice(Long userId,Long deviceId,Map<String,String> data,int command,NotyMessage.Type nt,boolean isRealTime) throws BizException {
   	 try{
	    	 Notification notification = new Notification();
			 if(data==null){
		        data = new HashMap<String,String>();
		     }
			 log.info("操作设备，推送命令给设备： userId = "+userId+" ; deviceId = "+deviceId+" ; command = "+command+" ; data = "+data.toString()); 
			 NotyTarget target = new NotyTarget(userId,deviceId);	//推送目标 
			 NotyMessage message = new NotyMessage();	//推送消息
			 message.setCommand(command);// 更换：20105  解除：20106
			 message.setData(data);
			 //message.setType(NotyMessage.Type.NOTICE);
			 message.setType(nt);
			 notification.setRealTime(isRealTime); //是否实时推送
			 notification.setMessage(message);
			 notification.setTarget(target);
			 notificationUtil.notification(notification);
   	 }catch(Exception e){
   		 log.error("推送异常：",e);
   		 throw new BizException(StatusBizCode.PUSH_FAILED.getValue());
   	 }
    }

	@Override
	public void signalSet(DeviceSignalRecord signal) throws BizException {
	} 
	
	
	
}
