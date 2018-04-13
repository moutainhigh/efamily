package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlUserFence;

public interface IEjlComUserFenceService  extends IBaseService<EjlUserFence>{

	public void deleteByfenceId(Long fenceId,Long updateId) throws BizException;
	
	public EjlUserFence getEjlUserFenceBy(Long fenceId,Long userId) throws BizException;
	
	public List<EjlUserFence> getAllUserFenceInFence(Long orgId);
}
