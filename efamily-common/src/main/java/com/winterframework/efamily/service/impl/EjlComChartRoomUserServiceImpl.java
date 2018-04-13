package com.winterframework.efamily.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComChartRoomUserDao;
import com.winterframework.efamily.entity.EjlChartRoomUser;
import com.winterframework.efamily.service.IEjlComChartRoomUserService;

@Service("ejlComChartRoomUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComChartRoomUserServiceImpl extends BaseServiceImpl<IEjlComChartRoomUserDao,EjlChartRoomUser> implements IEjlComChartRoomUserService {
 
	@Resource(name="ejlComChartRoomUserDaoImpl")
	private IEjlComChartRoomUserDao dao;
	@Override
	protected IEjlComChartRoomUserDao getEntityDao() { 
		return dao;
	}
	
	@Override
	public List<EjlChartRoomUser> getByRoomId(Long roomId) throws BizException {
		try {
			return dao.getByRoomId(roomId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	@Override
	public List<EjlChartRoomUser> getEffectiveByRoomId(Long roomId)
			throws BizException {
		List<EjlChartRoomUser> chatRoomUserList=getByRoomId(roomId);
		if(null!=chatRoomUserList){
			Iterator<EjlChartRoomUser> iter=chatRoomUserList.iterator();
			while(iter.hasNext()){
				EjlChartRoomUser chatRoomUser=iter.next();
				if(chatRoomUser.getStatus()==EjlChartRoomUser.Status.ABSENT.getValue()){
					iter.remove();
				}
			}
		}		
		return chatRoomUserList;
	}
	
}
