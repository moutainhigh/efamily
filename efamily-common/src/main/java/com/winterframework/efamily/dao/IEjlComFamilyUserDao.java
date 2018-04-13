package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlFamilyUser;

public interface IEjlComFamilyUserDao extends IBaseDao<EjlFamilyUser> {
	List<EjlFamilyUser> getByUserId(Long userId) throws Exception;
	List<EjlFamilyUser> getByFamilyId(Long familyId) throws Exception;
	
	public int updateStatusAndManageType(EjlFamilyUser familyUser) throws BizException;
	
	public EjlFamilyUser getFamilyUserByParm(Long userId, Long familyId) throws BizException;
	
	List<EjlFamilyUser> getListByFamilyId(Long familyId) throws Exception;
	List<EjlFamilyUser> getListByFamilyIdAndType(Long familyId,Integer type) throws Exception;
}
