package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.dto.ChatGroupDetailsList;
import com.winterframework.efamily.dto.UserChartRoomMemberInfo;
import com.winterframework.efamily.entity.EjlUserChartRoom;

public interface IEjlUserChartRoomDao  extends IEjlComUserChartRoomDao {
	public int updateUserChatGroupInfo(EjlUserChartRoom ejlUserChartRoom);
	public EjlUserChartRoom getByUserIdAndChatRoomId(EjlUserChartRoom ejlUserChartRoom);
	public List<ChatGroupDetailsList> getChatGroupDetailsList(EjlUserChartRoom ejlUserChartRoom);
	public List<UserChartRoomMemberInfo> getChatGroupMemberInfo(EjlUserChartRoom ejlUserChartRoom);
}
