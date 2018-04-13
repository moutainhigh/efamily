package com.winterframework.efamily.notification.service;

import com.winterframework.efamily.notification.exception.NotificationException;


public interface ICcontrolService {
	
	String getServer(Long userId,Long deviceId) throws NotificationException;
	
	boolean isConnected(Long userId,Long deviceId) throws NotificationException;
}
