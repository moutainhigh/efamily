package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComThirdPartyLoginDao;
import com.winterframework.efamily.entity.EjlThirdPartyLogin;
import com.winterframework.efamily.entity.ThirdPartyLoginStruc;
import com.winterframework.efamily.service.IEjlComThirdPartyLoginService;

@Service("ejlComThirdPartyLoginServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComThirdPartyLoginServiceImpl extends
		BaseServiceImpl<IEjlComThirdPartyLoginDao, EjlThirdPartyLogin>
		implements IEjlComThirdPartyLoginService {

	@Resource(name = "ejlComThirdPartyLoginDaoImpl")
	private IEjlComThirdPartyLoginDao ejlComThirdPartyLoginDaoImpl;

	@Override
	protected IEjlComThirdPartyLoginDao getEntityDao() {
		return ejlComThirdPartyLoginDaoImpl;
	}

	public List<ThirdPartyLoginStruc> getThirdPartyLoginStruc(Long userId){
		return ejlComThirdPartyLoginDaoImpl.getThirdPartyLoginStruc(userId);
	}
}
