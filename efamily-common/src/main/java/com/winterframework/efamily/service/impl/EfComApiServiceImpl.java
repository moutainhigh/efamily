package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;

import com.winterframework.efamily.dao.IEfComApiDao;

import com.winterframework.efamily.entity.EfApi;


import com.winterframework.efamily.service.IEfComApiService;


@Service("efComApiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComApiServiceImpl extends BaseServiceImpl<IEfComApiDao, EfApi>implements IEfComApiService{

	@Resource(name="efComApiDaoImpl")
	private IEfComApiDao dao;
	@Override
	protected IEfComApiDao getEntityDao() {
		return dao;
	}
	@Override
	public EfApi getByUrl(Context ctx,String url) throws BizException {
		EfApi efApi = new EfApi();
		efApi.setUrl(url);
		List<EfApi>  list = dao.getAllByEntity(efApi);
		if(list == null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}

	
}
