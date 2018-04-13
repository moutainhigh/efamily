package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.BindThirdPartyCertificationRequest;
import com.winterframework.efamily.dto.BindThirdPartyCertificationResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
 * @ClassName: bindThirdPartyCertificationHandler
 * @Description: TODO(绑定第三方认证)
 * @author jason
 * @date 2015-6-24 上午9:47:47
 * 
 */
@Service("bindThirdPartyCertificationHandler")
public class BindThirdPartyCertificationHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;

	@PropertyConfig("app.server.bind_third_party_certification")
	private String urlPath;

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request)throws ServerException {
		BindThirdPartyCertificationRequest bizReqList = new BindThirdPartyCertificationRequest();
		bizReqList.setUserId(Long.valueOf(request.getData().get("userId")));
		bizReqList.setSourceType(request.getData().get("sourceType"));
		bizReqList.setSourceId(request.getData().get("sourceId"));
		bizReqList.setOprType(request.getData().get("oprType"));
		Response<BindThirdPartyCertificationResponse> bizRes = http(serverUrl, urlPath,ctx, bizReqList, BindThirdPartyCertificationResponse.class);
		FmlResponse res = new FmlResponse(request);
		if (null != bizRes) {
			res.setStatus(bizRes.getStatus().getCode());
			res.setData(new HashMap<String, String>());
		} else {
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
