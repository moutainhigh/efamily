package com.winterframework.efamily.web.controller;
 

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.device.ResourceDownloadRequest;
import com.winterframework.efamily.dto.device.ResourceDownloadResponse;
import com.winterframework.efamily.dto.device.ResourceUploadRequest;
import com.winterframework.efamily.dto.device.ResourceUploadResponse;
import com.winterframework.efamily.entity.FmlResource;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IResourceService;
 
@Controller("resourceController")
@RequestMapping("/resource")
public class ResourceController {
	private static final Logger log= LoggerFactory.getLogger(ResourceController.class);
	
	@Resource(name = "resourceServiceImpl")
	private IResourceService resourceService;
	 
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestBody Request<ResourceUploadRequest> req) throws BizException{
		ResourceUploadRequest bizReq=req.getData();

		Response<ResourceUploadResponse> res=new Response<ResourceUploadResponse>(req); 
		FmlResource resource=DTOConvert.toFmlResource(bizReq);//?????resourceService.getById(bizReq.getResourceId());
		resourceService.save(req.getCtx(),resource);
		ResourceUploadResponse bizRes=new ResourceUploadResponse();
		bizRes.setResourceId(resource.getResourceId());
		log.error("AAAAAAAAAAAAAAAAAA:"+resource.getResourceId());
		res.setData(bizRes);
		if(null==res.getData()){
			res.setStatus(new Status(StatusBizCode.RESOURCE_UPLOAD_FAILED.getValue()));
		}else{
			res.setStatus(new Status(StatusCode.OK.getValue()));
		}
		return res;
	}
	
	@RequestMapping("/download")
	@ResponseBody
	public Object download(@RequestBody Request<ResourceDownloadRequest> req) throws BizException{
		ResourceDownloadRequest bizReq=req.getData();

		Response<ResourceDownloadResponse> res=new Response<ResourceDownloadResponse>(req); 
		FmlResource resource=resourceService.getById(bizReq.getResourceId());
		/*resource.setType(ResourceType.IMAGE);
		resource.setExtType(ResourceType.ImageType.JPG);
		//resource.setFilePath("/usr/local/nginx/html/123.jpg");
		//resource.setFilePath("D:"+File.separator+"appfile"+File.separator+"20150807"+File.separator+"1438935298348.txt");
		resource.setFilePath("http://192.168.1.111/123.jpg");
		resource.setResourceId(bizReq.getResourceId());*/
		res.setData(DTOConvert.toResourceDownloadResponse(resource));
		if(null==res.getData()){
			res.setStatus(new Status(StatusBizCode.RESOURCE_NOT_FOUND.getValue(),new String[]{bizReq.getResourceId()}));
		}else{
			res.setStatus(new Status(StatusCode.OK.getValue()));
		}
		return res;
	}
}
