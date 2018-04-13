package com.winterframework.efamily.server.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;

@Component
@Aspect
public class AuthAdvice {

	private final static Logger log = LoggerFactory.getLogger(AuthAdvice.class);
 
	@Around("execution(* com.winterframework.efamily.server.handler..*.*(..))")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) (pjp.getSignature()); 
		Method method = ms.getMethod(); 
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			for (int j = 0; j < annotations[i].length; j++) {
				if (annotations[i][j].annotationType().getName().equals(Auth.class.getName())) {
					FmlRequest req = (FmlRequest) pjp.getArgs()[0];
					String token = req.getToken();
					if(null==TokenManager.getTokenContext(token)){
						FmlResponse res= new FmlResponse(req);
						log.info("AuthAdvice 用户未登录：req = "+req.toString());
						res.setStatus(StatusCode.UN_LOGIN.getValue());
						return res;
					}
				}
			}
		}
		return pjp.proceed();
	}
}
