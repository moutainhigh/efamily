package com.winterframework.efamily.base.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Request;
 
public class RequestContext {
	private final static Logger log = LoggerFactory.getLogger(RequestContext.class); 
	private final static ThreadLocal<Context> contexts = new ThreadLocal<Context>() {};  
	@SuppressWarnings("rawtypes")
	public static void init(Request request) {
		Context ctx=request.getCtx();
		contexts.set(ctx);
	}
	/** 
	 * 获取当前请求的上下文 
	 * @return 
	 */
	public static Context get() {
		return contexts.get();
	}
}