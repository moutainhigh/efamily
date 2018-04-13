/**   
* @Title: IDeviceRemindService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-7 下午4:00:25 
* @version V1.0   
*/
package com.winterframework.efamily.service;


import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.dto.device.DeviceChatSettingResponse;

/** 
* @ClassName: IDeviceRemindService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-7 下午4:00:25 
*  
*/
public interface IDeviceRemindService {
	/**
	 * 上传设备提醒设置
	 * @param userId
	 * @param deviceId
	 * @param bizReq
	 * @return
	 * @throws BizException
	 */
	DeviceChatSettingResponse upload(Long userId,Long deviceId,DeviceChatSettingRequest bizReq) throws BizException;
}
