package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.ITskHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.service.ITskHealthHeartRateService;

@Service("tskHealthHeartRateServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class TskHealthHeartRateServiceImpl extends EjlComHealthHeartRateServiceImpl implements ITskHealthHeartRateService{

	@Resource(name = "tskHealthHeartRateDaoImpl")
	private ITskHealthHeartRateDao tskHealthHeartRateDao;
	
	@Override
	protected ITskHealthHeartRateDao getEntityDao() {
		return tskHealthHeartRateDao;
	}
	@Override
	public List<Map<String, Long>> getLastestUserIdDeviceIdListByCreateTime(
			Date fromTime,Date toTime) throws BizException {
		try{
			return getEntityDao().getLastestUserIdDeviceIdListByCreateTime(fromTime,toTime);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	@Override
	public List<EjlHealthHeartRate> getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(
			Long userId, Long deviceId, Integer low, Integer high,
			Date fromTime, Date toTime) throws BizException {
		try{
			return getEntityDao().getSortListByUserIdAndDeviceIdAndSpanAndCreateTime(userId, deviceId, low, high, fromTime, toTime);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
