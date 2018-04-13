package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserFriendDao;
import com.winterframework.efamily.entity.EjlUserFriend;
import com.winterframework.efamily.service.IEjlComUserFriendService;

@Service("ejlComUserFriendServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserFriendServiceImpl extends BaseServiceImpl<IEjlComUserFriendDao,EjlUserFriend> implements IEjlComUserFriendService {

	
	@Resource(name="ejlComUserFriendDaoImpl")
	private IEjlComUserFriendDao ejlComUserFriendDaoImpl;
	
	@Override
	protected IEjlComUserFriendDao getEntityDao() {
		return ejlComUserFriendDaoImpl;
	}

}
