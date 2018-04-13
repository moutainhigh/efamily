package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IEjlUserChartRoomDao;
import com.winterframework.efamily.dto.ChatGroupDetailsList;
import com.winterframework.efamily.dto.UserChartRoomMemberInfo;
import com.winterframework.efamily.entity.EjlUserChartRoom;

@Repository("ejlUserChartRoomDaoImpl")
public class EjlUserChartRoomDaoImpl extends EjlComUserChartRoomDaoImpl<EjlUserChartRoom> implements IEjlUserChartRoomDao {
	
	@Override
	public int updateUserChatGroupInfo(EjlUserChartRoom ejlUserChartRoom){
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByUserIdAndChatId"), ejlUserChartRoom);
	}
	
	@Override
	public EjlUserChartRoom getByUserIdAndChatRoomId(EjlUserChartRoom ejlUserChartRoom){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByUserIdAndChatRoomId"), ejlUserChartRoom);
	}
	@Override
	public List<ChatGroupDetailsList> getChatGroupDetailsList(EjlUserChartRoom ejlUserChartRoom){
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getChatGroupDetailsByUserIdAndChatId"), ejlUserChartRoom);
	}
	
	@Override
	public List<UserChartRoomMemberInfo> getChatGroupMemberInfo(EjlUserChartRoom ejlUserChartRoom){
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getChatGroupMemberInfo"), ejlUserChartRoom);
	}
	
	
	
}
