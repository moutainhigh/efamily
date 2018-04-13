package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEjlComDeviceService;

@Service("deviceOperationChargeStrategy")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationChargeStrategy extends AbstractDeviceOperationStrategy {
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Override
	protected void doBiz(Context ctx,Integer code, Long time, Integer status)
			throws BizException {
		EjlDevice device=ejlComDeviceService.get(ctx.getDeviceId());
		if(null!=device){
			if(status.intValue()==YesNo.YES.getValue()){
				device.setRunningMode(EfamilyConstant.DEVICE_RUNNING_MODE_YES_CHARGE);
			}else{
				device.setRunningMode(EfamilyConstant.DEVICE_RUNNING_MODE_NO_CHARGE);
			}
			ejlComDeviceService.save(ctx, device);
		} 		
	}

}
