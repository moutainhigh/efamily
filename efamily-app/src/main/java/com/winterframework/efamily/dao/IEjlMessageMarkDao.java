package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dto.MessageCountInfo;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.entity.EjlMessageMark;


public interface IEjlMessageMarkDao extends IEjlComMessageMarkDao {

	public List<MessageNoReadInfoResponse> getMessageByChatObj(EjlMessageMark ejlMessageMark) throws BizException ;
	
	public List<MessageNoReadInfoResponse> getLashMessageByChatObj(EjlMessageMark ejlMessageMark) throws BizException ;
	
	public List<MessageCountInfo> getNoReadMessageCount(EjlMessageMark ejlMessageMark) throws BizException ;
	
	public List<MessageNoReadInfoResponse> getP2PMessageByChatObj(EjlMessageMark ejlMessageMark) throws BizException ;
	
	public List<MessageCountInfo> getP2PNoReadMessageCount(EjlMessageMark ejlMessageMark) throws BizException ;
	
	public long updateMessageMarkStatus(EjlMessageMark ejlMessageMark) throws BizException ;
	
	public Long getTowMessageIdInternalCount(EjlMessageMark ejlMessageMark) throws BizException ;
}
