package com.winterframework.efamily.common;

/**
 * 描述： 常量类
 * @author jason
 *
 */
public class EfamilyConstant {
	//---------------        基本状态                    -----------------------------
	//*** 状态成功
	public static final long STATUS_SUCCESS = 0 ;
	//*** 状态失败
	public static final long STATUS_FAIL = 1 ;
	
	//---------------        查询好友时是否包括家庭用户                   -----------------------------	
	//*** 0:不包括
	public static final long FRIEND_QUERY_EXCLUDE_FAMILY_MEMBER = 0L ;
	//*** 1:包括
	public static final long FRIEND_QUERY_INCLUDE_FAMILY_MEMBER = 1L ;
	
	//---------------        设备状态                    -----------------------------	
	//*** 0:停用
	public static final long USER_DEVICE_STATUS_STOP = 0L ;
	//*** 1:使用中
	public static final long USER_DEVICE_STATUS_USING = 1L ;
	//*** 2:待确定
	public static final long USER_DEVICE_STATUS_WAIT_CONFIRM = 2L ;
		
	//---------------        聊天内容类型                    -----------------------------	
	//*** 1:文本
	public static final long CONTENT_TYPE_TEXT = 1L ;
	//*** 2:音频
	public static final long CONTENT_TYPE_VOICE = 2L ;
	//*** 3:视频
	public static final long CONTENT_TYPE_VIDIO = 3L ;
	//*** 4:图片
	public static final long CONTENT_TYPE_PICTURE = 4L ;
		
	//---------------        是否是手机用户                    -----------------------------	
	//*** 1:手机用户
	public static final long USER_PHONE = 1L ;
	//*** 2:系统用户
	public static final long USER_SYSTEM = 2L ;
	
	//---------------        是否在家庭中                    -----------------------------	
	//*** 0:在家庭中
	public static final long USER_IN_FAMILY = 0L ;
	//*** 1:不在家庭中
	public static final long USER_NOT_IN_FAMILY = 1L ;
	
		
	//---------------        聊天消息发送状态                -----------------------------	
	//*** 0:未发送
	public static final long CHAT_MESSAGE_SEND_NOT = 0L ;
	//*** 1:已发送
	public static final long CHAT_MESSAGE_SEND_SUCCESS = 1L ;
	//*** 2:发送失败
	public static final long CHAT_MESSAGE_SEND_FAIL = 2L ;
			
		
	//---------------        聊天类型                -----------------------------	
	//*** 1:对手表
	public static final long CHAT_TYPE_WATCH = 1L ;
	//*** 2:点对点
	public static final long CHAT_TYPE_P2P = 2L ;
	//*** 3:APP群聊
	public static final long CHAT_TYPE_ROOM = 3L ;
	//*** 4:固定家庭群聊+关注者
	public static final long CHAT_TYPE_FAMILY = 4L ;
	
	//---------------        消息状态                -----------------------------	
	//*** 0 未读，1已读，2撤销，3删除
	public static final long MESSAGE_STATUS_NO_READ = 0L ;
	public static final long MESSAGE_STATUS_YES_READ = 1L ;
	public static final long MESSAGE_STATUS_BACKOUT = 2L ;
	public static final long MESSAGE_STATUS_DELETE = 3L ;
	
		
	//---------------        好友来源                -----------------------------	
	//*** 1:系统强制关联
	public static final long USER_FRIEND_SOURCE_SYSTEM = 1L ;
	//*** 2:主动添加
	public static final long USER_FRIEND_SOURCE_ACTIVE = 2L ;
	//*** 3:被动添加
	public static final long USER_FRIEND_SOURCE_PASSIVE = 3L ;
	//*** 4:退出家庭时，之前的家庭用户，需要变为好友
	public static final long USER_FRIEND_SOURCE_FAMILY = 4L ;
	//*** 5:家庭成员，用户备注
	public static final long USER_FRIEND_SOURCE_FAMILY_REMARK = 5L ;
	
	//---------------        好友关系状态         -----------------------------	
	//*** 0: 好友(审核通过)
	public static final long USER_FRIEND_STATUS_SUCCESS = 0L ;
	//*** 1: 申请中
	public static final long USER_FRIEND_STATUS_APPLY = 1L ;
	//*** 2: 拒绝
	public static final long USER_FRIEND_STATUS_REJECT = 2L ;
	//*** 3: 被邀请中
	public static final long USER_FRIEND_STATUS_INVITE = 3L ;
	//*** 4: 已删除
	public static final long USER_FRIEND_STATUS_DELETE = 4L ;
	//*** 5: 家庭成员时使用备注
	public static final long USER_FRIEND_STATUS_FAMILY = 5L ;
	
