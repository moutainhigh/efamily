package com.winterframework.efamily.server.handler.device;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.ResourceType.DefinitionType;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.FileUtil;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.dto.device.ResourceUploadRequest;
import com.winterframework.efamily.dto.device.ResourceUploadResponse;
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
@Service("resourceUploadHandler")
public class ResourceUploadHandler extends AbstractHandler{ 	
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("resource.upload")
	private String urlPath;
	@PropertyConfig(value = "filePath")
	private String filePath;
	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	
	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException {
		Long userId=ctx.getUserId();
		Long deviceId=ctx.getDeviceId();
		String type=req.getValue("type");
		String mediaType=req.getValue("mediaType");
		String mediaContent=req.getValue("mediaContent");
		int isMulti=null==req.getValue("isMulti")?0:Integer.valueOf(req.getValue("isMulti"));
		
		ResourceUploadRequest bizReq=new ResourceUploadRequest();
		String resourceId=ResourceUtils.getMediaId(type);
		bizReq.setResourceId(resourceId);
		bizReq.setType(type);
		bizReq.setExtType(mediaType);
		String filepath=getFilePath(type);
		String definition=DefinitionType.HIGH;
		if(YesNo.YES.getValue()==isMulti){
			//保存多尺寸图
		}
		fileUtil.save(filepath+definition,Base64Util.getBytesFromBASE64(mediaContent));
		log.info("BBBBBBBBBBBBBBBBBBBBB:"+filepath);
		bizReq.setFilePath(filepath);
		bizReq.setIsMulti(isMulti);
		bizReq.setRemark("user="+userId+" device="+deviceId);
		
		Response<ResourceUploadResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,ResourceUploadResponse.class);	
		FmlResponse res=new FmlResponse(req);
		res=getFmlResponse(res, bizRes);
		if(res.getStatus()==StatusCode.OK.getValue()){			
			res.setValue("resourceId", bizRes.getData().getResourceId());
		}
		return res;
	}
	
	private String getFilePath(String type){  
		final String FILE_SUBFIXX = "";
		String fileName=System.currentTimeMillis()+"";
		String strDate = DateUtils.format(DateUtils.currentDate(),DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR);
		return filePath + File.separator+type + File.separator + strDate + File.separator  + fileName + FILE_SUBFIXX;
	}
	
}