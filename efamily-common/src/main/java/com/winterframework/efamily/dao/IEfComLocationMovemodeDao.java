package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;

import com.winterframework.efamily.entity.EfLocationMovemode;

public interface IEfComLocationMovemodeDao  extends IBaseDao<EfLocationMovemode>{ 
	public EfLocationMovemode getLastEfLocationMovemode(Long userId, Long deviceId);
	public List<EfLocationMovemode> getMoveDeviceList() throws Exception;	
	public List<EfLocationMovemode> getLastEfLocationMovemodeList(Long userId, Long deviceId,int count) throws Exception;
	public List<EfLocationMovemode> getStilDeviceList() throws Exception;
}
