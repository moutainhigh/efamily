package com.winterframework.efamily.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComFamilyUserDao;
import com.winterframework.efamily.entity.EjlFamilyUser;

@Repository("ejlComFamilyUserDaoImpl")
public class EjlComFamilyUserDaoImpl<E extends EjlFamilyUser> extends BaseDaoImpl<EjlFamilyUser> implements IEjlComFamilyUserDao {
	@Override
	public List<EjlFamilyUser> getByUserId(Long userId) throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getByUserId"), userId);
	}
	@Override
	public List<EjlFamilyUser> getByFamilyId(Long familyId) throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getByFamilyId"), familyId);
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
	
	/**
	 * 功能：根据familyID和userId查询家庭用户关系
	 * @param userId
	 * @param familyId
	 * @return
	 * @throws BizException
	 */
	public EjlFamilyUser getFamilyUserByParm(Long userId, Long familyId) throws BizException {
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setFamilyId(familyId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getFamilyUserByParm"), ejlFamilyUser);
	}
	@Override
	public List<EjlFamilyUser> getListByFamilyId(Long familyId)
			throws BizException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("familyId", familyId);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByFamilyId"), map);
	}
	
	@Override
	public List<EjlFamilyUser> getListByFamilyIdAndType(Long familyId,
			Integer type) throws BizException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("familyId", familyId);
		map.put("type", type);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByFamilyIdAndType"), map);
	}
}
