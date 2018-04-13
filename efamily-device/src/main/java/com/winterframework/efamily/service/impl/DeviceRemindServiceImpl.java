package com.winterframework.efamily.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.dto.device.DeviceChatSettingResponse;
import com.winterframework.efamily.service.IDeviceRemindService;
 

@Service("deviceRemindServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceRemindServiceImpl extends DeviceParamConfigServiceImpl implements IDeviceRemindService{
	private static final Logger log= LoggerFactory.getLogger(DeviceChatServiceImpl.class);
	/*@Resource(name="deviceTestDaoImpl")
	private IDeviceTestDao deviceTestDao; */

	@Override
	public DeviceChatSettingResponse upload(Long userId,Long deviceId,DeviceChatSettingRequest bizReq) throws BizException {
		/**
		 * 1.比对数据库和设备的聊天设置 
		 * 2.如果不一致则更新设备的真实值和标识，以备定时推送更新
		 */		
		String audio=bizReq.getAudio();
		int volume=bizReq.getVolume();
		/*?????
		List<DeviceAddressList> dbChatList=getByDeviceId(deviceId);
		if(!compare(dbChatList,contactsList)){	//不一致 则更新标识
			for(dbChatList){
				setFlag(1);
				setRealData(json);
			}
			save(dbChatList);
		}*/
		
		DeviceChatSettingResponse bizRes=new DeviceChatSettingResponse();
		return bizRes;
	}
	
}
