package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfKeyApiDao;
import com.winterframework.efamily.entity.EfKeyApi;
import com.winterframework.efamily.service.IEfKeyApiService;

 
@Service("efKeyApiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfKeyApiServiceImpl extends BaseServiceImpl<IEfKeyApiDao,EfKeyApi> implements IEfKeyApiService {
	@Resource(name="efKeyApiDaoImpl")
	private IEfKeyApiDao efKeyApiDao;
	@Override
	protected IEfKeyApiDao getEntityDao() { 
		return efKeyApiDao;
	}
	@Override
	public EfKeyApi getByKey(String key) throws BizException {
		try{
			return efKeyApiDao.getByKey(key);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}


