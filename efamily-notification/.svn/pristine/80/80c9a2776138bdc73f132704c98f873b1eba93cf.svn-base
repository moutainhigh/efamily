package com.winterframework.efamily.notification.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.event.BaseEventPublisher;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.notification.enums.QueuePrefix;
import com.winterframework.efamily.notification.exception.NotificationException;
import com.winterframework.efamily.notification.model.NotyQueue;
import com.winterframework.efamily.notification.model.NotyTask;
import com.winterframework.efamily.notification.service.INotyTaskService;

@Service("notyTaskNewServiceImpl")
public class NotyTaskNewServiceImpl implements INotyTaskService{
	private static final Logger log= LoggerFactory.getLogger(NotyTaskNewServiceImpl.class); 
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="baseEventPublisher")
	private BaseEventPublisher baseEventPublisher;
	
	@Override
	public void doProcess(NotyTask notyTask) throws NotificationException {
		/**
		 * 队列处理
		 */		
		//NotyRule rule=notyTask.getRule();
		NotyTarget target=notyTask.getTarget();
		String keyUser=target.getKey();
		String queueNameTarget=getQueueName(keyUser);
		redisQueue.add(queueNameTarget, JsonUtils.toJson(notyTask));
		
		redisQueue.add(NotyQueue.QUEUE_USER,queueNameTarget);
		//NotyQueue queue=NotyQueue.getQueue(rule.getIndex(),null!=target.getDeviceId(),false);
		
		/*NotificationEvent event=new NotificationEvent(notyTask);
		baseEventPublisher.publish(event);*/
	}
	
	private String getQueueName(String queueName){ 		
		return QueuePrefix.NOTY+Separator.underline+queueName;
	}
}
