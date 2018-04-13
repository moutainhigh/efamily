package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserP2pChatRoomDao;
import com.winterframework.efamily.entity.EjlUserP2pChatRoom;
import com.winterframework.efamily.service.IEjlComUserP2pChatRoomService;

@Service("ejlComUserP2pChatRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserP2pChatRoomServiceImpl extends
		BaseServiceImpl<IEjlComUserP2pChatRoomDao, EjlUserP2pChatRoom>
		implements IEjlComUserP2pChatRoomService {
	@Resource(name = "ejlComUserP2pChatRoomDaoImpl")
	private IEjlComUserP2pChatRoomDao ejlComUserP2pChatRoomDao;

	@Override
	protected IEjlComUserP2pChatRoomDao getEntityDao() {
		return ejlComUserP2pChatRoomDao;
	}
}
