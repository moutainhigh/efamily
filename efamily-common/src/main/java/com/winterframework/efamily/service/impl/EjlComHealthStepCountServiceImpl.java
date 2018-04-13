/**   
* @Title: EjlHealthManageServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:20:22 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComHealthStepCountDao;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.service.IEjlComHealthStepCountService;

/** 
* @ClassName: EjlHealthManageServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:20:22 
*  
*/
@Service("ejlComHealthStepCountServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComHealthStepCountServiceImpl extends BaseServiceImpl<IEjlComHealthStepCountDao, EjlHealthStepCount> implements
		IEjlComHealthStepCountService {


	@Resource(name = "ejlComHealthStepCountDaoImpl")
	private IEjlComHealthStepCountDao ejlComHealthStepCountDao;

	@Override
	protected IEjlComHealthStepCountDao getEntityDao() {
		return ejlComHealthStepCountDao;
	}
	
	@Override
	public List<EjlHealthStepCount> getListByUserIdDeviceIdBetweenTime(
			Long userId, Long deviceId, Date fromTime, Date toTime)
			throws BizException {
		try{
			return getEntityDao().getListByUserIdDeviceIdBetweenTime(userId, deviceId, fromTime, toTime);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	
}
