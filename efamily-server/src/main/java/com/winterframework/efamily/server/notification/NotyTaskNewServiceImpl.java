package com.winterframework.efamily.server.notification;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.event.BaseEventPublisher;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.utils.QueuePrefix;

@Service("notyTaskNewServiceImpl")
public class NotyTaskNewServiceImpl implements INotyTaskService{
	private static final Logger log= LoggerFactory.getLogger(NotyTaskNewServiceImpl.class); 
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	@Resource(name="baseEventPublisher")
	private BaseEventPublisher baseEventPublisher;
	
	@Override
	public void doProcess(NotyTask notyTask) throws ServerException {
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
	@Override
	public void doProcessBind(NotificationBind notyBind) throws ServerException {
		redisQueue.add(NotyQueue.QUEUE_BIND,JsonUtils.toJson(notyBind));
	}
	
	private String getQueueName(String queueName){ 		
		return QueuePrefix.NOTY+Separator.underline+queueName;
	}
}
