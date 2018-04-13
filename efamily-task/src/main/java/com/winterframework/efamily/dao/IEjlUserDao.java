package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlUser;

public interface IEjlUserDao extends IEjlComUserDao {

	/*EjlUser getUserByPhone(String phone);*/
	
	public EjlUser getUserByUserId(Long userId) throws BizException;
	public List<EjlUser> getEjlUserByAttribute(EjlUser user) throws BizException;
	public List<EjlUser> getChatRoomUserByRoomId(Long roomId) throws BizException;
	/*public List<EjlUser> getChatRoomUserByRoomId(Long roomId) throws BizException;*/
/*	public List<FriendListResponse> getEjlFriendListByUserId(Long userId) throws BizException ;
	public List<FriendListResponse> getEjlFamilyListByUserId(Long userId) throws BizException ;*/
	/*public List<UserStruc> getEjlFamilyListByFamilyIdAndType(EjlUser user) throws BizException ;*/
	/**
	 * 功能：根据用户ID，查找相关家庭数量
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	/*public Long getFamilyCountByUserId(Long userId) throws BizException;
	
	public List<ChatGroupListResponse> getGroupChatListByUserId(Long userId) throws BizException ;
	
	public List<EjlUser> getUserByAddressBookPhone(List<String> phone) throws BizException ;*/
	public Long getAppConnectNumber(List<String> idList);
	public Long getDeviceConnectNumber(List<String> idList);
}
