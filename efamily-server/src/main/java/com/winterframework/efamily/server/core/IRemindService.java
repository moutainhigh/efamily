package com.winterframework.efamily.server.core;

import java.util.List;

import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.server.exception.ServerException;

public interface IRemindService {
	void send(List<String> userIdList,RemindStruc remindRecord) throws ServerException;
}