	//---------------        请求的类型                    -----------------------------	
	//*** 1:“添加”请求
	public static final long USER_FRIEND_ADD = 1L ;
	//*** 2:“接受”请求
	public static final long USER_FRIEND_ACCEPT = 2L ;
	//*** 3:“删除好友”请求
	public static final long USER_FRIEND_DELETE = 3L ;
	//*** 4:“拒绝好友”请求
	public static final long USER_FRIEND_REJECT = 4L ;
		
	//---------------        用户状态                    -----------------------------	
	//*** 用户离线
	public static final int USER_STATUS_OFFLINE = 0 ;
	//*** 用户在线
	public static final int USER_STATUS_ONLINE = 1 ;
	//*** 用户被踢掉	
	public static final int USER_STATUS_TIREN = 2 ;
		
	//---------------        回复状态                    -----------------------------
	//*** 回复状态成功
	public static final int RESPONSE_STATUS_SUCCESS = 0 ;
	//*** 回复状态失败
	public static final int RESPONSE_STATUS_FAIL = 1 ;
	//*** 回复状态异常
	public static final int RESPONSE_STATUS_EXCEPTION = 9999 ;
	
	//---------------        设备类型                    -----------------------------	
	//*** 1 ： 手表
	public static final int DEVICE_TYPE_WATCH = 1 ;
	//*** 2 ： 手环
	public static final int DEVICE_TYPE_FUELBAND = 2 ;
	
	//---------------        用户类型                    -----------------------------	
	//*** APP用户
	public static final long USER_TYPE_APP = 1 ;
	//*** 手表用户
	public static final long USER_TYPE_WATCH = 2 ;
	//*** 无APP和无手表的用户
    public static final long USER_TYPE_NO_WATCH = 3 ;
    //*** 所有用户
    public static final long USER_TYPE_ALL = 9 ;
	
	//---------------        用户手表设备关系操作类型                    -----------------------------	
	//*** 1:当前用户换一块新的表
	public static final long UPDATE_WATCH_SWITCH_WATCH = 1 ;
	//*** 2:与其他用户换表
	public static final long UPDATE_WATCH_SWITCH_USER = 2 ;
	//*** 3:手表换一个新的用户
    public static final long UPDATE_WATCH_SWITCH_NEW_USER = 3 ;
    //*** 4:解除绑定
    public static final long UPDATE_WATCH_UN_BIND = 4 ;
    
    //---------------        用户加入家庭的状态                    -----------------------------	
  	//*** 1        申请的同意
  	public static final long MANAGE_TYPE_AGREE = 1 ;
    //*** 2   拒绝
  	public static final long MANAGE_TYPE_REFUSE = 2 ;
    //*** 3   申请
  	public static final long MANAGE_TYPE_APPLY = 3 ;
  	//*** 4        申请加入临时家庭,并创建临时家庭
  	public static final long MANAGE_TYPE_APPLY_TEMPORARY = 4 ;
    //*** 5   邀请
  	public static final long MANAGE_TYPE_INVITE = 5 ;
    //*** 6   退出
  	public static final long MANAGE_TYPE_QUIT = 6 ;
    //*** 7   邀请的同意
  	public static final long MANAGE_TYPE_INVITE_AGREE = 7 ;
    //*** 8   删除家庭成员
  	public static final long MANAGE_TYPE_DELETE = 8 ;
	
    //---------------        是否在聊天室中                    -----------------------------
  	//*** 还在聊天室
  	public static final long EXIST_CHAT_ROOT_YES = 0 ;
  	//*** 离开聊天室
  	public static final long EXIST_CHAT_ROOT_NO = 1 ;
  	
    //---------------        是否在置顶                   -----------------------------
  	//*** 置顶
  	public static final long CHAT_ROOM_TOP_YES = 0 ;
  	//*** 不置顶
  	public static final long CHAT_ROOM_TOP_NO = 1 ;
  	
