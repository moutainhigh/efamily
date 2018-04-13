package com.winterframework.efamily.api.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.winterframework.efamily.api.enums.ResultCode;

import com.winterframework.efamily.api.util.ValidaUtil;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;

import com.winterframework.efamily.base.utils.JsonUtils;

import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.efamily.dto.UserInfoReq;
import com.winterframework.efamily.utils.HttpUtil;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Controller("institutionUserController")
@RequestMapping("/api")
public class InstitutionUserController {

	private static final Logger log = LoggerFactory.getLogger(InstitutionUserController.class);

	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.userInfo")
	private String userInfoPath;
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;

	@RequestMapping(value = "userInfo", method = RequestMethod.POST)
	@ResponseBody
	protected Object userInfo(@RequestBody ComOrgReq<List<UserInfoReq>> req,String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			List<UserInfoReq> data = (List<UserInfoReq>)req.getData();
			if (data == null) {
				throw new BizException(ResultCode.PARAM_EMPTY.getCode());
			}
			for(UserInfoReq userInfoReq:data){
				if(userInfoReq.getAge()==null||userInfoReq.getDevicePhone()==null||userInfoReq.getGuardianPhone()==null||userInfoReq.getGuardianRelation()==null||userInfoReq.getHeight()==null
						||userInfoReq.getImei()==null||userInfoReq.getName()==null||userInfoReq.getOperType()==null||userInfoReq.getSex()==null||userInfoReq.getWeight()==null){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				
				if(userInfoReq.getImei().length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				
				if(!ValidaUtil.isMobile(userInfoReq.getGuardianPhone())||!ValidaUtil.isMobile(userInfoReq.getDevicePhone())){
					throw new BizException(ResultCode.PHONE_INVALID.getCode());
				}
				if(userInfoReq.getGuardianPhone().equals(userInfoReq.getDevicePhone())){
					throw new BizException(ResultCode.PHONE_INVALID.getCode());
				}
			}
			Context ctx = new Context();
			ctx.set("orgKey", key);
			ctx.set("userId", -1);
			Response<Object> bizRes = httpUtil.http(serverUrl,userInfoPath, ctx,data, Object.class);
			map.put("resultCode", bizRes.getStatus().getCode());
			map.put("errMsg", ResultCode.getResultCode(bizRes.getStatus().getCode()));
		} catch (BizException e) {
			ResultCode resultCode = ResultCode.getResultCode(e.getCode());
			log.error(e.getCode() + " " + resultCode.getMsg(), e);
			map.put("resultCode", e.getCode() + "");
			map.put("errMsg", resultCode.getMsg());
		} catch (Exception e1) {
			map.put("resultCode", "-1");
			map.put("errMsg", "unknown error.");
			log.error(e1.getMessage());
		}
		return map;
	}
}
