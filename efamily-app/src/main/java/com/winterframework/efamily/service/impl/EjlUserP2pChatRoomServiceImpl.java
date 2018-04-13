package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlUserP2pChatRoomDao;
import com.winterframework.efamily.entity.EjlUserP2pChatRoom;
import com.winterframework.efamily.service.IEjlUserP2pChatRoomService;

@Service("ejlUserP2pChatRoomServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserP2pChatRoomServiceImpl extends BaseServiceImpl<IEjlUserP2pChatRoomDao,EjlUserP2pChatRoom> implements IEjlUserP2pChatRoomService {
	@Resource(name="ejlUserP2pChatRoomDaoImpl")
	private IEjlUserP2pChatRoomDao ejlUserP2pChatRoomDao;
	@Override
	protected IEjlUserP2pChatRoomDao getEntityDao() { 
		return ejlUserP2pChatRoomDao;
	}
}
