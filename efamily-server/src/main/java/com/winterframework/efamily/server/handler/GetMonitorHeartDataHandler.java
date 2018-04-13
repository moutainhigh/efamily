package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.GetMonitorDataRequest;
import com.winterframework.efamily.dto.GetMonitorDataResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.StrUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Service("getMonitorHeartDataHandler")
public class GetMonitorHeartDataHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.get_monitor_heart_data")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		GetMonitorDataRequest  bizReqList =  new GetMonitorDataRequest();
		bizReqList.setCurrentPage(StrUtils.stringToInteger(request.getData().get("currentPage"), null));
		bizReqList.setPerPageSize(StrUtils.stringToInteger(request.getData().get("perPageSize"), null));
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId"))) ;
		bizReqList.setDeviceId(StrUtils.stringToLong(request.getData().get("deviceId"),null)) ;
		bizReqList.setStartDateTime(request.getData().get("startDateTime")==null?null:Long.valueOf(request.getData().get("startDateTime")));
		bizReqList.setEndDateTime(request.getData().get("endDateTime")==null?null:Long.valueOf(request.getData().get("endDateTime")));
		
		
		Response<GetMonitorDataResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,GetMonitorDataResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("unitDatas", bizRes.getData().getHealthyMonitorData());
				responseMap.put("bottomValue", bizRes.getData().getBottomValue()+"");
				responseMap.put("middleValue", bizRes.getData().getMiddleValue()+"");
				responseMap.put("topValue", bizRes.getData().getTopValue()+"");
				responseMap.put("rateRangeGt", bizRes.getData().getRateRangeGt()+"");
				responseMap.put("rateRangeLt", bizRes.getData().getRateRangeLt()+"");
				responseMap.put("operUserId", bizRes.getData().getOperUserId()==null?"":bizRes.getData().getOperUserId()+"");
				responseMap.put("operUserName", bizRes.getData().getOperUserName()==null?"":bizRes.getData().getOperUserName()+"");
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
