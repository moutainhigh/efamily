package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.entity.EjlUser;

@Repository("ejlUserDaoImpl")
public class EjlUserDaoImpl extends EjlComUserDaoImpl<EjlUser> implements IEjlUserDao {


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

	/*public List<HealthyProfileStruc> getEjlUsersByFamilyId(Long familyId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlUsersByFamilyId"), familyId);
	}*/
	
	
	/*public List<FriendListResponse> getEjlFriendListByUserId(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlFriendListByUserId"), userId);
	}*/
	
/*	@Override
	public List<FriendListResponse> getEjlFamilyListByUserId(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlFamilyListByUserId"), userId);
	}*/
	
	/*@Override
	public List<UserStruc> getEjlFamilyListByFamilyIdAndType(EjlUser user) throws BizException {
		if(user.getType().longValue() == EfamilyConstant.USER_TYPE_ALL){
			user.setType(null);
		}
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlFamilyListByFamilyIdAndType"), user);
	}*/
	
	
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
	
	/*@Override
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
	}*/

	@Override
	public List<EjlUser> getEjlUserByAttribute(EjlUser user) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlUserByAttribute"), user);
	}

	@Override
	public List<EjlUser> getChatRoomUserByRoomId(Long roomId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getChatRoomUserByRoomId"), roomId);
	}

	@Override
	public Long getAppConnectNumber(List<String> idList) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getAppConnectNumber"),idList);
	}

	@Override
	public Long getDeviceConnectNumber(List<String> idList) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getDeviceConnectNumber"),idList);
	}
	
}
