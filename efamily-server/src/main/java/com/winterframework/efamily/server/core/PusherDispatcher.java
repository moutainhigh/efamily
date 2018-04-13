/**   
* @Title: EventDispatcher.java 
* @Package com.winterframework.firefrog.event.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午11:34:32 
* @version V1.0   
*/
package com.winterframework.efamily.server.core;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.exception.ServerException;
 
@Service("pusherDispatcher")
@Transactional(rollbackFor = Exception.class)
public class PusherDispatcher implements IPusherDispatcher{
	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);
	
	@Resource(name = "devicePusherMap")
	private Map<Long, AbstractPusher> devicePusherMap; 
 
	@Override
	public void dispatch(Context ctx,Map<String,String> data) throws ServerException{
		if(null==data || data.size()==0 || null==data.get("command")){
			log.debug("PusherDispatcher.dispatch ,data is null ");
			throw new ServerException(StatusCode.PARAM_INVALID.getValue());
		}
		int command=Integer.parseInt(data.get("command"));
		data.remove("command");
		AbstractPusher pusher = getPusher(command);
		if(pusher!=null){ 
			log.info("The pusher is "+pusher.getClass());
			doDispatch(ctx,pusher,data);
		}else{ 
			log.error("No pusher to push this request.");
		}
	}
	private void doDispatch(Context ctx,AbstractPusher pusher,Map<String,String> data) throws ServerException{ 
		pusher.push(ctx,data);
	}
	protected AbstractPusher getPusher(int command){ 
		AbstractPusher pusher=devicePusherMap.get(String.valueOf(command));
		pusher.setCommand(command);
		return pusher;
	}
}
