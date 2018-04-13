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
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.service.IDeviceParamConfigService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("deviceParamConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceParamConfigServiceImpl  extends EjlComDeviceConfigServiceImpl implements IDeviceParamConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceParamConfigServiceImpl.class);
	
	@Resource(name="notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	@Override
	public void checkAndSetting(Context ctx,EjlDeviceParmConfig param) throws BizException {
		EjlDeviceParmConfig paramReal=getByDeviceIdAndKey(param.getDeviceId(), param.getParamKey()); 
		if(null!=param && !param.equals(paramReal)){	//if param=null then no setting so and no action. 
			Map<String,String> data=new HashMap<String,String>(); 
			data.put("paramCode", paramReal.getParamValue());
			data.put("paramValue", paramReal.getParamValue());
			
			NotyTarget target=new NotyTarget(ctx.getUserId(), ctx.getDeviceId());
			NotyMessage message=new NotyMessage();
			message.setCommand(20203);
			message.setType(NotyMessage.Type.MSG);
			message.setData(data);
			Notification notification=new Notification();
			notification.setTarget(target);
			notification.setMessage(message);
			notification.setRealTime(false);
			notificationUtil.notification(notification);
		}	
	}

	
	public void chargeHandler(Context ctx,Long userId,Long deviceId,Integer status,Long time){
            
		
		
		
		
	}
	
}
