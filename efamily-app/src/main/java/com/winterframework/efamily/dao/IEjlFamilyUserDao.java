package com.winterframework.efamily.dao;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlFamilyUser;

public interface IEjlFamilyUserDao extends IEjlComFamilyUserDao {
	
	/**
	 * 功能：退出家庭
	 * @param userId
	 * @param familyId
	 * @throws BizException
	 */
	public void quitFamily(Long userId, Long familyId) throws BizException;
	
	public int updateStatusAndManageType(EjlFamilyUser familyUser) throws BizException ;
	
	public int updateAttrByUserIdAndFamilyId(EjlFamilyUser familyUser) throws BizException ;
	
	/**
	 * 功能：删除家庭的用户，当更换家庭的时候
	 * @param userId
	 * @param familyId
	 * @throws BizException
	 */
	public void deleteUserBySwitchFamily(Long userId, Long familyId) throws BizException;
	
	public EjlFamilyUser getHostByfamilyIdAndPosition(EjlFamilyUser familyUser);
	
}
