package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComP2pChatRoomDao;
import com.winterframework.efamily.entity.EjlP2pChatRoom;
import com.winterframework.efamily.service.IEjlComP2pChatRoomService;

	@Service("ejlComP2pChatRoomServiceImpl")
	@Transactional(rollbackFor = Exception.class)
	public class EjlComP2pChatRoomServiceImpl extends BaseServiceImpl<IEjlComP2pChatRoomDao,EjlP2pChatRoom> implements IEjlComP2pChatRoomService {
		@Resource(name="ejlComP2pChatRoomDaoImpl")
		private IEjlComP2pChatRoomDao ejlComP2pChatRoomDaoImpl;
		@Override
		protected IEjlComP2pChatRoomDao getEntityDao() { 
			return ejlComP2pChatRoomDaoImpl;
		}
	}
