package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlMessageMarkDao;
import com.winterframework.efamily.dto.MessageCountInfo;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.entity.EjlMessageMark;
import com.winterframework.efamily.service.IEjlMessageMarkService;
import com.winterframework.efamily.service.IEjlP2pChatRoomService;

@Service("ejlMessageMarkServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlMessageMarkServiceImpl extends BaseServiceImpl<IEjlMessageMarkDao, EjlMessageMark>
		implements IEjlMessageMarkService {

	
	@Resource(name = "ejlP2pChatRoomServiceImpl")
	private IEjlP2pChatRoomService 	ejlP2pChatRoomServiceImpl;
	
	@Resource(name = "ejlMessageMarkDaoImpl")
	private IEjlMessageMarkDao ejlMessageMarkDaoImpl;

	@Override
	protected IEjlMessageMarkDao getEntityDao() {
		return ejlMessageMarkDaoImpl;
	}

	public List<MessageCountInfo> getAllNoReadMessageCount(Long receiveUserId,Long status) throws BizException {
		List<MessageCountInfo> messageCountInfoList = new ArrayList<MessageCountInfo>();
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setStatus(status);
		List<MessageCountInfo> messageCountInfoListTemp = ejlMessageMarkDaoImpl.getNoReadMessageCount(ejlMessageMark);
		if(messageCountInfoListTemp != null){
			messageCountInfoList.addAll(messageCountInfoListTemp);
		}
		ejlMessageMark.setChatType(EfamilyConstant.CHAT_TYPE_P2P);
		List<MessageCountInfo> messageCountInfoListP2P = ejlMessageMarkDaoImpl.getP2PNoReadMessageCount(ejlMessageMark);
		if(messageCountInfoListP2P != null){
			messageCountInfoList.addAll(messageCountInfoListP2P);
		}
		return messageCountInfoList ;
	}
	
	public List<MessageNoReadInfoResponse> getAllMessageByChatObj(Context ctx,Long sendUserId,Long receiveUserId,Long chatRoomId,Long chatType,Long page,Long pageSize) throws BizException {
		List<MessageNoReadInfoResponse> ejlMessageMarkList = new ArrayList<MessageNoReadInfoResponse>();
		//*** 如果是单人对单人聊天   需要根据sendUserId, receiveUserId 转换为 chatRoomId
		if(EfamilyConstant.CHAT_TYPE_P2P == chatType.longValue() ){
			chatRoomId = ejlP2pChatRoomServiceImpl.getP2pChatRoomId(ctx,sendUserId, receiveUserId);
		}
		//*** 拉取聊天信息
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setSendUserId(sendUserId);
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setChatRoomId(chatRoomId);
		ejlMessageMark.setChatType(chatType);
		ejlMessageMark.setPage(page);
		ejlMessageMark.setPageSize(pageSize+1);//查询时 多查一条  将messageId设置到前面的一条消息中
		List<MessageNoReadInfoResponse> ejlMessageMarkListTemp = ejlMessageMarkDaoImpl.getMessageByChatObj(ejlMessageMark);
		if(ejlMessageMarkListTemp != null){
			ejlMessageMarkList.addAll(ejlMessageMarkListTemp);
		}
		
		return ejlMessageMarkList;
	}
	
	public Long updateMessageMarkStatus(Context ctx,Long messageId,Long sendUserId,Long receiveUserId,Long status,Long chatRoomId,Long chatType) throws BizException {
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setStatus(status);
		ejlMessageMark.setMessageId(messageId+1);
		//*** 如果是单人对单人聊天   需要根据sendUserId, receiveUserId 转换为 chatRoomId
		if(EfamilyConstant.CHAT_TYPE_P2P == chatType.longValue() ){
			chatRoomId = ejlP2pChatRoomServiceImpl.getP2pChatRoomId(ctx,sendUserId, receiveUserId);
		}
		ejlMessageMark.setChatRoomId(chatRoomId);
		ejlMessageMark.setChatType(chatType);
		ejlMessageMark.setReceiveUserId(receiveUserId);
		return ejlMessageMarkDaoImpl.updateMessageMarkStatus(ejlMessageMark);
	}
	
	public Long getTowMessageIdInternalCount(Context ctx,Long messageId,Long sendUserId,Long receiveUserId,Long chatRoomId,Long chatType) throws BizException {
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setMessageId(messageId);
		//*** 如果是单人对单人聊天   需要根据sendUserId, receiveUserId 转换为 chatRoomId
		if(EfamilyConstant.CHAT_TYPE_P2P == chatType.longValue() ){
			chatRoomId = ejlP2pChatRoomServiceImpl.getP2pChatRoomId(ctx,sendUserId, receiveUserId);
		}
		ejlMessageMark.setChatRoomId(chatRoomId);
		ejlMessageMark.setChatType(chatType);
		
		return ejlMessageMarkDaoImpl.getTowMessageIdInternalCount(ejlMessageMark);
	}
	
	
	
	public List<MessageNoReadInfoResponse> getP2PMessageByChatObj(Long sendUserId,Long receiveUserId,Long page,Long pageSize) throws BizException {
		Long chatType = 2L;
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setSendUserId(sendUserId);
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setChatType(chatType);
		ejlMessageMark.setPage(page);
		ejlMessageMark.setPageSize(pageSize);
		
		return ejlMessageMarkDaoImpl.getP2PMessageByChatObj(ejlMessageMark);
	}
	
	public List<MessageNoReadInfoResponse> getLashMessageByChatObj(Long receiveUserId) throws BizException {

		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setStatus(0L);
		return ejlMessageMarkDaoImpl.getLashMessageByChatObj(ejlMessageMark);
	}
	
	
	
	public List<MessageCountInfo> getP2PNoReadMessageCount(Long receiveUserId,Long status) throws BizException {
		Long chatType = 2L;
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setChatType(chatType);
		ejlMessageMark.setStatus(status);
		return ejlMessageMarkDaoImpl.getNoReadMessageCount(ejlMessageMark);
	}
	
	
	public List<MessageNoReadInfoResponse> getMessageByChatObj(Long sendUserId,Long receiveUserId,Long chatRoomId,Long chatType,Long page,Long pageSize) throws BizException {
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setSendUserId(sendUserId);
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setChatRoomId(chatRoomId);
		ejlMessageMark.setChatType(chatType);
		ejlMessageMark.setPage(page);
		ejlMessageMark.setPageSize(pageSize);
		
		return ejlMessageMarkDaoImpl.getMessageByChatObj(ejlMessageMark);
	}
	
	public List<MessageCountInfo> getNoReadMessageCount(Long receiveUserId,Long status) throws BizException {
		EjlMessageMark ejlMessageMark = new EjlMessageMark();
		ejlMessageMark.setReceiveUserId(receiveUserId);
		ejlMessageMark.setStatus(status);
		
		return ejlMessageMarkDaoImpl.getNoReadMessageCount(ejlMessageMark);
	}
	
}