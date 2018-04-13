package com.winterframework.efamily.server.notification;

import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.server.exception.ServerException;


public interface INotyTaskService {
	/**
	 * 推送任务处理
	 * @param notyTask
	 * @return taskId
	 * @throws ServerException
	 */
	void doProcess(NotyTask notyTask) throws ServerException;
	
	/**
	 * 推送绑定
	 * @param imei
	 * @throws ServerException
	 */
	void doProcessBind(NotificationBind notyBind) throws ServerException;
}
