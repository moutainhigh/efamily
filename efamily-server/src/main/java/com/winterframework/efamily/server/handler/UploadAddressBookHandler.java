package com.winterframework.efamily.server.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.UploadAddressBookRequest;
import com.winterframework.efamily.dto.UploadAddressBookResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
* @ClassName: UploadAddressBookHandler 
* @Description: TODO(上传通讯录,如果通讯录中存在系统用户，则添加为好友) 
* @author jason 
* @date 2015-6-30 下午5:53:08 
*
 */
@Service("uploadAddressBookHandler")
public class UploadAddressBookHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.upload_address_book")
	private String urlPath;
	
	@Override
	protected FmlResponse doHandle(Context ctx,FmlRequest request) throws ServerException {
		UploadAddressBookRequest  bizReqList =  new UploadAddressBookRequest();
		bizReqList.setContactsData(request.getData().get("contactsData"));
		Response<UploadAddressBookResponse> bizRes=http(serverUrl,urlPath,ctx,bizReqList,UploadAddressBookResponse.class);
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