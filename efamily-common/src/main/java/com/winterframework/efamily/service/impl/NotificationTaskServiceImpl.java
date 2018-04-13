package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.INotificationTaskDao;
import com.winterframework.efamily.entity.NotificationTask;
import com.winterframework.efamily.service.INotificationTaskService;

@Service("notificationTaskServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class NotificationTaskServiceImpl  extends BaseServiceImpl<INotificationTaskDao,NotificationTask> implements INotificationTaskService {
	@Resource(name="notificationTaskDaoImpl")
	private INotificationTaskDao dao;
	@Override
	protected INotificationTaskDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
