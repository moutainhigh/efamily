/**   
* @Title: GameFeedbackController.java 
* @Package com.winterframework.efamily.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-1 上午9:40:56 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.MessageSender;

import com.winterframework.efamily.entity.EjlFeedback;

import com.winterframework.efamily.service.IEjlFeedbackService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GameFeedbackController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-1 上午9:40:56 
*  
*/
@Controller("gameFeedbackController")
@RequestMapping(value = "/feedback")
@SuppressWarnings("unchecked")
public class GameFeedbackController {

	private Logger log = LoggerFactory.getLogger(GameHelpController.class);

	@Resource(name = "ejlFeedbackServiceImpl")
	private IEjlFeedbackService ejlFeedbackServiceImpl;

	@PropertyConfig(value = "filePath")
	private String filePath;

	@PropertyConfig(value = "mail.host")
	private String mailHost;

	@PropertyConfig(value = "mail.password")
	private String mailPassword;

	@PropertyConfig(value = "mail.to")
	private String mailTo;

	@PropertyConfig(value = "fileServer.url")
	private String fileServer;

	@RequestMapping("/toFeedback")
	public ModelAndView addFeedback() throws Exception {
		ModelAndView view = new ModelAndView("feedback/addFeedback");
		return view;
	}

	/**
	 * 
	* @Title: toHelpList 
	* @Description:
	* @return
	 */
	@RequestMapping("/addFeedback")
	public ModelAndView addFeedback(@ModelAttribute("req") EjlFeedback request) throws Exception {
		ModelAndView view = new ModelAndView("feedback/addFeedback");
		try {
			Context ctx = new Context();
			ctx.set("userId", -1);
			ejlFeedbackServiceImpl.save(ctx,request);
			view.addObject("addSuccess", "1");
			// 指定使用SMTP协议来创建Session对象  
			Session session = MessageSender.createSession("smtp");
			MessageSender.sendMail(mailHost, mailTo, mailPassword, "问题反馈", request.getContent(), request.getFiles());
		} catch (Exception e) {
			log.error("add feedback error", e);
		}
		return view;
	}

	@RequestMapping("/upload")
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();
		ModelAndView view = new ModelAndView("feedback/fileName");
		try {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			String filesOld = (String) multiRequest.getParameter("filesOld");
			String contentOld = (String) multiRequest.getParameter("contentOld");
			try {
				Iterator<String> files = multiRequest.getFileNames();
				while (files.hasNext()) {
					MultipartFile multiFile = multiRequest.getFile(files.next());
					String oriName = this.getExtension(multiFile.getOriginalFilename());
					//生成唯一图片名称
					String fileNameNotType = org.apache.commons.codec.digest.DigestUtils.md5Hex(multiFile.getBytes());
					//压缩图片名称
					String fileName = fileNameNotType + "." + oriName;
					String fileNewPath = this.save(filePath + fileName, multiFile);
					view.addObject("newFiles", fileServer + fileName);
					if (filesOld.isEmpty()) {
						filesOld = fileNewPath;
					} else {
						filesOld += "," + fileNewPath;
					}
					break;
				}

			} catch (Exception e) {
				log.error("反馈文件上传失败", e);
				view.addObject("newFiles", fileServer + "222222");
			}
			view.addObject("files", filesOld);
			view.addObject("content", contentOld);
		} catch (Exception e) {
			view.addObject("newFiles", fileServer + "222222");
			log.error("反馈文件上传失败", e);
		}
		return view;
	}

	public String getExtension(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1);
		}
		return ext;
	}

	public String save(String filePath, MultipartFile multifile) {
		if (null != filePath && null != multifile) {
			try {
				File destFile = new File(filePath);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				multifile.transferTo(destFile);
				return destFile.getAbsolutePath();
			} catch (IOException e) {
				log.error("Error occurs when save file", e);
			} finally {
			}
		}
		return null;
	}

}
