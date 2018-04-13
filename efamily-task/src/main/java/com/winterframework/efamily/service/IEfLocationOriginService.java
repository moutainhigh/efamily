package com.winterframework.efamily.service;


public interface IEfLocationOriginService extends IEfComLocationOriginService {
	public void initLocationOrigin() throws Exception;
	
	public void initLocationOrigin(Long userId,Long deviceId) throws Exception;
}
