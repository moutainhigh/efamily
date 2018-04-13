package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.ManageJoinFamilyResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.dto.UserDeviceInfo;
import com.winterframework.efamily.dto.UserStruc;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;


public interface IEjlFamilyUserService extends IEjlComFamilyUserService{
	
	public List<UserStruc> getFamilyUserList(Long userId,Long familyId,Long userType)throws BizException;
	public Long quitFamily(Context ctx,Long userId, Long familyId,Integer isAttentionOldFamilyDevice) throws BizException;
	public int updateAttrByUserIdAndFamilyId(EjlFamilyUser ejlFamilyUser) throws BizException ;
	public void deviceJoinFamily(Context ctx,Long userId, Long familyId,Long manageType,Long operator,int status,long userType) throws BizException;
	public ManageJoinFamilyResponse  manageFamily(Context ctx,Long userId, Long familyId,Long manageType,Long operator) throws BizException;
	public void severFamilyRelation(Context ctx,Long userId, Long familyId)throws BizException;
	public void createFriendRelationShip(Context ctx,EjlUser user,List<EjlUser> friendList) throws BizException ;
	public EjlFamilyUser getEjlFamilyUserBy(Long userId,Long familyId) throws BizException;
	public void zombieUserJoinFamily(Context ctx,Long userId, Long familyId,int status,long userType) throws BizException;
	public List<UserDeviceInfo> getDeviceList(Long userId) throws BizException;
	public int updateFamilyUserType(Long userId,Long familyId,Long userType) throws BizException;
	public List<UnbindDeviceInfo> unbindFamilyAllDeviceByMemberQuit(Context ctx,Long userId) throws BizException;
	public void deleteUserFromFamily(Context ctx,String userIds,Long familyId) throws BizException;
	public boolean checkIsFamilyHost(Long userId,Long familyId) throws BizException;
	public void manageForbitSpeak(Context ctx,Long userId,Long chatType,Long chatRoomId,Integer oprType) throws BizException;
	public void TransferFamilyHost(Context ctx,Long familyId,Long userId,Long oprUserId) throws BizException;
	public EjlFamily  getEjlFamilyByUserId(Long userId)throws BizException;
	public Long getHostByCurUserId(Long userId)  throws BizException;
	public boolean checkCurUserIsHost(Long userId)  throws BizException;
	public Long getHostByFamilyId(Long familyId)  throws BizException;
}
