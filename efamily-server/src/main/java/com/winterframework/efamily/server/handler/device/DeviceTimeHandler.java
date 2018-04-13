package com.winterframework.efamily.server.handler.device;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
 
/**
 * 设备时间拉取
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 * 
 */
@Service("deviceTimeHandler")
public class DeviceTimeHandler extends AbstractHandler{ 	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {	
		FmlResponse res=new FmlResponse(req);
		res.setValue("value", DateUtils.getCurTime()+"");
		return res;
	}
	
}