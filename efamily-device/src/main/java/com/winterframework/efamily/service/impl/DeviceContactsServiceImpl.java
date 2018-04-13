package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.enums.DeviceParameter;
import com.winterframework.efamily.service.IDeviceContactsService;
import com.winterframework.efamily.service.IDeviceParamConfigService;
 

@Service("deviceContactsServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceContactsServiceImpl extends EjlComDeviceAddressListServiceImpl implements IDeviceContactsService{
	@Resource(name="deviceParamConfigServiceImpl")
	private IDeviceParamConfigService deviceParamConfigService;  

	@Override
	public void upload(Context ctx,List<EjlDeviceAddressList> cttList) throws BizException {
		/**
		 * 1.比对数据库和设备的通讯录 
		 * 2.如果不一致则推送更新
		 */		
		log.debug("welcome to contacts service.");
		for(EjlDeviceAddressList ctt:cttList){			
			log.info(ctt.getUserId()+" "+ctt.getName()
					+" "+ctt.getHeadImage()+" "+ctt.getPhoneNumber());
		}		 
		String key=DeviceParameter.CONTACTS.getValue();
		EjlDeviceParmConfig paramReal=new EjlDeviceParmConfig();
		paramReal.setDeviceId((Long)ctx.getDeviceId());
		paramReal.setParamKey(key);
		paramReal.setParamValue(JsonUtils.toJson(cttList));
		deviceParamConfigService.checkAndSetting(ctx,paramReal);	
	}
	
}
