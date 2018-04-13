package com.winterframework.efamily.server.core;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlResponse;

@Service("pushServiceImpl")
public class PushServiceImpl implements IPushService{
	private static final Logger log= LoggerFactory.getLogger(PushServiceImpl.class);
    
	@Resource(name="serviceHandler")
	private IServiceHandler serviceHandler;
	@Override
	public void push(Long userId, Long deviceId, FmlResponse response) throws ServerException {
		serviceHandler.push(userId,deviceId, response);
	}    
}
