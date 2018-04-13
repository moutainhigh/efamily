/**   
* @Title: DeviceParamSetController.java 
* @Package com.winterframework.efamily.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-18 上午11:03:40 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quartz.monitor.object.PageResult;
import com.quartz.monitor.util.FileUtil;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComPublicDataDao;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dao.IQrcodeDao;
import com.winterframework.efamily.entity.EjlPublicData;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: DeviceParamSetController 
* @Description: TODO(数据字典管理) 
* @author denny 
* @date 2015-12-14 上午11:03:40 
*  
*/
@Controller("publicDataController")
@RequestMapping(value = "/publicData")
@SuppressWarnings("unchecked")
public class PublicDataController {

	@Resource(name = "httpClientImpl")
	protected IHttpClient httpClientImpl;
	
	@PropertyConfig(value = "server.url")
	private String serverUrl;

	@PropertyConfig(value="server.url.action.push")
	private String serverUrlActionPush;


	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@Resource(name = "ejlComPublicDataDaoImpl")
	private IEjlComPublicDataDao ejlComPublicDataDaoImpl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl;
	
	@Resource(name = "qrcodeDaoImpl")
	private IQrcodeDao qrcodeDaoImpl;

	private Logger log = LoggerFactory.getLogger(PublicDataController.class);

	@RequestMapping("/toPublicData")
	public ModelAndView toPublicData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("param/publicData");
		return view;
	}
	
	@RequestMapping("/findPublicData")
	@ResponseBody
	public Object findPublicData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageRequest pageRequest  = new PageRequest();
		String currentPage = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if(!StringUtils.isEmpty(currentPage)) {
			pageRequest.setPageNo(Integer.valueOf(currentPage));
		}
		if(!StringUtils.isEmpty(pageSize)) {
			pageRequest.setPageSize(Integer.valueOf(pageSize));
		}
		Page<EjlPublicData> publicDataList = ejlComPublicDataDaoImpl.getAllByPage(pageRequest);
		PageResult<EjlPublicData> pageResult = new PageResult<EjlPublicData>();
		pageResult.setRows(publicDataList.getResult());
		pageResult.setTotal(publicDataList.getTotalCount());
//		view.addObject("data",JsonUtils.toJson(pageResult));
		return JsonUtils.toJson(pageResult);
	}
	
	@RequestMapping("/updatePublicData")
	@ResponseBody
	public Object updatePublicData(@RequestBody List<EjlPublicData> publicDataList) throws Exception {
		for(EjlPublicData ejlPublicData : publicDataList) {
			if(ejlPublicData.getId() != null) {
				ejlComPublicDataDaoImpl.update(ejlPublicData);
			}else {
				ejlComPublicDataDaoImpl.insert(ejlPublicData);
			}
		}
		return JsonUtils.toJson(publicDataList);
	}
	
	
	@RequestMapping("/delPublicData")
	@ResponseBody
	public Object delPublicData(@RequestBody List<Long> ids) throws Exception {
		if(ids != null) {
			for(Long id : ids) {
				ejlComPublicDataDaoImpl.delete(id);
			}
		}
		return JsonUtils.toJson(ids);
	}
	
	
	@RequestMapping("/addQrcodeByImei")
	@ResponseBody
	public Object addQrcodeByImei(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		List<String> imeiList = FileUtil.readLongString(filePath);
		for(String imei:imeiList) {
			Qrcode entity = new Qrcode();
			entity.setCreateTime(new Date());
			entity.setCreatorId(-1l);
			entity.setImsi("imsi");
			//状态(0:失效 1:有效)
			entity.setStatus(1);
			//是否有卡(0无 1有)
			entity.setSimStatus(0);
			//设备类型(1手表 2手环...)
			entity.setType(1);
			entity.setImei(imei);
			qrcodeDaoImpl.insert(entity);
		}
		return null;
	}
	

}
