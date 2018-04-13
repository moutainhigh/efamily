package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.PhoneAddressBookStruc;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserFriend;

public interface IEjlUserFriendService  extends IBaseService<EjlUserFriend>{

	public void manageUserFriend(Context ctx,Long userId,Long customerId,Long oprType) throws Exception;
	
	public List<EjlUser> systemAddUserFriend(Context ctx,Long userId,PhoneAddressBookStruc[] addressBook) throws Exception;
	
	public void updateUserFriend(Context ctx,Long userId,Long friendId,String remarkName) throws Exception;
}
