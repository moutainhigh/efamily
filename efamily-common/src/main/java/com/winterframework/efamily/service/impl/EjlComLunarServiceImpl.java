package com.winterframework.efamily.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComLunarDao;
import com.winterframework.efamily.entity.EfLunar;
import com.winterframework.efamily.service.IEfComLunarService;


@Service("ejlComLunarServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComLunarServiceImpl extends BaseServiceImpl<IEfComLunarDao,EfLunar> implements IEfComLunarService {
	@Resource(name="efComLunarDao")
	private IEfComLunarDao efComLunarDao;

	/**
	* Title: getEntityDao
	* Description:
	* @return 
	* @see com.winterframework.efamily.core.base.BaseServiceImpl#getEntityDao() 
	*/
	@Override
	protected IEfComLunarDao getEntityDao() {
		return efComLunarDao;
	}

	/**
	* Title: getEfLunarByDate
	* Description:
	* @param date
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEfComLunarService#getEfLunarByDate(java.util.Date) 
	*/
	@Override
	public EfLunar getEfLunarByDate(Date date) throws Exception {
		return efComLunarDao.getEfLunarByDate(date);
	}

	@Override
	public void saveOne(EfLunar efLunar) throws Exception {
		efComLunarDao.deleteByDate(efLunar.getSolarDate());
		efComLunarDao.insert(efLunar);
	}
}


