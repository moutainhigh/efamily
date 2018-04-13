package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dao.IEjlAttentionUserDao;
import com.winterframework.efamily.dto.AttentionDeviceStruc;
import com.winterframework.efamily.dto.AttentionUserStruc;
import com.winterframework.efamily.entity.EjlAttentionUser;


@Repository("ejlAttentionUserDaoImpl")
public class EjlAttentionUserDaoImpl extends EjlComAttentionUserDaoImpl<EjlAttentionUser> implements IEjlAttentionUserDao {
  
	public List<AttentionUserStruc> getAttentionUser(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAttentionUser"), userId);
	}
	
	public List<AttentionUserStruc> getAttentionUserByUserIdAndFamilyId(Long userId,Long familyId) throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAttentionUserByUserIdAndFamilyId"), map);
	}
	
	public List<AttentionDeviceStruc> getAttentionDevice(Long userId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAttentionDevice"), userId);
	}
	
	public List<EjlAttentionUser> getAttentionByUserIdAndFamilyId(Long userId,Long familyId) throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAttentionByUserIdAndFamilyId"), map);
	}
	
	
	public int updateAttentionUserBy(Context ctx,Long userId, Long familyId,Integer isForbitSpeak) throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("familyId", familyId);
		map.put("isForbitSpeak", Long.valueOf(isForbitSpeak));
		map.put("updatorId", ctx.getUserId());
		return this.sqlSessionTemplate.update(this.getQueryPath("updateAttentionUserBy"), map);
	}
	
	public int updateForCancelAttentionUser(Context ctx,Long attentionId, Long status) throws BizException {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("attentionId", attentionId);
		map.put("status", status);
		map.put("updatorId", ctx.getUserId());
		return this.sqlSessionTemplate.update(this.getQueryPath("updateForCancelAttentionUser"), map);
	}
	
	
	
}