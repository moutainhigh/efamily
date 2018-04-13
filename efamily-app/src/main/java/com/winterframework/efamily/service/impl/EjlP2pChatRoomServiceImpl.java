package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlP2pChatRoomDao;
import com.winterframework.efamily.entity.EjlP2pChatRoom;
import com.winterframework.efamily.service.IEjlP2pChatRoomService;

@Service("ejlP2pChatRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlP2pChatRoomServiceImpl extends BaseServiceImpl<IEjlP2pChatRoomDao, EjlP2pChatRoom> implements IEjlP2pChatRoomService {


	@Resource(name="ejlP2pChatRoomDaoImpl")
	private IEjlP2pChatRoomDao ejlP2pChatRoomDao;
	@Override
	protected IEjlP2pChatRoomDao getEntityDao() { 
		return ejlP2pChatRoomDao;
	}
	
	public Long getP2pChatRoomId(Context ctx,Long sendUserId,Long receiveUserId) throws BizException{
        Long chatRoomId = null;
		EjlP2pChatRoom ejlP2pChatRoom = new EjlP2pChatRoom();
		Long userIdLitle = sendUserId>receiveUserId?receiveUserId:sendUserId;
		Long userIdBig = sendUserId>receiveUserId?sendUserId:receiveUserId;
		ejlP2pChatRoom.setUserIdLitle(userIdLitle);
		ejlP2pChatRoom.setUserIdBig(userIdBig);
		EjlP2pChatRoom ejlP2pChatRoomData = ejlP2pChatRoomDao.selectOneObjByAttribute(ejlP2pChatRoom);
		if(ejlP2pChatRoomData != null){
			chatRoomId = ejlP2pChatRoomData.getId();
		}else{
			save(ctx,ejlP2pChatRoom);
			chatRoomId = ejlP2pChatRoom.getId();
		}		
		return chatRoomId;
	}
	
}
