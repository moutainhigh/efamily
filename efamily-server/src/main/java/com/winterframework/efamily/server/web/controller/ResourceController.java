package com.winterframework.efamily.server.web.controller;

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
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.efamily.server.utils.ImageHelper;
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
	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("resource.upload")
	private String urlPath_upload;
	@PropertyConfig("resource.download")
	private String urlPath_download;
	@PropertyConfig(value = "filePath")
	private String filePath;
	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	
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
	        String token=(String)multiRequest.getParameter("token");
	        Context ctx=TokenManager.getTokenContext(token);
	        if(null==ctx){
	        	throw new ServerException(StatusCode.TOKEN_INVALID.getValue());
	        }
	        Long userId=ctx.getUserId();
	        Long deviceId=ctx.getDeviceId();
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
	            if(YesNo.YES.getValue()==isMulti)
	            {
	            	if(ResourceType.IMAGE.equals(type)){
	            		scaleImage(filePath,extType);
	            	}
	            }
	            break;
	        }	  
	        ResourceUploadRequest bizReq=new ResourceUploadRequest();
			String resourceId=ResourceUtils.getMediaId(type);
			bizReq.setResourceId(resourceId);
			bizReq.setType(type);
			bizReq.setExtType(extType);
			bizReq.setFilePath(filePath.replace(ResourceType.DefinitionType.HIGH,""));
			bizReq.setIsMulti(isMulti);
			bizReq.setRemark("user="+ctx+" device="+deviceId);  
			Response<ResourceUploadResponse> bizRes= httpUtil.http(serverUrl,urlPath_upload,ctx,bizReq,ResourceUploadResponse.class);
			if(null==bizRes){	
				throw new ServerException(StatusCode.UNKNOW.getValue());
			}
			map.put("status", bizRes.getStatus().getCode()+"");
			if(bizRes.getStatus().getCode()==StatusCode.OK.getValue()){	
				map.put("resourceId", bizRes.getData().getResourceId());
			}else{
				String[] errParams=bizRes.getStatus().getParams();
				for(int i=0;i<errParams.length;i++){
					map.put(i+"", errParams[i]);					
				}
			}		
	    } catch (ServerException e) {
			map.put("status", e.getCode()+"");				
		}
	    return map;
	}
	
	private void scaleImage(String sourceImagePath, String oriName){
		String lowPath = sourceImagePath.replace(ResourceType.DefinitionType.HIGH, ResourceType.DefinitionType.LOW);
		String middlePath = sourceImagePath.replace(ResourceType.DefinitionType.HIGH, ResourceType.DefinitionType.MIDDLE);

			double size = ImageHelper.getFileSize(sourceImagePath)/1000;
			if(size<35){
				ImageHelper.scaleImageWithParams(sourceImagePath, lowPath, oriName, 1f);
			}else{
				ImageHelper.scaleImageWithParams(sourceImagePath, lowPath, oriName, 0.9f);
				size = ImageHelper.getFileSize(lowPath)/1000;
				int i=1;
				while(size>30){
					ImageHelper.scaleImageWithParams(sourceImagePath, lowPath, oriName, (float)(1-(i*0.1)));
					size = ImageHelper.getFileSize(lowPath)/1000;
					i++;
				}
			}

			size = ImageHelper.getFileSize(sourceImagePath)/1000;
			if(size<100){
				ImageHelper.scaleImageWithParams(sourceImagePath, middlePath, oriName, 1f);
			}else{
				ImageHelper.scaleImageWithParams(sourceImagePath, middlePath, oriName, 0.9f);
				size = ImageHelper.getFileSize(middlePath)/1000;
				int j=1;
				while(size>80){
					ImageHelper.scaleImageWithParams(sourceImagePath, middlePath, oriName, (float)(1-(j*0.1)));
					size = ImageHelper.getFileSize(middlePath)/1000;
					j++;
				}
			}
		
	}
 	@RequestMapping("/download")  
 	@ResponseBody
	public Object download(HttpServletRequest request,HttpServletResponse response){
 		Map<String,String> map=new HashMap<String,String>();
 		try{
	 		String token=request.getParameter("token"); 
	 		Context ctx=TokenManager.getTokenContext(token);
	 		if(null==ctx){
	        	throw new ServerException(StatusCode.TOKEN_INVALID.getValue());
	        }
	 		if(null==request.getParameter("resourceId")){
	 			throw new ServerException(StatusCode.PARAM_INVALID.getValue());
	 		}
	 		Long userId=ctx.getUserId();
	 		Long deviceId=ctx.getDeviceId();
	 		String resourceId=request.getParameter("resourceId");
	 		String definition=null==request.getParameter("definition")?ResourceType.DefinitionType.HIGH:request.getParameter("definition");	//low middle high
			ResourceDownloadRequest bizReq=new ResourceDownloadRequest();
			bizReq.setResourceId(resourceId); 
			Response<ResourceDownloadResponse> bizRes=httpUtil.http(serverUrl,urlPath_download,ctx,bizReq,ResourceDownloadResponse.class);	
			if(null==bizRes){	
				throw new ServerException(StatusCode.UNKNOW.getValue());
			}
			map.put("status", bizRes.getStatus().getCode()+"");
			if(bizRes.getStatus().getCode()==StatusCode.OK.getValue()){	
				
				String filename = bizRes.getData().getResourceId()+"."+bizRes.getData().getExtType();
				InputStream is =null; 
				OutputStream os =null;
				try{ 
					os = new BufferedOutputStream(response.getOutputStream());
			        response.addHeader("Content-Disposition", "attachment;filename="+filename);
			        response.setContentType("application/"+ bizRes.getData().getType()); 
			        File file = new File(bizRes.getData().getFilePath()+definition);
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
			}else{
				String[] errParams=bizRes.getStatus().getParams();
				for(int i=0;i<errParams.length;i++){
					map.put(i+"", errParams[i]);					
				}
				return map;
			}
		}catch(ServerException e){
			map.put("status", e.getCode()+"");
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
