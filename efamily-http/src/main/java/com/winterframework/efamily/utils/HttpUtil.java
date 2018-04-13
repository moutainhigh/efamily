/**   
* @Title: FormulaUtils.java 
* @Package com.winterframework.efamily.server.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-7 上午9:49:41 
* @version V1.0   
*/
package com.winterframework.efamily.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;

@Component("httpUtil")
public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	@Resource(name="httpClientImpl")
	private IHttpClient httpClient;
	
	public <T,R> Response<T> http(String serverUrl,String action,R requestData,Class<?> clazz) throws BizException{
		try{
			return httpClient.invokeHttp(serverUrl+action, requestData,clazz);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.HTTP.getValue());
		}
	}
	public <T,R> Response<T> http(String serverUrl,String action,Context ctx,R requestData,Class<?> clazz) throws BizException{
		try{ 
			return httpClient.invokeHttp(serverUrl+action,ctx,requestData,clazz);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.HTTP.getValue());
		}
	}
	@SuppressWarnings("rawtypes")
	public <R> Response http(String serverUrl,String action,R requestData) throws BizException{
		try{
			return httpClient.invokeHttpWithoutResultType(serverUrl+action, requestData);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.HTTP.getValue());
		}
	}
	public String httpGet(String url,String param) throws BizException{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try{
			HttpGet httpget = new HttpGet(url+"?"+param);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity(); 
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			return result;
		}catch(Exception e){
			log.error("http get error.url="+url+" param="+param);
			throw new BizException(StatusCode.HTTP.getValue());
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	public 	String httpPost(String url,Map<String,String> paramMap) throws BizException{
		DefaultHttpClient httpclient1 = new DefaultHttpClient();
		try{
			HttpPost  httppost = new HttpPost(url);
			List<NameValuePair> params=new ArrayList<NameValuePair>();  
			for(Map.Entry<String,String> entry :paramMap.entrySet()){
				params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
			httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
			HttpResponse response=httpclient1.execute(httppost);  
			HttpEntity entity = response.getEntity(); 
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			return result;
		}catch(Exception e){
			log.error("http post error.url="+url+" param="+paramMap.toString());
			throw new BizException(StatusCode.HTTP.getValue());
		}finally{
			httpclient1.getConnectionManager().shutdown();
		}
	}
}
