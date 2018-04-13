package com.winterframework.efamily.service;
import com.winterframework.efamily.core.base.IBaseService;

import com.winterframework.efamily.entity.EfLocationMovemode;

public interface IEfComLocationMovemodeService extends IBaseService<EfLocationMovemode> {
	public EfLocationMovemode getLastEfLocationMovemode(Long userId,
			Long deviceId);
}
