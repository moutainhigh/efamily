package com.winterframework.efamily.base.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.aop.annotation.RequestCxt;
import com.winterframework.efamily.base.http.RequestContext;
import com.winterframework.efamily.base.model.Request;

@Component("requestAdvice")
@Aspect
public class RequestAdvice {

	private static final Logger logger = LoggerFactory.getLogger(RequestAdvice.class);
 
	@Around("execution(* com.winterframework.efamily..web..*Controller.*(..))")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) (pjp.getSignature());
		Method method = ms.getMethod();
		doProcess(pjp, method);
		return pjp.proceed();
	}

	@SuppressWarnings("rawtypes")
	private void doProcess(ProceedingJoinPoint pjp, Method method) throws IllegalArgumentException,
			IllegalAccessException, Exception {
		Annotation[][] annotations = method.getParameterAnnotations();
		if (annotations.length > 0) {
			for (int i = 0; i < annotations.length; i++) {
				for (int j = 0; j < annotations[i].length; j++) {
					if (annotations[i][j].annotationType().getName().equals(RequestCxt.class.getName())) {
						RequestContext.init((Request) pjp.getArgs()[i]);
					}
				}
			}
		}

	}
}
