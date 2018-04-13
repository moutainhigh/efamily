package com.winterframework.efamily.ccontrol.utils;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.dto.common.RedisPrefix;

@Component("routeUtil")
public class RouteUtil {
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	/**
	 * 路由获取服务器IP
	 * @param userId
	 * @param deviceId
	 * @return
	 */
	public String getServerIp(Long userId,Long deviceId){
		String serverIP="";
		Set<String> routeSet=redisClient.keys(RedisPrefix.SERVER_ROUTE+Separator.start);
		if(null!=routeSet){
			String targetStr=userId+Separator.vertical+deviceId;
			for(String route:routeSet){
				/*if(redisClient.scontains(route, targetStr)){
					serverIP=route;
					break;
				}*/
			}
		}
		if(StringUtils.isEmpty(serverIP)){
			//重新获取IP
		}
		return serverIP;
	}
}
