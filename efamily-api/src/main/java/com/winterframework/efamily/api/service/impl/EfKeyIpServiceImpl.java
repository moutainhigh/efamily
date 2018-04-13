package com.winterframework.efamily.api.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.dao.IEfKeyIpDao;
import com.winterframework.efamily.api.service.IEfKeyIpService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.entity.EfKeyIp;

@Service("efKeyIpServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfKeyIpServiceImpl extends BaseServiceImpl<IEfKeyIpDao,EfKeyIp> implements IEfKeyIpService {

	@Resource(name="efKeyIpDaoImpl")
	private IEfKeyIpDao efKeyIpDaoImpl;
	
	@Override
	protected IEfKeyIpDao getEntityDao() {
		
		return efKeyIpDaoImpl;
	}

    public void saveOrUpdateKeyIpBy(Context ctx,String key,String ip) throws BizException{
    	EfKeyIp keyIp = new EfKeyIp();
    	keyIp.setUkey(key);
    	keyIp.setIp(ip);
    	EfKeyIp keyIpCheck = selectOneObjByAttribute(ctx, keyIp);
    	if(keyIpCheck !=null){
    		keyIp.setId(keyIpCheck.getId());
    	}
    	save(ctx, keyIp);
    }

}
