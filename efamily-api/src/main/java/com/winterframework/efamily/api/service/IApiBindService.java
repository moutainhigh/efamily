package com.winterframework.efamily.api.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlDevice;

public interface IApiBindService{

	 
	/**
	 * 获取时间戳之后的数据（按客户Key和时间）
	 * @param ctx
	 * @param type
	 * @param time
	 * @return
	 * @throws BizException
	 */
	List<EjlDevice> getBindListByKeyAndTime(Context ctx,Integer type,Date time) throws BizException;
}
