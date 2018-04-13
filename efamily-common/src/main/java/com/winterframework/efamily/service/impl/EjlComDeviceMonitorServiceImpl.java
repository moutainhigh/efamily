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
import com.winterframework.efamily.dao.IEjlComDeviceMonitorDao;
import com.winterframework.efamily.entity.EjlDeviceMonitor;
import com.winterframework.efamily.service.IEjlComDeviceMonitorService;

/** 
* @ClassName: EjlHealthManageServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:20:22 
*  
*/
@Service("ejlComDeviceMonitorServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComDeviceMonitorServiceImpl extends BaseServiceImpl<IEjlComDeviceMonitorDao, EjlDeviceMonitor> implements
	IEjlComDeviceMonitorService {


	@Resource(name = "ejlComDeviceMonitorDaoImpl")
	private IEjlComDeviceMonitorDao ejlComDeviceMonitorDao;

	@Override
	protected IEjlComDeviceMonitorDao getEntityDao() {
		return ejlComDeviceMonitorDao;
	}


}
