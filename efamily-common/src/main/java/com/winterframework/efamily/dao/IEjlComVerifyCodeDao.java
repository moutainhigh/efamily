package com.winterframework.efamily.dao;


import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlVerifyCode;

public interface IEjlComVerifyCodeDao extends IBaseDao<EjlVerifyCode> {
	public EjlVerifyCode getEffectiveVerifyCode(String phoneNumber,int type);
}
