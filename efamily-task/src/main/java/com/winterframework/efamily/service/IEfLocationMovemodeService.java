package com.winterframework.efamily.service;

import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EfLocationMovemode;

public interface IEfLocationMovemodeService extends IEfComLocationMovemodeService{
	public void doGetMovemode(Map<String,Long> device) throws Exception;
	
	public double getJuheDistance(Long userId,Long deviceId) throws BizException;
	
	
	public void doStill(EfLocationMovemode efLocationMovemode) throws Exception;
	
	public void doMovement(EfLocationMovemode efLocationMovemode) throws Exception;
	
	public void processMoveMode() throws BizException;
	void doProcessMoveMode(Long userId,Long deviceId) throws BizException;
	
}
