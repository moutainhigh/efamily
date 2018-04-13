package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlChartRoomUser;

public interface IEjlComChartRoomUserService extends IBaseService<EjlChartRoomUser>{
	List<EjlChartRoomUser> getByRoomId(Long roomId) throws BizException;
	/**
	 * 获取有效的聊天室用户
	 * @param roomId
	 * @return
	 * @throws BizException
	 */
	List<EjlChartRoomUser> getEffectiveByRoomId(Long roomId) throws BizException; 
	
}
