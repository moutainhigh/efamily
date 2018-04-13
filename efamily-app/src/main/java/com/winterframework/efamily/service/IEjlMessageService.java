package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlMessage;


public interface IEjlMessageService  extends IBaseService<EjlMessage>{

	EjlMessage sendChatMessage(Context ctx,Long sendUserId, Long receiveUserId, Long receiveUserType,
			String content, Long contentType, Long contentTime,Long appSendTime) throws BizException ;
 
}