    //---------------        是否消息通知                   -----------------------------
  	//*** 消息通知
  	public static final long MESSAGE_NOTIFY_YES = 0 ;
  	//*** 不消息通知
  	public static final long MESSAGE_NOTIFY_NO = 1 ;
  //---------------        手表绑定的状态                    -----------------------------	
  	//*** 0:未绑定
  	public static final long BIND_WATCH_STATUS_NOT = 0L ;
  	//*** 1:绑定
  	public static final long BIND_WATCH_STATUS_YES = 1L ;
  	//*** 2:未绑定(有捆绑，但是状态不可用)
  	public static final long BIND_WATCH_STATUS_NO_USE = 2L ;
    //*** 3:设备尚未入库
  	public static final long BIND_WATCH_STATUS_NO = 3L ;
    //*** 4:设备无效
  	public static final long BIND_WATCH_STATUS_NO_EFFECT = 4L ;
    //*** 5:设备待定
  	public static final long BIND_WATCH_STATUS_UNCONFIRM = 5L ;
  	
    //---------------        是否保存到通讯录                   -----------------------------
  	//*** 保存到通讯录
  	public static final long SAVE_ADDRESS_BOOK_YES = 0 ;
  	//*** 不保存到通讯录
  	public static final long SAVE_ADDRESS_BOOK_NO = 1 ;
	//*** 申请好友时的时间间隔 单位小时(只有超过这个时间间隔才能再次申请)
  	public static final long APPLY_FRIEND_TIME_INTERVAL = -1 ;
  	public static final long APPLY_FAMILY_TIME_INTERVAL = -1 ;
  	public static final long APPLY_FAMILY_TIME_INTERVAL_APPLY = 24 ;
	public static final long APPLY_FRIEND_TIME_INTERVAL_APPLY = 24 ;
	public static final long APPLY_ATTENTION_TIME_INTERVAL_APPLY = 24 ;
  	
    //---------------        填充字符常量                    -----------------------------	
  	//*** 空字符串
  	public static final String EMPTY_STR = "" ;
  	
	//*** 状态成功
	public static final int STATUS_SUCCESS_INT = 0 ;
	//*** 状态失败
	public static final int STATUS_FAIL_INT = 1 ;
	
    //---------------        默认资源类型                    -----------------------------	
  	//*** 头像
  	public static final int  RESOURCE_ASSIST_TYPE_HEADIMAGE= 1 ;
  	//*** 天气
  	public static final int  RESOURCE_ASSIST_TYPE_WETHERIMAGE= 2 ;
  	
	//---------------        健康数据更新                    -----------------------------	
	//*** 记步
	public static final long UPDATE_HEALTHY_TYPE_STEP = 1 ;
	//*** 心跳
	public static final long UPDATE_HEALTHY_TYPE_RATE = 2 ;
	//*** 血压
    public static final long UPDATE_HEALTHY_TYPE_BLOOD = 3 ;
  	
  	
    //---------------        人员关系                    -----------------------------	
  	//*** 1:家庭成员关系
  	public static final long RELATION_FAMILY = 1L ;
  	//*** 2:好友关系
  	public static final long RELATION_FRIEND = 2L ;
  	//*** 3:其他关系
  	public static final long RELATION_OTHER = 3L ;
  	
    //---------------        操作类型                    -----------------------------	
  	//*** 0：获取
  	public static final long OPRTYPE_GET = 0L ;
  	//*** 1：设置
  	public static final long OPRTYPE_SET = 1L ;
  	
  	//操作成功，推送消息
  	public static final Integer PUSH = 10005;

  	//聊天信息未处理
  	public static final int EJL_MESSAGE_STATUS_NOT_OPT = 0 ;
  	//聊天信息已处理
  	public static final int EJL_MESSAGE_STATUS_YES_OPT = 1 ;
  	
    //---------------        手表关注操作                    -----------------------------	
  	//*** 1:申请关注
  	public static final long ATTENTION_OPRTYPE_APPLY = 1L ;
  	//*** 2:同意关注
  	public static final long ATTENTION_OPRTYPE_AGREE = 2L ;
  	//*** 3:取消关注
  	public static final long ATTENTION_OPRTYPE_CALCEL = 3L ;
  	//*** 4:被取消关注
  	public static final long ATTENTION_OPRTYPE_BE_CALCELED = 4L ;
  	
