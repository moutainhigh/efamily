package com.winterframework.efamily.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;
/**
 * 诺安短信或取util
 * 
 * @author david
 *
 */
public class NovaMessageUtil {
	
		private static final Logger log = LoggerFactory.getLogger(NovaMessageUtil.class); 
		
		public static Integer getVerifyCode(String phone,Integer code){
			String url="http://api.wo100.com.cn/api/app/user/getSolgoValidateCode";
			Map<String,String> map=new HashMap<String, String>();
			map.put("phone", phone);
			map.put("type",3+"");
			map.put("code", code+"");
			map.put("deviceImei","000000");
			String charset="utf-8";
			DefaultHttpClient httpClient = new DefaultHttpClient();  
			HttpPost httpPost = null;
			String result = null;
			try{
				httpPost = new HttpPost(url);
				//设置参数
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String,String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
				}
				if(list.size() > 0){
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
					httpPost.setEntity(entity);
				}
				HttpResponse response = httpClient.execute(httpPost);
				if(response != null){
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						result = EntityUtils.toString(resEntity,charset);
						JsonParser jsonParser = new JsonParser();
						result=jsonParser.parse(result).getAsJsonObject().get("validateCode").getAsString();
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
				log.error("noan短信验证码获取错误"+ex);
			}
			return Integer.valueOf(result);
			
		}
		
		
		public static void main(String[] args) {
//			System.out.println(NovaMessageUtil.getVerifyCode("18623369944", 1234));
		}
		
	}

