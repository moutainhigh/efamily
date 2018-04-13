package com.winterframework.efamily.service;

import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.ChatGroupDetailsList;
import com.winterframework.efamily.dto.ChatGroupListResponse;
import com.winterframework.efamily.dto.ChatRoomUserNotify;
import com.winterframework.efamily.dto.FriendListResponse;
import com.winterframework.efamily.dto.HealthlyUserRequest;
import com.winterframework.efamily.dto.LoginRequest;
import com.winterframework.efamily.dto.LoginResponse;
import com.winterframework.efamily.dto.RegisterResponse;
import com.winterframework.efamily.dto.UserHealthlyConfigRequest;
import com.winterframework.efamily.dto.UserHealthlyConfigStruc;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.entity.EjlUser;

public interface IEjlUserService extends IBaseService<EjlUser> {
	/**登陆
	 * @param phoneNumber 手机 //不需要注册的功能，登陆即注册
	 * @param inviteNumber 邀请码 存在直接加入家庭
	 * @param verifyCode 验证码
	 * @param type 客户端类型 ios android ipad 等
	 * @return
	 */
	public LoginResponse login(Context ctx,LoginRequest loginRequest) throws BizException;

	/**注册
	 * @param phone 手机
	 * @param passwd 验证码
	 * @param type 客户端类型 ios android ipad 等
	 * @return
	 */
	public RegisterResponse register(String phone, String passwd, Long type);

	/**
	 * 
	* @Title: loginOut 
	* @Description: TODO(注销) 
	* @param userId
	 */
	public void loginOut(Context ctx,Long userId,String token)  throws BizException;

	/**
	 * 
	* @Title: getVerifyCode 
	* @Description: TODO(获取验证码) 
	* @param phoneNumber
	* @return
	 * @throws BizException 
	 */
	public Integer getVerifyCode(Context ctx,String phoneNumber,Integer appType) throws BizException;

	/**
	 * 
	* @Title: getUserByUserId 
	* @Description: TODO(根据用户ID获取用户) 
	* @param userId
	* @return
	* @throws BizException
	 */
	public EjlUser getUserByUserId(Long userId) throws BizException;
	
	/**
	 * 
	 * @param sourceId
	 * @param sourceType
	 * @return
	 * @throws BizException
	 */  
	public EjlUser getUserByThirdPartyToken(String sourceId,Integer sourceType) throws BizException;

	/**
	 * 
	 * @param userId
	 * @param userType
	 * @throws BizException
	 */
	public void updateUserType(Context ctx,long userId,long userType) throws BizException;
	/**
	 * 
	* @Title: saveOrUpdateUser 
	* @Description: TODO(保存或修改用户) 
	* @param user
	* @throws BizException
	 */
	public void saveOrUpdateUser(Context ctx,EjlUser user) throws BizException;

	/**
	 * 
	* @Title: getUserByPhone 
	* @Description: TODO(根据手机号码获取用户) 
	* @param phone
	* @return
	* @throws BizException
	 */
	public EjlUser getUserByPhone(String phone) throws BizException;
	/**
	 * 获取有效的用户（根据手机号码）
	 * @param phone
	 * @return
	 * @throws BizException
	 */
	EjlUser getValidUserByPhone(String phone) throws BizException;
	
	/**
	 * 
	* @Title: getEjlFriendListByUserId 
	* @Description: TODO(获取好友列表) 
	* @param userId
	* @return
	* @throws BizException
	 */
	public List<FriendListResponse> getEjlFriendListByUserId(Long userId,Long type) throws BizException;
	/**
	 * 
	* @Title: getEjlFamilyListByUserId 
	* @Description: TODO(获取家庭成员) 
	* @param userId
	* @return
	* @throws BizException
	 */
	public List<FriendListResponse> getEjlFamilyListByUserId(Long userId,Long type) throws BizException;
	/**
	 * 
	* @Title: getFamilyCountByUserId 
	* @Description: TODO(获取家庭数量) 
	* @param userId
	* @return
	* @throws BizException
	 */
	public Long getFamilyCountByUserId(Long userId) throws BizException ;
	
