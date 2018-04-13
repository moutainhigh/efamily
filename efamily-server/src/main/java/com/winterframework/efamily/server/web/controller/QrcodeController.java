package com.winterframework.efamily.server.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.FileAccess;
import com.winterframework.efamily.base.utils.FileUtil;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.QrcodeUtil;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.dto.device.DeviceHardwareRequest;
import com.winterframework.efamily.dto.device.DeviceHardwareResponse;
import com.winterframework.efamily.dto.device.DeviceInitRequest;
import com.winterframework.efamily.dto.device.DeviceInitResponse;
import com.winterframework.efamily.dto.device.DeviceSoftwareRequest;
import com.winterframework.efamily.dto.device.DeviceSoftwareResponse;
import com.winterframework.efamily.dto.device.ResourceUploadRequest;
import com.winterframework.efamily.dto.device.ResourceUploadResponse;
import com.winterframework.efamily.dto.device.UpdateQrcodeResourceIdRequest;
import com.winterframework.efamily.dto.device.UpdateQrcodeResourceIdResponse;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamConnect;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.efamily.server.web.result.BaseHttpResult;
import com.winterframework.efamily.server.web.result.DeviceInitResult;
import com.winterframework.efamily.server.web.result.DeviceTimeResult;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/** 根据内容下载二维码
 *QrcodeController
 * @ClassName
 * @Description
 * @author ibm
 * 2015年12月21日
 */
@Controller("qrcodeController")
@RequestMapping("/device")
public class QrcodeController {
	private static final Logger log = LoggerFactory.getLogger(QrcodeController.class);
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
	@PropertyConfig("server.url.device")
	private String deviceServerUrl;
	@PropertyConfig("device.hardware")
	private String updateUrlPath;
	@PropertyConfig("device.qrcode")
	private String updateQrcode;
	@PropertyConfig(value = "portal.url")
	private String qrcodeContentPrefix;
	@PropertyConfig(value = "device.qrcode_url")
	private String qrcodeUrlPath;
	@PropertyConfig(value = "device.v2.0.hardware")
	private String hardwareV20UrlPath;
	@PropertyConfig(value = "device.software_upload")
	private String softwareUrlPath;
	@PropertyConfig(value = "device.init")
	private String initUrlPath;
	@PropertyConfig(value = "device.time")
	private String timeUrlPath;
	
	public final String RESOURCE_TYPE_IMAGE=ResourceType.IMAGE;
	public final String RESOURCE_EXTENT_TYPE=ResourceType.ImageType.PNG;
	
	
	
