package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfDeviceAlarmTargetDao;
import com.winterframework.efamily.entity.EfDeviceAlarmTarget;
import com.winterframework.efamily.service.IEfDeviceAlarmTargetService;

@Service("efDeviceAlarmTargetServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfDeviceAlarmTargetServiceImpl  extends BaseServiceImpl<IEfDeviceAlarmTargetDao,EfDeviceAlarmTarget> implements IEfDeviceAlarmTargetService {
	@Resource(name="efDeviceAlarmTargetDaoImpl")
	private IEfDeviceAlarmTargetDao dao;
	
	@Override
	protected IEfDeviceAlarmTargetDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	} 
	@Override
	public List<EfDeviceAlarmTarget> getByStatus(Integer status) throws BizException {
		try{
			return dao.getByStatus(status);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	 
}