    //---------------        是否允许发言                   -----------------------------	
  	//*** 1:禁止发言
  	public static final int ATTENTION_FORBIT_SPEAK_YES = 1 ;
  	//*** 2:允许发言
  	public static final int ATTENTION_FORBIT_SPEAK_NO  = 2 ;
  	
  	
    //---------------        手表关注状态                    -----------------------------	
  	//*** 0:申请
  	public static final int ATTENTION_DEVICE_APPLY = 1 ;
  	//*** 1:关注中
  	public static final int ATTENTION_DEVICE_YES = 2 ;
  	//*** 2:取消关注
  	public static final int ATTENTION_DEVICE_CANCEL = 3 ;
  	
    //---------------        手表关注状态                    -----------------------------	
  	//*** 1:关注
  	public static final int ATTENTION_OLD_FAMILY_DEVICE_YES = 1 ;
  	//*** 2:不关注
  	public static final int ATTENTION_OLD_FAMILY_DEVICE_NO = 2 ;
  	
  	
    //---------------        登录方式                    -----------------------------	
  	//*** 1:密码登陆
  	public static final String LOGIN_PASSWORD = "1" ;
  	//*** 2:验证码登陆
  	public static final String LOGIN_VERIFYCODE = "2" ;
  	//*** 3:注册
  	public static final String LOGIN_REGISTER = "3" ;
  	//*** 4:第三方登陆
  	public static final String LOGIN_THIRD_PARTY = "4" ;
  	//*** 5:第三方注册
  	public static final String LOGIN_THIRD_PARTY_REGISTER = "5" ;
  	
  //*** 6:机构用户注册
  	public static final String LOGIN_ORG_REGISTER = "6" ;
  	
    //---------------        第三方营运商                    -----------------------------	
  	//*** 1:QQ
  	public static final String THIRD_PARTY_QQ = "1" ;
  	//*** 2:weChat
  	public static final String THIRD_PARTY_WECHAT = "2" ;
  	//*** 3:weiBo
  	public static final String THIRD_PARTY_WEIBO = "3" ;
  	
	//---------------        设备运行模式                    -----------------------------	
	//*** 未充电
    public static final int DEVICE_RUNNING_MODE_NO_CHARGE = 1 ;
    //*** 正在充电
    public static final int DEVICE_RUNNING_MODE_YES_CHARGE = 2 ;
    
  	
    //---------------        群组设置属性索引对应                    -----------------------------	
  	//*** 
  	public static final String GROUP_SETTING_NAME = "200001" ;//群组名称
  	public static final String GROUP_SETTING_TOP = "200002" ;//操作置顶
  	public static final String GROUP_SETTING_NOTIFY = "200003" ;//操作通知
  	public static final String GROUP_SETTING_BOOK = "200004" ;//操作保存通讯录
  	public static final String GROUP_ROOM_JOIN = "200005" ;//加入群组
  	public static final String GROUP_ROOM_QUIT = "200006" ;//退出群组
  	public static final String GROUP_ROOM_DELETE = "200007" ;//删除群组
  	
  	
    //---------------        手表操作类型                    -----------------------------	
  	//*** 
  	public static final String DEVICE_ADDRESS_BOOK = "100003" ;//操作手表通讯录
  	public static final String APP_DEVICE_ON_OFF_WATCH = "100013" ;//设备关机
  	public static final String APP_DEVICE_AUTO_ON_WATCH = "100014" ;//自动开机设置
  	public static final String APP_DEVICE_AUTO_OFF_WATCH = "100015" ;//自动关机设置
  	public static final String APP_DEVICE_UN_BIND = "100016" ;
  	public static final String APP_DEVICE_PHONE_NUMBER = "100018" ;
  	public static final String APP_DEVICE_RESTORE_SETTING = "100011" ;
  	
  	public static final Integer PUSH_SWITCH_WATCH = 20105 ;//推送更换手表：20105 
  	public static final Integer PUSH_UNBIND_WATCH = 20106 ;//推送解除手表：20106
  	public static final Integer PUSH_USER_INFO = 20206 ;//推送手表用户信息：20206
  	
  	public static final String MAXBATTERY = "maxBattery";
  	public static final String LASTBATTERY = "lastBattery";
  	public static final String LASTELECTRONICFENCENOTICE = "lastElectronicFenceNotice";
  	public static final String LOCATION_QUERY = "location_query";
  	//*********
  	public static final String SPLIT_DEVICE_FLAG = "@_@";
  	
  	public static final String LASTHEARTRATENOTICE = "lastHeartRateNotice";
  	
  	public static final String LASTBLOODPRESSURENOTICE = "lastBloodPressureNotice";
}
