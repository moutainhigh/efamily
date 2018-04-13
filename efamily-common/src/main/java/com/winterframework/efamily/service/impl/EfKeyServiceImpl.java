package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfKeyDao;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.service.IEfKeyService;

 
@Service("efKeyServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfKeyServiceImpl extends BaseServiceImpl<IEfKeyDao,EfKey> implements IEfKeyService {
	@Resource(name="efKeyDaoImpl")
	private IEfKeyDao efKeyDao;
	@Override
	protected IEfKeyDao getEntityDao() { 
		return efKeyDao;
	}
	@Override
	public EfKey getByKey(String key) throws BizException {
		try{
			return efKeyDao.getByKey(key);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}


