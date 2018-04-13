package com.winterframework.efamily.service.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfResourceDao;
import com.winterframework.efamily.entity.EfResourceAssist;
import com.winterframework.efamily.entity.FmlResource;
import com.winterframework.efamily.service.IEfResourceService;

@Service("efResourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfResourceServiceImpl extends BaseServiceImpl<IEfResourceDao,FmlResource> implements IEfResourceService {

	@Resource(name="efReourceDaoImpl")
	private IEfResourceDao efReourceDaoImpl;
	
	@Override
	protected IEfResourceDao getEntityDao() {
		
		return efReourceDaoImpl;
	}

	public List<FmlResource> getResourceByAssistantTypeAndStatus(int type,int status)throws BizException {
		EfResourceAssist efResourceAssist = new EfResourceAssist();
		efResourceAssist.setType(type);
		efResourceAssist.setStatus(status);
		return efReourceDaoImpl.getResourceByAssistantTypeAndStatus(efResourceAssist);
    }
	
	public FmlResource getRandResourceByAssistantTypeAndStatus(int type,int status) throws BizException{
		FmlResource fmlResource = null;
		List<FmlResource> fmlResourceList =  getResourceByAssistantTypeAndStatus( type, status);
		if(fmlResourceList==null || fmlResourceList.size()==0){
			return null;
		}
		Random rand = new Random();
		fmlResource = fmlResourceList.get(rand.nextInt(fmlResourceList.size()));
		return fmlResource;
	}
}
