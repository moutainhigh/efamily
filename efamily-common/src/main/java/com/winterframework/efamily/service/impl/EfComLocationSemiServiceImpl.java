package com.winterframework.efamily.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.service.IEfComLocationSemiService;

@Service("efComLocationSemiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComLocationSemiServiceImpl extends BaseServiceImpl<IEfComLocationSemiDao,EfLocationSemi> implements IEfComLocationSemiService {
	@Resource(name="efComLocationSemiDaoImpl")
	private IEfComLocationSemiDao efComLocationSemiDao;
	@Override
	protected IEfComLocationSemiDao getEntityDao() { 
		return efComLocationSemiDao;
	}
	@Override
	public EfLocationSemi getPrevious(EfLocationSemi locationSemi)
			throws BizException {
		List<Integer> flags=getValidFlags();
		return efComLocationSemiDao.getPrevious(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(),flags);
	}
	@Override
	public EfLocationSemi getNext(EfLocationSemi locationSemi)
			throws BizException {
		List<Integer> flags=getValidFlags();
		return efComLocationSemiDao.getNext(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(),flags);
	}
	
	public EfLocationSemi getLastLocation(Long userId, Long deviceId) throws BizException{
		try{
			return efComLocationSemiDao.getLastLocation(userId,deviceId);
		}catch(Exception e){
			log.error("dao error occurs.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	
	public EfLocationSemi getNewestLocationForQueryNotice(Long deviceId,Long deviceUserId, Date date) throws Exception{
		return efComLocationSemiDao.getNewestLocationForQueryNotice(deviceId,deviceUserId,date);
	}
	/**
	 * 获取有效点标识值
	 * @return
	 */
	protected List<Integer> getValidFlags(){
		List<Integer> flags=new ArrayList<Integer>();
		List<EfLocationSemi.Flag> sf=Arrays.asList(EfLocationSemi.Flag.values());
		for(EfLocationSemi.Flag f:sf){
			if(!f.equals(EfLocationSemi.Flag.DELETE) && !f.equals(EfLocationSemi.Flag.DISPOSE)) {
				flags.add(f.getValue());
			}
		}
		return flags;
	}
	protected List<Integer> getAllFlags(){
		List<Integer> flags=new ArrayList<Integer>();
		List<EfLocationSemi.Flag> sf=Arrays.asList(EfLocationSemi.Flag.values());
		for(EfLocationSemi.Flag f:sf){
			flags.add(f.getValue());
		}
		return flags;
	}
	@Override
	public EfLocationSemi getPreviousById(EfLocationSemi locationSemi)
			throws BizException {
		if(null!=locationSemi.getPreviousId()){
			return get(locationSemi.getPreviousId());
		}
		return null;
	}
	@Override
	public EfLocationSemi getNextById(EfLocationSemi locationSemi)
			throws BizException {
		if(null!=locationSemi.getNextId()){
			return get(locationSemi.getNextId());
		}
		return null;
	}
	@Override
	public List<EfLocationSemi> getDeviceLocationSemiByStatus(Integer count,
			Long deviceId, int status) throws Exception {
		return efComLocationSemiDao.getDeviceLocationSemiByStatus(count, deviceId, status);
	}
	@Override
	public Integer updateStatus(Long id, Integer status) throws Exception {
		return efComLocationSemiDao.updateStatus(id, status);
	}
}


