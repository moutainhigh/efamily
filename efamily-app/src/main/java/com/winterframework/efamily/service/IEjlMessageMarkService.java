package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.MessageCountInfo;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.entity.EjlMessageMark;

public interface IEjlMessageMarkService  extends IBaseService<EjlMessageMark>{
 
	public List<MessageCountInfo> getAllNoReadMessageCount(Long receiveUserId,Long sendStatus) throws BizException ;
	
	public List<MessageNoReadInfoResponse> getAllMessageByChatObj(Context ctx,Long sendUserId,Long receiveUserId,Long chatRoomId,Long chatType,Long page,Long pageSize) throws BizException ;
	
	public List<MessageNoReadInfoResponse> getLashMessageByChatObj(Long receiveUserId) throws BizException ;
	
	public List<MessageNoReadInfoResponse> getP2PMessageByChatObj(Long sendUserId,Long receiveUserId,Long page,Long pageSize) throws BizException ;
	
	public List<MessageCountInfo> getP2PNoReadMessageCount(Long receiveUserId,Long sendStatus) throws BizException ;
	
	public List<MessageNoReadInfoResponse> getMessageByChatObj(Long sendUserId,Long receiveUserId,Long chatRoomId,Long chatType,Long page,Long pageSize) throws BizException ;
	
	public List<MessageCountInfo> getNoReadMessageCount(Long receiveUserId,Long sendStatus) throws BizException;
	
	public Long updateMessageMarkStatus(Context ctx,Long messageId,Long sendUserId,Long receiveUserId,Long sendStatus,Long chatRoomId,Long chatType) throws BizException ;
	
	public Long getTowMessageIdInternalCount(Context ctx,Long messageId,Long sendUserId,Long receiveUserId,Long chatRoomId,Long chatType) throws BizException;
}
