package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.SaveFenceReq;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.entity.UserBarrierStruc;

public interface IEjlUserBarrierService extends IBaseService<EjlUserBarrier>{

	public EjlUserBarrier getEjlUserBarrier(Long userId) throws BizException ;
	
	public List<UserBarrierStruc> getEjlUserBarrierList(Long userId) throws BizException;
	
	public int setEjlUserBarrier(Context ctx,Long fenceId,Long userId,Integer status,String location,Long radius,Integer type,String remark,int orgTag) throws BizException ;
	
	public boolean checkSetEjlUserBarrier(Long optUserId,Long barrierUserId) throws BizException;
	
	public void batchSetUserBarrier(Context ctx,List<SaveFenceReq> list,String key,int orgTag) throws BizException;
	
	
}
