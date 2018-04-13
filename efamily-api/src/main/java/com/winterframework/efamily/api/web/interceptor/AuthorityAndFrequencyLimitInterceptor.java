package com.winterframework.efamily.api.web.interceptor;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;  

import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IEfKeyIpService;
import com.winterframework.efamily.api.service.ILoadCacheFromDatabaseService;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.modules.utils.SpringContextHolder;


public class AuthorityAndFrequencyLimitInterceptor implements HandlerInterceptor {  

	private final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	
	private final static String KEY ="key";
	@Resource(name = "loadCacheFromDatabaseServiceImpl")
	private ILoadCacheFromDatabaseService loadCacheFromDatabaseServiceImpl;

	@Resource(name = "efKeyIpServiceImpl")
	private IEfKeyIpService efKeyIpServiceImpl;
	

	private static final Logger log = LoggerFactory.getLogger(AuthorityAndFrequencyLimitInterceptor.class);

    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	log.debug("[preHandle]**********************  权限和频率验证开始  START   ****************************************");
    	boolean flag = false;
        try{
        	String key = getKeyFromRequest(request);
            String ip = request.getRemoteAddr();
            String requestUri = request.getRequestURI();
            //***  检查权限和频率 
            ResultCode resultCode = loadCacheFromDatabaseServiceImpl.authAndFrequencyLimitCheck(key, requestUri, ip);
        	if(resultCode.getCode()==ResultCode.OK.getCode()){
        		//******** 记录IP ************  
                Context ctx = new Context();
                ctx.set("userId", Long.valueOf(redisClient.get("auth_key_"+key)));
            	efKeyIpServiceImpl.saveOrUpdateKeyIpBy(ctx, key, ip);
            	flag = true;
        	}else{
        		//******** 返回错误码 *********
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("resultCode", resultCode.getCode());
				map.put("errMsg", resultCode.getMsg());
            	response.getOutputStream().write(JsonUtils.toJson(map).getBytes());
        	}
        }catch(Exception e){
        	 log.error("++++++++++++[  AuthorityAndFrequencyLimitInterceptor ：权限和频率检查时 出现异常  ]++++++++++++++++");
        	 e.printStackTrace();
        }
        log.debug("[preHandle]**********************  权限和频率验证开始  END    ********************************");
        return flag;  
    }  
    
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {  
            //AUTO
    }  


    @Override  
    public void afterCompletion(HttpServletRequest request,  HttpServletResponse response, Object handler, Exception ex)  
    throws Exception {  
        //AUTO
    }  
    
    /**
     * 
     * @param request
     * @return
     * @throws IOException
     */
    public String getKeyFromRequest(HttpServletRequest request) throws IOException{
    	String key = request.getParameter(KEY);
        try{
    	if(org.apache.commons.lang3.StringUtils.isBlank(key)){
    		List<String> listString = IOUtils.readLines(request.getInputStream());
    		String jsonStr = StringUtils.collectionToCommaDelimitedString(listString);
    		@SuppressWarnings("unchecked")
			ComOrgReq<Object> comOrgReq = JsonUtils.fromJson(jsonStr, ComOrgReq.class);
    		key = comOrgReq.getKey();
    	}
        }catch(IOException e){
        	e.printStackTrace();
        	log.error("获取KEY时出现异常");
        }
    	return key;
    }
      
}  