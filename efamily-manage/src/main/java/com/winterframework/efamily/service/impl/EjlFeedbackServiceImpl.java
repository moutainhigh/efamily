package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComFeedbackDao;
import com.winterframework.efamily.entity.EjlFeedback;
import com.winterframework.efamily.service.IEjlFeedbackService;
@Service("ejlFeedbackServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlFeedbackServiceImpl extends BaseServiceImpl<IEjlComFeedbackDao,EjlFeedback> implements IEjlFeedbackService{
 
	@Resource(name="ejlComFeedbackDaoImpl")
	private IEjlComFeedbackDao ejlComFeedbackDaoImpl;
	@Override
	protected IEjlComFeedbackDao getEntityDao() { 
		return ejlComFeedbackDaoImpl;
	}
			
}
