package com.quartz.monitor.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.util.XstreamUtil;

public class InitListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(InitListener.class);

	public void contextInitialized(ServletContextEvent event) {

		log.info("load properties to config container");
		String path = event.getServletContext().getRealPath("/WEB-INF/classes/quartz-config-jms.properties");
		FileInputStream in;
		try {
			in = new FileInputStream(path);
			Properties p = new Properties();
			try {
				p.load(in);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String uuid = p.getProperty("uuid");
			String host = p.getProperty("host");
			String port = p.getProperty("port");
			String userName = p.getProperty("userName");
			String password = p.getProperty("password");
			QuartzConfig config = new QuartzConfig(uuid, host, Integer.valueOf(port), userName, password);
			QuartzInstanceContainer.addQuartzConfig(config);
			QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
			QuartzInstance quartzInstance;
			try {
				quartzInstance = quartzConnectService.initInstance(config);
				QuartzInstanceContainer.addQuartzInstance(config.getUuid(), quartzInstance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
}
