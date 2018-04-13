package com.winterframework.efamily.server.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.DeleteRemindRequest;
import com.winterframework.efamily.dto.DeleteRemindResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Service("deleteRemindsHandler")
public class DeleteRemindsHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.delete_remind")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		DeleteRemindRequest  bizReqList =  new DeleteRemindRequest();
		String remindIdStr[] = request.getData().get("remindIds").split(",");
		List<Long> ids = new ArrayList<Long>();
		for(String remindId:remindIdStr){
			ids.add(Long.valueOf(remindId));
		}
		
		bizReqList.setRemindIds(ids);
		bizReqList.setType(Long.valueOf(request.getData().get("type")));
		
		Response<DeleteRemindResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,DeleteRemindResponse.class);
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
