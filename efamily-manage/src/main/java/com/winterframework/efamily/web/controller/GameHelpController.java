/**   
* @Title: GameLockWebController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-6 上午10:02:24 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;


import com.winterframework.efamily.entity.EjlHelp;
import com.winterframework.efamily.service.IEjlHelpService;


/**
 * 
* @ClassName: GameHelpController
* @Description:帮助controller
* @author floy
* @date 2013-9-23 下午3:52:52 
*
 */
@Controller("gameHelpController")
@RequestMapping(value = "/help")
@SuppressWarnings("unchecked")
public class GameHelpController {

	private Logger log = LoggerFactory.getLogger(GameHelpController.class);
	
	@Resource(name = "ejlHelpServiceImpl")
	private IEjlHelpService ejlHelpServiceImpl;

	/**
	 * 
	* @Title: toHelpList 
	* @Description:
	* @return
	 */
	@RequestMapping("/protocol")
	public ModelAndView toProtocol() throws Exception {
		ModelAndView view = new ModelAndView("help/xieyi");
		return view;
	}
	
	@RequestMapping("/novaprotocol")
	public ModelAndView novaProtocol() throws Exception {
		ModelAndView view = new ModelAndView("help/novaxieyi");
		return view;
	}
	
	@RequestMapping("/kangduoprotocol")
	public ModelAndView kangdooProtocol() throws Exception {
		ModelAndView view = new ModelAndView("help/kangdooxieyi");
		return view;
	}
	
	/**
	 * 
	* @Title: toHelpList 
	* @Description:
	* @return
	 */
	@RequestMapping("/index")
	public ModelAndView toHelpList() throws Exception {
		ModelAndView view = new ModelAndView("help/helpindex");
		List<EjlHelp> list = ejlHelpServiceImpl.getAll();
		view.addObject("list", list);
		return view;
	}
	
	/**
	 * 
	* @Title: getHelpById
	* @Description:
	* @return
	 */
	@RequestMapping("/getHelp")
	public ModelAndView getHelpById(@RequestParam("id") Long id) throws Exception {
		ModelAndView view = new ModelAndView("help/helpdetail");
		EjlHelp help = ejlHelpServiceImpl.get(id);
		view.addObject("help", help);
		return view;
	}
	
	
	
}
