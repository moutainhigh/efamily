package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlVerifyCode;

public interface IEjlVerifyCodeService extends IBaseService<EjlVerifyCode> {

	public List<EjlVerifyCode> getInvitedUserByFamily(Long familyId) throws BizException;

	public EjlVerifyCode getVerifyCode(String phoneNumber, String verifyCode);

	public Integer getVerifyCode(Context ctx,String phoneNumber) throws BizException;
	
	public EjlVerifyCode getEffectiveVerifyCode(String phoneNumber,int type);

}
