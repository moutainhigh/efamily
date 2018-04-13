package com.winterframework.efamily.dispatch.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dispatch.exception.DispatchException;
import com.winterframework.efamily.dispatch.model.ResultCode;
import com.winterframework.efamily.dispatch.model.ServerResult;
import com.winterframework.efamily.dispatch.service.IDispatchService;
 

/**
 * 服务器分发
 * @ClassName
 * @Description
 * @author ibm
 * 2016年12月1日
 */
@Controller("dispatchController")
@RequestMapping("/server")
public class DispatchController { 
	private static final Logger log = LoggerFactory.getLogger(DispatchController.class);
	
	@Resource(name="dispatchServiceImpl")
	protected IDispatchService dispatchService;
	
	@RequestMapping("/getServer")
	@ResponseBody
	public Object getServer(HttpServletRequest request, HttpServletResponse response){
		ServerResult result=new ServerResult();
		try{
			String key=request.getParameter("key");
			if(StringUtils.isEmpty(key)){
				throw new DispatchException(ResultCode.PARAM_EMPTY);
			}
			if(!key.equals("11111111222222223333333344444444")){
				throw new DispatchException(ResultCode.PARAM_INVALID);
			}
			String ip=request.getRemoteAddr();
			String server=dispatchService.getServer(ip);
			if(null!=server){
				result.setResultCode(ResultCode.OK.getCode());
				result.setServer(server);
			}else{
				throw new DispatchException(ResultCode.SERVER_NA);
			}
		}catch(DispatchException e){
			log.error("get server error.code="+e.getCode()+"  message="+e.getMessage(),e);
			result.setResultCode(e.getCode());
			result.setErrMsg(e.getMessage());
		}catch(Exception e){
			log.error("get server error.message="+e.getMessage(),e);
			result.setResultCode(ResultCode.UNKNOWN.getCode());
			result.setErrMsg(ResultCode.UNKNOWN.getMsg());
		}
		return result;
	}
}
