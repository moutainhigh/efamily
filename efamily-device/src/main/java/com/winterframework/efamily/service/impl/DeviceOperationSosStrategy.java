package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfDeviceOperation;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.DeviceOperation;
import com.winterframework.efamily.service.IDeviceOperationService;
import com.winterframework.efamily.service.IEfDeviceAlarmService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.IEjlComUserService;

@Service("deviceOperationSosStrategy")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationSosStrategy extends AbstractDeviceOperationStrategy {
	@Resource(name = "efDeviceAlarmServiceImpl")
    private IEfDeviceAlarmService efDeviceAlarmService;
	@Resource(name = "deviceOperationServiceImpl")
	private IDeviceOperationService deviceOperationService;
	@Resource(name = "ejlComLocationServiceImpl")
    private IEjlComLocationService ejlComLocationService;
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	
	@Override
	protected void doBiz(Context ctx, Integer code, Long time, Integer status)
			throws BizException {
		if(null!=status){
			boolean needAlarm=false;
			Long userId=ctx.getUserId();
			Long deviceId=ctx.getDeviceId();
			Integer type=EfDeviceAlarm.Type.SOS.getValue();
/*			//先判断是否已经上传过  ----目前上传机制存在重复 上传
			EfDeviceOperation deviceOperationExists=deviceOperationService.getByUserIdAndDeviceIdAndCodeAndStatusAndTime(userId, deviceId,type,status,time);
			if(null!=deviceOperationExists){
				log.error("sos upload duplicate.userId="+userId+" deviceId="+deviceId+" status="+status+" time="+time);
				return;
			}*/
			//0则直接告警  
			//非0则判断 上一条是不是0 如果也是非0 说明本次SOS没有上传0  则要告警
			if(status.intValue()==YesNo.NO.getValue()){
				needAlarm=true;
			}else{
				Long btime=DateUtils.addMinutes(DateUtils.currentDate(),-30).getTime();
				EfDeviceOperation deviceOperationLast=deviceOperationService.getLastOneByUserIdAndDeviceIdAndCodeAndTime(userId, deviceId, type, btime);
				if(null!=deviceOperationLast && deviceOperationLast.getStatus().intValue()!=YesNo.NO.getValue()){
					needAlarm=true;
				}
			}
			if(needAlarm){
			
				List<EfDeviceAlarm> efDeviceAlarmList = efDeviceAlarmService.getByUserIdAndDeviceIdAndTypeAndTime(userId, deviceId, type, time);
				if(efDeviceAlarmList!=null && efDeviceAlarmList.size()>0){
					log.error("sos upload duplicate.userId="+userId+" deviceId="+deviceId+" status="+status+" time="+time);
					return;
				}
				Date date=DateUtils.addHours(DateUtils.currentDate(), -1);
				EjlLocation location=ejlComLocationService.getUserLatestLocation(userId, deviceId,YesNo.YES.getValue(),date);
				String value="";
				String address="";
				if(null!=location){
					String loc=location.getLocation();
					String[] s=loc.split(Separator.comma);
					value=s[1]+Separator.comma+s[0];
					address = location.getAddress();
				}
				
	
				EfDeviceAlarm deviceAlarm=new EfDeviceAlarm();
				deviceAlarm.setUserId(userId);
				deviceAlarm.setDeviceId(deviceId);
				deviceAlarm.setType(type);
				Map<String,String> map=new HashMap<String,String>();
				map.put("location", value);
				map.put("alarmaddress", address);
				EjlUser user = ejlComUserService.get(userId);
				if(user != null){
					if(user.getPhone()!=null){
						map.put("telephone", user.getPhone());
					}else{
						map.put("telephone", "12345678911");
					}
					if(user.getSex()!=null){
						map.put("sex", user.getSex()==0?"男":"女");
					}
					if(user.getAge() != null){
						map.put("age", user.getAge()+"");
					}
					map.put("name", user.getNickName());
				}
				deviceAlarm.setValue(JsonUtils.toJson(map));
				deviceAlarm.setTime(time);
				deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
				efDeviceAlarmService.save(ctx, deviceAlarm);
			}
		}
		
	}

}
