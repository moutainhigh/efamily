package com.winterframework.efamily.service.impl;




import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComParamConfigDao;
import com.winterframework.efamily.entity.DeviceParamConfig;
import com.winterframework.efamily.service.IEjlComParamConfigService;
@Service("ejlComParamConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComParamConfigServiceImpl extends BaseServiceImpl<IEjlComParamConfigDao,DeviceParamConfig> implements IEjlComParamConfigService {
   
	@Resource(name="eJlComParamConfigDaoImpl")
	private IEjlComParamConfigDao dao;
	
	@Override
	protected IEjlComParamConfigDao getEntityDao() {
		return dao;
	}
}


