package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComPublicDataDao;
import com.winterframework.efamily.entity.EjlPublicData;
import com.winterframework.efamily.service.IEjlComPublicDataService;


	@Service("ejlComPublicDataServiceImpl")
	@Transactional(rollbackFor = Exception.class)
	public class EjlComPublicDataServiceImpl extends BaseServiceImpl<IEjlComPublicDataDao,EjlPublicData> implements IEjlComPublicDataService {
		@Resource(name="ejlComPublicDataDaoImpl")
		private IEjlComPublicDataDao ejlComPublicDataDaoImpl;
		@Override
		protected IEjlComPublicDataDao getEntityDao() { 
			return ejlComPublicDataDaoImpl;
		}
	}