	/** 
	 * @Description 根据imei内容下载二维码
	 * @author ibm
	 * 2015年12月21日
	 * @throws ServerException 
	 */
	@RequestMapping("/hardware")
	@ResponseBody
	public Object download(HttpServletRequest request,HttpServletResponse response) throws ServerException {
		//新建Context,用于插入数据库
		Context ctx = new Context();
		ctx.set("userId", -1);
		//得到请求的数据
		String type = request.getParameter("type");
		String imei = request.getParameter("imei");
		String imsi = request.getParameter("imsi");
		String model = request.getParameter("model");
		String simStatus = request.getParameter("simStatus");
		
		Map<String,String> map=new HashMap<String,String>();
		if(null==imei || null==type){
			map.put("status", StatusCode.PARAM_INVALID.getValue()+"");
			return map;
		}
		
		map.put("status", StatusCode.OK.getValue()+"");
		//将设备数据入库
		Response<DeviceHardwareResponse> bizRes = null;
		try {
			DeviceHardwareRequest deviceHardwareRequest = new DeviceHardwareRequest();
			deviceHardwareRequest.setType(Integer.valueOf(type));
			deviceHardwareRequest.setImei(imei);
			deviceHardwareRequest.setImsi(imsi);
			deviceHardwareRequest.setModel(model);
			deviceHardwareRequest.setSimStatus(Integer.valueOf(simStatus));
			bizRes = httpUtil.http(deviceServerUrl,updateUrlPath,ctx,deviceHardwareRequest,DeviceHardwareResponse.class);
		} catch (ServerException e1) {
			log.error("error when save qrcode",e1);
			throw new ServerException(StatusCode.SAVE_FAILED.getValue());
		}	
		if(null==bizRes || null==bizRes.getData()){
			map.put("status", StatusCode.SAVE_FAILED.getValue()+"");
			return map;
		}
		String resourceType = RESOURCE_TYPE_IMAGE;
		String extType=RESOURCE_EXTENT_TYPE;
		String filePath=getFilePath(resourceType); 
		//将内容生成二维码，返回二维码的绝对路径
		filePath = QrcodeUtil.generate(filePath, extType, bizRes.getData().getQrcodeUrl()+"?imei="+imei);
		
		//*** 把图片压缩成大中小
		//*****************  复制文件[high] *******************************************************
		FileAccess.copy(filePath, filePath+ResourceType.DefinitionType.HIGH);
		
		//*****************  压缩文件[low  middle] ************************************************
		FileAccess.scaleImage(filePath+ResourceType.DefinitionType.HIGH,extType);

		//将二维码图片信息插入到资源数据库中
		ResourceUploadRequest bizReq=new ResourceUploadRequest();
		String resourceId=ResourceUtils.getMediaId(resourceType);
		bizReq.setResourceId(resourceId);
		bizReq.setType(resourceType);
		bizReq.setExtType(extType);
		bizReq.setFilePath(filePath);
		bizReq.setIsMulti(0);
		bizReq.setRemark(imei);
		Response<ResourceUploadResponse> resourceBizRes = null;
		try {
			resourceBizRes = httpUtil.http(serverUrl,urlPath_upload,ctx,bizReq,ResourceUploadResponse.class);
		} catch (ServerException e1) {
			log.error("error when down qrcode",e1);
			map.put("status", StatusCode.SAVE_FAILED.getValue()+"");
		}
		if(null == resourceBizRes){	
			map.put("status", StatusCode.SAVE_FAILED.getValue()+"");
			return map;
		}
		//*********** 更新qr_code中对应的resource_id   ***************
		UpdateQrcodeResourceIdRequest bizQrcodeReq=new UpdateQrcodeResourceIdRequest();
		bizQrcodeReq.setResourceId(resourceId);
		bizQrcodeReq.setImei(imei);
		Response<UpdateQrcodeResourceIdResponse> bizQrcodeReqBizRes = null;
		try {
			bizQrcodeReqBizRes = httpUtil.http(deviceServerUrl,updateQrcode,ctx,bizQrcodeReq,UpdateQrcodeResourceIdResponse.class);
		} catch (ServerException e1) {
			log.error("error when down qrcode",e1);
			map.put("status", StatusCode.SAVE_FAILED.getValue()+"");
		}
		if(bizQrcodeReqBizRes==null){
			map.put("status", StatusCode.SAVE_FAILED.getValue()+"");
			return map;
		}
		
		//将文件的二进制流返回
		String filename = resourceId+"."+extType;
		InputStream is =null; 
		OutputStream os =null;
		byte[] buffer =null;
		try{ 
			os = new BufferedOutputStream(response.getOutputStream());
	        response.addHeader("Content-Disposition", "attachment;filename="+filename);
	        response.setContentType("application/"+ RESOURCE_TYPE_IMAGE); 
	        File file = new File(filePath);
	        is = new BufferedInputStream(new FileInputStream(file));
	        buffer = new byte[is.available()];
	        is.read(buffer); 
	        os.write(buffer);
	        os.flush(); 
	        return null;
		}catch(IOException e){
			log.error("error when down qrcode", e);
			map.put("status", StatusCode.UNKNOW.getValue()+"");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch(IOException e) {
					log.error("error when down qrcode", e);
					map.put("status", StatusCode.UNKNOW.getValue()+"");
				}
			}
		}
		return  map;
	}
	/**
	 * 仅用于IMEI数据入库 可独立于手表调用
	 * @param request
	 * @param response
	 * @return
	 * @throws ServerException
	 */
	@RequestMapping("/v2.0/hardware")
	@ResponseBody
	public Object hardwareV20(HttpServletRequest request,HttpServletResponse response) throws ServerException {
		//新建Context,用于插入数据库
		Context ctx = new Context();
		ctx.set("userId", -1);
		//得到请求的数据
		String type = request.getParameter("type");
		String imei = request.getParameter("imei");
		String imsi = request.getParameter("imsi");
		String model = request.getParameter("model");
		String simStatus = request.getParameter("simStatus");
		 
		//将设备数据入库
		Response<DeviceHardwareResponse> bizRes = null;
		BaseHttpResult result=new BaseHttpResult(); 
		try {
			if(null==type || null==imei){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			DeviceHardwareRequest deviceHardwareRequest = new DeviceHardwareRequest();
			deviceHardwareRequest.setType(Integer.valueOf(type));
			deviceHardwareRequest.setImei(imei);
			deviceHardwareRequest.setImsi(imsi);
			deviceHardwareRequest.setModel(model);
			deviceHardwareRequest.setSimStatus(Integer.valueOf(simStatus));
			bizRes = httpUtil.http(deviceServerUrl,hardwareV20UrlPath,ctx,deviceHardwareRequest,DeviceHardwareResponse.class);
			if(null==bizRes){
				throw new ServerException(StatusCode.SAVE_FAILED.getValue()); 
			}else if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
				throw new ServerException(bizRes.getStatus().getCode()); 
			}
			result.setStatus(StatusCode.OK.getValue());
		} catch (ServerException e1) {
			log.error("error when save qrcode",e1);
			result.setStatus(e1.getCode());
		}	
		return  result;
	}
	/**
	 * 上传软件版本
	 * @param request
	 * @param response
	 * @return
	 * @throws ServerException
	 */
	@RequestMapping("/time")
	@ResponseBody
	public Object time(HttpServletRequest request,HttpServletResponse response) throws ServerException {
		//新建Context,用于插入数据库
		Context ctx = new Context();
		ctx.set("userId", -1);
		//得到请求的数据
		String imei=request.getParameter("imei"); 

		DeviceTimeResult result=new DeviceTimeResult();
		Response<Long> bizRes = null;
		try {
			if(null==imei){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			bizRes=httpUtil.http(deviceServerUrl,timeUrlPath,ctx,imei,Long.class);
			if(null==bizRes || null==bizRes.getData()){
				throw new ServerException(StatusCode.FAILED.getValue());
			}else if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
				throw new ServerException(bizRes.getStatus().getCode());
			}
			result.setStatus(StatusCode.OK.getValue());
			result.setValue(bizRes.getData());
		} catch (ServerException e) {
			log.error("error when save version",e);
			result.setStatus(e.getCode());
 		}		 
		return  result;
	}
	/**
	 * 上传软件版本
	 * @param request
	 * @param response
	 * @return
	 * @throws ServerException
	 */
	@RequestMapping("/software")
	@ResponseBody
	public Object software(HttpServletRequest request,HttpServletResponse response) throws ServerException {
		//新建Context,用于插入数据库
		Context ctx = new Context();
		ctx.set("userId", -1);
		//得到请求的数据
		String key=request.getParameter("key");
		String imei=request.getParameter("imei");
		String version=request.getParameter("version");

		BaseHttpResult result=new BaseHttpResult(); 
		try{
			if(null==key || null==imei || null==version){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			if(!"11111111222222223333333344444444".equals(key)){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			DeviceSoftwareRequest deviceSoftwareRequest = new DeviceSoftwareRequest();
			deviceSoftwareRequest.setImei(imei);
			deviceSoftwareRequest.setVersion(version);
			Response<DeviceSoftwareResponse> bizRes = httpUtil.http(deviceServerUrl,softwareUrlPath,ctx,deviceSoftwareRequest,DeviceSoftwareResponse.class);
			if(null==bizRes){
				throw new ServerException(StatusCode.FAILED.getValue());
			}else if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
				throw new ServerException(bizRes.getStatus().getCode());
			}
			result.setStatus(StatusCode.OK.getValue());
		}catch(ServerException e){
			log.error("error when save version",e);
			result.setStatus(e.getCode());
		}
		return  result;
	}
	
	/**
	 * 下载二维码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServerException
	 */
	@RequestMapping("/qrcode")
	@ResponseBody
	public Object qrcode(HttpServletRequest request,HttpServletResponse response) throws ServerException {
		//新建Context,用于插入数据库
		Context ctx = new Context();
		ctx.set("userId", -1);
		//得到请求的数据
		String key=request.getParameter("key");
		String imei=request.getParameter("imei");
		Response<String> bizRes = null;
		BaseHttpResult result=new BaseHttpResult(); 
		try{
			if(null==key || null==imei){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			if(!"11111111222222223333333344444444".equals(key)){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			try {
				bizRes = httpUtil.http(deviceServerUrl,qrcodeUrlPath,ctx,imei,String.class);
				
				if(null==bizRes || null==bizRes.getData()){
					throw new ServerException(StatusCode.FAILED.getValue());
				}else if(bizRes.getStatus().getCode()!=StatusCode.OK.getValue()){
					throw new ServerException(bizRes.getStatus().getCode());
				}
			} catch (ServerException e1) {
				log.error("error when save qrcode",e1);
				throw new ServerException(e1.getCode());
			}	
			
			Map<String,Object> map=new LinkedHashMap<String,Object>(); 
			map.put("status", StatusCode.OK.getValue());
			
			String resourceType = RESOURCE_TYPE_IMAGE;
			String extType = RESOURCE_EXTENT_TYPE;
			String filePath=getFilePath(resourceType); 
			//将内容生成二维码，返回二维码的绝对路径
			filePath = QrcodeUtil.generate(filePath, extType, bizRes.getData()+"?imei="+imei);
			
			//*****************  复制文件[high] *******************************************************
			FileAccess.copy(filePath, filePath+ResourceType.DefinitionType.HIGH);
			
			//*****************  压缩文件[low  middle] ************************************************
			FileAccess.scaleImage(filePath+ResourceType.DefinitionType.HIGH,extType);
			
			//将二维码图片信息插入到资源数据库中
			ResourceUploadRequest bizReq=new ResourceUploadRequest();
			String resourceId=ResourceUtils.getMediaId(resourceType);
			bizReq.setResourceId(resourceId);
			bizReq.setType(resourceType);
			bizReq.setExtType(extType);
			bizReq.setFilePath(filePath);
			bizReq.setIsMulti(0);
			bizReq.setRemark(imei);
			Response<ResourceUploadResponse> resourceBizRes = null;
			try {
				resourceBizRes = httpUtil.http(serverUrl,urlPath_upload,ctx,bizReq,ResourceUploadResponse.class);
			} catch (ServerException e1) {
				log.error("error when getting qrcode",e1);
				throw new ServerException(StatusCode.SAVE_FAILED.getValue());
			}
			if(null == resourceBizRes){	
				log.error("resourceBizRes is null.resourceId="+resourceId);
				throw new ServerException(StatusCode.SAVE_FAILED.getValue());
			}
			//*********** 更新qr_code中对应的resource_id   ***************
			UpdateQrcodeResourceIdRequest bizQrcodeReq=new UpdateQrcodeResourceIdRequest();
			bizQrcodeReq.setResourceId(resourceId);
			bizQrcodeReq.setImei(imei);
			Response<UpdateQrcodeResourceIdResponse> bizQrcodeReqBizRes = null;
			try {
				bizQrcodeReqBizRes = httpUtil.http(deviceServerUrl,updateQrcode,ctx,bizQrcodeReq,UpdateQrcodeResourceIdResponse.class);
			} catch (ServerException e1) {
				log.error("error when down qrcode",e1);
				throw new ServerException(StatusCode.SAVE_FAILED.getValue());
			}
			if(bizQrcodeReqBizRes==null){
				log.error("bizQrcodeReqBizRes is null.resourceId="+resourceId);
				throw new ServerException(StatusCode.SAVE_FAILED.getValue());
			}
			//将文件的二进制流返回
			String filename = resourceId+"."+extType;
			InputStream is =null; 
			OutputStream os =null;
			byte[] buffer =null;
			try{ 
				os = new BufferedOutputStream(response.getOutputStream());
		        response.addHeader("Content-Disposition", "attachment;filename="+filename);
		        response.setContentType("application/"+ RESOURCE_TYPE_IMAGE); 
		        File file = new File(filePath);
		        is = new BufferedInputStream(new FileInputStream(file));
		        buffer = new byte[is.available()];
		        is.read(buffer); 
		        os.write(buffer);
		        os.flush(); 
			}catch(IOException e){
				log.error("error when down qrcode", e);
				throw new ServerException(StatusCode.UNKNOW.getValue());
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch(IOException e) {
						log.error("error when down qrcode", e);
						throw new ServerException(StatusCode.UNKNOW.getValue());
					}
				}
			}
		}catch(ServerException e){
			log.error("qrcode error.",e);
			result.setStatus(e.getCode());
			return result;
		}
		return null;
	}
	
	/**
	 * 手表数据初始化
	 * @param request
	 * @param response
	 * @return
	 * @throws ServerException
	 */
	@RequestMapping("/init")
	@ResponseBody
	public Object init(HttpServletRequest request,HttpServletResponse response) throws ServerException {
		//新建Context,用于插入数据库
		Context ctx = new Context();
		ctx.set("userId", -1);
		//得到请求的数据
		String key=request.getParameter("key");
		String imei=request.getParameter("imei"); 
		Response<DeviceInitResponse> res = null;
		DeviceInitResult result=new DeviceInitResult();
		try {
			if(null==key || null==imei){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			if(!"11111111222222223333333344444444".equals(key)){
				throw new ServerException(StatusCode.PARAM_INVALID.getValue());
			}
			DeviceInitRequest bizReq=new DeviceInitRequest();
			bizReq.setImei(imei);
			res=httpUtil.http(deviceServerUrl,initUrlPath,ctx,bizReq,DeviceInitResponse.class);
			if(null==res || null==res.getData()){
				throw new ServerException(StatusCode.FAILED.getValue());
			}else if(res.getStatus().getCode()!=StatusCode.OK.getValue()){
				throw new ServerException(res.getStatus().getCode());
			}
			 
			DeviceInitResponse bizRes=res.getData();
			result.setStatus(StatusCode.OK.getValue());
			result.setConnect(JsonUtils.fromJson(bizRes.getConnect(),DeviceParamConnect.class));
			result.setCommon(JsonUtils.fromJson(bizRes.getCommon(),DeviceParamCommon.class));
			result.setFrequency(JsonUtils.fromJson(bizRes.getFrequency(),DeviceParamFrequency.class));
			result.setHealth(JsonUtils.fromJson(bizRes.getHealth(),DeviceParamHealth.class));
		} catch (ServerException e) {
			log.error("error when get init data.",e);
			result.setStatus(e.getCode());
		} 
		return  result;
	}
	private String getFilePath(String type){  
		String fileName=System.currentTimeMillis()+"";
		return filePath + File.separator+type + File.separator + "qrcode" + File.separator  + fileName;
	}
}
