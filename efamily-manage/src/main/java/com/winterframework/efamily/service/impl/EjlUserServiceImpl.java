package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IStatisticComUserConnectDao;
import com.winterframework.efamily.dto.CustomerDeviceStatisticsDto;
import com.winterframework.efamily.dto.CustomerStatisticsDataInfo;
import com.winterframework.efamily.dto.CustomerStatisticsPageShowInfo;
import com.winterframework.efamily.dto.manage.UserMonitor;
import com.winterframework.efamily.dto.manage.UserMonitorStruc;
import com.winterframework.efamily.dto.manage.UserMonitorStrucDetail;
import com.winterframework.efamily.dto.manage.UserStruc;
import com.winterframework.efamily.entity.EfCustomer;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.StatisticUserConnect;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.web.controller.MonitorController;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserServiceImpl extends EjlComUserServiceImpl implements IEjlUserService {

	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDao;

	@Resource(name = "statisticComUserConnectDao")
	private IStatisticComUserConnectDao statisticComUserConnectDao;

	@Resource(name = "efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerServiceImpl;
	
	@PropertyConfig("netty.ip.list")
	private String ips;
	
	private Logger log = LoggerFactory.getLogger(EjlUserServiceImpl.class);

	public CustomerStatisticsPageShowInfo getDeviceStatistics()throws Exception {
		CustomerStatisticsPageShowInfo customerStatisticsPageShowInfo = new CustomerStatisticsPageShowInfo();
		
		List<CustomerDeviceStatisticsDto> customerDeviceStatisticsDtoList = ejlUserDao.getNumberModelStatistcsList();
		statisticsDataHandler(customerStatisticsPageShowInfo, customerDeviceStatisticsDtoList, 0);
		
		List<CustomerDeviceStatisticsDto> customerDeviceModelStatisticsDtoList = ejlUserDao.getNumberModelStatusStatistcsList();
		statisticsDataHandler(customerStatisticsPageShowInfo, customerDeviceModelStatisticsDtoList, 1);

		return customerStatisticsPageShowInfo;
	}
	
	
	
	
	/**
	  	public List<CustomerStatisticsDataInfo> getDeviceStatistics()throws Exception {
		List<CustomerStatisticsDataInfo> customerStatisticsDataInfoList = new ArrayList<CustomerStatisticsDataInfo>(); 
		Map<String,CustomerStatisticsDataInfo> CustomerStatisticsDataInfoMap = new TreeMap<String,CustomerStatisticsDataInfo>();
		
		CustomerStatisticsPageShowInfo customerStatisticsPageShowInfo = new CustomerStatisticsPageShowInfo();
		
		List<CustomerDeviceStatisticsDto> customerDeviceStatisticsDtoList = ejlUserDao.getNumberModelStatistcsList();
		statisticsDataHandler(customerStatisticsPageShowInfo, customerDeviceStatisticsDtoList, 0);
		
		List<CustomerDeviceStatisticsDto> customerDeviceModelStatisticsDtoList = ejlUserDao.getNumberModelStatusStatistcsList();
		statisticsDataHandler(customerStatisticsPageShowInfo, customerDeviceModelStatisticsDtoList, 1);
		
		List<CustomerStatisticsDataInfo> customerStatisticsDataInfoList = new ArrayList<CustomerStatisticsDataInfo>();
		for(CustomerStatisticsDataInfo temp:CustomerStatisticsDataInfoMap.values()){
			customerStatisticsDataInfoList.add(temp);
		}
		
		
		return customerStatisticsDataInfoList;
	}
	 * @param customerStatisticsPageShowInfo
	 * @param customerDeviceStatisticsDtoList
	 * @param type
	 */
	
	
	public void statisticsDataHandler(CustomerStatisticsPageShowInfo customerStatisticsPageShowInfo,List<CustomerDeviceStatisticsDto> customerDeviceStatisticsDtoList,Integer type){
		Map<String,CustomerStatisticsDataInfo> customerStatisticsDataInfoMap = customerStatisticsPageShowInfo.getCustomerStatisticsDataInfoMap();
		for(CustomerDeviceStatisticsDto temp:customerDeviceStatisticsDtoList){
			if(!temp.getModel().equals("SG-A017")){
				continue;
			}
			CustomerStatisticsDataInfo customerStatisticsDataInfo = customerStatisticsDataInfoMap.get(temp.getCustomerNumber()+"_"+temp.getModel());
			if(customerStatisticsDataInfo == null){
				customerStatisticsDataInfo = new CustomerStatisticsDataInfo();
				customerStatisticsDataInfo.setCustomerNumber(temp.getCustomerNumber());
				customerStatisticsDataInfo.setModel(temp.getModel());
				customerStatisticsDataInfo.setCustomerName(temp.getCustomerName());
				setCustomerProducerData(customerStatisticsDataInfo);
				customerStatisticsDataInfoMap.put(temp.getCustomerNumber()+"_"+temp.getModel(), customerStatisticsDataInfo);
			}
            if(type == 0){
            	customerStatisticsDataInfo.setStorageDeviceData(temp.getStatisticsData());
            	customerStatisticsPageShowInfo.addStorageDeviceDataTotal(temp.getStatisticsData());
            }else if(type == 1){
            	if(temp.getStatus()==1){
            		customerStatisticsDataInfo.setBindDeviceData(temp.getStatisticsData());
            		customerStatisticsPageShowInfo.addBindDeviceDataTotal(temp.getStatisticsData());
            	}else{
            		customerStatisticsDataInfo.setUnbindDeviceData(temp.getStatisticsData());
            		customerStatisticsPageShowInfo.addUnbindDeviceDataTotal(temp.getStatisticsData());
            	}
            }
		}
	}
	
	public void setCustomerProducerData(CustomerStatisticsDataInfo customerStatisticsDataInfo) {
		EfCustomer customer = new EfCustomer();
		try{
			customer.setNumber(customerStatisticsDataInfo.getCustomerNumber());
			customer = efComCustomerServiceImpl.selectOneObjByAttribute(new Context(), customer);
			if(customer != null){
				customerStatisticsDataInfo.setProduceDeviceData(Integer.valueOf(customer.getRemark().trim()));
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("计算客户生产设备数量时出现异常： customerNumber = "+customerStatisticsDataInfo.getCustomerNumber()+" ; remark = "+customer.getRemark()+" ; ");
		}
	}
	
	
	@Override
	public UserMonitorStruc getStatistics() throws BizException {
		List<UserMonitor> userMonitorList = null;
		try {
			userMonitorList = ejlUserDao.getStatistics();
		} catch (Exception e) {
			log.error("dao error.");
			throw new BizException(StatusCode.DAO_ERROR.getValue(), e);
		}

		String[] serverIps = ips.split(",");
		List<String> ipsList = Arrays.asList(serverIps);
		Set<String> ipSet = new HashSet<String>(ipsList);

		List<UserMonitorStrucDetail> udlist = new ArrayList<UserMonitorStrucDetail>();

		for (String ip : ipSet) {
			UserMonitorStrucDetail userMonitorStrucd = new UserMonitorStrucDetail();
			StatisticUserConnect suc = statisticComUserConnectDao.getNewestIpRecord(ip);
			if (suc != null) {
				userMonitorStrucd.setTotalOnlineAppCount(suc.getAppcount());
				userMonitorStrucd.setTotalOnlineWatchCount(suc.getDevicecount());
				userMonitorStrucd.setTotalOnlineCount(suc.getAppcount() + suc.getDevicecount());
				userMonitorStrucd.setIp(ip);
				userMonitorStrucd.setTime(DateUtils.format(suc.getTime(), DateUtils.DATETIME_FORMAT_PATTERN));
				udlist.add(userMonitorStrucd);
			}
		}

		UserMonitorStruc userMonitorStruc = new UserMonitorStruc();
//		StatisticUserConnect suc = statisticComUserConnectDao.getNewestRecord();
//		userMonitorStruc.setTotalOnlineAppCount(suc.getAppcount());
//		userMonitorStruc.setTotalOnlineWatchCount(suc.getDevicecount());
//		userMonitorStruc.setTotalOnlineCount(suc.getAppcount() + suc.getDevicecount());
		userMonitorStruc.setUserMonitorStrucDetail(udlist);
		if (null != userMonitorList) {
			for (UserMonitor userMonitor : userMonitorList) {
				userMonitorStruc.setTotalCount(userMonitorStruc.getTotalCount() + userMonitor.getCount());
				if (userMonitor.getType().intValue() == EjlUser.Type.WATCH.getCode()) {
					userMonitorStruc.setTotalWatchCount(userMonitorStruc.getTotalWatchCount() + userMonitor.getCount());
					// if(userMonitor.getStatus().intValue()==YesNo.NO.getValue()){
					// userMonitorStruc.setTotalOnlineCount(userMonitorStruc.getTotalOnlineCount()+userMonitor.getCount());
					// userMonitorStruc.setTotalOnlineWatchCount(userMonitorStruc.getTotalOnlineWatchCount()+userMonitor.getCount());
					// }
				} else {
					userMonitorStruc.setTotalAppCount(userMonitorStruc.getTotalAppCount() + userMonitor.getCount());
					// if(userMonitor.getStatus().intValue()==YesNo.NO.getValue()){
					// userMonitorStruc.setTotalOnlineCount(userMonitorStruc.getTotalOnlineCount()+userMonitor.getCount());
					// userMonitorStruc.setTotalOnlineAppCount(userMonitorStruc.getTotalOnlineAppCount()+userMonitor.getCount());
				}
				// }
			}
		}
		return userMonitorStruc;
	}

	@Override
	public List<UserStruc> getUserList() throws BizException {
		try {
			return ejlUserDao.getUserList();
		} catch (Exception e) {
			log.error("dao error.");
			throw new BizException(StatusCode.DAO_ERROR.getValue(), e);
		}
	}
}
