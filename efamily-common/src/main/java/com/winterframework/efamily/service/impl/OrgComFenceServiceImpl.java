package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComFenceDao;
import com.winterframework.efamily.entity.OrgFence;
import com.winterframework.efamily.service.IOrgComFenceService;

@Service("orgComFenceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComFenceServiceImpl  extends BaseServiceImpl<IOrgComFenceDao,OrgFence> implements IOrgComFenceService {
	@Resource(name="orgComFenceDaoImpl")
	private IOrgComFenceDao dao;
	@Override
	protected IOrgComFenceDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public void deleteOrgFence(Context ctx,Long fenceId) throws BizException{
		OrgFence orgFence = getEntityDao().getById(fenceId);
		orgFence.setStatus(0);
		save(ctx, orgFence);
	}
	
}
