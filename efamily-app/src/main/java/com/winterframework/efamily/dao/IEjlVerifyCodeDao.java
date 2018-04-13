package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlVerifyCode;

public interface IEjlVerifyCodeDao extends IEjlComVerifyCodeDao {
	public List<EjlVerifyCode> getInvitedUserByFamily(Long familyId) throws BizException;;
	public EjlVerifyCode getVerifyCode(String phoneNumber, String verifyCode);
	
	
}
