package com.winterframework.efamily.api.enums;


public enum ResultCode{
		OK(0,"成功"),	//请求成功
		KEY_INVALID(100000,"key invalid."),
		IMEI_INVALID(100001,"imei invalid."),
		DATA_NOT_EXIST(100002,"data not exists."),
		DB_ERROR(100003,"access database error."),
		NUMBER_INVALID(100004,"number invalid."),
		PARAM_EMPTY(100005,"there is parameter empty."),
		PARAM_INVALID(100006,"there is parameter invalid."),
		TIME_INVALID(100007,"time invalid."),
		HEART_NOT_EXIST(100008,"heart not exists."),
		SPORTS_NOT_EXIST(100009,"sport not exists."),
		API_UNDEFINED(100010,"api undefined."),
		API_ACCESS_FREQUENTLY(100011,"frequent limited"),
		TIME_TOO_LONG(100012,"time limited."),
		CUSTOMER_UNPERMISSIONS(100013,"donot have the permission."),
		VERIFICATIONCODE_INVALID(100015,"verification code invalid."),
		PHONE_INVALID(100016,"phone invalid."),
		NO_AUTH_ACCESS_URL(100017,"No permission to access this URL."),
		API_DAY_ACCESS_FREQUENTLY(100018,"No permission to access this URL."),
		API_MINUTE_ACCESS_FREQUENTLY(100019,"No permission to access this URL."),
		DEVICE_NOT_BIND(100020,"Device no bind."),
		
		DEVICE_UN_VALID(901,"device invalid."),
		DEVICE_USER_NOT_BIND(904,"device user not bind"),
		ORG_UN_VALID(300000,"org invalid."),
		SAVE_FAILED(10,"save failed."),
		DEVICE_WAIT_CONFIRM_THIS_PHONE(911,"current phone binding device is wait for confirmation "),
		USER_PHONE_EXIST(317,"user phone is exist"),//用户手机号码已经存在
		IMEI_UKEY_EMPTY(200010,"this imei ukey not exists "),//设备对应的用户无ukey
		IMEI_UKEY_ORGUKEY_NOTSAME(200011,"the imei ukey not the same to org ukey"),//imei ukey和机构ukey不一致
		DEVICE_KEY_INVALID(200009,"imei do not having device user"),//设备Key无效
		USER_NOT_BIND_DEVICE(908,"设备未绑定用户"),//用户没有绑定设备
		IMEI_NOT_BIND_ORG(200012,"设备无法与原有用户解绑"),//设备未绑定机构
		ORG_KEY_INVALID(200013,"org key invalid"),//机构key无效
		ORG_KEY_NO_PERMINT(200015,"机构key无权限解绑不在结构的设备"),//机构key无权限解绑不在结构的设备
		DEVICE_CUSTOMER_NO_EXIST(200008,"设备对应客户不存在"),//设备对应的客户不存在
		ORG_BINDED_BY_OTHER(200014,"设备已经被其他机构绑定"),//设备已经被其他机构绑定
		UNKNOWN(-1,"unknown error.");
		private int _code=1;
		private String _msg="";
		ResultCode(int code,String msg){
			_code=code;
			_msg=msg;
		}
		public int getCode(){
			return _code;
		}
		public String getMsg(){
			return _msg;
		}
		
		public static ResultCode getResultCode(int code){
			for(ResultCode resultCode:ResultCode.values()){
				if(code==resultCode.getCode()){
					return resultCode;
				}
			}
			return ResultCode.UNKNOWN;
		}
	}