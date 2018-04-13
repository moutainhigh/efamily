package com.winterframework.efamily.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.service.IEfDeviceSettingService;

@Service("deviceOperationLocationStrategy")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationLocationStrategy extends AbstractDeviceOperationStrategy {
	@Resource(name = "efDeviceSettingServiceImpl")
	private IEfDeviceSettingService efDeviceSettingService;
	@Override
	protected void doBiz(Context ctx,Integer code, Long time, Integer status)
			throws BizException {
		log.error("location onff changed.status="+status+" deviceId="+ctx.getDeviceId());
		EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(ctx.getUserId(),ctx.getDeviceId());
		if(deviceSetting == null){
			deviceSetting = new EfDeviceSetting();
			deviceSetting.setDeviceId(ctx.getDeviceId());
			deviceSetting.setUserId(ctx.getUserId());
		}
		
		String commonSett=deviceSetting.getCommon();
		DeviceParamCommon paramCommon=null;
		if(commonSett!= null){
			paramCommon=JsonUtils.fromJson(commonSett, DeviceParamCommon.class);
		}
		
		if(paramCommon==null){
			paramCommon=new DeviceParamCommon();
		}
		paramCommon.setLocationOnff(status);
		String commonStr=JsonUtils.toJson(paramCommon);
		deviceSetting.setCommon(commonStr);
		efDeviceSettingService.save(ctx, deviceSetting);
	}

}
