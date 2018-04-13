package com.winterframework.efamily.server.core;

import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlResponse;

public interface IPushService {
	void push(Long userId,Long deviceId, FmlResponse response) throws ServerException;
}
