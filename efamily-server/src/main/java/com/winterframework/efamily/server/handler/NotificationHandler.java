package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.device.NotyTaskRequest;
import com.winterframework.efamily.dto.device.NotyTaskResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 

/**
 * 系统推送响应处理
 * @ClassName
 * @Description
 * @author ibm
 * 2015年9月28日
 */
@Service("notificationHandler")
public class NotificationHandler extends AbstractHandler{ 
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("noty.task.save")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		/**
		 * 请求status=0 则更新已达到，如果更新出错则APP重发请求
		 */
/*		NotyTaskRequest  bizReq=new NotyTaskRequest(); 
		bizReq.setId(Long.valueOf(req.getValue("notyId")));
		bizReq.setStatus(NotyTask.Status.ARRIVED.getCode());
		try{
			http(serverUrl,urlPath,ctx,bizReq,NotyTaskResponse.class);
		}catch(ServerException e){
			log.error(e.getMessage(),e);
		}*/
		return null;
	}
	
}