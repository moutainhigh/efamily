package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.ChatGroupListResponse;
import com.winterframework.efamily.dto.ChatRoomUserNotify;
import com.winterframework.efamily.dto.FamilyAttentionUserStruc;
import com.winterframework.efamily.dto.FriendListResponse;
import com.winterframework.efamily.dto.HealthlyUserRequest;
import com.winterframework.efamily.dto.HealthyProfileStruc;
import com.winterframework.efamily.dto.UserDeviceInfo;
import com.winterframework.efamily.dto.UserStruc;
import com.winterframework.efamily.entity.EjlUser;

@Repository("ejlUserDaoImpl")
public class EjlUserDaoImpl extends EjlComUserDaoImpl<EjlUser> implements IEjlUserDao {

	@Override
	public EjlUser getUserByPhone(String phone) {
		return this.sqlSessionTemplate.selectOne("getUserByPhone", phone);
	}

	/**
	* Title: getEjlUsersByFamilyId
	* Description:
	* @param familyId
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.dao.IEjlUserDao#getEjlUsersByFamilyId(java.lang.Long) 
	*/
	@Override
	public List<HealthyProfileStruc> getEjlUsersByFamilyId(Long familyId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlUsersByFamilyId"), familyId);
	}
	
	@Override
	public List<FriendListResponse> getEjlFriendListByUserId(EjlUser user) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlFriendListByUserId"), user);
	}
	
	@Override
	public List<FriendListResponse> getEjlFamilyListByUserId(EjlUser user) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlFamilyListByUserId"), user);
	}
	
	public List<UserStruc> getEjlFamilyListByFamilyIdAndType(EjlUser user) throws BizException {
		if(user.getType().longValue() == EfamilyConstant.USER_TYPE_ALL){
			user.setType(null);
		}
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlFamilyListByFamilyIdAndType"), user);
	}
	public List<UserDeviceInfo> getDeviceList(EjlUser user) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getDeviceList"), user);
	}

	public List<UserDeviceInfo> getAttentionDeviceList(EjlUser user) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAttentionDeviceList"), user);
	}
	
	/**
	* Title: getUserByUserId
	* Description:
	* @param userId
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.dao.IEjlUserDao#getUserByUserId(java.lang.Long) 
	*/
	@Override
	public EjlUser getUserByUserId(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserByUserId"), userId);
	}

	public EjlUser getUserByThirdPartyToken(String thirdPartyToken,Integer type) throws BizException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("thirdPartyToken", thirdPartyToken);
		map.put("type", type+"");
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserByThirdPartyToken"), map);
	}
	
	
	@Override
	public Long getFamilyCountByUserId(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getFamilyCountByUserId"), userId);
	}
	
	@Override
	public List<ChatGroupListResponse> getGroupChatListByUserId(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getGroupChatListByUserId"), userId);
	}

	@Override
	public List<EjlUser> getUserByAddressBookPhone(List<String> phone)
			throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserByAddressBookPhone"), phone);
	}

	@Override
	public List<ChatRoomUserNotify> getUserByMoreUserId(List<String> userIds) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserByMoreUserId"), userIds);
	}
	
	@Override
	public List<EjlUser> getEjlUserByAttribute(EjlUser user) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlUserByAttribute"), user);
	}

	@Override
	public List<EjlUser> getChatRoomUserByRoomId(Long roomId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getChatRoomUserByRoomId"), roomId);
	}

	@Override
	public List<HealthlyUserRequest> getHealthlyUserByUserId(Long userId)
			throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getHealthlyUserByUserId"),userId);
	}
	
	public List<FamilyAttentionUserStruc> getDeviceAndAppUserByFamilyId(Long userId,Long familyId)
			throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getDeviceAndAppUserByFamilyId"),map);
	}
	
	public List<FamilyAttentionUserStruc> getDeviceAttentionUserByFamilyId(Long userId,Long familyId)
			throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getDeviceAttentionUserByFamilyId"),map);
	}
	
	public int clearPhoneForUnconfirmUser(Context ctx,Long familyId)throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("updatorId", ctx.getUserId());
		map.put("familyId", familyId);
		return this.sqlSessionTemplate.update("clearPhoneForUnconfirmUser",map);
	}

	@Override
	public UserDeviceInfo getModeleTypeByDeviceId(Long deviceId)
			throws BizException {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getModeleTypeByDeviceId"), deviceId);
	}
}
