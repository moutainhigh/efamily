package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.service.IEfLocationSemiService;


@Service("efLocationSemiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfLocationSemiServiceImpl extends BaseServiceImpl<IEfLocationSemiDao,EfLocationSemi> implements IEfLocationSemiService {

	@Resource(name="efLocationSemiDaoImpl")
	private IEfLocationSemiDao efLocationSemiDao;
	
	@Override
	protected IEfLocationSemiDao getEntityDao() {
		return efLocationSemiDao;
	}
	
	@Override
	public List<EfLocationSemi> getListByTimeSpan(Long userId, Long deviceId,
			Date timeFrom, Date timeTo, Integer type) throws BizException {
		try{
			return efLocationSemiDao.getListByTimeSpan(userId, deviceId, timeFrom, timeTo, type);
		}catch(Exception e){
			log.error("",e);
		}
		return null;
	}
	
}

