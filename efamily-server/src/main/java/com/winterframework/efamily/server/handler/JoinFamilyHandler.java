package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;

/**
 * 加入家庭
 * 
 * @author david
 * 
 */
@Service("joinFamilyHandler")
public class JoinFamilyHandler extends AbstractHandler{

	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		FmlResponse res=new FmlResponse(request);
		return res;
	}
}
