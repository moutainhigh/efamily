package com.winterframework.winter_common;

import com.bcloud.msg.http.HttpSender;


public class TestSend {
	public static void main(String[] args) {
		String uri = "http://222.73.117.158/msg/";//应用地址
		String account = "vip-yjl";//账号
		String pswd = "Tch123456";//密码
		String mobiles = "18623369944";//手机号码，多个号码使用","分割
		String content = "亲爱的亿家联用户，您的验证码是123456，5分钟内有效。";//短信内容
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		 
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
			System.out.println(returnString);
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
	}
}
