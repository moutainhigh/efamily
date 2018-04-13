/**   
* @Title: DeviceBatteryRecordServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午4:52:33 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.enums.DeviceParameter;
import com.winterframework.efamily.service.IDeviceBatteryService;
import com.winterframework.efamily.service.IDeviceParamConfigService;

/** 
* @ClassName: DeviceBatteryRecordServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午4:52:33 
*  
*/
@Service("deviceBatteryServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceBatteryServiceImpl extends DeviceBatteryRecordServiceImpl implements IDeviceBatteryService {
	@Resource(name="deviceParamConfigServiceImpl")
	private IDeviceParamConfigService deviceParamConfigService; 
	
	@Override
	public void save(Context ctx,Long userId, Long deviceId,
			List<DeviceBatteryRecord> batteryList) throws BizException {
		log.debug("welcome to Battery service."+batteryList.size());
		if(null!=batteryList){
			save(ctx,batteryList); 
			Collections.sort(batteryList, new Comparator<DeviceBatteryRecord>() {
				@Override
				public int compare(DeviceBatteryRecord o1, DeviceBatteryRecord o2) { 
					return o1.getTime()>=o2.getTime()?1:-1;
				}
			});
			DeviceBatteryRecord battery=batteryList.get(batteryList.size()-1);
			String key=DeviceParameter.BATTERY.getValue();
			EjlDeviceParmConfig param=deviceParamConfigService.getByDeviceIdAndKey(deviceId, key);
			if(null==param){
				param=new EjlDeviceParmConfig();
				param.setDeviceId(deviceId);
				param.setParamKey(key);
			}
			param.setParamValue(battery.getValue()+"");
			deviceParamConfigService.save(ctx,param);
		}
	}
}
