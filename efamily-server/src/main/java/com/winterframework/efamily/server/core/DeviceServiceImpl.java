package com.winterframework.efamily.server.core;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.exception.ServerException;

@Service("deviceServiceImpl")
public class DeviceServiceImpl implements IDeviceService{
	private static final Logger log= LoggerFactory.getLogger(DeviceServiceImpl.class);
    
	@Resource(name = "pusherDispatcher")
	private IPusherDispatcher pusherDispatcher; 
	
	@Override
	public void operation(Context ctx, Map<String, String> data) throws ServerException{  
		pusherDispatcher.dispatch(ctx, data);
	}
    
}
