package com.winterframework.efamily.notification.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.notification.exception.NotificationException;
import com.winterframework.efamily.notification.service.ICcontrolService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ccontrolServiceImpl")
public class CcontrolServiceImpl implements ICcontrolService{
	private static final Logger log= LoggerFactory.getLogger(CcontrolServiceImpl.class);
	@PropertyConfig("server.url.ccontrol")
	private String serverUrl;
	@PropertyConfig("ccontrol.server.route")
	private String urlPath;
	@PropertyConfig("ccontrol.server.isConnected")
	private String isConnectedPath;
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	
	
	@Override
	public String getServer(Long userId, Long deviceId)
			throws NotificationException {
		Context ctx=new Context();
		ctx.set("userId",userId);
		ctx.set("deviceId",deviceId);
		try{
			Response<String> res=httpUtil.http(serverUrl, urlPath,ctx,"", String.class);
			return res.getData();
		}catch(BizException e){
			log.error("get server error.userId="+userId+" deviceId="+deviceId);
			throw new NotificationException(e.getCode(), e.getMessage(),e);
		}
	}
	@Override
	public boolean isConnected(Long userId, Long deviceId)
			throws NotificationException {
		Context ctx=new Context();
		ctx.set("userId",userId);
		ctx.set("deviceId",deviceId);
		try{
			Response<Boolean> res=httpUtil.http(serverUrl, isConnectedPath,ctx,"", Boolean.class);
			return res.getData();
		}catch(BizException e){
			log.error("is connect error.userId="+userId+" deviceId="+deviceId);
			throw new NotificationException(e.getCode(), e.getMessage(),e);
		}
	}
	
}
