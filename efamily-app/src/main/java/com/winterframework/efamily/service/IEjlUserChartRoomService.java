package com.winterframework.efamily.service;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlUserChartRoom;

public interface IEjlUserChartRoomService extends IBaseService<EjlUserChartRoom>{

	public EjlUserChartRoom getByUserIdAndChatRoomId(Long userId,Long chartRoomId);
}