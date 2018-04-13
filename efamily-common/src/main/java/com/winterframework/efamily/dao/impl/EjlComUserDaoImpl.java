package com.winterframework.efamily.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.entity.EjlUser;

@Repository("ejlComUserDaoImpl")
public class EjlComUserDaoImpl<E extends EjlUser> extends BaseDaoImpl<EjlUser> implements IEjlComUserDao {

		//查询拥有设备的用户
		public List<EjlUser> findDeviceUser() throws Exception {
			return this.sqlSessionTemplate.selectList(this.getQueryPath("findDeviceUser"));
		}
		
		@Override
		public List<EjlUser> getAttentionUserByFamilyId(Long familyId) throws BizException {
			return this.sqlSessionTemplate.selectList(this.getQueryPath("getAttentionUserByFamilyId"), familyId);
		}
		
		@Override
		public List<EjlUser> getUserByfenceId(Long fenceId) throws BizException {
			return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserByfenceId"), fenceId);
		}
		
		@Override
		public List<EjlUser> getUserByDeviceAndApp(Long familyId) throws BizException {
			return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserByDeviceAndApp"), familyId);
		}
		
		@Override
		public EjlUser getFamilyHostUser(Long familyId) throws BizException {
			return this.sqlSessionTemplate.selectOne(this.getQueryPath("getFamilyHostUser"), familyId);
		}
		
		@Override
		public List<EjlUser> getValidListByType(Integer type) throws Exception {
			return this.sqlSessionTemplate.selectList(getQueryPath("getValidListByType"), type);
		}
		
		@Override
		public EjlUser getUserByDeviceId(Long deviceId) throws Exception {
			return this.sqlSessionTemplate.selectOne(getQueryPath("getUserByDeviceId"), deviceId);
		}

		@Override
		public Integer updatePasswdByUserId(Context ctx,Long userId,String password) throws Exception {
			EjlUser  user = new EjlUser();
			user.setId(userId);
			user.setPasswd(password);
			user.setUpdatorId(ctx.getUserId());
			user.setUpdateTime(new Date());
			return this.sqlSessionTemplate.update(getQueryPath("updatePasswdByUserId"), user);
		}		
		

		@Override
		public EjlUser getAttentionUserByUserIdAndAttendionId(Long userId,
				Long attentionId) throws Exception {
			Map map = new HashMap();
			map.put("userId", userId);
			map.put("attentionId", attentionId);
			return this.sqlSessionTemplate.selectOne(getQueryPath("getAttentionUserByUserIdAndAttendionId"), map);
		}
}