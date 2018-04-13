package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserBarrierDao;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.service.IEjlComUserBarrierService;

@Service("ejlComUserBarrierServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserBarrierServiceImpl extends BaseServiceImpl<IEjlComUserBarrierDao,EjlUserBarrier> implements IEjlComUserBarrierService {
 
	@Resource(name="ejlComUserBarrierDaoImpl")
	private IEjlComUserBarrierDao ejlComUserBarrierDaoImpl;
	@Override
	protected IEjlComUserBarrierDao getEntityDao() { 
		return ejlComUserBarrierDaoImpl;
	}
	
	public void deleteByFenceId(Long fenceId,Long updateId){
		ejlComUserBarrierDaoImpl.deleteByFenceId( fenceId, updateId);
	}
	
}
