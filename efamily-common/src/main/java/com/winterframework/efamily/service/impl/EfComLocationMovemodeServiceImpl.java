package com.winterframework.efamily.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComLocationMovemodeDao;
import com.winterframework.efamily.entity.EfLocationMovemode;
import com.winterframework.efamily.service.IEfComLocationMovemodeService;

@Service("efComLocationMovemodeServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class EfComLocationMovemodeServiceImpl  extends BaseServiceImpl<IEfComLocationMovemodeDao,EfLocationMovemode> implements IEfComLocationMovemodeService {
	@Resource(name="efComLocationMovemodeDaoImpl")
	private IEfComLocationMovemodeDao dao;

	@Override
	public EfLocationMovemode getLastEfLocationMovemode(Long userId,
			Long deviceId) {
		return dao.getLastEfLocationMovemode(userId, deviceId);
	}

	@Override
	protected IEfComLocationMovemodeDao getEntityDao() {
		return dao;
	}
	
	
}
