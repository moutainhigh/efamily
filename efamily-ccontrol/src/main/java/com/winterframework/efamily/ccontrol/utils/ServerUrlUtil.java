package com.winterframework.efamily.ccontrol.utils;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Component("serverUrlUtil")
public class ServerUrlUtil {
	@Resource(name="routeUtil")
	private RouteUtil routeUtil;
	@PropertyConfig(value="server.url.connect")
	private String serverConnectUrl;
	
	 
	/**
	 * 获取路由的连接服务器URL
	 * @param userId
	 * @param deviceId
	 * @return
	 */
	public String getConnectServer(Long userId,Long deviceId){
		String serverIp=routeUtil.getServerIp(userId, deviceId);
		return "";// MessageFormat.format(serverConnectUrl, new Object[]{serverIp});
	}
}
