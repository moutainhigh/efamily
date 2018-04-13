package com.winterframework.efamily.api.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EfOrg;

public interface IEfOrgService{
	public String register(EfOrg entity) throws BizException;
}
