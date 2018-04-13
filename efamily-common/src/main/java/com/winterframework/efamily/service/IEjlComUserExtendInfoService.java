package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlUserExtendInfo;

public interface IEjlComUserExtendInfoService extends IBaseService<EjlUserExtendInfo>{
	EjlUserExtendInfo getByUserId(Long userId) throws BizException;
	public int update(EjlUserExtendInfo entity) throws BizException;
	
	public List<String> getAllCityName() throws Exception;
}
