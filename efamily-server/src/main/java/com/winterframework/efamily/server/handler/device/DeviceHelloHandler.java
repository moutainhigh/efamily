package com.winterframework.efamily.server.handler.device;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.ResourceType.DefinitionType;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.QrcodeUtil;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.dto.UpdateUserLoginRecordRequest;
import com.winterframework.efamily.dto.UpdateUserLoginRecordResponse;
import com.winterframework.efamily.dto.device.DeviceHelloRequest;
import com.winterframework.efamily.dto.device.DeviceHelloResponse;
import com.winterframework.efamily.dto.device.ResourceUploadRequest;
import com.winterframework.efamily.dto.device.ResourceUploadResponse;
import com.winterframework.efamily.server.core.AbstractHandler;
import com.winterframework.efamily.server.core.TokenManager;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 设备向服务器打招呼处理类
 * 
 * @ClassName
 * @Description
 * @author ibm 2015年3月17日
 */
@Service("deviceHelloHandler")
public class DeviceHelloHandler extends AbstractHandler {
	@PropertyConfig("server.url.device")
	private String serverUrl;
	@PropertyConfig("device.hello")
	private String urlPath;
	@PropertyConfig("device.loginRecord")
	private String urlLoginRecord;	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "qrcodeUtil")
	private QrcodeUtil qrcodeUtil;
	@PropertyConfig("server.url.app")
	private String serverUrlApp;
	@PropertyConfig("resource.upload")
	private String urlPathResource;
	@PropertyConfig(value = "filePath")
	private String filePath; 
	@PropertyConfig("portal.url")
	private String portalUrl;
	private final String DIR_QRCODE="qrcode";

	@Override
	protected FmlResponse doHandle(Context ctx, FmlRequest req) throws ServerException{ 
		DeviceHelloRequest bizReq=(DeviceHelloRequest)JsonUtils.fromJson(req.getValue("data"), DeviceHelloRequest.class);
		FmlResponse res=new FmlResponse(req);
		//APP绑定手表往redis写入key=imei,value=appId(已有则绑定请求扔入推送队列fromId-toId） 
		Response<DeviceHelloResponse> bizRes=http(serverUrl,urlPath,ctx,bizReq,DeviceHelloResponse.class);	  
		res=getFmlResponse(res,bizRes);
		if(null!=bizRes && null!=bizRes.getData()){ 
			/*String qrcodeId="";
			try{
				String definition=DefinitionType.HIGH;   
				String filepath=qrcodeUtil.generate(filePath+File.separator+DIR_QRCODE+File.separator+bizRes.getData().getDeviceId()+definition,ResourceType.ImageType.JPG,portalUrl+"/qr?imei="+bizReq.getImei());
				
				ResourceUploadRequest bizReq2=new ResourceUploadRequest();
				String resourceId=ResourceUtils.getMediaId(ResourceType.ImageType.JPG);
				bizReq2.setResourceId(resourceId);
				bizReq2.setType(ResourceType.IMAGE);
				bizReq2.setExtType(ResourceType.ImageType.JPG); 
				bizReq2.setFilePath(filepath);
				bizReq2.setIsMulti(YesNo.NO.getValue());
				bizReq2.setRemark("user="+bizRes.getData().getUserId()+" device="+bizRes.getData().getDeviceId()); 
				Response<ResourceUploadResponse> bizRes2=http(serverUrlApp,urlPathResource,ctx,bizReq2,ResourceUploadResponse.class);
				
				if(StatusCode.OK.getValue()!=bizRes2.getStatus().getCode()){
					log.info("exception when save resource.statuscode="+bizRes2.getStatus().getCode());
				}
				qrcodeId=resourceId;	//成功之后赋值
			}catch(Exception e){
				log.error("Error occurs when generate qrcode.",e);
			}*/
			
			Map<String,String> resData=new HashMap<String,String>();
			resData.put("userId",bizRes.getData().getUserId()+"");
			resData.put("deviceId",bizRes.getData().getDeviceId()+"");
			resData.put("fromId",bizRes.getData().getFromId()+"");
			resData.put("nickName",bizRes.getData().getNickName());
			resData.put("phoneNumber",bizRes.getData().getPhoneNumber());
			resData.put("familyName",bizRes.getData().getFamilyName());
			resData.put("chatRoomId",bizRes.getData().getChatRoomId()+"");
			resData.put("time", DateUtils.getCurTime()+"");
			//resData.put("qrcodeId",qrcodeId);
			res.setData(resData);
			
			ctx.set("userId",bizRes.getData().getUserId());
			ctx.set("deviceId",bizRes.getData().getDeviceId());
			ctx.set("nickName",bizRes.getData().getDeviceNickName());
			ctx.set("phoneNumber",bizRes.getData().getDevicePhoneNumber());
			ctx.set("familyName",bizRes.getData().getFamilyName());
			String token=getToken(ctx);
			res.setToken(token);
			
			try{
				UpdateUserLoginRecordRequest  loginReq =  new UpdateUserLoginRecordRequest();
				loginReq.setStatus(1);	// status 1:登录 
				loginReq.setToken(token);
				loginReq.setRemark("ip="+req.getIp()+";clientType="+req.getClinetType()+";");
				Response<UpdateUserLoginRecordResponse> loginRecordRes=http(serverUrl,urlLoginRecord,ctx,loginReq,UpdateUserLoginRecordResponse.class);
				if(loginRecordRes!=null){
					log.debug("记录用户登录状态成功： userId = "+ctx.getUserId()+" ; token = "+token+" ; ");
				}
			}catch(Exception e){
				log.error("设备登录，记录日志时出现异常：token = "+token);
			}
			
		}else{
			Map<String,String> resData=new HashMap<String,String>();
			resData.put("time",DateUtils.getCurTime()+"");
			res.setData(resData);
		}
		return res;
	}
	private String getToken(Context ctx) {
		return TokenManager.save(ctx);
	}

}