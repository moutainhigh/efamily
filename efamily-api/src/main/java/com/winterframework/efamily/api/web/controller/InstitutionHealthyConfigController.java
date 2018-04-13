package com.winterframework.efamily.api.web.controller;
import java.util.ArrayList;
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
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.efamily.dto.HealthyRequest;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("institutionHealthyConfigController")
@RequestMapping("/api")
public class InstitutionHealthyConfigController {

	private static final Logger log = LoggerFactory.getLogger(InstitutionHealthyConfigController.class);

	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.batchSaveHealthyUserConfig")
	private String urlPath;
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;

	@RequestMapping(value = "healthySetting", method = RequestMethod.POST)
	@ResponseBody
	protected Object healthySetting(@RequestBody ComOrgReq<List<HealthyRequest>> req,String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JsonMapper om=new JsonMapper();
		try {
			List<HealthyRequest> data = (List<HealthyRequest>)req.getData();
			//String key = req.getKey();
			
			if (data == null) {
				throw new BizException(ResultCode.PARAM_EMPTY.getCode());
			}
			for(HealthyRequest healthyRequest:data){
				if(healthyRequest.getImei()==null){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				if(healthyRequest.getImei().length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
			}
			Context ctx = new Context();
			ctx.set("orgKey", key);
			ctx.set("userId", -1);
			Response<Object> bizRes = httpUtil.http(serverUrl,urlPath, ctx,data, Object.class);
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
