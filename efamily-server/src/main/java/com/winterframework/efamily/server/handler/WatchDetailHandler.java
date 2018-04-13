package com.winterframework.efamily.server.handler;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.WatchDetailRequest;
import com.winterframework.efamily.dto.WatchDetailResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取手表详情handler
 * @author david
 *
 */
@Service("watchDetailHandler")
public class WatchDetailHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.watch_detail")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		WatchDetailRequest  bizReqList =  new WatchDetailRequest();
		bizReqList.setUserId(request.getData().get("userId"));
		bizReqList.setWatchId(request.getData().get("watchId"));

        Response<WatchDetailResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,WatchDetailResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			if(bizRes.getData() != null){
				res.setData(bizRes.getData().getMap());
			}
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
