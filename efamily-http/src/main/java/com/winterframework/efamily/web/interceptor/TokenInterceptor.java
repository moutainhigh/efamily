package com.winterframework.efamily.web.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;  
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.result.BaseHttpResult;
import com.winterframework.efamily.utils.TokenManager;
public class TokenInterceptor implements HandlerInterceptor {  
	private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	log.debug("[preHandle]**********************  token验证开始  START   ****************************************");
    	boolean flag = false;
        try{
        	String token = request.getParameter("token");
        	BaseHttpResult result = new BaseHttpResult();
        	if(null==TokenManager.getTokenContext(token)){
        		result.setResultCode(StatusCode.UN_LOGIN.getValue());
        		response.getOutputStream().write(JsonUtils.toJson(result).getBytes());
        	}else{
        		flag=true;
        	}
        }catch(Exception e){
        	 log.error("++++++++++++[  TokenInterceptor ：token验证 出现异常  ]++++++++++++++++");
        	 e.printStackTrace();
        }
        log.debug("[preHandle]**********************  token验证开始  END    ********************************");
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
}  