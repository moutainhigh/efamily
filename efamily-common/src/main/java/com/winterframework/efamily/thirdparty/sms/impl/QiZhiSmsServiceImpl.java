package com.winterframework.efamily.thirdparty.sms.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("qiZhiSmsServiceImpl")
public class QiZhiSmsServiceImpl extends BaseSmsServiceImpl {
	private static final Logger log= LoggerFactory.getLogger(QiZhiSmsServiceImpl.class);
	
	@PropertyConfig("sms.qizhi.url")
	private String smsUrl;
	@PropertyConfig("sms.qizhi.acc")
	private String smsAccount;
	@PropertyConfig("sms.qizhi.pwd")
	private String smsPasswd;
	
	protected String getHttpUrl(String phoneNumber,String content){
		String encodedContent = null;
		try {
			encodedContent = URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
			return null;
		}
		StringBuffer strBuf = new StringBuffer(smsUrl);
		strBuf.append("?un=").append(smsAccount)
			  .append("&pw=").append(smsPasswd)
			  .append("&phone=").append(phoneNumber)
			  .append("&rd=").append("1")
			  .append("&msg=").append(encodedContent);
		
		return strBuf.toString();
	}
	
	@Override
	protected String getMsgTemplate() {
		return "【奇智科技】";
	}

	@Override
	protected void checkResult(String result) throws BizException {
		String code = result.split("\r|\n")[0].split(",")[1];
		if(!"0".equals(code)){
			throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue());
		}
	}
	
}

