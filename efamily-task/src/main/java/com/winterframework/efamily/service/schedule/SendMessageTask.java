package com.winterframework.efamily.service.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComMessageDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlMessageMark;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlComChartRoomService;
import com.winterframework.efamily.service.IEjlComMessageMarkService;
import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.utils.NotificationUtil;

/** 
* @ClassName: 消息发送
* @Description:
* @author floy 
* @date 2013-10-23 上午10:31:32 
*  
*/
public class SendMessageTask {

	private Logger log = LoggerFactory.getLogger(NoticeDisposableTask.class);

	@Resource(name = "ejlComMessageDaoImpl")
	private IEjlComMessageDao ejlMessageDao;
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name = "ejlComMessageMarkServiceImpl")
	private IEjlComMessageMarkService 	ejlMessageMarkServiceImpl;
	
	@Resource(name = "ejlComMessageServiceImpl")
	private IEjlComMessageService ejlMessageServiceImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;	
	
	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;
	
	@Resource(name="ejlComChartRoomServiceImpl")
	private IEjlComChartRoomService ejlChartRoomService;
	
	

	public void execute() throws Exception {
		EjlMessage ejlMessageQuery = new EjlMessage();
		ejlMessageQuery.setStatus(EfamilyConstant.EJL_MESSAGE_STATUS_NOT_OPT);
		List<EjlMessage> list = ejlMessageDao.getAllByEntity(ejlMessageQuery);
		if(!list.isEmpty()){
			for(EjlMessage em:list){
				Long sendUserId = em.getSendUserId();
				Long receiveUserType = em.getChatType();
				EjlUser sendUser = ejlUserDaoImpl.getById(sendUserId);
				Context ctx = new Context();
				ctx.set("userId", em.getReceiveUserId());
				List<EjlUser> userList = new ArrayList<EjlUser>();
				em.setStatus(EfamilyConstant.EJL_MESSAGE_STATUS_YES_OPT);
				ejlMessageServiceImpl.save(ctx, em);
		        //******** 根据条件，组装需要发送到用户 userList  和  发送到消息 em   *************
				if (EfamilyConstant.CHAT_TYPE_WATCH == receiveUserType) {
					//*** 手表和APP，只能是当前操作的手表，家庭中其他手表不需要发送信息
					EjlUser receiveUser = ejlUserDaoImpl.getById(em.getReceiveUserId());
					EjlUser userParm = new EjlUser();
					userParm.setFamilyId(sendUser.getFamilyId());
					userParm.setType(EfamilyConstant.USER_TYPE_APP);
					userList = ejlUserDaoImpl.getEjlUserByAttribute(userParm);
					if (receiveUser.getType() == EfamilyConstant.USER_TYPE_WATCH) {
						userList.add(receiveUser);
					}
					em.setSenderName(sendUser.getNickName());
					
				} else if (EfamilyConstant.CHAT_TYPE_P2P == receiveUserType) {
					//*** 点对点的聊天
					EjlUser receiveUser = ejlUserDaoImpl.getById(em.getReceiveUserId());
					userList.add(receiveUser);
					userList.add(sendUser);
					em.setSenderName(sendUser.getNickName());
					
					
				} else if (EfamilyConstant.CHAT_TYPE_ROOM == receiveUserType) {
					//*** 群聊，聊天室中的每个成员(除发送者外)都需要接收消息
					userList = ejlUserDaoImpl.getChatRoomUserByRoomId(em.getChatRoomId());
					EjlChartRoom chatRoom=ejlChartRoomService.get(em.getChatRoomId());
					em.setSenderName(chatRoom.getName());
				}else if (EfamilyConstant.CHAT_TYPE_FAMILY == receiveUserType) {
					//*** 固定家庭聊天室，所有设备和所有APP用户 ，不包括僵尸用户
					userList = ejlUserDaoImpl.getUserByDeviceAndApp(em.getChatRoomId());
					log.error("AAAAAABBBBBB: userList = " + userList);
					List<EjlUser> attentionList =  ejlUserDaoImpl.getAttentionUserByFamilyId(em.getChatRoomId());
					log.error("AAAAAABBBBBB: attentionList = " + attentionList);
					if(attentionList!=null && attentionList.size()>0){
						//userList.addAll(attentionList);
						for (EjlUser ejlUser:attentionList) {
							if(!userList.contains(ejlUser)){
								userList.add(ejlUser);
							}
						}
					}
					em.setSenderName(sendUser.getNickName());
					
				}  else {
					log.error("发送聊天信息时，聊天类型未定义，发送失败。 chatType = " + receiveUserType);
		        	throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
				}
				if (userList == null || userList.size() == 0) {
					log.error("发送聊天信息时，接收聊天信息的用户为空，发送失败。 chatType = " + receiveUserType);
					return;
				}
				//*** 发送消息
				for (int i = 0; i < userList.size(); i++) {
					EjlUser userTemp = userList.get(i);
					EjlMessageMark messageMark = new EjlMessageMark();
					if (userTemp.getId().longValue() == sendUserId.longValue()) {
						//*** 发送者的信息默认 发送成功
						messageMark.setSendStatus(EfamilyConstant.CHAT_MESSAGE_SEND_SUCCESS);
						messageMark.setStatus(EfamilyConstant.MESSAGE_STATUS_YES_READ);
					}else{
						messageMark.setSendStatus(EfamilyConstant.CHAT_MESSAGE_SEND_NOT);
						messageMark.setStatus(EfamilyConstant.MESSAGE_STATUS_NO_READ);
					}
					messageMark.setSendUserId(sendUserId);
					messageMark.setReceiveUserId(userTemp.getId());
					messageMark.setMessageId(em.getId());
					messageMark.setContent(em.getContent());
					messageMark.setContentType(em.getContentType());
					messageMark.setGmtCreated(new Date());
					messageMark.setSendTime(new Date());
					messageMark.setContentTime(em.getContentTime());
					messageMark.setChatType(receiveUserType);
					messageMark.setChatRoomId(em.getChatRoomId());
					ejlMessageMarkServiceImpl.save(ctx,messageMark);
					if (userTemp.getId().longValue() == sendUserId.longValue()) {
						//****  发送者自己不需要接收消息  
		                continue;
					}
					try {
						if(EfamilyConstant.USER_TYPE_APP == userTemp.getType().longValue()){ 
							NotyTarget t=new NotyTarget(userTemp.getId(),null);							 
							Map<String,String> data=new HashMap<String,String>(); 
							data.put("sendUserType", "2");
							data.put("content", em.getContent());
							data.put("contentType", em.getContentType()+"");
							data.put("contentTime", em.getContentTime()+"");
							data.put("sendTime", new Date().getTime()+"");
							data.put("chatGroupId", em.getChatRoomId()+"");
							data.put("chatType", em.getChatType()+"");
							data.put("sendUserId", em.getSendUserId()+"");
							data.put("messageId", em.getId()+"");
							data.put("senderName", em.getSenderName());
							NotyMessage message=new NotyMessage();
							message.setId(em.getId());
							message.setType(NotyMessage.Type.MSG);
							message.setCommand(EfamilyConstant.PUSH);
							message.setData(data);
							Notification notification=new Notification();
							notification.setTarget(t);
							notification.setMessage(message);
							notification.setRealTime(false);
							if(em.getContentType()!=null && (em.getContentType().intValue() == EjlMessage.ContentType.CLIENT.getCode()||em.getContentType().intValue() == EjlMessage.ContentType.TIPS.getCode())){
								notification.setRealTime(true);//客户端自定义的信息 不进行离线推送
							}
							notificationUtil.notification(notification);
						}else if(EfamilyConstant.USER_TYPE_WATCH == userTemp.getType().longValue()){
							//非手机用户，不发tips消息
							if(em.getContentType()!=null && em.getContentType().intValue() == EjlMessage.ContentType.TIPS.getCode()){
								continue;
							}
							Long deviceId = null;
							EjlUserDevice userDevice = new EjlUserDevice();
							userDevice.setUserId(userTemp.getId());
							userDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
							userDevice = ejlUserDeviceDaoImpl.getEjlUserDevice(userDevice);
							if(userDevice != null){
								deviceId = userDevice.getDeviceId();
							}
							
							
							DeviceChatMessageRequest bizReq=new DeviceChatMessageRequest();
							bizReq.setSenderId(sendUserId);
							bizReq.setReceiverId(ctx.getUserId());
							//bizReq.setChatRoomId(userTemp.getFamilyId());
							bizReq.setChatRoomId(em.getChatRoomId());
							bizReq.setType(getResourceType((Integer)em.getContentType().intValue()));	//内容类型 1 文字 2 音频 3 视频 4 图片
							bizReq.setContent(em.getContent());
							bizReq.setContentTime(em.getContentTime());
							bizReq.setTime(DateUtils.getCurTime());
							bizReq.setChatType(em.getChatType());
							
							Map<String,String> data = new HashMap<String,String>(); 
							data.put("data", JsonUtils.toJson(bizReq)); 
							
							NotyTarget t=new NotyTarget(userTemp.getId(),deviceId);	
							NotyMessage message=new NotyMessage();
							message.setId(em.getId());
							message.setType(NotyMessage.Type.MSG);
							message.setCommand(NoticeType.APP_DEVICE_SEND_MESSAGE.getValue());
							message.setData(data);
							Notification notification=new Notification();
							notification.setTarget(t);
							notification.setMessage(message); 
							notification.setRealTime(false);
							notificationUtil.notification(notification);
							
						}else if(EfamilyConstant.USER_TYPE_NO_WATCH == userTemp.getType().longValue()){
							log.warn("发消息给无设备用户，发送失败： sendUserId = "+sendUserId+" ; receiveUserId = "+userTemp.getId()+" ;  ");
						}else{
							log.warn("发送消息时，用户类型未定义，发送失败： sendUserId = "+sendUserId+" ; receiveUserId = "+userTemp.getId()+" ;  ");
						}
						
					} catch (Exception e) { 
						log.info("发送消息时出现异常：  userId = "+userTemp.getId(),e);
					}
				}
			}
		}
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
