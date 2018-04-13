package com.winterframework.efamily.server.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.UpdateOperateLogRequest;
import com.winterframework.efamily.dto.UpdateOperateLogResponse;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.efamily.server.utils.MapToJavaBeanUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;
public abstract class AbstractHandler{
	protected Logger log = LoggerFactory.getLogger(getClass()); 
	
	@PropertyConfig("server.url.app")
	private String serverUrl;
	
	@PropertyConfig("app.server.update_operate_log")
	private String urlPath;

	@PropertyConfig("command.operate.log")
	private String commandOperateLog;
	
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	
	public FmlResponse handle(Context ctx,FmlRequest req) throws ServerException{ 
		FmlResponse res=null;
		preHandle(ctx,req);
		if(     null==TokenManager.getTokenContext(req.getToken()) 
				&& 
				   req.getCommand()!=Command.DEVICE_HELLO.getCode() 
			    && 
			    req.getCommand()!=Command.DEVICE_HELLO_NEW.getCode() 
				&& 
				   req.getCommand()!=Command.DEVICE_HARDWARE.getCode() 
			    && (
			    		req.getCommand()!=Command.REGISTER.getCode() 
			    		&& 
			    		req.getCommand()!=Command.LOGON.getCode() 
			    		&& 
			    		req.getCommand()!=Command.CHECK_STATUS.getCode()
				        && 
				        req.getCommand()!=Command.NOTICE.getCode()
				        && 
				        req.getCommand()!=Command.PUBLIC_DATA.getCode()
				        &&
				        req.getCommand()!=Command.MODULE_DATA.getCode()
				        && 
				        req.getCommand()!=Command.VERIFYCODE.getCode()
				     )
			){
			res=new FmlResponse(req);
			res.setStatus(StatusCode.UN_LOGIN.getValue());
			log.info("【用户未登录】"+"：req.getToken() = "+req.getToken()
							+"：req.getCommand() = "+req.getCommand()
							+"：TokenManager.get(req.getToken()) = "+TokenManager.getTokenContext(req.getToken())
							+"：Command.REGISTER.getCode() = "+Command.REGISTER.getCode()
							+"：Command.LOGON.getCode() = "+Command.LOGON.getCode()
							+"：Command.PUBLIC_DATA.getCode() = "+Command.PUBLIC_DATA.getCode()
							+"：Command.VERIFYCODE.getCode()"+Command.VERIFYCODE.getCode()
							+": request.toString() = "+req.toString()
							+": ctx: "+ctx.getAttrs().toString()); 
		}else{ 
			res=doHandle(ctx,req);
		}
		afterHandle(ctx,req);
		return res;
	}
	
	protected void preHandle(Context ctx,FmlRequest req) throws ServerException{
		if(StringUtils.isBlank(commandOperateLog)){
			return;
		}
		String command = req.getCommand()+"";
		List<String> commandList = Arrays.asList(commandOperateLog.split("#"));
		if(commandList.contains(command)){
			UpdateOperateLogRequest  bizReqList =  new UpdateOperateLogRequest();
			bizReqList.setDeviceId(ctx.getDeviceId());
			bizReqList.setUserId(ctx.getUserId());
			bizReqList.setData( JsonUtils.toJson(req.getData()));
			bizReqList.setCommand(req.getCommand()+"");
			bizReqList.setTime(DateUtils.getCurTime());
			Response<UpdateOperateLogResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,UpdateOperateLogResponse.class);
	        log.info("bizRes:"+bizRes.toString());
		}
	} 
	
	protected abstract FmlResponse doHandle(Context ctx,FmlRequest req) throws ServerException;
	 
	protected void afterHandle(Context ctx,FmlRequest req) throws ServerException{
	}
	
	@SuppressWarnings("rawtypes")
	protected <R> Response http(String serverUrl,String action,R requestData) throws ServerException{
		return httpUtil.http(serverUrl,action, requestData);
	}
	protected <T,R> Response<T> http(String serverUrl,String action,R requestData,Class<?> clazz) throws ServerException{
		return httpUtil.http(serverUrl,action, requestData, clazz);
	}
	protected <T,R> Response<T> http(String serverUrl,String action,Context ctx,R requestData,Class<?> clazz) throws ServerException{
		return httpUtil.http(serverUrl,action, ctx, requestData, clazz);
	}
	protected FmlResponse getFmlResponse(FmlResponse res,Response<?> bizRes){
		if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			if(res.getStatus()!=StatusCode.OK.getValue()){
				Map<String,String> resData=new HashMap<String,String>();
				String[] errParams=bizRes.getStatus().getParams();
				if(null!=errParams){
					for(int i=0;i<errParams.length;i++){
						resData.put(i+"", errParams[i]);					
					}
				}
				res.setData(resData);
			}
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}	
	protected FmlResponse echoFmlResponseByBeanToMap(FmlResponse res,Response<?> bizRes){
		if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			res.setData(MapToJavaBeanUtils.transBean2Map(bizRes.getData()));
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}	
}
