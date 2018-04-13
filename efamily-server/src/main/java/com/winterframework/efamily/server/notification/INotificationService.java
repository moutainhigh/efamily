package com.winterframework.efamily.server.notification;

import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.server.exception.ServerException;


public interface INotificationService {
	/**
	 * @param notification
	 * @return notyTaskId
	 * @throws ServerException
	 */
	Long notify(Notification notification) throws ServerException;
	/**
	 * 推送绑表
	 * @param imei
	 * @throws ServerException
	 */
	void notifyBind(NotificationBind notyBind) throws ServerException;
}
