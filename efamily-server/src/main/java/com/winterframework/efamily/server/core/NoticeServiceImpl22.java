package com.winterframework.efamily.server.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlResponse;

@Service("noticeServiceImpl22")
public class NoticeServiceImpl22 implements INoticeService{
	private static final Logger log= LoggerFactory.getLogger(NoticeServiceImpl22.class);
    
	@Resource(name="serviceHandler")
	private IServiceHandler serviceHandler;
	@Resource(name="propertyUtil")
	private PropertyUtil propertyUtil;
	
	private static final LinkedList<UserNotice> messageQueue = new LinkedList<UserNotice>();
	private static final ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Override
	public void notify(UserNotice userNotice) {
		if(null!=userNotice){
			synchronized (messageQueue) {				
				messageQueue.addLast(userNotice);
				messageQueue.notify();
			}
			push();	   
		}
	} 
 
    private void reduce() { 
        while (null!=messageQueue && messageQueue.size()>0) {
	        synchronized (messageQueue) { 
	            while (messageQueue.size()<=0) {
	                try {
	                	messageQueue.wait();
	                } catch (InterruptedException e) {
	                    log.error("Thread be interrupted.",e);
	                }
	            }
	            UserNotice userNotice = messageQueue.getFirst();
	                      	
	            FmlResponse response=new FmlResponse();
	            response.setEncode((byte)0);
	            response.setEncrypt((byte)0);
	            response.setVersion((byte)1);
	            response.setClinetType((byte)0);
	            response.setExtend((byte)0);
	            response.setSessionId(System.currentTimeMillis());
	            
	            response.setCommand(Command.NOTICE.getCode()); //固定，聊天消息推送
	            //response.setCommand(userNotice.getNoticeType().getValue()); //固定，聊天消息推送
	            response.setValue("notyType", NotyMessage.Type.NOTICE.getCode()+"");
				response.setStatus(StatusCode.OK.getValue());  //默认0，ok 
				response.setValue("noticeType", userNotice.getNoticeType().getValue()+"");
				Map<String,String> paramMap=userNotice.getParamMap();
				if(null!=paramMap){
					for(Map.Entry<String, String> param:paramMap.entrySet()){
						response.setValue(param.getKey(), param.getValue());					
					}
				} 
				String content=propertyUtil.getProperty(userNotice.getNoticeType().getValue()+"");
				response.setValue("content", replaceParam(content,paramMap));
				
				/*String content="{$userName}请求加你为好友";
				System.out.println(userNotice.getNoticeType().getValue()+" "+userNotice.getUserId()+" "+replaceParam(content,paramMap));*/
	            try {
	            	messageQueue.remove(userNotice);
					serviceHandler.push(userNotice.getUserId(),null, response);
				} catch (ServerException e) {
					log.error(e.getCode()+"",e);
				}
	        }
        }
    } 
    private String replaceParam(String template, Map<String, String> paramMap) {
    	if(null==template) return "";
    	if(null==paramMap) return "";
		try {
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String key = "{$" + entry.getKey() + "}";
				template = template.replace(key, entry.getValue());
			}
		} catch (Exception e) {
			log.error("replace template param error", e);
		}
		return template;
	}
    private void threadPoolPush() {
    	threadPool.execute(
				new Thread(){
			    	public void run() {
			    		reduce(); 
			    	};
			    }
			);
	}
    private void push(){
		threadPoolPush();
    }
    
    public static void main(String[] args){
    	INoticeService msg=new NoticeServiceImpl22();
    	List<Long> userIdList=new ArrayList<Long>();
    	UserNotice r=new UserNotice();
    	r.setUserId(111L);
    	r.setNoticeType(UserNotice.NoticeType.FRIEND);
    	Map<String,String> paramMap=new HashMap<String,String>();
    	paramMap.put("userName", "xiaom");
    	r.setParamMap(paramMap);
    	/*msg.notify(r);
    	UserNotice r2=new UserNotice();
    	r2.setUserId(112L);
    	r2.setNoticeType(UserNotice.NoticeType.FRIEND);
    	Map<String,String> paramMap2=new HashMap<String,String>();
    	paramMap2.put("userName", "xiaom2");
    	r2.setParamMap(paramMap2);
    	msg.notify(r2);*/
    }
    
}
