package com.winterframework.efamily.thirdparty.sms.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.thirdparty.sms.SmsResult;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("kangdooSmsServiceImpl")
public class KangdooSmsServiceImpl extends BaseSmsServiceImpl {
	private static final Logger log= LoggerFactory.getLogger(KangdooSmsServiceImpl.class);
	
	@PropertyConfig("sms.kangdoo.url")
	private String smsUrl;
	@PropertyConfig("sms.kangdoo.acc")
	private String smsAccount;
	@PropertyConfig("sms.kangdoo.pwd")
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
		strBuf.append("?un=").append(smsAccount);
		strBuf.append("&pw=").append(smsPasswd);
		strBuf.append("&da=").append(phoneNumber);
		strBuf.append("&sm=").append(encodedContent);
		strBuf.append("&dc=15&rd=1&rf=2&tf=3");
		
		return strBuf.toString();
	}
	
	@Override
	protected String getMsgTemplate() {
		return "【康朵】";
	}
	
	protected void checkResult(String result) throws BizException{
		SmsResult smsResult=JsonUtils.fromJson(result, SmsResult.class);
		if(null==smsResult || !smsResult.isSuccess()){
			throw new BizException("http response result invalid:"+smsResult);
		}
	}
	
	
}
