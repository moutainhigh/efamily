package com.winterframework.efamily.service;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlUserBarrier;

public interface IEjlComUserBarrierService extends IBaseService<EjlUserBarrier>{
	
	public void deleteByFenceId(Long fenceId,Long updateId);

}