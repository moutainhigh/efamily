package com.winterframework.efamily.base.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushClientUtil {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientUtil.class);

    //private static final String appKey ="9b2b4f97a5ddad7890f6bc17";
    //private static final String masterSecret = "8ec8891d349625e49743e430";
    
    private static final String songguoAppKey ="af47c12624236cfcb0131f2f";
	private static final String songguoMasterSecret = "782e99c5bc74a931233a656b";
	
	private static final String novaAppKey ="37f17bee8dae8f07afb2bd74";
	private static final String novaMasterSecret = "d65421bd91df299c3942df39";
	
	private static final String kangduoAppKey ="59432a526899d347dfa3f736";
	private static final String kangduoMasterSecret = "8354a919811def461e6c9efc";
	
	
	private static final String huaShouAppKey ="45d1f51e58063d8462de55e4";
	private static final String huaShouMasterSecret = "839d34698769718051903152";
	
	
	private static final String zhiHuiAppKey ="ed8b2d6db5714e8695d14f3c";
	private static final String zhiHuiMasterSecret = "56f9685a0f747ec8ffa14678";
	
	private static final String qiZhiAppKey ="00d27bfd42121b4f7be2f3c2";
	private static final String qiZhiMasterSecret = "c754e149e47c73744b7e1920";
	
	private static final String beiDouAppKey ="dae70379a6687690779d5e38";
	private static final String beiDouMasterSecret = "e006cdcc0ec6cc6038f35dfe";
	
	private static final String anLiXinAppKey ="059b3d43f328051cfbde7c6f";
	private static final String anLiXinMasterSecret = "f75727d214e78edef03ca10c";
	
/*	 AppKey： 59432a526899d347dfa3f736

	 Master Secret： ? 8354a919811def461e6c9efc*/
	
	public static final String TITLE = "TITLE";
    public static final String ALERT = "ALERT";
    public static final String MSG_CONTENT = "MSG_CONTENT";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    public static final boolean APNS_PRODUCTION = true;	//app store =true
    
	

	
	public static void sendPushBy( String alias,String alert,Map<String,String> data,String appType) {
		String appKey="";
		String masterSecret="";
		if(StringUtils.isBlank(alias) || alias.equals("null")){
			LOG.error("alias 为空：极光推送失败 ; data = "+data+" ; ");
			return;
		}
		if(StringUtils.isBlank(appType)){
			LOG.error("appType 为空：极光推送失败 ; data = "+data+" ; ");
			return;
		}
		
		if("1".equals(appType)){//松果 亿家联
			 appKey=songguoAppKey;
			 masterSecret=songguoMasterSecret;
		}else if("2".equals(appType)){//诺安诺泰
			 appKey=novaAppKey;
			 masterSecret=novaMasterSecret;
		}else if("3".equals(appType)){//康朵
			 appKey=kangduoAppKey;
			 masterSecret=kangduoMasterSecret;
		}else if("5".equals(appType)){//华寿
			 appKey=huaShouAppKey;
			 masterSecret=huaShouMasterSecret;
		}else if("6".equals(appType)){//智慧健康
			 appKey=zhiHuiAppKey;
			 masterSecret=zhiHuiMasterSecret;
		}else if("7".equals(appType)){//奇智科技
			 appKey=qiZhiAppKey;
			 masterSecret=qiZhiMasterSecret;
		}else if("8".equals(appType)){//北斗天云
			 appKey=beiDouAppKey;
			 masterSecret=beiDouMasterSecret;
		}else if("9".equals(appType)){//中斗安立信
			 appKey=anLiXinAppKey;
			 masterSecret=anLiXinMasterSecret;
		}else{
			 LOG.error("appType 未定义：极光推送失败 ; data = "+data+" ; appType = "+appType+" ; ");
			 return;
		}
		
		sendPushBy(alias, alert, data,appKey,masterSecret);
	}
	
	/**
	 * 
	 * @param alias    ==>  userId
	 * @param alert    ==>  提醒内容
	 * @param data     ==>  推送数据  KEY-VALUE
	 * @param message  ==>  消息
	 */
	public static void sendPushBy( String alias,String alert,Map<String,String> data,String appKey,String masterSecret) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        PushPayload payload = buildPushObject_all_alias_alert(alias,alert,"message",data);
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("result : "+result);
            LOG.info("Got result - " + result+" alias="+alias);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. alias="+alias, e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
	}
	
	
	public static void sendPush() {
	    // HttpProxy proxy = new HttpProxy("localhost", 3128);
	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        JPushClient jpushClient = new JPushClient(songguoMasterSecret, songguoAppKey, 3);
        
        // For push, all you need do is to build PushPayload object.
         PushPayload payload = buildPushObject_all_all_alert();
        //PushPayload payload = buildPushObject_all_alias_alert();
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("result : "+result);
            LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
	}
	
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(ALERT);
	}
	
    public static PushPayload buildPushObject_all_alias_alert(String alias,String alert,String message,Map<String,String> data) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("from", "JPush")
                                .build())
                         .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert("")
                                .setTitle("")
                                .setBuilderId(3)
                                .addExtra("from", "JPush")
                                .build()) 
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(message)
                        .setContentType("msg")
                        .addExtras(data)
                        .build())
                .setOptions(Options.newBuilder()
                         .setApnsProduction(APNS_PRODUCTION)
                         .build())   
                .build();
    }
    
    public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag,String alert,String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
    
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("default")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(APNS_PRODUCTION)
                         .build())
                 .build();
    }
    
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    
    public static void main(String[] args) {
		String userId ="666";
		String content = "[content]: 你妈妈叫你回家吃饭 !!!";
		String message = "messageJson";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("key_1", "value_1");
		dataMap.put("key_2", "value_2");
		dataMap.put("key_3", "value_3");
	    //sendPush();
	    sendPushBy( userId, content,dataMap,"2"); 
	    System.out.println("sssss");
	     
	}
}