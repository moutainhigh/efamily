package com.winterframework.efamily.thirdparty.sms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("huashouSmsServiceImpl")
public class HuashouSmsServiceImpl extends KangdooSmsServiceImpl {
	private static final Logger log= LoggerFactory.getLogger(HuashouSmsServiceImpl.class);
	
	@Override
	protected String getMsgTemplate() {
		return "【华寿管家】";
	}
}
