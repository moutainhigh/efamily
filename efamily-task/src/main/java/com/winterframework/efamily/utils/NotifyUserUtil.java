package com.winterframework.efamily.utils;

import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;

public class NotifyUserUtil {

	public static void notifyUser(Map<String, String> data, List<EjlUser> userList, Long userId, NoticeType noticeType,
			Long notNoticeUserId,NotificationUtil notificationUtil,boolean isRealTime) {
		if (userList == null || userList.size() == 0) {
			//log.info("需要推送的用户为空。 userId = " + userId + " ; noticeType = " + noticeType);
			return;
		}
		for (EjlUser userTemp : userList) {
			if (notNoticeUserId != null && notNoticeUserId.longValue() == userTemp.getId().longValue()) {
				continue;
			}  
			try { 
				NotyTarget t=new NotyTarget(userTemp.getId(),null); 
				data.put("noticeType", noticeType.getValue()+"");  
				NotyMessage message=new NotyMessage();
				message.setId(null);
				message.setType(NotyMessage.Type.ALARM);
				message.setCommand(EfamilyConstant.PUSH);
				message.setData(data);
				Notification notification=new Notification();
				notification.setTarget(t);
				notification.setMessage(message);
				notification.setRealTime(isRealTime);
				notificationUtil.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
