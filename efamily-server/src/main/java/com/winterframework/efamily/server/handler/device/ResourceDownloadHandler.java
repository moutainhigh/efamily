package com.winterframework.efamily.server.handler.device;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.base.utils.FileUtil;
import com.winterframework.efamily.dto.device.ResourceDownloadRequest;
import com.winterframework.efamily.dto.device.ResourceDownloadResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 设备资源获取
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("resourceDownloadHandler")
public class ResourceDownloadHandler extends AbstractHandler{
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("resource.download")
	private String urlPath;
	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		String resourceId=req.getValue("resourceId");
		String definition=req.getValue("definition");
		ResourceDownloadRequest bizReq=new ResourceDownloadRequest();
		bizReq.setResourceId(resourceId);
		
		Response<ResourceDownloadResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,ResourceDownloadResponse.class);	
		
		FmlResponse res=new FmlResponse(req);
		res=getFmlResponse(res, bizRes);
		if(bizRes.getStatus().getCode()==StatusCode.OK.getValue()){
			ResourceDownloadResponse bizRess=bizRes.getData();
			if(YesNo.NO.getValue()==bizRess.getIsMulti()){
				definition=ResourceType.DefinitionType.HIGH;
			}else{
				definition=null==definition?ResourceType.DefinitionType.LOW:definition;	//多图如果设备没指定清度则传小图
			}
			res.setValue("type", bizRess.getType());
			res.setValue("mediaType", bizRess.getExtType());
			res.setValue("mediaContent",Base64Util.getBASE64(fileUtil.readBytes(bizRess.getFilePath()+definition)));
		}
		return res;
	}
	
}