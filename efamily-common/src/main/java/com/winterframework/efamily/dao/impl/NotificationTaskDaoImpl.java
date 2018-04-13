package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.INotificationTaskDao;
import com.winterframework.efamily.entity.NotificationTask;
 
@Repository("notificationTaskDaoImpl")
public class NotificationTaskDaoImpl<E extends NotificationTask> extends BaseDaoImpl<NotificationTask> implements INotificationTaskDao{
}
