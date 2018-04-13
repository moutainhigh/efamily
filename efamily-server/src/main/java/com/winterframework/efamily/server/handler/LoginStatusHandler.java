package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;

@Service("loginStatusHandler")
public class LoginStatusHandler extends AbstractHandler{	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		FmlResponse res=new FmlResponse(request);
		if(null==TokenManager.getTokenContext(request.getToken())){
			res.setStatus(StatusCode.KICK_OUT.getValue());
		}else{
			res.setStatus(StatusCode.OK.getValue());
		}
		return res;
	}
}
