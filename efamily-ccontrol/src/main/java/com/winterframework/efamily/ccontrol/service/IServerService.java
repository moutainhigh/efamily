package com.winterframework.efamily.ccontrol.service;

import java.util.List;

import com.winterframework.efamily.ccontrol.exception.CcontrolException;
import com.winterframework.efamily.dto.server.ServerLoad;




public interface IServerService {
 
	/**
	 * 终端连接
	 * @param userId
	 * @param deviceId
	 * @return
	 * @throws CcontrolException
	 */
	void connect(Long userId,Long deviceId,String serverIp) throws CcontrolException;
	/**
	 * 终端连接断开
	 * @param userId
	 * @param deviceId
	 * @return
	 * @throws CcontrolException
	 */
	void disconnect(Long userId,Long deviceId) throws CcontrolException;
	/**
	 * 负载
	 * @param userId
	 * @param deviceId
	 * @param serverIp
	 * @throws CcontrolException
	 */
	List<ServerLoad> load() throws CcontrolException;
	/**
	 * 路由
	 * @param userId
	 * @param deviceId
	 * @return 服务器IP
	 * @throws CcontrolException
	 */
	String route(Long userId,Long deviceId) throws CcontrolException;
	boolean isConnected(Long userId,Long deviceId) throws CcontrolException;
}