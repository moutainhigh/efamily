package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlFamilyUser;


public interface IEjlComFamilyUserService extends IBaseService<EjlFamilyUser> {
	List<EjlFamilyUser> getByUserId(Long userId) throws BizException;
	EjlFamilyUser getEffectiveByUserId(Long userId) throws BizException;
	List<EjlFamilyUser> getByFamilyId(Long familyId) throws BizException;
	List<EjlFamilyUser> getEffectiveByFamilyId(Long familyId) throws BizException;
	
	public int updateStatusAndManageType(EjlFamilyUser familyUser) throws BizException ;
	
	
	List<EjlFamilyUser> getListByFamilyId(Long familyId) throws BizException;
	List<EjlFamilyUser> getValidListByFamilyId(Long familyId) throws BizException;
	List<EjlFamilyUser> getListByFamilyIdAndType(Long familyId,Integer type) throws BizException;
	List<EjlFamilyUser> getValidListByFamilyIdAndType(Long familyId,Integer type) throws BizException;
	
	public Long getFamilyHostUserId(Long familyId) throws BizException;
}
