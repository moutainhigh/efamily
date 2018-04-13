package com.winterframework.efamily.server.core;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.dto.Message;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlResponse;
@Service("messageServiceImpl22")
public class MessageServiceImpl22 implements IMessageService{
	private static final Logger log= LoggerFactory.getLogger(MessageServiceImpl22.class);
    
	@Resource(name="serviceHandler")
	private IServiceHandler serviceHandler; 
	 
	
	private static final Map<String,LinkedList<Message>> messageQueue = new ConcurrentHashMap<String,LinkedList<Message>>();
	private static final ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Override
	public void send(List<String> tokenKeyList,Message chatRecord) {
		if(null!=tokenKeyList){ 
			for(String tokenKey:tokenKeyList){
				add(tokenKey,chatRecord);
			}
		}
	} 
 
    private void add(String tokenKey, Message chatRecord) { 
    	if(null==chatRecord){
    		log.error("消息为空.tokenKey:"+tokenKey);
    		return;
    	}
    	synchronized (tokenKey) {
	    	LinkedList<Message> messageList = messageQueue.get(tokenKey);
			if (messageList == null) {
	        	messageList = new LinkedList<Message>();
	    	}
	    	synchronized (messageList) { 
	        	messageList.addLast(chatRecord);
	        	messageQueue.put(tokenKey, messageList);
	        	messageList.notifyAll(); 
	    	}
    	}
    	push();	   
    } 
 
    private void reduce(String tokenKey) { 
        LinkedList<Message> messageList = messageQueue.get(tokenKey);
        while (null!=messageList) {
	        synchronized (messageList) { 
	            while (messageList.size()<=0) {
	                try {
	                	messageList.wait();
	                } catch (InterruptedException e) {
	                    log.error("Thread be interrupted.",e);
	                }
	            }
	            Message chatRecord = messageList.removeFirst();
	            FmlResponse response=new FmlResponse();
				try {  
					//user = ejlUserServiceImpl.getUserByUserId(chatRecord.getSendUserId());
					//response.setValue("sendUserType", user.getType()+"");
					//####################  此处的用户类型先写为 2  后期在做处理 
					response.setValue("sendUserType", "2");
				} catch (Exception e) {
					log.error("get user info error.tokenKey="+tokenKey,e);
				}
	          
	            response.setEncode((byte)0);
	            response.setEncrypt((byte)0);
	            response.setVersion((byte)1);
	            response.setClinetType((byte)0);
	            response.setExtend((byte)0);
	            response.setSessionId(System.currentTimeMillis());
	            //response.setCommand(Command.CHAT_MESSAGE.getCode()); //固定，聊天消息推送
	            response.setCommand(Command.NOTICE.getCode()); //固定，聊天消息推送
	            response.setValue("notyType", NotyMessage.Type.MSG.getCode()+"");
				response.setStatus(StatusCode.OK.getValue());  //默认0，ok 
				response.setValue("content", chatRecord.getContent());
				response.setValue("contentType", chatRecord.getContentType()+"");
				response.setValue("contentTime", chatRecord.getContentTime()+"");
				response.setValue("sendTime", new Date().getTime()+"");
				//response.setValue("messageId", chatRecord.getId()+"");
				response.setValue("chatGroupId", chatRecord.getChatRoomId()+"");
				response.setValue("chatType", chatRecord.getChatType()+"");
				response.setValue("sendUserId", chatRecord.getSendUserId()+"");
	            try {
	            	String tokenKeyReal=new String(Base64Util.getBytesFromBASE64(tokenKey));
	            	String[] userInfos=tokenKeyReal.split(Separator.escape+Separator.vertical);
	            	Long userId=(1==userInfos.length && null!=userInfos[0])?Long.valueOf(userInfos[0]):null;
	            	Long deviceId=(2==userInfos.length && null!=userInfos[1])?Long.valueOf(userInfos[1]):null;
					serviceHandler.push(userId,deviceId, response);
				} catch (ServerException e) {
					log.error(e.getCode()+"",e);
				}
	        }
        }
    } 
    private void threadPoolPush(final String tokenKey) {
    	threadPool.execute(
				new Thread(){
			    	public void run() {
			    		reduce(tokenKey); 
			    	};
			    }
			);
	}
    private void push(){
    	for(Map.Entry<String,LinkedList<Message>> entry:messageQueue.entrySet()){
			threadPoolPush(entry.getKey());
		}
    }
    
    public static void main(String[] args){
    	IMessageService msg=new MessageServiceImpl22();
    	List<String> userIdList=new ArrayList<String>();
    	
    }
    
}
