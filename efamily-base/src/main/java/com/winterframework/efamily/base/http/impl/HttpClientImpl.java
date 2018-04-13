package com.winterframework.efamily.base.http.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.efamily.base.http.HttpClientUtil;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;

 
/**
 * 用get方式发起一个http请求，结果为json格式，方法返回java对象。 
 * @ClassName
 * @Description
 * @author ibm
 * 2015年6月2日
 */
@Service("httpClientImpl")
//@Transactional(propagation=Propagation.NOT_SUPPORTED)	//不支持事务
public class HttpClientImpl implements IHttpClient {
	private static final Logger log = LoggerFactory.getLogger(HttpClientImpl.class);
	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.IHttpClient.http.IHttpJsonClient#invokeHttp(java.lang.String) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		Response<T> t = (Response<T>) this.invokeHttp(url, request, dataClass);
		return t;
	}

	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		request.setData(requestData);
		Response<T> t = this.invokeHttp(url, request, dataClass);
		return t;
	}     
	@Override
	public <T, R> Response<T> invokeHttp(String url,Context ctx,R requestData, Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		request.setCtx(ctx);
		request.setData(requestData);
		Response<T> t = this.invokeHttp(url, request, dataClass);
		return t;
	}   
	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.IHttpClient.http.IHttpJsonClient#invokeHttp(java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, TypeReference typeReference) throws Exception {
		Request<R> request = new Request<R>();  
		Response<T> t = (Response<T>) this.invokeHttp(url, request, typeReference);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.IHttpClient.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, TypeReference typeReference) throws Exception {
		Request<R> request = new Request<R>();
		request.setData(requestData);
		Response<T> t = this.invokeHttp(url, request, typeReference);
		return t;
	}
	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.IHttpClient.http.IHttpJsonClient#invokeHttp(java.lang.String) 
	*/
	@Override
	public Response invokeHttpWithoutResultType(String url) throws Exception {
		Request request = new Request();
		Response t = (Response) this.invokeHttp(url, request);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.IHttpClient.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object) 
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData) throws Exception {
		Request<R> request = new Request<R>();
		request.setData(requestData);
		Response t = this.invokeHttp(url, request);
		return t;
	}
	
	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param params
	* @return 
	* @see com.winterframework.firefrog.IHttpClient.http.IHttpJsonClient#invokeHttp(java.lang.String, com.winterframework.modules.web.jsonresult.Request) 
	*/
	private <T, R> Response<T> invokeHttp(String url, Request<R> request, Class... classType) throws Exception {
		return HttpClientUtil.postJsonObject(url, request, classType);
	}

	private <T, R> Response<T> invokeHttp(String url, Request<R> request, TypeReference typeReference) throws Exception {
		return HttpClientUtil.postJsonObject(url, request, typeReference);
	}

	private <R> Response invokeHttp(String url, Request<R> request) throws Exception {
		return HttpClientUtil.postJsonObject(url, request);
	}
	private <R> void printRequest(R requestData){
		Class clazz=requestData.getClass();
		StringBuilder sb=new StringBuilder();
		sb.append(clazz.getSimpleName()).append("[");
		Class superclass=clazz.getSuperclass();
		do{
			for(Field field:superclass.getDeclaredFields()){
				sb.append(field.getName()).append("=");
				try{
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz); 
		            Method method = pd.getReadMethod();
		            sb.append(method.invoke(this, new Object[] {})).append(",");
				}catch(Exception e){
					log.error("print request error:",e);
				}
			}
			superclass=superclass.getSuperclass();
		}while (null!=superclass);
		for(Field field:clazz.getDeclaredFields()){
			sb.append(field.getName()).append("=");
			try{
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz); 
				Method method= pd.getReadMethod();
	            sb.append(method.invoke(requestData, new Object[] {})).append(",");
			}catch(Exception e){
				log.error("print request error:",e);
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");

		log.info(sb.toString());
	}
}
