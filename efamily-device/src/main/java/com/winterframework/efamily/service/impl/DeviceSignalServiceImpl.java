package com.winterframework.efamily.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.DeviceSignalRecord;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.enums.DeviceParameter;
import com.winterframework.efamily.service.IDeviceParamConfigService;
import com.winterframework.efamily.service.IDeviceSignalService;
 

@Service("deviceSignalServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceSignalServiceImpl extends DeviceSignalRecordServiceImpl implements IDeviceSignalService{
	@Resource(name="deviceParamConfigServiceImpl")
	private IDeviceParamConfigService deviceParamConfigService; 

	@Override
	public void save(Context ctx,Long userId,Long deviceId,List<DeviceSignalRecord> signalList) throws BizException {
		/**
		 * 1.insert设备信号记录表
		 * 2.保存最新数据值 至 设备属性中
		 */		 
		log.debug("welcome to signal service.");
		if(null!=signalList && signalList.size()>0){
			//电量值转换格数
			/*直接传百分比整数 
			EjlDeviceParmConfig signalMax=deviceParamConfigService.getByDeviceIdAndKey(deviceId, DeviceParameter.SIGNAL_MAX.getValue());
			for(DeviceSignalRecord signal:signalList){
				signal.setLevel(UnitUtil.getSignalLevel(signal.getLevel(), Integer.parseInt(signalMax.getParamValue()), -1));
			}*/
			save(ctx,signalList);
		
			Collections.sort(signalList, new Comparator<DeviceSignalRecord>() {
				@Override
				public int compare(DeviceSignalRecord o1, DeviceSignalRecord o2) { 
					return o1.getTime()>=o2.getTime()?1:-1;
				}
			});
			DeviceSignalRecord signal=signalList.get(signalList.size()-1);
			String key=DeviceParameter.SIGNAL.getValue();
			EjlDeviceParmConfig param=deviceParamConfigService.getByDeviceIdAndKey(deviceId, key);
			if(null==param){
				param=new EjlDeviceParmConfig();
				param.setDeviceId(deviceId);
				param.setParamKey(key);
			}
			param.setParamValue(signal.getLevel()+"");
			deviceParamConfigService.save(ctx,param);
		}
	}
	
}
