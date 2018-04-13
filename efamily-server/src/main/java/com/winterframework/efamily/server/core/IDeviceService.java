package com.winterframework.efamily.server.core;

import java.util.Map;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.exception.ServerException;


public interface IDeviceService {
	void operation(Context ctx,Map<String,String> data) throws ServerException;
}
