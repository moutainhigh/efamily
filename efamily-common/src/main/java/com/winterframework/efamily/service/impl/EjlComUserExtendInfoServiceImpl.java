package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserExtendInfoDao;
import com.winterframework.efamily.entity.EjlUserExtendInfo;
import com.winterframework.efamily.service.IEjlComUserExtendInfoService;

@Service("ejlComUserExtendInfoServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserExtendInfoServiceImpl  extends BaseServiceImpl<IEjlComUserExtendInfoDao,EjlUserExtendInfo> implements IEjlComUserExtendInfoService{

	@Resource(name="ejlComUserExtendInfoDaoImpl")
	private IEjlComUserExtendInfoDao ejlComUserExtendInfoDao;
	
	@Override
	protected IEjlComUserExtendInfoDao getEntityDao() {		
		return ejlComUserExtendInfoDao;
	}
	@Override
	public EjlUserExtendInfo getByUserId(Long userId) throws BizException {
		try{
			return ejlComUserExtendInfoDao.getByUserId(userId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		} 
	}
	@Override
	public int update(EjlUserExtendInfo entity) throws BizException {
		return ejlComUserExtendInfoDao.update(entity);
	}

	@Override
	public List<String> getAllCityName() throws Exception {
		return ejlComUserExtendInfoDao.getAllCityName();
	}
	
}
