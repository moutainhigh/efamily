/**   
* @Title: EventDispatcher.java 
* @Package com.winterframework.firefrog.event.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午11:34:32 
* @version V1.0   
*/
package com.winterframework.efamily.server.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
 
@Service("handlerDispatcher")
@Transactional(rollbackFor = Exception.class)
public class HandlerDispatcher implements IHandlerDispatcher {
	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);
	
	@Resource(name = "handlerTypeMap")
	private Map<Integer, Map<Long, AbstractHandler>> handlerTypeMap; 

	@Override
	public FmlResponse dispatch(Context ctx,FmlRequest req) throws ServerException{
		if(req!=null){
			log.info("HandlerDispatcher.dispatch ,req is not null ");
			AbstractHandler handler = getHandler(req.getCommand());
			if(handler!=null){ 
				log.info("The handler is "+handler.getClass());
				return doDispatch(ctx,handler,req);
			}else{ 
				log.error("No handler to handle this request. FmlRequest："+req);
			}
		}else{
			log.info("HandlerDispatcher.dispatch ,req == null ");
		}
		return null;
	}
	private FmlResponse doDispatch(Context ctx,AbstractHandler handler,FmlRequest req) throws ServerException{ 
		return handler.handle(ctx,req);
	}
	protected AbstractHandler getHandler(int command){
		Map<Long, AbstractHandler> handlerMap=handlerTypeMap.get(getHandlerType(command));
		return handlerMap.get(String.valueOf(command));
	}
	private String getHandlerType(int command){
		return String.valueOf(command).substring(0,1); 
	}
	
	public static void main(String[] args){
		List<Map<String,String>> l=new ArrayList<Map<String,String>>();
		Map<String,String> m=new HashMap<String,String>();
		m.put("aa", "111");
		l.add(m);
		String s=JsonUtils.toJson(m); 
		/*List<Map> l2 = jmapper.fromJson(s,
				jmapper.createCollectionType(ArrayList.class, Map.class));*/
		Map<String,String> mm=JsonUtils.fromJson(s, Map.class);
		//System.out.println(l2.get(0).get("aa"));
		System.out.println(mm.get("aa"));
	}

}
