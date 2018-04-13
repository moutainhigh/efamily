package com.winterframework.efamily.base.enums;
public enum StatusCode{
		/*
		 * 系统 个位
		 * 基础 1xx
		 * 
		 */
		REQUEST(-1),	//请求
		OK(0),	//请求成功
		CONTINUE(1),	//存在后续包
		FAILED(3),	//请求失败
		NETTY(4),	//Netty异常
		HTTP(5),	//Http请求异常 --url错误或者响应数据格式不正确
		SAVE_FAILED(10),	//保存失败
		DAO_ERROR(11),	//DAO错误
		TOKEN_INVALID(12),	//TOKEN无效
		KICK_OUT(13),	//登录踢出
		PARAM_INVALID(77),	//参数不正确
		UN_CONNECT(100),	//网络未连接
		UN_LOGIN(101),	//未登录
		REGISTER_FAIL(110),	//注册失败
		UN_VALID_VERIFY_CODE(201),//验证码错误
		UNKNOW(999),	//未知异常
		TEST(201201);	
		private int _value=1;
		StatusCode(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}