package com.winterframework.efamily.server.daemon;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.server.main.FamilyServer;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service
public class NettyServer {
	private static final Logger log = LoggerFactory.getLogger(NettyServer.class);
	
	@PropertyConfig("server.port")
	private int port;
	
	@PostConstruct
	public void startup(){
		try {
			log.info("平台 服务 启动......");
			new Thread(new FamilyServer(port)).start();
			/*final Thread t=new Thread(new FamilyServer(port));
			log.info("平台 服务 启动......");
			t.start();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() { 
					t.interrupt();
				}
			});*/
		} catch (Exception e) {
			log.error("平台 服务 异常:",e);
		} 
		
	}
}