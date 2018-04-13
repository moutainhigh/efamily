package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.service.IDeviceCommonService;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("deviceCommonServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceCommonServiceImpl implements IDeviceCommonService{ 
	@Resource(name="notificationUtil")
	private NotificationUtil notificationUtil; 

	@Override
	public void uploadTime(Context ctx, Long time) throws BizException {
		Long curTime=System.currentTimeMillis();
		if(Math.abs(time-curTime)>800){	//时间误差800ms以上 则重新设置
			Map<String,String> data=new HashMap<String,String>(); 
			data.put("value", curTime+"");
			
			NotyTarget target=new NotyTarget(ctx.getUserId(), ctx.getDeviceId());
			NotyMessage message=new NotyMessage();
			message.setCommand(20151);
			message.setType(NotyMessage.Type.MSG);
			message.setData(data);
			Notification notification=new Notification();
			notification.setTarget(target);
			notification.setMessage(message);
			notification.setRealTime(false);
			notificationUtil.notification(notification);
		}		
	}	
}
