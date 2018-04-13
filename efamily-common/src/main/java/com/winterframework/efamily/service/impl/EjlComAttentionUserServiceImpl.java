package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComAttentionUserDao;
import com.winterframework.efamily.entity.EjlAttentionUser;
import com.winterframework.efamily.service.IEjlComAttentionUserService;


@Service("ejlComAttentionUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComAttentionUserServiceImpl extends BaseServiceImpl<IEjlComAttentionUserDao,EjlAttentionUser> implements IEjlComAttentionUserService {
 
	@Resource(name="ejlComAttentionUserDaoImpl")
	private IEjlComAttentionUserDao ejlComAttentionUserDaoImpl;
	@Override
	protected IEjlComAttentionUserDao getEntityDao() { 
		return ejlComAttentionUserDaoImpl;
	}
			
}
