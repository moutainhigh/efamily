package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.ChatGroupListResponse;
import com.winterframework.efamily.dto.ChatRoomUserNotify;
import com.winterframework.efamily.dto.FamilyAttentionUserStruc;
import com.winterframework.efamily.dto.FriendListResponse;
import com.winterframework.efamily.dto.HealthlyUserRequest;
import com.winterframework.efamily.dto.HealthyProfileStruc;
import com.winterframework.efamily.dto.UserDeviceInfo;
import com.winterframework.efamily.dto.UserStruc;
import com.winterframework.efamily.entity.EjlUser;

public interface IEjlUserDao extends IEjlComUserDao {

	EjlUser getUserByPhone(String phone);
	public List<HealthyProfileStruc> getEjlUsersByFamilyId(Long familyId) throws BizException;
	public EjlUser getUserByUserId(Long userId) throws BizException;
	public List<EjlUser> getEjlUserByAttribute(EjlUser user) throws BizException;
	public List<EjlUser> getChatRoomUserByRoomId(Long roomId) throws BizException;
	public List<FriendListResponse> getEjlFriendListByUserId(EjlUser user) throws BizException ;
	public List<FriendListResponse> getEjlFamilyListByUserId(EjlUser user) throws BizException ;
	public List<UserStruc> getEjlFamilyListByFamilyIdAndType(EjlUser user) throws BizException ;
	/**
	 * 功能：根据用户ID，查找相关家庭数量
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public Long getFamilyCountByUserId(Long userId) throws BizException;
	
	public List<ChatGroupListResponse> getGroupChatListByUserId(Long userId) throws BizException ;
	
	public List<EjlUser> getUserByAddressBookPhone(List<String> phone) throws BizException ;
	
	public List<HealthlyUserRequest>getHealthlyUserByUserId(Long userId) throws BizException;

	public List<UserDeviceInfo> getDeviceList(EjlUser user) throws BizException ;
	
	public List<UserDeviceInfo> getAttentionDeviceList(EjlUser user) throws BizException ;

	public List<ChatRoomUserNotify> getUserByMoreUserId(List<String> userIds) throws BizException ;
	
	public List<FamilyAttentionUserStruc> getDeviceAndAppUserByFamilyId(Long userId,Long familyId) throws BizException ;
	
	public List<FamilyAttentionUserStruc> getDeviceAttentionUserByFamilyId(Long userId,Long familyId) throws BizException ;
	
	public int clearPhoneForUnconfirmUser(Context ctx,Long familyId)throws BizException ;
	
	public EjlUser getUserByThirdPartyToken(String thirdPartyToken,Integer type) throws BizException;
	
	public UserDeviceInfo getModeleTypeByDeviceId(Long  deviceId) throws BizException ;
	
	
}
