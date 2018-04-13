package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserFriendDao;
import com.winterframework.efamily.entity.EjlUserFriend;

@Repository("ejlComUserFriendDaoImpl")
public class EjlComUserFriendDaoImpl<E extends EjlUserFriend> extends BaseDaoImpl<EjlUserFriend> implements IEjlComUserFriendDao {
	
}
