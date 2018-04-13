package com.winterframework.efamily.service;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLocationOrigin;


public interface IEfComLocationOriginService extends IBaseService<EfLocationOrigin> {
	public Integer updateStatus(Long id,Integer status) throws Exception;
}
