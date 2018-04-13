package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.ITskLocationDao;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.service.ITskLocationService;

@Service("tskLocationServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class TskLocationServiceImpl extends EjlComLocationServiceImpl implements ITskLocationService  {
	private Logger log = LoggerFactory.getLogger(TskLocationServiceImpl.class);
	@Resource(name = "tskLocationDaoImpl")
	private ITskLocationDao tskLocationDao;
	
	@Override
	public List<EjlLocation> getListByUserIdDeviceIdAfterTime(Long userId,
			Long deviceId, Date time) throws BizException {
		try{
			return tskLocationDao.getListByUserIdDeviceIdAfterTime(userId, deviceId, time);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}
