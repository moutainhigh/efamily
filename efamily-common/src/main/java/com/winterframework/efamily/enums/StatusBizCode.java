package com.winterframework.efamily.enums;
public enum StatusBizCode{
		/*
		 * 系统 个位
		 * 基础 1xx
		 * 
		 */
		////////////////////COMMON//////////////////
		DATA_NOT_EXIST(100001),	//实体不存在
	
		////////////////////APP/////////////////////
		REQUEST(-1),	//请求成功
		OK(0),	//请求成功
		CONTINUE(1),	//存在后续包
		NETTY(4),	//Netty异常
		HTTP(5),	//Http请求异常 --url错误或者响应数据格式不正确
		UNKNOW(999),	//未知异常
		FAILED(3),	//请求失败
		SAVE_FAILED(10),	//保存失败
		SMS_SEND_FAILED(10000),	//短信发送失败
		RESOURCE_NOT_FOUND(404),//资源不存在
		RESOURCE_UPLOAD_FAILED(405),//资源上传失败
		REGISTER_FAIL(110),	//注册失败
		UN_CONNECT(100),	//网络未连接
		UN_LOGIN(101),	//未登录
		UN_VALID_VERIFY_CODE(201),//验证码错误
		UN_VALID_PASSWORD(202),//密码错误
		UN_PASSWORD_EMPTY(203),//密码为空 之前未设置过
		VERIFY_CODE_VALID_IP_LIMIT(204),//单个IP地址24小时内请求验证码的次数超过最大次数
		VERIFY_CODE_VALID_PHONE_LIMIT(205),//同一手机号码发送验证码的总次数超过最大次数
		VERIFY_CODE_VALID_INTERVAL_LIMIT(206),//验证码获取的间隔时间，需大于120秒
		USER_UN_VALID(301),//用户无效
		USER_UN_TYPE_VALID(302),//用户类型不是APP用户
		USER_UN_FAMILY_HOST(303),//当前用户不是家庭群主
		USER_UN_FAMILY_MEMBER(304),//当前用户不是家庭成员
		USER_FRIEND_SAME_FAMILY(310),//同一个家庭的用户之间不能加为好友
		USER_FRIEND_SAME_USER(311),//同一个用户不能与自己成为好友
		USER_FRIEND_REPEAT_APPLY(312),//用户好友已申请中 （重复申请）
		USER_FRIEND_ALREADY_SUCCESS(313),// 用户好友已添加成功 （重复申请）
		USER_FRIEND_NOT(314),// 不是好友关系
		USER_FRIEND_FAMILY_NOT(315),//既不是好友 也不是 家庭成员
		USER_NO_FAMILY(316),//用户不存在家庭中
		USER_PHONE_EXIST(317),//用户手机号码已经存在
		SLEEP_EXIST(318),//用户睡眠已经设置
		USER_ISOFFLINE(319),//用户已经关机
		USER_EXIST(320),//用户已经存在
		USER_PASSWORD_INVALID(321),//用户注册密码非法
		USER_THIRD_PART_BIND_EXIST(322),//用户第三方绑定已经存在
		PARAM_INCOMPLETE(401),//参数不完整
		TYPE_UNDEFINED(501),//类型未定义
		MESSAGE_REPEAT_SEND(502),//消息重复发送
		REPEAT_OPERATION(601),//重复操作
		CHAT_ROOM_UN_VALID(701),//群组无效
		CHAT_ROOM_USER_NOT(702),//用户已经不在群组中
		FAMILY_UN_VALID(801),//家庭无效
		FAMILY_USER_ALREADY_IN_FAMILY(802),//用户已经在家庭中
		FAMILY_USER_REPEAT_APPLY(803),//用户重复申请加入家庭
		FAMILY_USER_REPEAT_INVITE(804),//用户被重复邀请加入家庭
		FAMILY_USER_PAST_DUE(805),//用户邀请或申请过期
		FRIEND_USER_PAST_DUE(806),//用户邀请或申请过期
		FRIEND_USER_NOT_IN_FAMILY(807),//用户不在家庭中
		FRIEND_USER_NOT_INVITE_ONESELF(808),//用户不能邀请自己加入家庭
		PUSH_FAILED(809),	//推送失败
		FAMILY_QUIT_FAIL_FOR_HOST(810),	//当前退出的成员为家主,但是不是最后一个成员,则不允许退出家庭
		FAMILY_NOT_JOIN_OTHER_FAMILY(811),//用户已经有家庭 不允许加入其他家庭
		USER_ATTENTION_YES(820),//用户已经关注设备
		USER_ATTENTION_REPEAT_APPLY(821),//用户重复申请关注设备
		USER_ATTENTION_PAST_DUE(822),//用户申请关注设备已过期
		USER_ATTENTION_IN_FAMILY(823),//设备已经在家庭中  不需要关注 

