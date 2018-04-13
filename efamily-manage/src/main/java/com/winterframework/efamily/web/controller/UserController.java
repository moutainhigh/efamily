/**   
* @Title: DeviceParamSetController.java 
* @Package com.winterframework.efamily.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-18 上午11:03:40 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quartz.monitor.object.PageResult;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: UserController 
* @Description: TODO() 用户查询
* @author denny 
* @date 2016-1-13 上午11:03:40 
*  
*/
@Controller("userController")
@RequestMapping(value = "/user")
@SuppressWarnings("unchecked")
public class UserController {
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	


	private Logger log = LoggerFactory.getLogger(UserController.class);
 	
	@RequestMapping("/toUser")
	public ModelAndView toUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("param/user");
		return view;
	}
	
	@RequestMapping("/findUser")
	@ResponseBody
	public Object findUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest<EjlUser> pageRequest  = new PageRequest<EjlUser>();
		String currentPage = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String phone = request.getParameter("phone");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		if(!StringUtils.isEmpty(currentPage)) {
			pageRequest.setPageNo(Integer.valueOf(currentPage));
		}
		if(!StringUtils.isEmpty(pageSize)) {
			pageRequest.setPageSize(Integer.valueOf(pageSize));
		}
		if(!StringUtils.isEmpty(status)||!StringUtils.isEmpty(type)||!StringUtils.isEmpty(phone)) {
			EjlUser queryUser = new EjlUser();
			if(!StringUtils.isEmpty(status)) {
				queryUser.setStatus(Long.valueOf(status));
			}
			if(!StringUtils.isEmpty(phone)) {
				queryUser.setPhone(phone);
			}
			if(!StringUtils.isEmpty(type)) {
				queryUser.setType(Long.valueOf(type));
			}
			pageRequest.setSearchDo(queryUser);
		}
		Page<EjlUser> userList = ejlUserDaoImpl.getAllByPage(pageRequest);
		PageResult<EjlUser> pageResult = new PageResult<EjlUser>();
		
		pageResult.setRows(userList.getResult());
		pageResult.setTotal(userList.getTotalCount());
//		view.addObject("data",JsonUtils.toJson(pageResult));
		return pageResult;
	}
	

	

	

	

}
