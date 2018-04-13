package com.winterframework.efamily.dispatch.service;

import com.winterframework.efamily.dispatch.exception.DispatchException;



public interface IDispatchService {
 
	/**
	 * 获取服务器信息
	 * @param ip
	 * @return	IP+Port
	 */
	String getServer(String ip) throws DispatchException;
}