	/**
	 * 
	* @Title: saveInitUser 
	* @Description: TODO(初次保存用户) 
	* @param familyId
	* @param userType
	* @param phone
	* @return
	* @throws BizException
	 */
	public long saveInitUser(Context ctx,Long familyId,Long userType,String phone,String userName)throws BizException ;
	/**
	 * 
	* @Title: getGroupChatListByUserId 
	* @Description: TODO(获得聊天群组信息) 
	* @param userId
	* @return
	* @throws BizException
	 */
	public List<ChatGroupListResponse> getGroupChatListByUserId(Long userId)throws BizException;
	/**
	 * 
	* @Title: updateUserChatGroupInfo 
	* @Description: TODO(修改聊天群组信息) 
	* @param userId
	* @param request
	* @return
	* @throws BizException
	 */
	public String manageUserChatGroupInfo(Context ctx,Long userId,String parameterIndex,String parameterContext,String chatGroupId,Long oprType,Long chatType) throws BizException ;
	/**
	 * 
	* @Title: quitUserChatGroupInfo 
	* @Description: TODO(退出聊天群组) 
	* @param userId
	* @param chatRoomId
	* @throws BizException
	 */
	public void quitUserChatGroupInfo(Long userId,Long chatRoomId) throws BizException ;
	
	public int deleteUserChatGroupInfo(String userIds,Long chatRoomId) throws BizException ;
	
	/**
	 * 
	* @Title: joinUserChatGroupInfo 
	* @Description: TODO(加入聊天群组) 
	* @param userIds
	* @param chatRoomId
	* @return
	* @throws BizException
	 */
	public int joinUserChatGroupInfo(Context ctx,String userIds,Long chatRoomId) throws BizException ;
	/**
	 * 
	* @Title: createChatRoom 
	* @Description: TODO(创建聊天室) 
	* @param userId
	* @return
	* @throws BizException
	 */
	public EjlChartRoom createChatRoom(Context ctx,Long userId)  throws BizException ;

	/**
	 * 
	* @Title: getChatGroupDetailsList 
	* @Description: TODO(获取聊天组详细信息) 
	* @param userId
	* @param chatRoomId
	* @return
	* @throws BizException
	 */
	public List<ChatGroupDetailsList> getChatGroupDetailsList(Long userId,Long chatRoomId)throws BizException ;
	
	public void notifyForFamilyInfoUpdate(Map<String,String> data,Long userId,Long familyId) throws BizException;
	
	/**
	 * 
	* @Title: notifyForScanWatch 
	* @Description: TODO(扫描手表时，进行推送) 
	* @param data
	* @param userId
	* @param familyId
	* @throws BizException
	 */
	public void notifyForScanWatch(Map<String,String> data,Long userId,Long familyId) throws BizException;
	
	/**
	 * 
	 * @param data
	 * @param userId
	 * @param friendList
	 * @param noticeType
	 * @throws BizException
	 */
	public void notifyForAddressFriendShip(Map<String,String> data,Long userId,List<EjlUser> friendList,NoticeType noticeType) throws BizException;
	/**
	 * 
	* @Title: notifyForUpdateWatchOwner 
	* @Description: TODO(更换手表拥有者时，进行推送) 
	* @param data
	* @param userId
	* @throws BizException
	 */
	 public void notifyForUpdateWatchOwner(Map<String,String> data,Long userId) throws BizException;
	 /**
	  * 
	 * @Title: notifyForManageJoinFamily 
	 * @Description: TODO(管理用户加入家庭时，进行推送) 
	 * @param data
	 * @param userId
	 * @param familyId
	 * @param noticeType
	 * @throws BizException
	  */
	 public void notifyForManageJoinFamily(Map<String,String> data,Long userId,Long familyId,NoticeType noticeType,Long manageType,Long notNoticeUserId) throws BizException;
	 
	 
	 /**
	  * 功能：管理家庭退出
	  * @param userIdStr
	  * @param userId
	  * @param familyId
	  * @param noticeType
	  * @param manageType
	  * @param notNoticeUserId
	  * @throws BizException
	  */
	 public void notifyForManageFamilyQuitAndDelete(String userIdStr,Long userId,Long familyId,NoticeType noticeType,Long manageType,Long notNoticeUserId) throws BizException;
	 
