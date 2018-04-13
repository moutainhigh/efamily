/**   
* @Title: EjlRemindServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午4:30:28 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComRemindDao;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.service.IEjlComRemindService;

/** 
* @ClassName: EjlRemindServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午4:30:28 
*  
*/
@Service("ejlComRemindServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComRemindServiceImpl extends BaseServiceImpl<IEjlComRemindDao, EjlRemind> implements IEjlComRemindService {

	@Resource(name = "ejlComRemindDaoImpl")
	private IEjlComRemindDao ejlComRemindDaoImpl;

	@Override
	protected IEjlComRemindDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlComRemindDaoImpl;
	}

}
