package com.winterframework.efamily.server.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.server.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
 
/**
 * 二维码浏览器打开 跳转
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月26日
 */
@Controller("qrcodeRedirectController")
@RequestMapping("/qr")
public class QrcodeRedirectController {
	private static final Logger log = LoggerFactory.getLogger(QrcodeRedirectController.class);
	@PropertyConfig(value = "app.download.url")
	private String appDownloadUrl;
	@PropertyConfig(value = "portal.url")
	private String portalUrl;
	
	@PropertyConfig(value = "app.server.getDownLoaderUrl")
	private String getDownLoaderUrl;
	
	@PropertyConfig("server.url.app")
	private String serverUrl;
	
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	
	
	
	@RequestMapping("/")
	@ResponseBody
	public void qr(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.sendRedirect(appDownloadUrl);
		} catch (IOException e) {
			log.error("qr error.",e);
		}
	}
	@RequestMapping("/tp")
	@ResponseBody
	public void tp(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.sendRedirect(portalUrl+"/download/app_zhms.html");
		} catch (IOException e) {
			log.error("qr error.",e);
		}
	}
	@RequestMapping("/tp/novatech")
	@ResponseBody
	public void nant(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.sendRedirect("http://www.wo100.com.cn/app/watch.html");
		} catch (IOException e) {
			log.error("qr error.",e);
		}
	}
	@RequestMapping("/tp/kangdoo")
	@ResponseBody
	public void kangdoo(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.sendRedirect("http://download.kangdoo.com/download.html");
		} catch (IOException e) {
			log.error("qr error.",e);
		}
	}
	
	
	@RequestMapping("/general")
	@ResponseBody
	public void general(HttpServletRequest request,HttpServletResponse response) {
		try {
			String imei = request.getParameter("imei");
			Response<String> bizRes=httpUtil.http(serverUrl,getDownLoaderUrl,imei,String.class);
			response.sendRedirect(bizRes.getData());
		} catch (Exception e) {
			log.error("qr error.",e);
		}
	}
}
