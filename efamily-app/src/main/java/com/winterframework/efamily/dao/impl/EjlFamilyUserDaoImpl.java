package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlFamilyUserDao;
import com.winterframework.efamily.entity.EjlFamilyUser;

@Repository("ejlFamilyUserDaoImpl")
public class EjlFamilyUserDaoImpl extends EjlComFamilyUserDaoImpl<EjlFamilyUser> implements IEjlFamilyUserDao {

	/**
	* Title: quitFamily
	* Description:
	* @param userId
	* @param familyId
	* @throws BizException 
	* @see com.winterframework.efamily.dao.IEjlFamilyUserDao#quitFamily(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void quitFamily(Long userId, Long familyId) throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		this.sqlSessionTemplate.update(this.getQueryPath("quitFamily"), map);
	}
	@Override
	public void deleteUserBySwitchFamily(Long userId, Long familyId) throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		this.sqlSessionTemplate.delete(this.getQueryPath("deleteUserBySwitchFamily"), map);
	}
	
	/**
	 * 功能：修改状态和操作类型
	 * @param userId
	 * @param familyId
	 * @param manageType
	 * @param status
	 * @return
	 * @throws BizException
	 */
	public int updateStatusAndManageType(EjlFamilyUser familyUser) throws BizException {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateStatusAndManageType"), familyUser);
	}
	
	@Override
	public int updateAttrByUserIdAndFamilyId(EjlFamilyUser familyUser) throws BizException {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateAttrByUserIdAndFamilyId"), familyUser);
	}
	
	
	@Override
	public EjlFamilyUser getHostByfamilyIdAndPosition(EjlFamilyUser familyUser) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getHostByfamilyIdAndPosition"), familyUser);
	}
	
}
