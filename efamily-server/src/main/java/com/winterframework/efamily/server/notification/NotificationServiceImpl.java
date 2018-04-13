package com.winterframework.efamily.server.notification;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyRule;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.NotyTask;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.CharsetFactory;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.dto.device.NotyTaskRequest;
import com.winterframework.efamily.dto.device.NotyTaskResponse;
import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.core.IPushService;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("notificationServiceImpl")
public class NotificationServiceImpl implements INotificationService{
	private static final Logger log= LoggerFactory.getLogger(NotificationServiceImpl.class);
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	@PropertyConfig("noty.task.save")
	private String urlPath;
	@Resource(name="notyTaskNewServiceImpl")
	private INotyTaskService notyTaskService;
	@Resource(name="pushServiceImpl")
	private IPushService pushService;
	
	
	@Override
	public Long notify(Notification notification) throws ServerException{
		NotyTarget target=notification.getTarget();
		NotyMessage message=notification.getMessage();
		NotyMessage.Type type=message.getType();
		Map<String,String> data=null!=message.getData()?message.getData():new HashMap<String,String>();
		Long userId=target.getUserId();
		Long deviceId=target.getDeviceId();
		
		NotyTaskRequest bizReq=new NotyTaskRequest();
		bizReq.setNotyType(type.getCode());
		bizReq.setMessageId(message.getId());
		bizReq.setUserId(userId);
		bizReq.setDeviceId(deviceId);
		bizReq.setStatus(NotyTask.Status.UN_SEND.getCode());
		String remark = "noticeType="+data.get("noticeType")+";command="+data.get("command");
		bizReq.setRemark(remark);
		Response<NotyTaskResponse> bizRes= httpUtil.http(serverUrl,urlPath,bizReq,NotyTaskResponse.class);
		if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
			throw new ServerException(bizRes.getStatus().getCode());
		}		
		if(null!=target && null==target.getDeviceId()){
			data.put("notyType", type.getCode()+"");
			data.put("notyId", bizRes.getData().getId()+"");			
		}
		Long taskId=bizRes.getData().getId();
		if(!notification.isRealTime()){
			NotyRule rule=NotyRuleFactory.getInstance(type);
			NotyTask task=new NotyTask(); 
			task.setRule(rule);
			task.setTarget(target);
			task.setCommand(message.getCommand());
			task.setNotyType(type);
			task.setData(data);
			task.setTaskTime(DateUtils.getCurTime());
			task.setOffline(Boolean.FALSE);
			task.setSentOffline(Boolean.FALSE);
			task.setId(taskId);
			notyTaskService.doProcess(task);	
		}else{
			notifyRealTime(userId,deviceId,taskId,message.getCommand(),data);
		}
		return taskId;
	}
	private void notifyRealTime(Long userId,Long deviceId,Long taskId,int command,Map<String,String> data) throws ServerException { 
		boolean isConnected=ChannelManager.isConnected(userId, deviceId); 
		if(isConnected){
			//正常连接
			FmlResponse response=new FmlResponse();
			response.setEncode((byte) CharsetFactory.CharsetEnum.UTF8.getCode());
			response.setEncrypt((byte) YesNo.NO.getValue());
	        response.setVersion((byte)1);
	        response.setClinetType((byte)-1);
	        response.setExtend((byte)0);
	        response.setSessionId(DateUtils.getCurTime());
	        response.setCommand(command); //消息推送
			response.setStatus(null!=deviceId?StatusCode.REQUEST.getValue():StatusCode.OK.getValue());  //默认0，ok 
			//res.setStatus(StatusCode.REQUEST.getValue()); 
			response.setToken(TokenManager.getToken(userId,deviceId)); 
			response.setData(data);
			
			try {
				pushService.push(userId, deviceId, response);
				//更新状态：已发送 
				NotyTaskRequest bizReq2=new NotyTaskRequest();
				bizReq2.setId(taskId);
				bizReq2.setStatus(NotyTask.Status.SENT.getCode());
				Context ctx=new Context();
				ctx.set("userId", -1+"");	//系统
				Response<NotyTaskResponse> bizRes2= httpUtil.http(serverUrl,urlPath,ctx,bizReq2,NotyTaskResponse.class);
				if(bizRes2.getStatus().getCode()!=StatusCode.OK.getValue()){
					throw new ServerException(bizRes2.getStatus().getCode());
				}
			} catch (ServerException e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	@Override
	public void notifyBind(NotificationBind notyBind) throws ServerException {
		notyTaskService.doProcessBind(notyBind);
	}
}
