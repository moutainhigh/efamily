/**   
* @Title: EjlHealthManageServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:20:22 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.core.base.BaseServiceImpl;

import com.winterframework.efamily.dao.IEjlComHealthBloodPressureDao;


import com.winterframework.efamily.entity.EjlHealthBloodPressure;

import com.winterframework.efamily.service.IComEjlHealthBloodPressureService;


/** 
* @ClassName: EjlHealthManageServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:20:22 
*  
*/
@Service("ejlComHealthBloodPressureServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComHealthBloodPressureServiceImpl extends BaseServiceImpl<IEjlComHealthBloodPressureDao, EjlHealthBloodPressure> implements
		IComEjlHealthBloodPressureService {

	@Resource(name = "ejlComHealthBloodPressureDaoImpl")
	private IEjlComHealthBloodPressureDao ejlComHealthBloodPressureDaoImpl;

	@Override
	protected IEjlComHealthBloodPressureDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlComHealthBloodPressureDaoImpl;
	}

	@Override
	public EjlHealthBloodPressure getLastBloodPressure(
			EjlHealthBloodPressure ejlHealthBloodPressure) throws Exception {
		// TODO Auto-generated method stub
		return ejlComHealthBloodPressureDaoImpl.getLastBloodPressure(ejlHealthBloodPressure);
	}
	
	
	public List<EjlHealthBloodPressure> getBloodPressureByTime(Long userId, Long fromTime,Long endTime){
		return ejlComHealthBloodPressureDaoImpl.getBloodPressureByTime(userId, fromTime, endTime);
	}
	
}
