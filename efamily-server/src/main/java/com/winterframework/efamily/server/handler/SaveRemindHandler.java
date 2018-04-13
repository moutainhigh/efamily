package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SaveRemindRequest;
import com.winterframework.efamily.dto.SaveRemindResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.StrUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 保存提醒信息handler
 * @author floy
 *
 */
@Service("saveRemindHandler")
public class SaveRemindHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.save_remind")
	private String urlPath;
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		SaveRemindRequest  bizReqList =  new SaveRemindRequest();
		bizReqList.setRemindId(Long.valueOf(request.getData().get("remindId"))) ;
		bizReqList.setRemindText(request.getData().get("remindText"));
		bizReqList.setContent(request.getData().get("content")) ;
	    bizReqList.setRemindName(request.getData().get("remindName")) ;
	    bizReqList.setRemindType(Long.valueOf(request.getData().get("remindType"))) ;
	    bizReqList.setRemindRepeatType(Long.valueOf(request.getData().get("remindRepeatType"))) ;
	    bizReqList.setDeliverTime(StrUtils.stringToLong(request.getData().get("deliverTime"),null)) ;
	    bizReqList.setDeliverDeadTime(StrUtils.stringToLong(request.getData().get("deliverDeadTime"),null)) ;
	    bizReqList.setDeliverUserId(Long.valueOf(request.getData().get("deliverUserId"))) ;
	    bizReqList.setDurationTime(StrUtils.stringToLong(request.getData().get("durationTime"),null)) ;
	    
		Response<SaveRemindResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,SaveRemindResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}


