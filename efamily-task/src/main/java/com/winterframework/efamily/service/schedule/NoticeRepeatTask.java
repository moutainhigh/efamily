package com.winterframework.efamily.service.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.dto.RemindTaskStruc;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlRemindService;
import com.winterframework.efamily.utils.NotificationUtil;

/** 
* @ClassName: NoticeTask 
* @Description:定时提醒
* @author floy 
* @date 2013-10-23 上午10:31:32 
*  
*/
public class NoticeRepeatTask {

	private Logger log = LoggerFactory.getLogger(NoticeDisposableTask.class);

	@Resource(name = "ejlRemindServiceImpl")
	private IEjlRemindService ejlRemindServiceImpl;
	/*@Resource(name = "remindServiceImpl")
	private IRemindService remindServiceImpl;*/
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlUserDaoImpl; 
	@Resource(name = "notificationUtil")
	protected NotificationUtil notificationUtil; 

	public void execute() throws Exception {
		log.debug("---begin noticeTask----");
		List<EjlRemind> list = ejlRemindServiceImpl.queryAllRepeatRemindForTask();
		Context ctx=new Context();
		ctx.set("userId", -1L);
		for (EjlRemind ejlRemind : list) {
			//添加到mq
			List<String> userIdList = new ArrayList<String>();
			userIdList.add(String.valueOf(ejlRemind.getSendUserId()));
			EjlUser user = ejlUserDaoImpl.getById(ejlRemind.getUserId());
			RemindStruc struc = this.convertEntityToRemindStruc(ejlRemind,user.getNickName()!=null?user.getNickName():user.getPhone());
			struc.setHeadImg(user.getHeadImg()); 
			
			NotyTarget target=new NotyTarget(ejlRemind.getSendUserId(),null);  
			RemindTaskStruc taskStruc = new RemindTaskStruc();
			taskStruc.setRemindId(struc.getRemindId());
			taskStruc.setRemindName(struc.getRemindName());
			taskStruc.setUserId(struc.getDeliverUserId());
			taskStruc.setUserName(struc.getDeliverUserName());
			Map<String,String> data=new HashMap<String,String>(); 
			data.put("remind", JsonUtils.toJson(taskStruc));
			
			NotyMessage message=new NotyMessage();
			message.setId(taskStruc.getRemindId());
			message.setType(NotyMessage.Type.REMIND);
			message.setCommand(EfamilyConstant.PUSH);
			message.setData(data);
			Notification notification=new Notification();
			notification.setTarget(target);
			notification.setMessage(message);
			 
			notificationUtil.notification(notification);
			
			//remindServiceImpl.send(userIdList, DTOConvert.convertEntityToRemindStruc(ejlRemind,user.getNickName()!=null?user.getNickName():user.getPhone()));
			//更新为已发送
			ejlRemind.setSentStatus(1l);
			ejlRemindServiceImpl.saveOrUpdate(ctx,ejlRemind);
			EjlRemind record = new EjlRemind();
			record.setRemindId(ejlRemind.getId());
			record.setGmtCreated(new Date());
			record.setUserId(ejlRemind.getSendUserId());
			record.setDeleteStatus(0l);
			record.setRemindState(0l);
			ejlRemindServiceImpl.insertRecord(record);
			  
   			try {
   				NotyTarget t=new NotyTarget(user.getId(),null); 
   				Map<String,String> data2=new HashMap<String,String>(); 
   				data2.put("noticeType", NoticeType.REMIND_SEND.getValue()+"");
   				data2.put("remindId", String.valueOf(ejlRemind.getId()));
   				data2.put("remindTime", String.valueOf(DateUtils.currentDate().getTime()));  
   				NotyMessage message2=new NotyMessage();
   				message2.setId(null);
   				message2.setType(NotyMessage.Type.NOTICE);
   				message2.setCommand(EfamilyConstant.PUSH);
   				message2.setData(data2);
   				Notification notification2=new Notification();
   				notification2.setTarget(t);
   				notification2.setMessage(message2);
   				notificationUtil.notification(notification2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("推送[notifyUser]时出现异常：  userId = "+user.getId());
			}
			
		}
		log.debug("---end noticeTask----");
	}
	
	public static RemindStruc convertEntityToRemindStruc(EjlRemind entity,String userName) {
		if (entity != null) {
			RemindStruc struc = new RemindStruc();
			struc.setRemindId(entity.getId());
			struc.setDeliverDeadTime(DateUtils.convertDate2Long(entity.getSendTimeEnd()));
			struc.setDeliverTime(DateUtils.convertDate2Long(entity.getSendTime()));
			struc.setDeliverUserId(entity.getUserId());
			struc.setRemindContent(entity.getContent());
			struc.setRemindName(entity.getName());
			struc.setRemindRepeatType(entity.getSendPeriod());
			struc.setRemindType(entity.getSendMethod());
			struc.setDurationTime(entity.getDurationTime()==null?0l:entity.getDurationTime());
			struc.setDeliverUserName(userName);
			return struc;
		}
		return null;
	}

}
