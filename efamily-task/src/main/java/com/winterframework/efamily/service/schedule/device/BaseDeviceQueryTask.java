package com.winterframework.efamily.service.schedule.device;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.utils.NotificationUtil;

public abstract class BaseDeviceQueryTask {

	private Logger log = LoggerFactory.getLogger(BaseDeviceQueryTask.class);
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "notificationUtil")
	protected NotificationUtil notificationUtil;
	
	public Notification getPusherParam(Long userId,Long deviceId)
	{ 
		NotyTarget target=new NotyTarget();
		target.setUserId(userId);
		target.setDeviceId(deviceId);
		
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(getCommond());
		message.setData(getParamData());
		Notification notification=new Notification();
		notification.setTarget(target);
		notification.setMessage(message);
		return notification;
	}
	
	public abstract int getCommond();
	
	public abstract Map<String,String> getParamData();
	
	public void execute() throws Exception{
		try{
			EjlUserDevice  eEjlUserDevice= new EjlUserDevice();
			eEjlUserDevice.setStatus(1l);
			List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
			for(EjlUserDevice ejlUserDevice:list){
				Notification notification = getPusherParam(ejlUserDevice.getUserId(),ejlUserDevice.getDeviceId());
				notificationUtil.notification(notification);
			}
		}catch(Exception e){
			log.error("device query task error", e);
		}
	}
}
