/**   
* @Title: EjlHealthManageServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:20:22 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEJLComHealthSleepDao;
import com.winterframework.efamily.entity.EjlHealthSleep;
import com.winterframework.efamily.service.IEjlComHealthSleepService;

/** 
* 健康睡眠 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:20:22 
*  
*/
@Service("ejlComHealthSleepServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComHealthSleepServiceImpl extends BaseServiceImpl<IEJLComHealthSleepDao, EjlHealthSleep> implements
		IEjlComHealthSleepService {

	@Resource(name = "ejlComHealthSleepDaoImpl")
	private IEJLComHealthSleepDao dao;

	@Override
	protected IEJLComHealthSleepDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}


	
}