		USER_BARRIER_NOT_EXIST(830),// 被修改的设备围栏已不存在
		USER_BARRIER_SET_ONLY_FAMILY_MEMBER(831),// 只有家人才能设置  手表  围栏
		
		//------------APP---------
		PARAM_CODE_INVALID(100001), //参数编码无效
		USER_HEALTH_SETT_MISSING(100002), //用户健康设置缺失
		APP_DEVICE_SOFTWARE_ING(100003), //设备软件更新中
		APP_DEVICE_UNBOUND(100004), //设备已解绑
		APP_DEVICE_NO_NEW_VERSION(100005), //设备软件没有新版本
		APP_DEVICE_PLATFORM_VERSION_MISSING(100006), //平台设备版本信息缺失
		APP_DEVICE_NOT_EXISTS(100007),	//设备不存在
		APP_DEVICE_BUSING(100008),	//设备忙中
		APP_DEVICE_OPER_FAILED(100009),	//设备操作失败
		APP_DEVICE_SOFTWARE_DOING(100010),	//设备软件更新中
		
		////////////////////DEVICE////////////////////////
		DEVICE_UN_VALID(901),//设备无效
		DEVICE_USER_NOT_IN_FAMILY(902),//设备和用户不在同一个家庭
		DEVICE_USER_ALREADY_BIND(903),//设备已经绑定到该用户
		DEVICE_USER_NOT_BIND(904),//设备没有绑定到任何用户
		DEVICE_PARAM_IS_NULL(905),//设备没有设置参数：参数为空
		DEVICE_NOT_INTO_STOCK(906),//设备未入库
		DEVICE_SEND_COMMAND_EXCEPTION(907),//发送命令给设备时出现异常
		USER_NOT_BIND_DEVICE(908),//用户没有绑定设备
		USER_BIND_DEVICE_IN_FAMILY(909),//家庭中还存在用户绑定的设备
		USER_NOT_UNBIND_DEVICE(910),//用户既不是绑定设备的APP用户也不是 设备本身使用者，没有权限解绑设备
		DEVICE_WAIT_CONFIRM_THIS_PHONE(911),//当前电话号码对应的手表已经在等待确认中
		USER_FORBIT_SPEAK(912),//用户已经被禁言
		USER_ATTENTION_FAMILY_DEVICE(913),//用户没有关注家庭的设备  不允许发言
		
		//--------设备20xxxx-----------
		DEVICE_NO_BIND_REQ(200001),//设备不存在绑定请求
		DEVICE_IMEI_INVALID(200002),//设备IMEI无效
		DEVICE_MOBILE_IMPORTED(200003),//设备移动信息已导入
		DEVICE_USER_NO_FAMILY(200004),//用户不存在家庭
		DEVICE_USER_NOT_EXISTS(200005),//用户无效
		DEVICE_FAMILY_INVALID(200006),//家庭无效
		DEVICE_CONFIRM_FAIL(200007),//设备确认绑定时，失败。（例如：家庭中没有APP用户）
		DEVICE_CUSTOMER_NO_EXIST(200008),//设备对应的客户不存在
		DEVICE_KEY_INVALID(200009),//设备Key无效
		IMEI_UKEY_EMPTY(200010),//设备对应的用户无ukey
		IMEI_UKEY_ORGUKEY_NOTSAME(200011),//设备对应的用户ukey与机构的ukey不同
		IMEI_NOT_BIND_ORG(200012),//设备未绑定机构
		ORG_KEY_INVALID(200013),//机构key无效
		ORG_BINDED_BY_OTHER(200014),//设备已经被其他机构绑定
		DEVICE_HAS_BIND(200016),//设备已经绑定
		DEVICE_NOT_SUPPORT(200017),	//设备不支持该功能
		//---------平台22xxxx-------
		PLATFORM_DEVICE_SETT_MISSING(220015),//平台设备设置缺失
		PLATFORM_HEALTH_SETT_MISSING(220016),//平台设备设置缺失
		
		//---------第三方29xxxx-------
		CUSTOMER_ALARM_SETT_MISSING(290014),//客户告警设置缺失
		ORG_KEY_NO_PERMINT(200015),//机构key无权限解绑不在结构的设备
		CUSTOMER_HEALTH_DATA_SEND_SETT_MISSING(290016),//客户健康数据发送设置缺失
		TEST(201201),
		
		///////////////////////////////////////////////////////
		ORG_UN_VALID(300000);//机构未注册
		
		private int _value=1;
		StatusBizCode(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}