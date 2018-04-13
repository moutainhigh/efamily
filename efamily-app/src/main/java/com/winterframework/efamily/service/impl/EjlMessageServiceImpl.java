package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlChartRoomUserDao;
import com.winterframework.efamily.dao.IEjlMessageDao;
import com.winterframework.efamily.dao.IEjlMessageMarkDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.dto.AttentionUserStruc;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.DeviceChatMessageRequest;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.entity.EjlChartRoomUser;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlMessageMark;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.EjlUserFriend;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlAttentionUserService;
import com.winterframework.efamily.service.IEjlChartRoomService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlMessageMarkService;
import com.winterframework.efamily.service.IEjlMessageService;
import com.winterframework.efamily.service.IEjlP2pChatRoomService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("ejlMessageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlMessageServiceImpl extends BaseServiceImpl<IEjlMessageDao, EjlMessage> implements IEjlMessageService {

	private static final Logger log = LoggerFactory.getLogger(EjlMessageServiceImpl.class);

	@Resource(name = "ejlMessageDaoImpl")
	private IEjlMessageDao ejlMessageDao;

	@Resource(name = "ejlMessageMarkDaoImpl")
	private IEjlMessageMarkDao ejlMessageMarkDao;

	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;

	@Resource(name = "ejlUserFriendDaoImpl")
	private IEjlUserFriendDao ejlUserFriendDaoImpl;

	@Resource(name = "ejlChartRoomUserDaoImpl")
	private IEjlChartRoomUserDao ejlChartRoomUserDaoImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;

	@Resource(name = "ejlMessageMarkServiceImpl")
	private IEjlMessageMarkService ejlMessageMarkServiceImpl;

	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;

	@Resource(name = "ejlP2pChatRoomServiceImpl")
	private IEjlP2pChatRoomService ejlP2pChatRoomServiceImpl;
	@Resource(name = "ejlChartRoomServiceImpl")
	private IEjlChartRoomService ejlChartRoomService;

	@Resource(name = "ejlAttentionUserServiceImpl")
	private IEjlAttentionUserService ejlAttentionUserServiceImpl;

	@Resource(name = "ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;

	private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);

	@Override
	protected IEjlMessageDao getEntityDao() {
		return ejlMessageDao;
	}

	@Override
	public EjlMessage sendChatMessage(Context ctx, Long sendUserId, Long receiveUserId, Long receiveUserType,
			String content, Long contentType, Long contentTime, Long appSendTime) throws BizException {

		EjlMessage em = new EjlMessage();

		// *** 消息查重
		EjlMessage ejlMessageCheck = new EjlMessage();
		ejlMessageCheck.setAppSendTime(appSendTime);
		ejlMessageCheck.setSendUserId(sendUserId);
		ejlMessageCheck = selectOneObjByAttribute(null, ejlMessageCheck);
		if (ejlMessageCheck != null) {
			log.error(" 消息重复发送。 ejlMessageCheck = " + ejlMessageCheck.toString() + " sendUserId = " + sendUserId
					+ " ; receiveUserId = " + receiveUserId);
			// throw new
			// BizException(StatusBizCode.MESSAGE_REPEAT_SEND.getValue());
			return ejlMessageCheck;
		}

		// ****发送消息之前进行合法性检查
		if (checkBeforeSendChatMessage(sendUserId, receiveUserId, receiveUserType, appSendTime)) {
			em.setSendUserId(sendUserId);
			em.setContent(content);
			em.setContentType(contentType);
			em.setGmtCreated(new Date());
			em.setChatType(receiveUserType);
			em.setContentTime(contentTime);
			em.setStatus(EfamilyConstant.EJL_MESSAGE_STATUS_NOT_OPT);
			em.setAppSendTime(appSendTime);

			// ******** 根据条件，组装需要发送到用户 userList 和 发送到消息 em *************
			if (EfamilyConstant.CHAT_TYPE_WATCH == receiveUserType) {
				// *** 手表和APP，只能是当前操作的手表，家庭中其他手表不需要发送信息
				em.setReceiveUserId(receiveUserId);
				// em.setChatRoomId(sendUser.getFamilyId());
				em.setChatRoomId(receiveUserId);
			} else if (EfamilyConstant.CHAT_TYPE_P2P == receiveUserType) {
				// *** 点对点的聊天
				em.setReceiveUserId(receiveUserId);
				em.setChatRoomId(ejlP2pChatRoomServiceImpl.getP2pChatRoomId(ctx, sendUserId, receiveUserId));
			} else if (EfamilyConstant.CHAT_TYPE_ROOM == receiveUserType) {
				// *** 群聊，聊天室中的每个成员(除发送者外)都需要接收消息
				em.setReceiveUserId(0L);
				em.setChatRoomId(receiveUserId);
			} else if (EfamilyConstant.CHAT_TYPE_FAMILY == receiveUserType) {
				// *** 固定家庭聊天室，所有设备和所有APP用户+关注的用户 ，不包括僵尸用户
				em.setReceiveUserId(receiveUserId);
				em.setChatRoomId(receiveUserId);
			} else {
				log.error("发送聊天信息时，聊天类型未定义，发送失败。 chatType = " + receiveUserType);
				throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
			}
			// *** 保存聊天信息
			this.save(ctx, em);

			/*try{
				// 发送消息
				new SendThread(em, userList, sendUserId, receiveUserType, ctx).start();
			}catch(Exception e){
				log.error("发送消息异常.",e);
			}
*/
		}
		return em;
	}

	class SendThread extends Thread {
		private EjlMessage em;
		private List<EjlUser> userList;
		private Long sendUserId;
		private Long receiveUserType;
		private Context ctx;

		public SendThread(EjlMessage em, List<EjlUser> userList, Long sendUserId, Long receiveUserType, Context ctx) {
			this.em = em;
			this.userList = userList;
			this.sendUserId = sendUserId;
			this.receiveUserType = receiveUserType;
			this.ctx = ctx;
		}

		public void run() {
			// ************
			List<Notification> noties = new ArrayList<Notification>();
			// *** 发送消息
			for (int i = 0; i < userList.size(); i++) {
				EjlUser userTemp = userList.get(i);
				EjlMessageMark messageMark = new EjlMessageMark();
				if (userTemp.getId().longValue() == sendUserId.longValue()) {
					// *** 发送者的信息默认 发送成功
					messageMark.setSendStatus(EfamilyConstant.CHAT_MESSAGE_SEND_SUCCESS);
					messageMark.setStatus(EfamilyConstant.MESSAGE_STATUS_YES_READ);
				} else {
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
				try {
					ejlMessageMarkServiceImpl.save(ctx, messageMark);
				} catch (Exception e1) {
					log.info("拆分消息时出现异常：  messageId = " + em.getId(), e1);
				}
				if (userTemp.getId().longValue() == sendUserId.longValue()) {
					// **** 发送者自己不需要接收消息
					continue;
				}
				try {
					if (EfamilyConstant.USER_TYPE_APP == userTemp.getType().longValue()) {
						NotyTarget t = new NotyTarget(userTemp.getId(), null);
						Map<String, String> data = new HashMap<String, String>();
						data.put("sendUserType", "2");
						data.put("content", em.getContent());
						data.put("contentType", em.getContentType() + "");
						data.put("contentTime", em.getContentTime() + "");
						data.put("sendTime", new Date().getTime() + "");
						data.put("chatGroupId", em.getChatRoomId() + "");
						data.put("chatType", em.getChatType() + "");
						data.put("sendUserId", em.getSendUserId() + "");
						data.put("messageId", em.getId() + "");
						data.put("senderName", em.getSenderName());
						NotyMessage message = new NotyMessage();
						message.setId(em.getId());
						message.setType(NotyMessage.Type.MSG);
						message.setCommand(EfamilyConstant.PUSH);
						message.setData(data);
						Notification notification = new Notification();
						notification.setTarget(t);
						notification.setMessage(message);
						notification.setRealTime(false);

						// notificationUtil.notification(notification);
						noties.add(notification);
					} else if (EfamilyConstant.USER_TYPE_WATCH == userTemp.getType().longValue()) {
						// 非手机用户，不发tips消息
						if (em.getContentType() != null
								&& em.getContentType().intValue() == EjlMessage.ContentType.TIPS.getCode()) {
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
						

						DeviceChatMessageRequest bizReq = new DeviceChatMessageRequest();
						bizReq.setSenderId(sendUserId);
						bizReq.setReceiverId(ctx.getUserId());
						// bizReq.setChatRoomId(userTemp.getFamilyId());
						bizReq.setChatRoomId(em.getChatRoomId());
						bizReq.setType(getResourceType((Integer) em.getContentType().intValue())); // 内容类型
																									// 1
																									// 文字
																									// 2
																									// 音频
																									// 3
																									// 视频
																									// 4
																									// 图片
						bizReq.setContent(em.getContent());
						bizReq.setContentTime(em.getContentTime());
						bizReq.setTime(DateUtils.getCurTime());
						bizReq.setChatType(em.getChatType());

						Map<String, String> data = new HashMap<String, String>();
						data.put("data", JsonUtils.toJson(bizReq));

						NotyTarget t = new NotyTarget(userTemp.getId(), deviceId);
						NotyMessage message = new NotyMessage();
						message.setId(em.getId());
						message.setType(NotyMessage.Type.MSG);
						message.setCommand(NoticeType.APP_DEVICE_SEND_MESSAGE.getValue());
						message.setData(data);
						Notification notification = new Notification();
						notification.setTarget(t);
						notification.setMessage(message);
						notification.setRealTime(false);
						// notificationUtil.notification(notification);
						noties.add(notification);

					} else if (EfamilyConstant.USER_TYPE_NO_WATCH == userTemp.getType().longValue()) {
						log.warn("发消息给无设备用户，发送失败： sendUserId = " + sendUserId + " ; receiveUserId = " + userTemp.getId()
								+ " ;  ");
					} else {
						log.warn("发送消息时，用户类型未定义，发送失败： sendUserId = " + sendUserId + " ; receiveUserId = "
								+ userTemp.getId() + " ;  ");
					}

				} catch (Exception e) {
					log.info("发送消息时出现异常：  userId = " + userTemp.getId(), e);
				}

			}

			// 发送消息
			for (Notification no : noties) {
				final Notification nos = no;
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							notificationUtil.notification(nos);
						} catch (Exception e) {
							log.info("发送消息时出现异常：  userId = " + nos.getTarget().getUserId()+" msgId="+nos.getMessage().getId(), e);
						}
					}
				});
			}
			// **********
		}

	}

	private String getResourceType(Integer type) {
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(1, ResourceType.TEXT);
		m.put(2, ResourceType.AUDIO);
		m.put(3, ResourceType.VIDEO);
		m.put(4, ResourceType.IMAGE);
		return m.get(type);
	}

	public boolean checkBeforeSendChatMessage(Long sendUserId, Long receiveUserId, Long receiveUserType,
			Long appSendTime) throws BizException {

		EjlUser sendUser = ejlUserDaoImpl.getById(sendUserId);
		if (EfamilyConstant.CHAT_TYPE_WATCH == receiveUserType) {
			// *** 手表和APP 检查是否还在家庭中
			EjlUser receiveUser = ejlUserDaoImpl.getById(receiveUserId);
			if (sendUser.getFamilyId().longValue() != receiveUser.getFamilyId().longValue()) {
				log.error(" 设备和用户不在同一个家庭。 sendUserId = " + sendUserId + " ; receiveUserId = " + receiveUserId);
				throw new BizException(StatusBizCode.DEVICE_USER_NOT_IN_FAMILY.getValue());
			}
		} else if (EfamilyConstant.CHAT_TYPE_P2P == receiveUserType) {
			// *** 点对点的聊天 对方有没有删除自己(是否是好友，或者家庭成员)
			EjlUser receiveUser = ejlUserDaoImpl.getById(receiveUserId);
			EjlUserFriend user = new EjlUserFriend();
			user.setUserId(sendUserId);
			user.setFriendId(receiveUserId);
			user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			EjlUserFriend userFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(user);
			if ((sendUser.getFamilyId() == null || receiveUser.getFamilyId() == null
					|| sendUser.getFamilyId().longValue() != receiveUser.getFamilyId().longValue())
					&& userFriendCheck == null) {
				log.error("既不是 好友 也不是 家庭成员 ，则不能发送点对点信息。 sendUserId = " + sendUserId + " ; receiveUserId = "
						+ receiveUserId);
				throw new BizException(StatusBizCode.USER_FRIEND_FAMILY_NOT.getValue());
			}

		} else if (EfamilyConstant.CHAT_TYPE_ROOM == receiveUserType) {
			// *** 群聊，聊天室中的每个成员(除发送者外)都需要接收消息 ，如果发送者 不在聊天室里，则提示不允许发送信息
			EjlChartRoomUser ejlChartRoomUser = new EjlChartRoomUser();
			ejlChartRoomUser.setUserId(sendUserId);
			ejlChartRoomUser.setChartRoomId(receiveUserId);
			ejlChartRoomUser.setStatus(EfamilyConstant.EXIST_CHAT_ROOT_YES);
			ejlChartRoomUser = ejlChartRoomUserDaoImpl.selectOneObjByAttribute(ejlChartRoomUser);
			if (ejlChartRoomUser == null) {
				log.error("发送者已经不在聊天室里。 sendUserId = " + sendUserId + " ; receiveUserId = " + receiveUserId);
				throw new BizException(StatusBizCode.CHAT_ROOM_USER_NOT.getValue());
			}
		} else if (EfamilyConstant.CHAT_TYPE_FAMILY == receiveUserType) {
			// *** 固定家庭聊天室，所有设备和所有APP用户+关注的用户 ，不包括僵尸用户
			if (sendUser.getFamilyId() == null || (sendUser.getFamilyId().longValue() != receiveUserId.longValue())) {
				List<AttentionUserStruc> attentionUserStrucList = ejlAttentionUserServiceImpl
						.getAttentionUserByUserIdAndFamilyId(sendUserId, receiveUserId);
				if (attentionUserStrucList == null || attentionUserStrucList.size() == 0) {
					log.error(" 发送者 没有关注家庭的设备，消息发送失败。 sendUserId = " + sendUserId + " ; receiveUserId = "
							+ receiveUserId);
					throw new BizException(StatusBizCode.USER_ATTENTION_FAMILY_DEVICE.getValue());
				}
				if (ejlAttentionUserServiceImpl.checkAttentionIsForbitSpeak(sendUserId, receiveUserId)) {
					log.error(" 发送者 已经被禁言，消息发送失败,关注者。 sendUserId = " + sendUserId + " ; receiveUserId = "
							+ receiveUserId);
					throw new BizException(StatusBizCode.USER_FORBIT_SPEAK.getValue());
				}
			} else {
				EjlFamilyUser familyUser = ejlFamilyUserServiceImpl.getEjlFamilyUserBy(sendUser.getId(),
						sendUser.getFamilyId());
				if (familyUser.getIsForbitSpeak() != null && familyUser.getIsForbitSpeak().intValue() == 1) {
					log.error(" 发送者 已经被禁言，消息发送失败,家庭成员。 sendUserId = " + sendUserId + " ; receiveUserId = "
							+ receiveUserId);
					throw new BizException(StatusBizCode.USER_FORBIT_SPEAK.getValue());
				}
			}
		} else {
			log.error("发送聊天信息检查数据时，聊天类型未定义，发送失败。 chatType = " + receiveUserType);
			throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
		}

		return true;
	}

}
