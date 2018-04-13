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
import com.winterframework.efamily.dao.IEjlComHealthSittingDao;
import com.winterframework.efamily.entity.EjlHealthSitting;
import com.winterframework.efamily.service.IEjlComHealthSittingService;

/** 
* @ClassName: EjlComHealthSittingServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-11-23 下午5:20:22 
*  
*/
@Service("ejlComHealthSittingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComHealthSittingServiceImpl extends BaseServiceImpl<IEjlComHealthSittingDao, EjlHealthSitting> implements
		IEjlComHealthSittingService {


	@Resource(name = "ejlComHealthSittingDaoImpl")
	private IEjlComHealthSittingDao ejlComHealthSittingDao;

	@Override
	protected IEjlComHealthSittingDao getEntityDao() {
		return ejlComHealthSittingDao;
	}


}
