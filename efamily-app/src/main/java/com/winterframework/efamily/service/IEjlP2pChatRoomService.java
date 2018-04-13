package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlP2pChatRoom;

public interface IEjlP2pChatRoomService extends IBaseService<EjlP2pChatRoom> {

	public Long getP2pChatRoomId(Context ctx,Long sendUserId,Long receiveUserId) throws BizException;
}
