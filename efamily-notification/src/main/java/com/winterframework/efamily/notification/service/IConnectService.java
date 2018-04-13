package com.winterframework.efamily.notification.service;

import com.winterframework.efamily.dto.notification.NotificationInner;
import com.winterframework.efamily.notification.enums.PushStatus;
import com.winterframework.efamily.notification.exception.NotificationException;


public interface IConnectService {
	
	PushStatus push(NotificationInner notificationInner) throws NotificationException ;
	
}
