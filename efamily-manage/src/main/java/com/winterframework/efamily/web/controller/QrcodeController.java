/**   
* @Title: DeviceParamSetController.java 
* @Package com.winterframework.efamily.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-18 上午11:03:40 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.quartz.monitor.util.FileUtil;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.dao.IQrcodeDao;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: QrcodeController 
* @Description: TODO(数据字典管理) 
* @author denny 
* @date 2015-12-14 上午11:03:40 
*  
*/
@Controller("qrcodeImeiController")
@RequestMapping(value = "/qrcode")
@SuppressWarnings("unchecked")
public class QrcodeController {
	//批量处理数量
	public static int BATCH_COUNT=500;

	@Resource(name = "httpClientImpl")
	protected IHttpClient httpClientImpl;
	
	@PropertyConfig(value = "server.url")
	private String serverUrl;

	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@Resource(name = "qrcodeDaoImpl")
	private IQrcodeDao qrcodeDaoImpl;

	private Logger log = LoggerFactory.getLogger(QrcodeController.class);
	
	@RequestMapping("/toQrcode")
	public ModelAndView toQrcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("param/qrcode");
		return view;
	}
	
	@RequestMapping("/addQrcodeByImei")
	@ResponseBody
	public Object addQrcodeByImei(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		List<String> imeiList = FileUtil.readLongString(filePath);
		for(String imei:imeiList) {
			Qrcode qrcode = qrcodeDaoImpl.getByImei(imei);
			if(qrcode != null && qrcode.getId() != null) {
				continue;
			}
			Qrcode entity = new Qrcode();
			entity.setCreateTime(new Date());
			entity.setCreatorId(-1l);
			entity.setImsi("imsi");
			//状态(0:失效 1:有效)
			entity.setStatus(0);
			//是否有卡(0无 1有)
			entity.setSimStatus(0);
			//设备类型(1手表 2手环...)
			entity.setType(1);
			entity.setImei(imei);
			qrcodeDaoImpl.insert(entity);
		}
		return null;
	}
	
	
	@RequestMapping("/addQrcodeByFile")
	@ResponseBody
	public Object addQrcodeByFile(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file) throws Exception {
		if(file != null && file.getInputStream() != null) {
			ModelAndView view = new ModelAndView("param/qrcode");
//			String filePath = request.getParameter("filePath");
			InputStream fileInputStream = file.getInputStream();  
			List<String> imeiList = FileUtil.readLongString(fileInputStream);
			//将数据分为多批次，分批次插入
			List<List<String>> batchList = getBatchList(imeiList,BATCH_COUNT);
			for(List<String> imeiBatch:batchList) {
				List<Qrcode> qrcodeList = qrcodeDaoImpl.getByImeiList(imeiBatch);
				Map<String,String> queryMap = new HashMap<String,String>();
				List<Qrcode> insertBatch = new ArrayList<Qrcode>();
				for(Qrcode qrcode:qrcodeList) {
					queryMap.put(qrcode.getImei(), qrcode.getImei());
				}
				for(String imei:imeiBatch) {
//					Qrcode qrcode = qrcodeDaoImpl.getByImei(imei);
					//如果imei已经存在，则不插入改
					if(queryMap != null && queryMap.containsKey(imei)) {
						continue;
					}
					Qrcode entity = new Qrcode();
					entity.setCreateTime(new Date());
					entity.setCreatorId(-1l);
					entity.setImsi("imsi");
					//状态(0:失效 1:有效)
					entity.setStatus(0);
					//是否有卡(0无 1有)
					entity.setSimStatus(0);
					//设备类型(1手表 2手环...)
					entity.setType(1);
					entity.setImei(imei);
					insertBatch.add(entity);
				}
				if(!insertBatch.isEmpty()) {
					qrcodeDaoImpl.insertBatchEntity(insertBatch);
				}
			}
			Map model = new HashMap<String,String>();
			model.put("success", "success");
			view.addAllObjects(model);
			return view;
		}
		return null;
	}
	
	
	
//	@RequestMapping("/addQrcodeByFile")
//	@ResponseBody
//	public Object addQrcodeByFile(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file) throws Exception {
//		if(file != null && file.getInputStream() != null) {
//			ModelAndView view = new ModelAndView("param/qrcode");
////			String filePath = request.getParameter("filePath");
//			InputStream fileInputStream = file.getInputStream();  
//			List<String> imeiList = FileUtil.readLongString(fileInputStream);
//			for(String imei:imeiList) {
//				Qrcode qrcode = qrcodeDaoImpl.getByImei(imei);
//				if(qrcode != null && qrcode.getId() != null) {
//					continue;
//				}
//				Qrcode entity = new Qrcode();
//				entity.setCreateTime(new Date());
//				entity.setCreatorId(-1l);
//				entity.setImsi("imsi");
//				//状态(0:失效 1:有效)
//				entity.setStatus(0);
//				//是否有卡(0无 1有)
//				entity.setSimStatus(0);
//				//设备类型(1手表 2手环...)
//				entity.setType(1);
//				entity.setImei(imei);
//				qrcodeDaoImpl.insert(entity);
//			}
//			Map model = new HashMap<String,String>();
//			model.put("success", "success");
//			view.addAllObjects(model);
//			return view;
//		}
//		return null;
//	}
	
	private List<List<String>> getBatchList(List<String> list,int batchCount) {
		if(list == null || list.isEmpty()) {
			return null;
		}
		List<List<String>> batchList = new ArrayList<List<String>>();
		List<String> strList = new ArrayList<String>();
 		for(int i=0;i<list.size();i++) {
			if(i%batchCount == 0 && i!=0) {
				batchList.add(strList);
				strList = new ArrayList<String>();
			}
			strList.add(list.get(i));
		}
 		batchList.add(strList);
 		return batchList;
	}

}
