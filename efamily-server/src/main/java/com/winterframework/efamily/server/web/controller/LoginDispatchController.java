package com.winterframework.efamily.server.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.GetPublicDatasRequest;
import com.winterframework.efamily.dto.GetPublicDatasResponse;
import com.winterframework.efamily.dto.GetThirdPartyLinkPhoneNumberRequest;
import com.winterframework.efamily.dto.GetThirdPartyLinkPhoneNumberResponse;
import com.winterframework.efamily.dto.GetVerifyCodeRequest;
import com.winterframework.efamily.dto.GetVerifyCodeResponse;
import com.winterframework.efamily.dto.UpdateUserPasswordRequest;
import com.winterframework.efamily.dto.UpdateUserPasswordResponse;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * @author 登陆ip分发
 *
 */
@Controller("loginDispatchController")
@RequestMapping("/loginDispatch")
public class LoginDispatchController {
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginDispatchController.class);
	@PropertyConfig("server.url.app")
	private String serverUrl; 	
	
	@PropertyConfig("netty.ip.mod0")
	private String ipmod0; 
	
	@PropertyConfig("netty.ip.mod1")
	private String ipmod1; 
	@PropertyConfig("app.server.get_verify_code")
	private String urlVerifyCodePath;
	@PropertyConfig("app.server.get_public_datas")
	private String urlPublicDataPath;
	@PropertyConfig("app.server.get_third_party_link_phonenumber")
	private String getThirdPartyLinkPhoneNumber;
	@PropertyConfig("app.server.update_user_password")
	private String updateUserPassword;	
	
	
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	
	@RequestMapping("/getServerIp")
	@ResponseBody
	public Object getServerIp(@RequestParam String phoneNumber) throws ServerException {
//		LoginDispatchRequest req=new LoginDispatchRequest();
//		req.setPhoneNumber(phoneNumber);
//		Response<LoginDispatchResponse> bizRes=httpUtil.http(serverUrl,urlPath,req,LoginDispatchResponse.class);
		String ip ="";
		String key="ipmod"+ Long.parseLong(phoneNumber)%2;
		ip ="ipmod0".equals(key)?ipmod0.trim():ipmod1.trim();
		return ip;
	}
	@RequestMapping("/getVerifyCode")
	@ResponseBody
	public Object getVerifyCode(HttpServletRequest request, HttpServletResponse response,@RequestParam String phoneNumber,@RequestParam(value = "clientType", required = false) String clientType) throws ServerException {
		GetVerifyCodeRequest req=new GetVerifyCodeRequest();
		req.setPhoneNumber(phoneNumber);
		req.setIp(request.getRemoteAddr());
		if(clientType!=null){
			Integer type=Integer.valueOf(clientType);
			if(type==21||type==22){
				req.setAppType(3);//康朵
			}else if(type==11||type==12){
				req.setAppType(2);//诺安诺泰
			}else if(type==31||type==32){
				req.setAppType(5);//华寿
			}else if(type==51||type==52){
				req.setAppType(7);//奇智
			}else if(type==61||type==62){
				req.setAppType(8);//北斗天云
			}else if(type==71||type==72){
				req.setAppType(9);//中斗安利信
			}
			else{
				req.setAppType(1);//松果 亿家联
			}
		}else{
			req.setAppType(1);//默认亿家联请求
		}
		Response<GetVerifyCodeResponse> bizRes=httpUtil.http(serverUrl,urlVerifyCodePath,req,GetVerifyCodeResponse.class);
		
		return bizRes.getStatus();
	}
	@RequestMapping(value="/getPublicData", produces = "application/json;charset=UTF-8")  
	@ResponseBody 
	public Object getPublicData(@RequestParam Integer clientType,@RequestParam(value="appVersion", required=false) String appVersion) throws ServerException {
		GetPublicDatasRequest  bizReqList =  new GetPublicDatasRequest();
		byte ct = clientType.byteValue();
		bizReqList.setClientType(ct);
		bizReqList.setAppVersion(appVersion) ;
		Response<GetPublicDatasResponse> bizRes=httpUtil.http(serverUrl,urlPublicDataPath,bizReqList,GetPublicDatasResponse.class);
		return bizRes.getData();
	}
	
	@RequestMapping("/getThirdPartyLinkPhoneNumber")
	@ResponseBody
	public Object getThirdPartyLinkPhoneNumber(@RequestParam String sourceId,@RequestParam Integer sourceType ) throws ServerException {
		GetThirdPartyLinkPhoneNumberRequest req=new GetThirdPartyLinkPhoneNumberRequest();
		req.setSourceId(sourceId);
		req.setSourceType(sourceType);
		Response<GetThirdPartyLinkPhoneNumberResponse> bizRes=httpUtil.http(serverUrl,getThirdPartyLinkPhoneNumber,req,GetThirdPartyLinkPhoneNumberResponse.class);
		return bizRes.getData();
	}
	
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	public Object updateUserPassword(@RequestParam(value="oldPassword", required=false) String oldPassword,@RequestParam String password,@RequestParam String phoneNumber,@RequestParam(value="verifyCode", required=false) String verifyCode,@RequestParam Integer oprType) throws ServerException {
		UpdateUserPasswordRequest req=new UpdateUserPasswordRequest();
		req.setOldPassword(oldPassword);  
		req.setPassword(password);
		req.setPhoneNumber(phoneNumber);
		req.setVerifyCode(verifyCode);
		req.setOprType(oprType);
		
		Response<UpdateUserPasswordResponse> bizRes=httpUtil.http(serverUrl,updateUserPassword,req,UpdateUserPasswordResponse.class);
		
		UpdateUserPasswordResponse updateUserPasswordResponse = new UpdateUserPasswordResponse();
        if(null!=bizRes){			
        	updateUserPasswordResponse.setStatus(bizRes.getStatus().getCode()); 
		}else{
			updateUserPasswordResponse.setStatus(StatusCode.UNKNOW.getValue());
		}
		return updateUserPasswordResponse;
	}
}
