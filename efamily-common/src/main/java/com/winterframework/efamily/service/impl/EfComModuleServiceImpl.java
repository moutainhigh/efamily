package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComModuleDao;
import com.winterframework.efamily.entity.EfModule;
import com.winterframework.efamily.service.IEfComModuleService;

@Service("efComModuleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComModuleServiceImpl extends BaseServiceImpl<IEfComModuleDao,EfModule> implements IEfComModuleService{

	@Resource(name="efComModuleDaoImpl")
	private IEfComModuleDao dao;
	@Override
	public List<EfModule> getEfModuleListByDevice(Long deviceId)
			throws BizException {
		try {
			return dao.getEfModuleListByDevice(deviceId);
		} catch (Exception e) {
			throw new BizException();
		}
	}

	@Override
	protected IEfComModuleDao getEntityDao() {
		return dao;
	}

	@Override
	public List<EfModule> getEfModuleList() throws BizException {
		return dao.getEfModuleList();
	}

}
