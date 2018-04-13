package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.SaveUserBaseInfoRequest;
import com.winterframework.efamily.dto.SaveUserBaseInfoResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.INoticeService;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.StrUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 保存用户基本信息handler
 * @author floy
 *
 */
@Service("saveUserBaseInfoHandler")
public class SaveUserBaseInfoHandler extends AbstractHandler {
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@Resource(name="noticeServiceImpl")
	private INoticeService noticeServiceImpl;
	@PropertyConfig("app.server.save_user_base_info")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest request) throws ServerException {
		SaveUserBaseInfoRequest bizReq=new SaveUserBaseInfoRequest();
		bizReq.setAge(StrUtils.stringToLong(request.getValue("age"),null));
		bizReq.setImage(request.getValue("image"));
		bizReq.setUserId(Long.valueOf(request.getValue("userId")));
		bizReq.setNickName(request.getValue("nickName"));
		bizReq.setPhone(request.getValue("phone"));
		bizReq.setSex(StrUtils.stringToLong(request.getValue("sex"),null));
		bizReq.setUserName(request.getValue("userName"));
		bizReq.setSignature(request.getValue("signature"));
		Response<SaveUserBaseInfoResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,SaveUserBaseInfoResponse.class);
		FmlResponse res=new FmlResponse(request);
        if(null!=bizRes){			
			res.setStatus(bizRes.getStatus().getCode()); 
			Map<String,String> responseMap = new HashMap<String,String>();
			if(bizRes.getData()!=null){
				responseMap.put("image", bizRes.getData().getImage());
			}
			res.setData(responseMap);
		}else{
			res.setStatus(StatusCode.UNKNOW.getValue());
		}
		return res;
	}
}
