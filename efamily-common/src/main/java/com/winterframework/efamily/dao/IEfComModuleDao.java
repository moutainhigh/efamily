package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfModule;

public interface IEfComModuleDao extends IBaseDao<EfModule>{
	public List<EfModule> getEfModuleListByDevice(Long deviceId) throws Exception;
	public List<EfModule> getEfModuleList() throws BizException;
}
