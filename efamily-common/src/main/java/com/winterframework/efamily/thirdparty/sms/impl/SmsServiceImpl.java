package com.winterframework.efamily.thirdparty.sms.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.ServerException;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bcloud.msg.http.HttpSender;
import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.thirdparty.sms.ISmsService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("smsServiceImpl")
public class SmsServiceImpl implements ISmsService {
	@PropertyConfig("sms.server.url")
	private String smsServerUrl;
	@PropertyConfig("sms.server.key")
	private String smsServerKey;
	@PropertyConfig("sms.server.pwd")
	private String smsServerPwd;
	
	private boolean needstatus = true;//是否需要状态报告，需要true，不需要false
	private String product = null;//产品ID
	private String extno = null;//扩展码
	
	@Override
	public String send(String phoneNumber, String content) throws ServerException{
		String returnString="";
		try {
			returnString= HttpSender.batchSend(smsServerUrl, smsServerKey, smsServerPwd, phoneNumber, content, needstatus, product, extno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	@Override
	public String send(List<String> phoneNumberList, String content) throws ServerException{
		StringBuffer sb=new StringBuffer();
		for(String phoneNumber:phoneNumberList){
			sb.append(phoneNumber).append(Separator.comma);
		}
		return send(sb.toString(), content);
	}
	
	private String getParams(Object[] objs){
		String paramsTemp="account=" + smsServerKey + "&pswd=" + smsServerPwd + "&mobile={0}&msg={1}&needstatus=true";
		return MessageFormat.format(paramsTemp,objs);
	}
	
	@SuppressWarnings("unused")
	private String getSend(String strUrl,String param){
		URL url = null;
		HttpURLConnection connection = null;
		
		try {
			url = new URL(strUrl + "?" + param);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
				}
		}
	}
	
	public static String postSend(String strUrl,String param){
		URL url = null;
		HttpURLConnection connection = null;
		
		try {
			URLEncoder.encode(param, "UTF-8");
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();
 
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
				}
		}
		
		
	} 

}
