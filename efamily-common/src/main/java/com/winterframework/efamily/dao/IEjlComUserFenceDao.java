package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlUserFence;

public interface IEjlComUserFenceDao extends IBaseDao<EjlUserFence> {
	
	public void deleteByfenceId(Long fenceId,Long updatorId) throws BizException;
	
	public List<EjlUserFence> getAllUserFenceInFence(Long orgId);
}
