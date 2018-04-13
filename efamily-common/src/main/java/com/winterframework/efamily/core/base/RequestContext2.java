package com.winterframework.efamily.core.base;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.utils.CharsetFactory;
import com.winterframework.modules.web.util.IUser;
 
@SuppressWarnings("unused")
public class RequestContext2 {
	private final static Logger log = LoggerFactory.getLogger(RequestContext2.class); 
	private final static ThreadLocal<RequestContext2> contexts = new ThreadLocal<RequestContext2>() {};  
	private ServletContext context;
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String,Object> attributes =new ConcurrentHashMap<String,Object>();
  
	public static RequestContext2 init(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) {
		RequestContext2 rc = new RequestContext2(); 
		rc.context = ctx;
		rc.request = req;
		rc.response = res;
		rc.response.setCharacterEncoding(CharsetFactory.CharsetEnum.UTF8.getValue());
		rc.session = req.getSession(false); 
		contexts.set(rc);
		return rc;
	} 
	/** 
	 * 获取当前请求的上下文 
	 * @return 
	 */
	public static RequestContext2 get() {
		return contexts.get();
	}
	public void set(String key,Object value){
		attributes.put(key, value);
	}
	public Object get(String key){
		return attributes.get(key);
	}
	public void destroy() { 
		this.context = null;
		this.request = null;
		this.response = null;
		this.session = null; 
		this.attributes=null;
		contexts.remove();
	}
	public void closeCache() {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0L);
	} 

	public ServletContext context() {
		return context;
	}

	public HttpSession session() {
		return session;
	}

	public HttpSession session(boolean create) {
		return (session == null && create) ? (session = request.getSession()) : session;
	}

	public Object sessionAttr(String attr) {
		HttpSession ssn = session();
		return (ssn != null) ? ssn.getAttribute(attr) : null;
	} 
}