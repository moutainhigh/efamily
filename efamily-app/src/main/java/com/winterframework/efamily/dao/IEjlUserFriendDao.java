 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.dao;

 import com.winterframework.efamily.entity.EjlUserFriend;

 
public interface IEjlUserFriendDao  extends IEjlComUserFriendDao {

	
	public int updateByUserIdAndFriendId(EjlUserFriend ejlUserFriend);
	
}