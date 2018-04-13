package com.winterframework.efamily.api.interceptor;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.api.service.IApiAccessManageService;
import com.winterframework.efamily.base.model.Context;

@Component
@Aspect
public class ApiAuthorization {

	private final static Logger log = LoggerFactory.getLogger(ApiAuthorization.class);
	@Resource(name = "apiAccessManageServiceImpl")
	private IApiAccessManageService apiAccessManageService;
	
	@Around("@annotation(com.winterframework.efamily.api.interceptor.annatation.ApiAuthAnnotation)")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		Context ctx=(Context)pjp.getArgs()[0];
		String key=(String)ctx.get("key");
		String url=(String)ctx.get("url");
		apiAccessManageService.apiAccessManage(key, url);
		return pjp.proceed();
	}
}
