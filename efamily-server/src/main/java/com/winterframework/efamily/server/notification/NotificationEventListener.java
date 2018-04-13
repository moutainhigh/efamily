package com.winterframework.efamily.server.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.event.BaseEvent;
import com.winterframework.efamily.base.event.BaseEventListener;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.redis.RedisQueue;
 
@Component("notyEventListener")
public class NotificationEventListener extends BaseEventListener{
	private static final Logger log= LoggerFactory.getLogger(NotificationProcessor.class);
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(100);
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	
	@Override
	public void onApplicationEvent(BaseEvent event) {  
		/*if (event instanceof NotificationEvent) { 
			NotyTask notyTask=(NotyTask)event.getSource();
			log.debug("hahaha.have got the event."+notyTask.getRule().getPriority());
			threadPool.execute(new Thread(new NotificationProcessor(NotyQueue.getQueue(notyTask.getRule().getIndex(),null!=notyTask.getTarget().getDeviceId()).getName())));
		}*/
	}

}
