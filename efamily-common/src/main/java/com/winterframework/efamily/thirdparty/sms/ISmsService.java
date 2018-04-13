package com.winterframework.efamily.thirdparty.sms;

import java.rmi.ServerException;
import java.util.List;

public interface ISmsService {
	String send(String phoneNumber,String content) throws ServerException;
	String send(List<String> phoneNumberList,String content) throws ServerException;
}
