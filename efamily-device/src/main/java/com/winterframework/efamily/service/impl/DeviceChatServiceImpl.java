package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.device.DeviceChatSettingRequest;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlMessageMark;
import com.winterframework.efamily.enums.DeviceParameter;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceChatService;
import com.winterframework.efamily.service.IDeviceParamConfigService;
import com.winterframework.efamily.service.IEjlComChartRoomUserService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComMessageMarkService;
import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("deviceChatServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceChatServiceImpl extends DeviceParamConfigServiceImpl implements IDeviceChatService{ 
	@Resource(name="deviceParamConfigServiceImpl")
	private IDeviceParamConfigService deviceParamConfigService; 
	@Resource(name="ejlComMessageServiceImpl")
	private IEjlComMessageService ejlComMessageService; 
	@Resource(name="ejlComChartRoomUserServiceImpl")
	private IEjlComChartRoomUserService ejlComChartRoomUserService; 
	@Resource(name="ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserService; 
	@Resource(name="ejlComMessageMarkServiceImpl")
	private IEjlComMessageMarkService ejlComMessageMarkService;  
	
	@Resource(name="notificationUtil")
	private NotificationUtil notificationUtil; 
	

	@Override
	public void settingUpload(Context ctx,DeviceChatSettingRequest bizReq) throws BizException {
		/**
		 * 1.比对数据库和设备的聊天设置 
		 * 2.如果不一致则推送更新
		 */		
		String audio=bizReq.getAudio();
		int volume=bizReq.getVolume();
		log.info("service get:audio="+audio+" volume="+volume);
		String key=DeviceParameter.CHAT_SETTING.getValue();
		EjlDeviceParmConfig param=new EjlDeviceParmConfig();
		param.setDeviceId((Long)ctx.get("deviceid"));
		param.setParamKey(key);
		param.setParamValue(JsonUtils.toJson(bizReq)); 
		deviceParamConfigService.checkAndSetting(ctx, param);
	}
	@Override
	public void receive(Context ctx,Long userId, Long deviceId,EjlMessage message) throws BizException {
		/**
		 * 1.获取用户所在的chatroom
		 * 2.消息入库 (未推送)
		 * 3.推送消息??任务推送
		 */	
		log.debug("welcome to chat service.");
		
		//*** 消息查重  
		EjlMessage ejlMessageCheck = new EjlMessage();
		ejlMessageCheck.setAppSendTime(message.getAppSendTime());
		ejlMessageCheck.setSendUserId(message.getSendUserId());
		ejlMessageCheck = ejlComMessageService.selectOneObjByAttribute(null, ejlMessageCheck);
		if(ejlMessageCheck != null){
        	log.error(" 消息重复发送。 ejlMessageCheck = " + ejlMessageCheck.toString()+" sendUserId = " + message.getSendUserId()+" ; receiveUserId = "+message.getReceiveUserId());
        	//throw new BizException(StatusBizCode.MESSAGE_REPEAT_SEND.getValue());
        	return;
		}
		
		message.setStatus(0);
		ejlComMessageService.save(ctx,message);
		/*EjlFamilyUser familyUser=ejlComFamilyUserService.getEffectiveByUserId(message.getSendUserId());
		if(null==familyUser){
			throw new BizException(StatusBizCode.USER_NO_FAMILY.getValue(),new String[]{message.getSendUserId()+""});
		}*/
		/*List<EjlChartRoomUser> chatRoomUserList=ejlComChartRoomUserService.getEffectiveByRoomId(message.getChatRoomId());
		if(null==chatRoomUserList){
			throw new BizException(StatusBizCode.CHAT_ROOM_UN_VALID.getValue(),new String[]{message.getChatRoomId()+""});
		}*/
		//手表所在家庭的非手表用户
		/*List<EjlFamilyUser> familyUserList=ejlComFamilyUserService.getEffectiveByFamilyId(familyUser.getFamilyId());
		if(null==familyUserList){
			throw new BizException(StatusBizCode.FAMILY_UN_VALID.getValue(),new String[]{message.getChatRoomId()+""});
		}
		excludeWatchUserExceptSelf(familyUser.getUserId(),familyUserList);
		for(EjlFamilyUser fmluser:familyUserList){
			EjlMessageMark messageMark = new EjlMessageMark();
			if (message.getSendUserId().longValue() == fmluser.getUserId().longValue()) {
				//*** 发送者的信息默认 发送成功
				messageMark.setSendStatus(EfamilyConstant.CHAT_MESSAGE_SEND_SUCCESS);
				messageMark.setStatus(EfamilyConstant.MESSAGE_STATUS_YES_READ);
			}else{
				messageMark.setSendStatus(EfamilyConstant.CHAT_MESSAGE_SEND_NOT);
				messageMark.setStatus(EfamilyConstant.MESSAGE_STATUS_NO_READ);
			}
			messageMark.setSendUserId(message.getSendUserId());
			messageMark.setReceiveUserId(fmluser.getUserId());
			messageMark.setMessageId(message.getId());
			messageMark.setContent(message.getContent());
			messageMark.setContentType(message.getContentType());
			messageMark.setSendTime(DateUtils.currentDate());
			messageMark.setContentTime(message.getContentTime());
			messageMark.setChatType(message.getChatType());
			messageMark.setChatRoomId(message.getChatRoomId());
			ejlComMessageMarkService.save(ctx,messageMark);
		}
		for(EjlFamilyUser fmluser:familyUserList){
			if(fmluser.getUserId()==familyUser.getUserId()) continue; 
			
			NotyTarget t=new NotyTarget(fmluser.getUserId(),null); 
			Map<String,String> data=new HashMap<String,String>();
			data.put("sendUserType", "2");
			data.put("content", message.getContent());
			data.put("contentType", message.getContentType()+"");
			data.put("contentTime", message.getContentTime()+"");
			data.put("sendTime", new Date().getTime()+"");
			data.put("chatGroupId", familyUser.getUserId()+"");
			data.put("chatType", message.getChatType()+"");
			data.put("sendUserId", userId+"");
			data.put("messageId", message.getId()+"");
			data.put("senderName", ctx.get("nickName")+"");
			NotyMessage message2=new NotyMessage();
			message2.setId(message.getId());
			message2.setType(NotyMessage.Type.MSG);
			message2.setCommand(EfamilyConstant.PUSH);
			message2.setData(data);
			Notification notification=new Notification();
			notification.setTarget(t);
			notification.setMessage(message2);
			 
			notificationUtil.notification(notification);
		}*/
	}
	/**
	 * 排除手表用户（除了手表自身）
	 * @param userId
	 * @param familyUserList
	 */
	private void excludeWatchUserExceptSelf(Long userId,List<EjlFamilyUser> familyUserList) {
		Iterator<EjlFamilyUser> iter=familyUserList.iterator();
		while (iter.hasNext()) {
			EjlFamilyUser fmluser=iter.next();
			if(EjlFamilyUser.Type.WATCH.getValue()==fmluser.getType().longValue() && userId.longValue()!=fmluser.getUserId().longValue()){
				iter.remove();
			}
		}
	}
}
