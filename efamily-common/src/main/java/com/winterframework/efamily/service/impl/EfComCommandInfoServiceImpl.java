package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComCommandInfoDao;
import com.winterframework.efamily.entity.EfCommandInfo;
import com.winterframework.efamily.service.IEfComCommandInfoService;

	@Service("efComCommandInfoServiceImpl")
	@Transactional(rollbackFor = Exception.class)
	public class EfComCommandInfoServiceImpl extends BaseServiceImpl<IEfComCommandInfoDao, EfCommandInfo>
			implements IEfComCommandInfoService {

		
		@Resource(name="efComCommandInfoDaoImpl")
		private IEfComCommandInfoDao dao;
		
		@Override
		protected IEfComCommandInfoDao getEntityDao() {
			// TODO Auto-generated method stub
			return dao;
		}
		
	}
