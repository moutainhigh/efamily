package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComVerifyCodeDao;
import com.winterframework.efamily.entity.EjlVerifyCode;
import com.winterframework.efamily.service.IEjlComVerifyCodeService;

@Service("ejlComVerifyCodeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComVerifyCodeServiceImpl extends BaseServiceImpl<IEjlComVerifyCodeDao, EjlVerifyCode> implements
		IEjlComVerifyCodeService {

	@Resource(name = "ejlComVerifyCodeDaoImpl")
	private IEjlComVerifyCodeDao ejlComVerifyCodeDaoImpl;

	@Override
	protected IEjlComVerifyCodeDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlComVerifyCodeDaoImpl;
	}
	
	@Override
	public EjlVerifyCode getEffectiveVerifyCode(String phoneNumber,int type) {
		return ejlComVerifyCodeDaoImpl.getEffectiveVerifyCode(phoneNumber,type);
		
	}
}
