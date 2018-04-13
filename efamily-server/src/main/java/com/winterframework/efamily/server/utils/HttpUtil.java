/**   
* @Title: FormulaUtils.java 
* @Package com.winterframework.efamily.server.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-7 上午9:49:41 
* @version V1.0   
*/
package com.winterframework.efamily.server.utils;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.server.exception.ServerException;

@Component("httpUtil")
public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	@Resource(name="httpClientImpl")
	private IHttpClient httpClient;
	
	public <T,R> Response<T> http(String serverUrl,String action,R requestData,Class<?> clazz) throws ServerException{
		try{
			return httpClient.invokeHttp(serverUrl+action, requestData,clazz);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new ServerException(StatusCode.HTTP.getValue());
		}
	}
	public <T,R> Response<T> http(String serverUrl,String action,Context ctx,R requestData,Class<?> clazz) throws ServerException{
		try{ 
			return httpClient.invokeHttp(serverUrl+action,ctx,requestData,clazz);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new ServerException(StatusCode.HTTP.getValue());
		}
	}
	@SuppressWarnings("rawtypes")
	public <R> Response http(String serverUrl,String action,R requestData) throws ServerException{
		try{
			return httpClient.invokeHttpWithoutResultType(serverUrl+action, requestData);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new ServerException(StatusCode.HTTP.getValue());
		}
	}
}
