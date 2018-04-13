package com.winterframework.efamily.server.handler;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ScanWatchRequest;
import com.winterframework.efamily.dto.ScanWatchResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.INoticeService;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("scanWatchHandler")
public class ScanWatchHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl; 
	@PropertyConfig("app.server.scan_watch")
	private String urlPath;
	
	@Resource(name="noticeServiceImpl")
	private INoticeService noticeServiceImpl;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		ScanWatchRequest  bizReqList =  new ScanWatchRequest();
		bizReqList.setNickName(request.getData().get("nickName"));
		bizReqList.setWatchCode(request.getData().get("watchCode"));
		bizReqList.setZombieUserId(request.getData().get("zombieUserId"));
		bizReqList.setPhoneNumber(request.getData().get("phoneNumber"));
		if(((int)request.getClinetType())==21||((int)request.getClinetType())==22){
			ctx.set("appType", 3);//康朵
		}else if(((int)request.getClinetType())==11||((int)request.getClinetType())==12){
			ctx.set("appType", 2);//诺安诺泰
		}else if((int)request.getClinetType()==31||(int)request.getClinetType()==32){
			ctx.set("appType", 5);	////华寿用户
		}else if((int)request.getClinetType()==41||(int)request.getClinetType()==42){
			ctx.set("appType", 6);	////智慧民生
		}else if((int)request.getClinetType()==51||(int)request.getClinetType()==52){
			ctx.set("appType", 7);	////奇智科技
		}else if((int)request.getClinetType()==61||(int)request.getClinetType()==62){
			ctx.set("appType", 8);	////北斗天云
		}else if((int)request.getClinetType()==71||(int)request.getClinetType()==72){
			ctx.set("appType", 9);	////中斗安利信
		}else{
			ctx.set("appType", 1);//松果 亿家联
		}
		
		
		Response<ScanWatchResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,ScanWatchResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
			   responseMap.put("familyId", bizRes.getData().getFamilyId()+"");
			   responseMap.put("familyHostUserId", bizRes.getData().getFamilyHostUserId()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}