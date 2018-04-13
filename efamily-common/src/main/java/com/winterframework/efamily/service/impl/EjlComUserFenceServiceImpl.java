package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserFenceDao;
import com.winterframework.efamily.entity.EjlUserFence;
import com.winterframework.efamily.service.IEjlComUserFenceService;

@Service("ejlComUserFenceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserFenceServiceImpl extends BaseServiceImpl<IEjlComUserFenceDao,EjlUserFence> implements IEjlComUserFenceService {

	
	@Resource(name="ejlComUserFenceDaoImpl")
	private IEjlComUserFenceDao ejlComUserFenceDaoImpl;
	
	@Override
	protected IEjlComUserFenceDao getEntityDao() {
		return ejlComUserFenceDaoImpl;
	}
	
	public void deleteByfenceId(Long fenceId,Long updateId) throws BizException{
		ejlComUserFenceDaoImpl.deleteByfenceId(fenceId,updateId);
	}
	
	public EjlUserFence getEjlUserFenceBy(Long fenceId,Long userId) throws BizException{
		EjlUserFence ejlUserFence = new EjlUserFence();
		ejlUserFence.setFenceId(fenceId);
		ejlUserFence.setUserId(userId);
		return ejlComUserFenceDaoImpl.selectOneObjByAttribute(ejlUserFence);
	}

	public List<EjlUserFence> getAllUserFenceInFence(Long orgId){
		return ejlComUserFenceDaoImpl.getAllUserFenceInFence(orgId);
	}
}
