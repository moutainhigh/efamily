package com.winterframework.efamily.service;


import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlVerifyCode;

public interface IEjlComVerifyCodeService extends IBaseService<EjlVerifyCode> {
	public EjlVerifyCode getEffectiveVerifyCode(String phoneNumber,int type);
}
