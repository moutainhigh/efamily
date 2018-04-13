package com.winterframework.efamily.server.core;

import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.server.exception.ServerException;


public interface INoticeService {
	void notify(UserNotice userNotice) throws ServerException;
}
