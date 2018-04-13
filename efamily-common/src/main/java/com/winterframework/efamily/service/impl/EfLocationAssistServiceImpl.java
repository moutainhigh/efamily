package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfLocationAssistDao;
import com.winterframework.efamily.entity.EfLocationAssist;
import com.winterframework.efamily.service.IEfLocationAssistService;

/**
 * 定位辅助服务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月8日
 */
@Service("efLocationAssistServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfLocationAssistServiceImpl extends BaseServiceImpl<IEfLocationAssistDao,EfLocationAssist> implements IEfLocationAssistService {
	@Resource(name="efLocationAssistDaoImpl")
	private IEfLocationAssistDao efLocationAssistDao;
	@Override
	protected IEfLocationAssistDao getEntityDao() { 
		return efLocationAssistDao;
	}
	@Override
	public EfLocationAssist getByLocationId(Long locationId)
			throws BizException {
		try{
			return efLocationAssistDao.getByLocationId(locationId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}


