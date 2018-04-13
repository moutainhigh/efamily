package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.entity.UserBarrierStruc;
import com.winterframework.efamily.entity.UserDeviceBarrierStruc;

public interface IEjlComUserBarrierDao extends IBaseDao<EjlUserBarrier> {

	public EjlUserBarrier getOneBarrierByUserId(Long userId,Integer orgTag);
	
	public List<UserBarrierStruc> getListBarrierByUserId(Long userId,Integer orgTag);
	
	public List<UserDeviceBarrierStruc> getUserBarrierList(Long userBarrierId,Integer orgTag);
	
	public void deleteByUserAndOrgTag(Long userId,Integer orgTag);
	
	public void deleteByFenceId(Long fenceId,Long updateId);
}