package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.entity.EjlUserFriend;

@Repository("ejlUserFriendDaoImpl")
public class EjlUserFriendDaoImpl extends EjlComUserFriendDaoImpl<EjlUserFriend> implements IEjlUserFriendDao {
	
	@Override
	public int updateByUserIdAndFriendId(EjlUserFriend ejlUserFriend) {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByUserIdAndFriendId"), ejlUserFriend);
	}
	
	
	
}
