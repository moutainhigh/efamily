package com.winterframework.efamily.server.handler;



import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UserHealthlyConfigRequest;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("saveHealthyUserConfigHandler")
public class SaveHealthyUserConfigHandler extends AbstractHandler{

	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.saveHealthyUserConfig")
	private String urlPath;
	
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		UserHealthlyConfigRequest struc = new UserHealthlyConfigRequest();
		struc.setDeviceId(Long.valueOf(request.getData().get("deviceId")));
		struc.setUserId(Long.valueOf(request.getData().get("userId")));
		struc.setAge(request.getData().get("age")==null||Long.valueOf(request.getData().get("age"))==-1?null:Long.valueOf(request.getData().get("age")));
		struc.setSex(request.getData().get("sex")==null||Long.valueOf(request.getData().get("sex"))==-1?null:Long.valueOf(request.getData().get("sex")));
		struc.setHeight(request.getData().get("height")==null||Long.valueOf(request.getData().get("height"))==-1?null:Long.valueOf(request.getData().get("height")));
		struc.setWeight(request.getData().get("weight")==null||Double.valueOf(request.getData().get("weight"))==-1?null:Double.valueOf(request.getData().get("weight")));
		
		struc.setArm(request.getValue("arm")==null?null:Integer.valueOf(request.getValue("arm")));
		struc.setSitTime(request.getData().get("sitTime")==null||Float.valueOf(request.getData().get("sitTime"))==-1?null:Float.valueOf(request.getData().get("sitTime")));
		struc.setRemindSwitch(request.getData().get("remindSwitch")==null||Integer.valueOf(request.getData().get("remindSwitch"))==-1?null:Integer.valueOf(request.getData().get("remindSwitch")));
		struc.setStepCountSwitch(request.getData().get("stepCountSwitch")==null||Integer.valueOf(request.getData().get("stepCountSwitch"))==-1?null:Integer.valueOf(request.getData().get("stepCountSwitch")));
		struc.setHeartSwitch(request.getData().get("heartSwitch")==null||Integer.valueOf(request.getData().get("heartSwitch"))==-1?null:Integer.valueOf(request.getData().get("heartSwitch")));
		struc.setClimbSwitch(request.getData().get("climbSwitch")==null||Integer.valueOf(request.getData().get("climbSwitch"))==-1?null:Integer.valueOf(request.getData().get("climbSwitch")));
		struc.setStepCount(request.getData().get("stepCount")==null||Long.valueOf(request.getData().get("stepCount"))==-1?null:Long.valueOf(request.getData().get("stepCount")));
		struc.setId((request.getData().get("id")==null||request.getData().get("id").equals("null")||Long.valueOf(request.getData().get("id")).longValue()==-1l)?null:Long.valueOf(request.getData().get("id")));
		struc.setSleepSwitch(request.getData().get("sleepSwitch")==null||Integer.valueOf(request.getData().get("sleepSwitch"))==-1?null:Integer.valueOf(request.getData().get("sleepSwitch")));
		struc.setSittingSpan(request.getData().get("sittingSpan")==null?null:request.getData().get("sittingSpan"));
		struc.setSleepSpan(request.getData().get("sleepSpan")==null?null:request.getData().get("sleepSpan"));
		
		Response<Object> bizRes=http(serverUrl,urlPath,ctx,struc,Object.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode());
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
