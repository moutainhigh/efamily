package com.winterframework.efamily.thirdparty.sms;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;

public interface IBaseSmsService {
	String send(String phoneNumber,String content) throws BizException;
	void send(List<String> phoneNumberList,String content) throws BizException;
}
