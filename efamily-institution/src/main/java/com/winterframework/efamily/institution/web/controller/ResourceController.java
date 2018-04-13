package com.winterframework.efamily.institution.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.FileUtil;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.dto.device.ResourceDownloadRequest;
import com.winterframework.efamily.dto.device.ResourceDownloadResponse;
import com.winterframework.efamily.dto.device.ResourceUploadRequest;
import com.winterframework.efamily.dto.device.ResourceUploadResponse;
import com.winterframework.efamily.entity.OrgResource;
import com.winterframework.efamily.institution.exception.ServerException;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.service.IOrgComResourceService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/** 校验文件类型http://blog.163.com/songyalong1117@126/blog/static/17139189720144273308468/
 *ResourceController
 * @ClassName
 * @Description
 * @author ibm
 * 2015年8月17日
 */
@Controller("resourceController")
@RequestMapping("/resource")
public class ResourceController {
	private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
	
/*	@PropertyConfig("server.url.app")
	private String serverUrl;*/
	
/*	@PropertyConfig("resource.upload")
	private String urlPath_upload;
	
	@PropertyConfig("resource.download")
	private String urlPath_download;*/
	
	@PropertyConfig(value = "filePath")
	private String filePath;
	
	@Resource(name="fileUtil")
	private FileUtil fileUtil;

	@Resource(name = "orgComResourceServiceImpl")
	private IOrgComResourceService orgComResourceServiceImpl;
	
	@RequestMapping("/toUpload")
	public ModelAndView toUpload(HttpServletRequest request,HttpServletResponse response){
		ModelAndView model = new ModelAndView("upload_file");
	    return model;
	}	
	
	@RequestMapping("/toDownload")
	public ModelAndView toDownload(HttpServletRequest request,HttpServletResponse response){
		ModelAndView model = new ModelAndView("download");
	    return model;
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> map=new HashMap<String,String>();
		try{
			CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
		    if(!cmr.isMultipart(request)){
		    	throw new ServerException(StatusCode.FAILED.getValue());
		    }
	        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);

	        String type=(String)multiRequest.getParameter("type");
	        String extType=(String)multiRequest.getParameter("mediaType");
	        int isMulti=Integer.parseInt(multiRequest.getParameter("isMulti"));	//是否多尺寸
	        
	        Iterator<String> files = multiRequest.getFileNames();
	        String filePath=getFilePath(type); 
	        if(!files.hasNext()){
	        	throw new ServerException(StatusCode.UNKNOW.getValue());
	        }
	        while(files.hasNext()){
	            MultipartFile multiFile = multiRequest.getFile(files.next());
	            filePath+=ResourceType.DefinitionType.HIGH;
	            filePath=fileUtil.save(filePath,multiFile);
	            break;
	        }	  
			Context ctx = new Context();
			ctx.set("userId", -1);
			OrgResource orgResource = new OrgResource();
			String resourceId=ResourceUtils.getMediaId(type);
			orgResource.setResourceId(resourceId);
			orgResource.setType(type);
			orgResource.setExtType(extType);
			orgResource.setFilePath(filePath);
			orgResource.setIsMulti(isMulti);
			orgResource.setRemark("org");
			orgComResourceServiceImpl.save(ctx, orgResource);
			
			//***
			map.put("resultCode", "0");
			map.put("filePath", filePath);
			map.put("resourceId", resourceId);
	    } catch(Exception e){
			e.printStackTrace();
			log.error("");
			map.put("resultCode", "9999");
			map.put("memberList", "");
		}
	    return map;
	}

 	@RequestMapping("/download")  
 	@ResponseBody
	public Object download(HttpServletRequest request,HttpServletResponse response){
 		Map<String,String> map=new HashMap<String,String>();
 		try{
	 		Context ctx= new Context();
	 		ctx.set("userId", -1);
	 		if(null==request.getParameter("resourceId")){
	 			throw new ServerException(StatusCode.PARAM_INVALID.getValue());
	 		}
	 		String resourceId=request.getParameter("resourceId");
	 		
/*			String resourceId=ResourceUtils.getMediaId(type);
			orgResource.setResourceId(resourceId);
			orgResource.setType(type);
			orgResource.setExtType(extType);
			orgResource.setFilePath(filePath.replace(ResourceType.DefinitionType.HIGH,""));
			orgResource.setIsMulti(isMulti);
			orgResource.setRemark("org");*/
	 		
			OrgResource orgResource = orgComResourceServiceImpl.getByResourceId(resourceId);
			
			String filename = orgResource.getResourceId()+"."+orgResource.getExtType();
			InputStream is =null; 
			OutputStream os =null;
			try{ 
				os = new BufferedOutputStream(response.getOutputStream());
				
				/**
				 		response.setCharacterEncoding("utf-8");
		                response.setContentType("multipart/form-data");
		                response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
				  
				 */
				
				//response.setCharacterEncoding("utf-8");
		        response.addHeader("Content-Disposition", "attachment;filename="+filename);
		        response.setContentType("application/"+ orgResource.getType()); 
                //response.setContentType("multipart/form-data");
                
		        File file = new File(orgResource.getFilePath());
		        is = new BufferedInputStream(new FileInputStream(file));
		        byte[] buffer = new byte[is.available()];
		        is.read(buffer); 
		        os.write(buffer);
		        os.flush(); 
		        
			}catch(IOException e){
				throw new ServerException(StatusCode.FAILED.getValue()); 
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch(IOException e) {
						throw new ServerException(StatusCode.FAILED.getValue());
					}
				}
			}
		}catch(Exception e){
			map.put("resultCode", "9999");
			return map;
		}
 		return null;
	}
 	
 	
	private String getFilePath(String type){  
		final String FILE_SUBFIXX = "";
		String fileName=System.currentTimeMillis()+"";
		String strDate = DateUtils.format(DateUtils.currentDate(),DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR);
		return filePath + File.separator+type + File.separator + strDate + File.separator  + fileName + FILE_SUBFIXX;
	}
}
