package com.winterframework.efamily.server.core;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.notification.INotificationService;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

public abstract class AbstractPusher {
	protected Logger log = LoggerFactory.getLogger(getClass());
	protected static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "pushServiceImpl")
	protected IPushService pushService;
	@PropertyConfig("server.version")
	private String serverVersion;
	@Resource(name = "notificationServiceImpl")
	protected INotificationService notificationService;
	private int command;

	@Deprecated
	public void push(Context ctx, FmlRequest req) throws ServerException {
		log.info("userId="+ctx.getUserId()+","+req.toString());
		FmlResponse res = new FmlResponse(req);
		res.setStatus(StatusCode.REQUEST.getValue());
		res.setData(getData(ctx, req));
		prePush(ctx, res);
		push(ctx.getUserId(), ctx.getDeviceId(), doPush(ctx, res));
		afterPush(ctx, res);
	}

	public void push(Context ctx,Map<String, String> data) throws ServerException {
		NotyTarget target=new NotyTarget();
		target.setUserId(ctx.getUserId());
		target.setDeviceId(ctx.getDeviceId());
		
		NotyMessage message=new NotyMessage();
		message.setId(null);
		message.setType(NotyMessage.Type.OPER);
		message.setCommand(getCommand());
		message.setData(getData(ctx, data));
		Notification notification=new Notification();
		notification.setTarget(target);
		notification.setMessage(message);
		
		notificationService.notify(notification);
		
		/*Long userId =ctx.getUserId();
		FmlResponse res=new FmlResponse();
		res.setEncode((byte) CharsetFactory.CharsetEnum.UTF8.getCode());
		res.setEncrypt((byte) YesNo.NO.getValue());
		res.setVersion((byte) Integer.parseInt(serverVersion));
		res.setClinetType((byte) -1);
		res.setExtend((byte) 0);
		res.setSessionId(System.currentTimeMillis());
		res.setStatus(StatusCode.REQUEST.getValue());
		res.setCommand(getCommand());
		res.setToken(TokenManager.getToken(userId,ctx.getDeviceId())); 
		res.setData(getData(ctx, data));*/
		
		//prePush(ctx, res);
		//push(userId, ctx.getDeviceId(), doPush(ctx, res));
		//afterPush(ctx, res);
	}

	protected void prePush(Context ctx, FmlResponse res) throws ServerException {
	}

	protected FmlResponse doPush(Context ctx, FmlResponse res) throws ServerException{
		return res;
	}
	
	protected void afterPush(Context ctx, FmlResponse res) throws ServerException {
	}
	protected Map<String, String> getData(Context ctx, Map<String, String> data) throws ServerException {
		return data;
	}
	
	@Deprecated
	protected Map<String, String> getData(Context ctx, FmlRequest req) throws ServerException {
		return req.getData();
	}

	private void push(Long userId, Long deviceId, FmlResponse res) throws ServerException {
		pushService.push(userId, deviceId, res);
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public int getCommand() {
		return command;
	}
	
}
