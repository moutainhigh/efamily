package com.winterframework.efamily.api.web.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.winterframework.efamily.api.service.ILoadCacheFromDatabaseService;
import com.winterframework.efamily.base.exception.BizException;

public class LoadCacheFromDatabaseProcessor implements BeanPostProcessor {

	
	private static final Logger log = LoggerFactory.getLogger(LoadCacheFromDatabaseProcessor.class);
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof ILoadCacheFromDatabaseService){ 
			//判断Object是否是该类的bean   
			try {
				log.info("LoadCacheFromDatabaseProcessor START : 加载权限信息到缓存 ");
				((ILoadCacheFromDatabaseService) bean).loadAuthAndFrequencyFromData();
				log.info("LoadCacheFromDatabaseProcessor END   : 加载权限信息到缓存 ");
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
		
		return bean;
	}

}