	 /**
	  * 
	 * @Title: notifyForUpdateUserInfo 
	 * @Description: TODO(更改用户基本信息) 
	 * @param data
	 * @param userId
	 * @throws BizException
	  */
	 public void notifyForUpdateUserInfo(Map<String,String> data,Long userId,Long notNoticeUserId) throws BizException;
	/**
	 * 
	* @Title: notifyForManageGroupSetting 
	* @Description: TODO(群组设置时，进行推送给群组中的用户) 
	* @param data
	* @param userId
	* @param groupId
	* @throws BizException
	 */
	 public void notifyForManageGroupSetting(Map<String,String> data,Long userId,Long groupId,NoticeType noticeType,String parameterContext) throws BizException;
    /**
     * 
    * @Title: notifyForManageFriendShip 
    * @Description: TODO(管理好友时，进行推送给好友) 
    * @param data
    * @param userId
    * @param cunstomId
    * @param noticeType
    * @throws BizException
     */
	 public void notifyForManageFriendShip(Map<String,String> data,Long userId,Long cunstomId,NoticeType noticeType) throws BizException;
	 /**
	  * 
	  * @param oprType
	  * @param data
	  * @param userId
	  * @throws Exception
	  */
	 public void notifyForUpdateWatchOwnerForDevice(String oprType,Map<String,String> data,Long userId) throws Exception;
	 /**
	  * 
	  * @param userId
	  * @param userType
	  * @param familyId
	  * @throws BizException
	  */
	 public void zombieToDeviceUser(Context ctx,Long userId,Long userType,Long familyId,String phoneNumber) throws BizException;
	 
	 /**
	  * 功能：获取用户健康信息
	  * @param userId
	  * @return
	  * @throws BizException
	  */
	 public List<HealthlyUserRequest> getHealthlyUserByUserId(Long userId) throws BizException;
	 
	 /**
	  * 功能：获取健康用户信息
	  * @param userId
	  * @param deviceId
	  * @return
	  * @throws BizException
	  */
	 public UserHealthlyConfigStruc getHealthlyUserConfig(Long userId,Long deviceId) throws BizException;
	 
	 /**
	  * 功能：修改健康用户信息
	  * @param userHealthlyConfigStruc
	  * @throws BizException
	  */
	 public void updateHealthlyUserConfig(Context ctx,UserHealthlyConfigRequest userHealthlyConfigStruc) throws BizException;
	 
	 /**
	  * 功能：推送消息到设备
	  * @param userId
	  * @param deviceId
	  * @param data
	  * @param command
	  * @param nt
	  * @throws Exception
	  */
	 public void pushDevice(Long userId,Long deviceId,Map<String,String> data,int command,NotyMessage.Type nt) throws Exception;
	 /**
	  * 功能：登录记录
	  * @param ctx
	  * @param userId
	  * @throws BizException
	  */
	 public void loginRecord(Context ctx,Long userId,String token,String remark,Integer status) ;
	 
	 
	 public void pushDevice(Long userId,Long deviceId,Map<String,String> data,int command,NotyMessage.Type nt,boolean isRealTime) throws BizException;
	 
	 public void switchUserPhone(Context ctx,long userIdA,long userIdB) throws BizException;
	 
	 public void updateUserTypeAndPhone(Context ctx,long userId,long userType,String phoneNumber) throws BizException;
	 
	 public List<ChatRoomUserNotify> getUserByMoreUserId(List<String> userIds) throws BizException;
	 
	 public List<ChatGroupDetailsList> getChatRoomDetails(Context ctx,Long userId,Long chatRoomId,Long chatType) throws BizException;
	 
	 public List<ChatGroupDetailsList> getP2pChatRoomDetails(Context ctx,Long userId,Long chatRoomId) throws BizException;
	 
	 public void notifyForManageForbitSpeak(Map<String,String> data,Long userId,NoticeType noticeType) throws BizException;
	 
	 public void notifyForManageAttention(Map<String,String> data,Long userId,Long oprUserId,Long oprType,NoticeType noticeType,String code) throws BizException;
	 
	 public void notifyForTransferFamilyHost(Map<String,String> data,Long userId,Long oprUserId,Long familyId,NoticeType noticeType) throws BizException;
	 
	 public EjlUser getFamilyHostByDeviceCode(String deviceCode)throws BizException;
	 
	 public int clearPhoneForUnconfirmUser(Context ctx,Long familyId)throws BizException;
	 
	 public void notifyForUnbindDevice(Map<String,String> data,Long userId,List<EjlUser> userList) throws BizException;
	 
	 public List<EjlUser> getEjlUserFamilyAndAttentionList(Long userId)throws BizException;
	 
	 public boolean modifyPassword(Context ctx,String phoneNumber,String verifyCode,String password,Integer oprType,String oldPassword) throws Exception;
	 
	 public void bindThirdPartyCertification(Context ctx,Long userId,String sourceId,String sourceType,String oprType) throws BizException;
	 
	 public boolean checkVerifyCode(Context ctx,String ip,String phoneNumber,Integer appType) throws BizException;
}
