package com.winterframework.efamily.server.core;

import java.util.List;

import com.winterframework.efamily.dto.Message;
import com.winterframework.efamily.server.exception.ServerException;

public interface IMessageService {
	void send(List<String> userIdList,Message chatRecord) throws ServerException;
}
