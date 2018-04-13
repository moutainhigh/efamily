package com.winterframework.efamily.exception;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;

@SuppressWarnings("serial")
public class TestException extends BizException {
	private static int code=StatusCode.TEST.getValue();
	public TestException(){
		super(code,"",null);
	}
	public TestException(String[] params){
		super(code,params);
	}
	
}
