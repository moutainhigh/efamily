package com.winterframework.efamily.dispatch.model;


public enum ResultCode{
		OK(0,"success."),	//请求成功
		SERVER_NA(900001,"server not available."),
		PARAM_EMPTY(900002,"parameter is empty."),
		PARAM_INVALID(900003,"parameter is invalid."),
		HTTP(5,"http error."),
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