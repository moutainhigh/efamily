package com.winterframework.efamily.notification.service;

import java.util.Map;

import com.winterframework.efamily.notification.exception.NotificationException;


/**
 * 第三方推送服务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年12月2日
 */
public interface INotificationTpService {
	void jpush(Long userId,Long deviceId,String title,Map<String,String> data) throws NotificationException;
	
}
