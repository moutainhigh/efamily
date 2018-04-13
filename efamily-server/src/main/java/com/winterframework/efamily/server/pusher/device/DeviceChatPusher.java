package com.winterframework.efamily.server.pusher.device;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
 
/**
 * 设备聊天消息推送类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("deviceChatPusher")
public class DeviceChatPusher extends AbstractPusher{
	@Override
	protected Map<String, String> getData(Context ctx,
			Map<String, String> data) throws ServerException {
		DeviceChatMessageRequest bizReq=new DeviceChatMessageRequest();
		bizReq.setSenderId(Long.valueOf(data.get("userId")));
		bizReq.setReceiverId(ctx.getUserId());
		bizReq.setChatRoomId(Long.valueOf(data.get("chatRoomId")));
		bizReq.setType(getResourceType(Integer.valueOf(data.get("type"))));	//内容类型 1 文字 2 音频 3 视频 4 图片
		bizReq.setContent(data.get("content"));
		bizReq.setContentTime(Long.valueOf(data.get("contentTime")));
		bizReq.setTime(Long.valueOf(data.get("time")));
		data.clear();
		data.put("data", JsonUtils.toJson(bizReq));
		return data;
	}
	private String getResourceType(Integer type){
		Map<Integer,String> m=new HashMap<Integer,String>();
		m.put(1, ResourceType.TEXT);
		m.put(2, ResourceType.AUDIO);
		m.put(3, ResourceType.VIDEO);
		m.put(4, ResourceType.IMAGE);
		return m.get(type);		
	}
}