package com.winterframework.efamily.thirdparty.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("aliyunSmsServiceImpl")
public class AliyunSmsServiceImpl extends BaseSmsServiceImpl {
	private static final Logger log= LoggerFactory.getLogger(AliyunSmsServiceImpl.class);
	
	@PropertyConfig("sms.anlixin.key")
	private String smsKey;
	@PropertyConfig("sms.anlixin.secret")
	private String smsSecret;
	@PropertyConfig("sms.anlixin.signname")
	private String smsSignName;
	@PropertyConfig("sms.anlixin.templatecode")
	private String smsTemplateCode;
	
	/**
	 * 功能：发送消息给单个手机号码（阿里云封装的方法）
	 */
	public String send(String phoneNumber, String content) throws BizException{
        String result = "";
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsKey, smsSecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(smsSignName);
            request.setTemplateCode(smsTemplateCode);
            request.setParamString("{\"code\":\""+content+"\"}");
            request.setRecNum(phoneNumber);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            if(httpResponse != null){
            	checkResult(httpResponse.getRequestId());
            	result = httpResponse.getRequestId();
            }else{
    			throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue());            	
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("阿里云短信发送失败： phoneNumber = "+phoneNumber+" ; content = "+content+" ; ",e.getMessage());
			throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue(),e.getMessage(),null);
        }
		return result;
	}
	
	
	/**
	 * 功能：发送消息给多个手机号码（阿里云封装的方法）
	 */
	public void send(List<String> phoneNumberList, String content) throws BizException{
		StringBuffer sb=new StringBuffer();
		for(String phoneNumber:phoneNumberList){
			sb.append(phoneNumber).append(Separator.comma);
		}
		send(sb.toString(), content);
	}
	
	protected String getHttpUrl(String phoneNumber,String content){
		return "";
	}
	
	@Override
	protected String getMsgTemplate() {
		return "【安立信】";
	}

	@Override
	protected void checkResult(String result) throws BizException {
		if(StringUtils.isBlank(result)){
			throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue());
		}
	}

}

