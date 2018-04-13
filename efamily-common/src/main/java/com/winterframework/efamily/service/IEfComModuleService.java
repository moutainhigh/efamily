package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfModule;

public interface IEfComModuleService extends IBaseService<EfModule> {
	public List<EfModule> getEfModuleListByDevice(Long deviceId)
			throws BizException ;
	
	
	public List<EfModule> getEfModuleList()
			throws BizException ;
}
