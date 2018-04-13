package com.winterframework.efamily.notification.model;


public enum ResultCode{
		OK(0,"成功"),	//请求成功
		JPUSH_FAILED(920001,"jpush failed."),
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