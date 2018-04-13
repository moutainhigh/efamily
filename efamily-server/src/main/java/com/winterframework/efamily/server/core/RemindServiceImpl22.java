package com.winterframework.efamily.server.core;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.dto.RemindTaskStruc;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlResponse;

@Service("remindServiceImpl22")
public class RemindServiceImpl22 implements IRemindService {
	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl22.class);

	@Resource(name = "serviceHandler")
	private IServiceHandler serviceHandler;
	private static final Map<String, LinkedList<RemindStruc>> messageQueue = new ConcurrentHashMap<String, LinkedList<RemindStruc>>();
	private static final ExecutorService threadPool = Executors.newCachedThreadPool();

	@Override
	public void send(List<String> tokenKeyList, RemindStruc chatRecord) {
		if (null != tokenKeyList) {
			for (String tokenKey : tokenKeyList) {
				add(tokenKey, chatRecord);
			}
		}
	}

	private void add(String tokenKey, RemindStruc chatRecord) {
		if (null == chatRecord) {
			log.error("消息为空.tokenKey:" + tokenKey);
			return;
		}
		synchronized (tokenKey) {
			LinkedList<RemindStruc> messageList = messageQueue.get(tokenKey);
			if (messageList == null) {
				messageList = new LinkedList<RemindStruc>();
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
		LinkedList<RemindStruc> messageList = messageQueue.get(tokenKey);
		while (null != messageList) {
			synchronized (messageList) {
				while (messageList.size() <= 0) {
					try {
						messageList.wait();
					} catch (InterruptedException e) {
						log.error("Thread be interrupted.", e);
					}
				}
				RemindStruc chatRecord = messageList.removeFirst();
				ObjectMapper mapper=new ObjectMapper();
				RemindTaskStruc struc = new RemindTaskStruc();
				struc.setRemindId(chatRecord.getRemindId());
				struc.setRemindName(chatRecord.getRemindName());
				struc.setUserId(chatRecord.getDeliverUserId());
				struc.setUserName(chatRecord.getDeliverUserName());

				FmlResponse response = new FmlResponse();
				response.setEncode((byte) 0);
				response.setEncrypt((byte) 0);
				response.setVersion((byte) 1);
				response.setClinetType((byte) 0);
				response.setExtend((byte) 0);
				response.setSessionId(System.currentTimeMillis());
				//response.setCommand(Command.REMIND.getCode()); //固定，聊天消息推送
				response.setCommand(Command.NOTICE.getCode()); //固定，聊天消息推送
		        response.setValue("notyType", NotyMessage.Type.REMIND.getCode()+"");
		        response.setStatus(StatusCode.OK.getValue()); //默认0，ok 
				try {
					response.setValue("remind", mapper.writeValueAsString(struc));
				} catch (JsonProcessingException e) {
					response.setValue("remind", struc.getRemindName());
				}
                try {
                	String tokenKeyReal=new String(Base64Util.getBytesFromBASE64(tokenKey));
	            	String[] userInfos=tokenKeyReal.split(Separator.escape+Separator.vertical);
	            	Long userId=(1==userInfos.length && null!=userInfos[0])?Long.valueOf(userInfos[0]):null;
	            	Long deviceId=(2==userInfos.length && null!=userInfos[1])?Long.valueOf(userInfos[1]):null;
					serviceHandler.push(userId,deviceId, response);
				} catch (ServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    } 
    private void threadPoolPush() {
		threadPool.execute(
			new Thread(){
		    	public void run() {
		    		push();
		    	};
		    }
		);
	}

	private void threadPoolPush(final String tokenKey) {
		threadPool.execute(new Thread() {
			public void run() {
				reduce(tokenKey);
			};
		});
	}

	private void push() {
		for (Map.Entry<String, LinkedList<RemindStruc>> entry : messageQueue.entrySet()) {
			threadPoolPush(entry.getKey());
		}
	}

}
