package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.AttentionDeviceStruc;
import com.winterframework.efamily.dto.AttentionUserStruc;
import com.winterframework.efamily.entity.EjlAttentionUser;

public interface IEjlAttentionUserDao extends IEjlComAttentionUserDao {

	public List<AttentionUserStruc> getAttentionUser(Long userId) throws BizException;
	
	public List<AttentionDeviceStruc> getAttentionDevice(Long userId) throws BizException;
	
	public int updateAttentionUserBy(Context ctx,Long userId, Long familyId,Integer isForbitSpeak) throws BizException;
	
	public List<AttentionUserStruc> getAttentionUserByUserIdAndFamilyId(Long userId,Long familyId) throws BizException;
	
	public List<EjlAttentionUser> getAttentionByUserIdAndFamilyId(Long userId,Long familyId) throws BizException ;
	
	public int updateForCancelAttentionUser(Context ctx,Long attentionId, Long status) throws BizException;
}
