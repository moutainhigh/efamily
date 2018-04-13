package com.winterframework.efamily.api.web.controller;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.api.enums.ResultCode;

import com.winterframework.efamily.api.service.IEfOrgService;
import com.winterframework.efamily.api.util.ValidaUtil;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;

import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.GetVerifyCodeRequest;
import com.winterframework.efamily.dto.GetVerifyCodeResponse;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EjlVerifyCode;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.efamily.service.IEjlComVerifyCodeService;
import com.winterframework.efamily.utils.HttpUtil;


import com.winterframework.modules.spring.exetend.PropertyConfig;
@Controller("institutionRegisterController")
@RequestMapping("/api")
public class InstitutionRegisterController{

		@Resource(name = "efOrgServiceImpl")
		private IEfOrgService efOrgServiceImpl;
		
		@Resource(name = "ejlComVerifyCodeServiceImpl")
		private IEjlComVerifyCodeService ejlComVerifyCodeServiceImpl;
		
		private static final Logger log = LoggerFactory.getLogger(InstitutionRegisterController.class);
		
		@PropertyConfig("server.url.app")
		private String serverUrl;
		
		@PropertyConfig("app.server.get_verify_code")
		private String urlVerifyCodePath;
		
		@Resource(name="httpUtil")
		protected HttpUtil httpUtil;
		
		@Resource(name="efKeyServiceImpl")
		private IEfKeyService efKeyService;
		
		private final static String AUTH_KEY_ ="auth_key_";
		
		@Resource(name = "RedisClient")
		private RedisClient redisClient;

		@RequestMapping("verificationCode")
		@ResponseBody
		protected Object verificationCode(HttpServletRequest request){
			String phoneNumber = request.getParameter("phoneNumber");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			try{
			if(phoneNumber == null||!ValidaUtil.isMobile(phoneNumber)){
				throw new BizException(ResultCode.PARAM_EMPTY.getCode());
			}
			if(!ValidaUtil.isMobile(phoneNumber)){
				throw new BizException(ResultCode.PHONE_INVALID.getCode());
			}
			GetVerifyCodeRequest req=new GetVerifyCodeRequest();
			req.setPhoneNumber(phoneNumber);
			req.setAppType(4);//机构验证码
			Response<GetVerifyCodeResponse> bizRes=httpUtil.http(serverUrl,urlVerifyCodePath,req,GetVerifyCodeResponse.class);
			map.put("resultCode", "0");
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}
			catch(Exception e1){
				map.put("resultCode", "-1");
				map.put("errMsg", "unknown error.");
				log.error(e1.getMessage());
			}
			return map;
		}
		
		
		@RequestMapping("/register")
		@ResponseBody
		protected Object register(HttpServletRequest request) throws Exception {
			String key = request.getParameter("key");
			String institutionName = request.getParameter("institutionName");
			String institutionScale = request.getParameter("institutionScale");
			String city = request.getParameter("city");
			String phoneNumber = request.getParameter("phoneNumber");
			String verificationCode = request.getParameter("verificationCode");
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			String iKey = null;
			try
			{	
				if(institutionName==null || institutionScale==null||city==null||phoneNumber==null||verificationCode==null){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				institutionName = URLDecoder.decode(institutionName, "utf-8");
				city = URLDecoder.decode(city, "utf-8");
				EjlVerifyCode vCode = ejlComVerifyCodeServiceImpl.getEffectiveVerifyCode(phoneNumber, 3);
				if(vCode==null||!vCode.getVerifyCode().equals(verificationCode)){
					throw new BizException(ResultCode.VERIFICATIONCODE_INVALID.getCode());
				}
				
				EfOrg efOrg = new EfOrg();
				efOrg.setCity(city);
				efOrg.setUkey(key);
				efOrg.setName(institutionName);
				efOrg.setScale(institutionScale);
				efOrg.setPhone(phoneNumber);
				efOrg.setStatus(1);
				iKey = efOrgServiceImpl.register(efOrg);
				map.put("resultCode", "0");
				map.put("institutionKey", iKey);
				
				EfKey efKey = new EfKey();
				efKey.setUkey(key);
				Context ctx = new Context();
				ctx.set("userId", -1);
				efKey = efKeyService.selectOneObjByAttribute(ctx, efKey);
				
				EfKey entity = new EfKey();
				entity.setUkey(iKey);
				entity.setCustomerId(efKey.getCustomerId());
				entity.setRemark("机构key");
				entity.setCreatorId(-1l);
				entity.setCreateTime(DateUtils.currentDate());
				efKeyService.save(ctx, entity);
				redisClient.set(AUTH_KEY_+iKey, efKey.getCustomerId()+"");
				
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				map.put("resultCode", e.getCode()+"");
				map.put("errMsg", resultCode.getMsg());
			}catch(Exception e1){
				map.put("resultCode", "-1");
				map.put("errMsg", "unknown error.");
				log.error(e1.getMessage());
			}
			return map;
		}
}
