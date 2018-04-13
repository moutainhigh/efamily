package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComHelpDao;
import com.winterframework.efamily.entity.EjlHelp;
import com.winterframework.efamily.service.IEjlHelpService;

@Service("ejlHelpServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlHelpServiceImpl extends BaseServiceImpl<IEjlComHelpDao, EjlHelp> implements  IEjlHelpService{

	@Resource(name = "ejlComHelpDaoImpl")
	private IEjlComHelpDao ejlComHelpDaoImpl;

	@Override
	protected IEjlComHelpDao getEntityDao() {
		return ejlComHelpDaoImpl;
	}

	/**
	* Title: getAll
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlHelpService#getAll() 
	*/
	@Override
	public List<EjlHelp> getAll() throws Exception {
		return ejlComHelpDaoImpl.getAll();
	}

}
