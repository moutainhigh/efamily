package com.winterframework.efamily.service.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dto.DeviceRemindStruc;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.service.IEjlRemindService;
import com.winterframework.efamily.utils.NotificationUtil;

/** 
* @ClassName: NoticeTask 
* @Description:定时提醒
* @author floy 
* @date 2013-10-23 上午10:31:32 
*  
*/
public class NoticeDeviceTask {

	private Logger log = LoggerFactory.getLogger(NoticeDisposableTask.class);

	@Resource(name = "ejlRemindServiceImpl")
	private IEjlRemindService ejlRemindServiceImpl;

	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;	

	public void execute() throws Exception {
		log.debug("---begin noticeTask----");
		List<EjlRemind> list = ejlRemindServiceImpl.queryAllDeviceRemindForTask();
		Context ctx=new Context();
		ctx.set("userId", -1L);
		for (EjlRemind ejlRemind : list) {
			if (ejlRemind.getDeviceCommond() != null) {
				try{
					DeviceRemindStruc struc = new DeviceRemindStruc();
					struc.setContent(ejlRemind.getContent());
					struc.setPeriodType(ejlRemind.getSendPeriod().intValue());
					struc.setTime(DateUtils.convertDate2Long(ejlRemind.getSendTime()));
					struc.setEndTime(DateUtils.convertDate2Long(ejlRemind.getSendTimeEnd()));
					struc.setType(ejlRemind.getSendMethod() == 1 ? ResourceType.AUDIO
							: ejlRemind.getSendMethod() == 2 ? ResourceType.VIDEO : ResourceType.TEXT);
					struc.setUserId(ejlRemind.getUserId());
					struc.setId(ejlRemind.getId());
					struc.setName(ejlRemind.getName()); 
					EjlUserDevice ejlUserDevice = ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(ejlRemind.getSendUserId());
					if (ejlUserDevice != null) { 
						int command=-1;
						switch (ejlRemind.getDeviceCommond().intValue()) {
						case 1:
							command=20403;
							break;
						case 2:
							command=20404;
							break;
						case 3:
							command=20405;
							break;
						default:
							break;
						}
						if(-1==command) continue;
						
						List<DeviceRemindStruc> sendList = new ArrayList<DeviceRemindStruc>();
						sendList.add(struc);
						Map<String,String> data=new HashMap<String,String>();
						data.put("data", JsonUtils.toJson(sendList));
						
						NotyTarget target=new NotyTarget(ejlRemind.getSendUserId(),ejlUserDevice.getDeviceId());
						NotyMessage message=new NotyMessage();
						message.setCommand(command);
						message.setType(NotyMessage.Type.MSG);
						message.setData(data);
						Notification notification=new Notification();
						notification.setTarget(target);
						notification.setMessage(message);
						notification.setRealTime(false);
						notificationUtil.notification(notification);
						
						ejlRemind.setDeviceCommond(4l);
						ejlRemind.setReceiveTime(DateUtils.currentDate());
						ejlRemindServiceImpl.saveOrUpdate(ctx,ejlRemind);
					}
				}catch(Exception e){
					log.error("",e); 
				}
			}
		}

		log.debug("---end noticeTask----");
	}

}
