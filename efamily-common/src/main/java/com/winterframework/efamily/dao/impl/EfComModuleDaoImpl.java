package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComModuleDao;
import com.winterframework.efamily.entity.EfModule;
@Repository("efComModuleDaoImpl")
public class EfComModuleDaoImpl<E extends EfModule> extends BaseDaoImpl<EfModule> implements IEfComModuleDao{

	@Override
	public List<EfModule> getEfModuleListByDevice(Long deviceId)
			throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEfModuleListByDevice"), deviceId);
	}

	@Override
	public List<EfModule> getEfModuleList() throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEfModuleList"));
	}
	
	
	
	
}
