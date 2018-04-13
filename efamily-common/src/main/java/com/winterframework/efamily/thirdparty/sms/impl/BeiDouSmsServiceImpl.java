package com.winterframework.efamily.thirdparty.sms.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.modules.spring.exetend.PropertyConfig;

	@Service("beiDouSmsServiceImpl")
	public class BeiDouSmsServiceImpl extends BaseSmsServiceImpl {
		private static final Logger log= LoggerFactory.getLogger(BeiDouSmsServiceImpl.class);
		
		@PropertyConfig("sms.beidou.url")
		private String smsUrl;
		@PropertyConfig("sms.beidou.acc")
		private String smsAccount;
		@PropertyConfig("sms.beidou.pwd")
		private String smsPasswd;
		@PropertyConfig("sms.beidou.spcode")
		private String smsSpcode;
		
		public String send(String phoneNumber, String content) throws BizException{
			String info = null;
			
			try{
				HttpClient httpclient = new HttpClient();
				
				/*
				        企业编码：238573
	                                        用户名：admin1
	                                        密码：Tianyun@9909 
				 */
				
				//PostMethod post = new PostMethod("http://112.65.228.39:8897/sms/Api/Send.do");//
				PostMethod post = new PostMethod(smsUrl);//
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
				post.addParameter("SpCode", smsSpcode);
				post.addParameter("LoginName", smsAccount);
				post.addParameter("Password", smsPasswd);
				post.addParameter("MessageContent", content);
				post.addParameter("UserNumber", phoneNumber);
				post.addParameter("SerialNumber", phoneNumber.substring(phoneNumber.length()-3)+DateUtils.format(new Date(), DateUtils.DATETIME_SENDTIME_FORMAT_PATTERN));
				post.addParameter("ScheduleTime", "");
				post.addParameter("ExtendAccessNum", "");
				post.addParameter("f", "1");
				httpclient.executeMethod(post);
				info = new String(post.getResponseBody(),"gbk");
				checkResult(info);
				System.out.println(info);
			}catch (Exception e) {
				log.error("短信发送失败：info = "+info+" ; phoneNumber = "+phoneNumber+" ; content = "+content+" ; ");
				throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue(),e.getMessage(),null);
			}
			return info;
		}
		
		
		protected String getHttpUrl(String phoneNumber,String content){
			String encodedContent = null;
			try {
				encodedContent = URLEncoder.encode(content, "utf-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(),e);
				return null;
			}
			StringBuffer strBuf = new StringBuffer(smsUrl);
			strBuf.append("?SpCode=").append(smsSpcode)
				  .append("&LoginName=").append(smsAccount)
				  .append("&smsPasswd=").append(smsPasswd)
				  .append("&MessageContent=").append(encodedContent)
				  .append("&UserNumber=").append(phoneNumber)
				  .append("&SerialNumber=").append(phoneNumber.substring(phoneNumber.length()-6)+DateUtils.format(new Date(), DateUtils.DATETIME_SENDTIME_FORMAT_PATTERN))
				  .append("&ScheduleTime=").append("")
				  .append("&ExtendAccessNum=").append("")
				  .append("&f=").append("1");
			return strBuf.toString();
		}
		
		@Override
		protected String getMsgTemplate() {
			return "";//北斗天云  E通信 方加签名
		}

		@Override
		protected void checkResult(String result) throws BizException {
			//返回格式: result=0&description=错误描述&faillist=失败号码列表
/*			try {
				result = new String(result.getBytes(),"gbk");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				log.error("短信结果解析出现异常： result = "+result+" ; ");
				e.printStackTrace();
			}*/
			String code = result.split("&")[0].split("=")[1];
			if(!"0".equals(code)){
				log.error("短线发送失败： result = "+result+" ; ");
				throw new BizException(StatusBizCode.SMS_SEND_FAILED.getValue());
			}else{
				log.info("短信发送成功：result = "+result+" ; ");
			}
		}
		
	}
