/**   
* @Title: EjlHealthManageServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:20:22 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.dao.IEjlComHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.IEjlComHealthHeartRateService;

/** 
* @ClassName: EjlHealthManageServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:20:22 
*  
*/
@Service("ejlComHealthHeartRateServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComHealthHeartRateServiceImpl extends BaseServiceImpl<IEjlComHealthHeartRateDao, EjlHealthHeartRate> implements
		IEjlComHealthHeartRateService {

	@Resource(name = "ejlComHealthHeartRateDaoImpl")
	private IEjlComHealthHeartRateDao ejlComHealthHeartRateDaoImpl;

	@Override
	protected IEjlComHealthHeartRateDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlComHealthHeartRateDaoImpl;
	}
	
	

	@Override
	public int update(Context ctx, EjlHealthHeartRate entity)
			throws BizException {
		return ejlComHealthHeartRateDaoImpl.update(entity);
	}
	
	public List<EjlHealthHeartRate> getUserHeartRateByTime(Long userId, Long fromTime,Long endTime){
		return ejlComHealthHeartRateDaoImpl.getUserHeartRateByTime(userId, fromTime, endTime);
	}
}
