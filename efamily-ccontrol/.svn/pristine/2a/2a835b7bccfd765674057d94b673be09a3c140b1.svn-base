package com.winterframework.efamily.ccontrol.model;


public enum ResultCode{
		OK(0,"成功"),	//请求成功
		PARAM_INVALID(910001,"param invalid."),
		SERVER_IP_EMPTY(910002,"server ip is empty."),
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
	}