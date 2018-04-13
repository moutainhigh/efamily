package com.winterframework.efamily.thirdparty.sms.impl;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.thirdparty.sms.IBaseSmsService;

public abstract class BaseSmsServiceImpl implements IBaseSmsService {
	private static final Logger log= LoggerFactory.getLogger(BaseSmsServiceImpl.class);
	@Override
	public String send(String phoneNumber, String content) throws BizException{
		String result = "";
		try {
			HttpResponse res=new DefaultHttpClient().execute(
					              new HttpGet(getHttpUrl(phoneNumber,getMsgTemplate()+content))
					          );
			if(null==res){					
				throw new BizException("response is null.");
			}
			result = EntityUtils.toString(res.getEntity());
			log.debug("短信发送结果：result = "+result+" ; phoneNumber = "+phoneNumber+" ; content = "+content+" ; ");
			checkResult(result);
		} catch (Exception e) {
			log.error("短信发送失败：result = "+result+" ; phoneNumber = "+phoneNumber+" ; content = "+content+" ; ");
			throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue(),e.getMessage(),null);
		}
		return result;
	}

	@Override
	public void send(List<String> phoneNumberList, String content) throws BizException{
		StringBuffer sb=new StringBuffer();
		for(String phoneNumber:phoneNumberList){
			sb.append(phoneNumber).append(Separator.comma);
		}
		send(sb.toString(), content);
	} 
	
	abstract protected String getHttpUrl(String phoneNumber,String content);
	abstract protected String getMsgTemplate();
	abstract protected void checkResult(String result)throws BizException;

}
