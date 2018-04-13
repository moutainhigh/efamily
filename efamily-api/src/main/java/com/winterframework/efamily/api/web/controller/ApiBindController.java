package com.winterframework.efamily.api.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.api.dto.BindStruc;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.result.ApiBindResult;
import com.winterframework.efamily.api.service.IApiBindService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EjlDevice;

	
	/**
	 * 设备绑定解绑api
	 * @ClassName
	 * @Description
	 * @author ibm
	 * 2016年6月23日
	 */
	@Controller("apiBindController")
	@RequestMapping("/api")
	public class ApiBindController{

		@Resource(name = "apiBindServiceImpl")
		private IApiBindService apiBindService; 
		
		private static final Logger log = LoggerFactory.getLogger(ApiBindController.class);

		@RequestMapping("/bindList")
		@ResponseBody
		protected String bind(HttpServletRequest request) throws Exception {
			ApiBindResult result=new ApiBindResult();
			try{
				String key = request.getParameter("key");
				String typeStr = request.getParameter("type");
				String bizTimeStr = request.getParameter("time");
				String apiUrl=request.getRequestURI();
				if(null==key || null==bizTimeStr|| null==typeStr){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				if(bizTimeStr.length()!=13){
					throw new BizException(ResultCode.TIME_INVALID.getCode());
				}
				Date bizTime=null;
				Integer type=null;
				try{
					bizTime=DateUtils.getDate(Long.valueOf(bizTimeStr));
					type=Integer.valueOf(typeStr);
				}catch(NumberFormatException e){
					log.error("number invalid.",e);
					throw new BizException(ResultCode.NUMBER_INVALID.getCode());
				}
				
				//拦截器 校验key url 和key ids权限  访问次数 访问频率 
				Context ctx=new Context();
				ctx.set("key", key);
				ctx.set("url", apiUrl);
				List<EjlDevice> deviceList=apiBindService.getBindListByKeyAndTime(ctx,type,bizTime);
				 
				if(null== deviceList || deviceList.size()==0){
					result.setResultCode(ResultCode.DATA_NOT_EXIST.getCode());
					result.setErrMsg(ResultCode.DATA_NOT_EXIST.getMsg());
				}else{
					List<BindStruc> bindList=new ArrayList<BindStruc>();
					for(EjlDevice device:deviceList){
						BindStruc bind=new BindStruc();
						bind.setImei(device.getCode());
						bind.setTime(device.getUpdateTime().getTime());
						bindList.add(bind);
					}
					result.setResultCode(ResultCode.OK.getCode());
					result.setBindList(bindList);
				}
			}catch(BizException e){
				ResultCode resultCode=ResultCode.getResultCode(e.getCode());
				log.error(e.getCode()+" "+resultCode.getMsg(),e);
				result.setResultCode(e.getCode());
				result.setErrMsg(resultCode.getMsg());
			}catch(Exception e){
				log.error(e.getMessage(),e);
				result.setResultCode(ResultCode.UNKNOWN.getCode());
				result.setErrMsg(ResultCode.UNKNOWN.getMsg());
			}
			return JsonUtils.toJson(result);
		}
}
