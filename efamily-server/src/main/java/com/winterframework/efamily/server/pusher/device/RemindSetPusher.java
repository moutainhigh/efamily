/**   
* @Title: BatteryGetPusher.java 
* @Package com.winterframework.efamily.server.push.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-6 下午12:03:27 
* @version V1.0   
*/
package com.winterframework.efamily.server.pusher.device;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;

/** 
* @ClassName: BatteryGetPusher 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-6 下午12:03:27 
*  
*/
/**
 * 提醒设置
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
@Service("remindSetPusher")
public class RemindSetPusher extends AbstractPusher{
	@Override
	protected Map<String, String> getData(Context ctx, FmlRequest req) throws ServerException {
		/*Map<String,String> data=new HashMap<String,String>();
		DeviceRemindStruc struc = new DeviceRemindStruc();
		struc.setContent(req.getValue("content"));
		struc.setPeriodType(Integer.valueOf(req.getValue("periodType")));
		struc.setTime(Long.valueOf(req.getValue("time")));
		struc.setType(Long.valueOf(req.getValue("type"))==1?"audio":Long.valueOf(req.getValue("type"))==2?"video":"txt");
		struc.setVolume(req.getValue("volume"));
		data.put("remind",req.getData().get("remind"));
		data.put("deviceId", req.getValue("deviceId"));*/
		DeviceChatSettingRequest bizReq=new DeviceChatSettingRequest();
		bizReq.setAudio(req.getValue("audio"));
		bizReq.setVolume(Integer.valueOf(req.getValue("volume")));
		Map<String,String> data=new HashMap<String,String>();
		data.put("data", JsonUtils.toJson(bizReq));
		return data;
	}

}
