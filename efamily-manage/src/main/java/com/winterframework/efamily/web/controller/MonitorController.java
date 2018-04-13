/**   
* @Title: DeviceParamSetController.java 
* @Package com.winterframework.efamily.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-18 上午11:03:40 
* @version V1.0   
*/
package com.winterframework.efamily.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winterframework.efamily.dto.CustomerStatisticsDataInfo;
import com.winterframework.efamily.dto.CustomerStatisticsPageShowInfo;
import com.winterframework.efamily.dto.manage.UserMonitorStruc;
import com.winterframework.efamily.service.IEjlUserService;
 
@Controller("monitorController")
@RequestMapping(value = "/monitor") 
public class MonitorController { 
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserService; 
	private Logger log = LoggerFactory.getLogger(MonitorController.class);

	@RequestMapping(value = "/user")
	public String queryOrders(Model model) throws Exception {
		UserMonitorStruc userMonitorStruc=ejlUserService.getStatistics();

		model.addAttribute("userMonitor",userMonitorStruc);

		return "/monitor/user"; 

	}
	
	@RequestMapping(value = "/deviceStatistics")
	public String queryDeviceStatistics(Model model) throws Exception {
		CustomerStatisticsPageShowInfo customerStatisticsPageShowInfo = ejlUserService.getDeviceStatistics();
		
		Integer produceDeviceDataTotal = 0;
		List<CustomerStatisticsDataInfo> customerStatisticsDataInfoList = new ArrayList<CustomerStatisticsDataInfo>();
		for(CustomerStatisticsDataInfo temp:customerStatisticsPageShowInfo.getCustomerStatisticsDataInfoMap().values()){
			produceDeviceDataTotal+=temp.getProduceDeviceData();
			customerStatisticsDataInfoList.add(temp);
		}
		
		model.addAttribute("statisticsDataList",customerStatisticsDataInfoList);
		model.addAttribute("bindDeviceDataTotal",customerStatisticsPageShowInfo.getBindDeviceDataTotal());
		model.addAttribute("unbindDeviceDataTotal",customerStatisticsPageShowInfo.getUnbindDeviceDataTotal());
		model.addAttribute("storageDeviceDataTotal",customerStatisticsPageShowInfo.getStorageDeviceDataTotal());
		model.addAttribute("produceDeviceDataTotal",produceDeviceDataTotal);
		
		return "/monitor/deviceStatistics"; 

	}
}
