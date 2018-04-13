package com.winterframework.efamily.base.aop.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * HTTP请求上下文
 * @ClassName
 * @Description
 * @author ibm
 * 2015年11月13日
 */
@Target({PARAMETER })
@Retention(RUNTIME)
public @interface RequestCxt {
}
