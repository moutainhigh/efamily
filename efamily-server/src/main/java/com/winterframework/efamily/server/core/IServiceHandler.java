package com.winterframework.efamily.server.core;

import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlResponse;
 
/**
 * 服务主处理接口
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
public interface IServiceHandler {
	void push(final Long userId, Long deviceId, final FmlResponse response) throws ServerException;
}
