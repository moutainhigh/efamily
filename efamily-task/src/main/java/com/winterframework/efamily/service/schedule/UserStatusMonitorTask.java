package com.winterframework.efamily.service.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IStatisticUserConnectDao;
import com.winterframework.efamily.entity.StatisticUserConnect;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
public class UserStatusMonitorTask {

	private Logger log = LoggerFactory.getLogger(NoticeDisposableTask.class);
//	@PropertyConfig(value = "server.url")
//	private String serverUrl;
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name = "ejlComUserServiceImpl")
	IEjlComUserService ejlComUserServiceImpl;
	
	@Resource(name = "statisticUserConnectDaoImpl")
	IStatisticUserConnectDao statisticUserConnectDaoImpl;
	
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	@PropertyConfig("server.getUserStatus.url")
	private String getUserStatusUrl;
	
	@PropertyConfig("netty.ip.list")
	private String ips; 
	
	public void execute() throws Exception {
		try{
			
			String[] serverIps=ips.split(",");
			List<String> ipsList= Arrays.asList(serverIps);
			Set<String> ipSet=new HashSet<String>(ipsList);//容错处理，前期服务器只有一台
			
			for(String ip:ipSet){
				StatisticUserConnect suc=new StatisticUserConnect();
				Map<String,String> map =  new HashMap<String,String>();
				map.put("id", "111");
				Response<Map<String, String>> res=null;
				String serverUrl="http://"+ip+":6060/";
				try {
					res = httpUtil.http(serverUrl, getUserStatusUrl, map, Map.class);
				} catch (Exception e) {//连不上时默认服务器上连接为0,可以不记录此条数据，直接continue
//					suc.setAppcount(0);
//					suc.setDevicecount(0);
//					suc.setTime(new Date());
//					suc.setIp(ip);
//					suc.setRemark("error data");
//					statisticUserConnectDaoImpl.insert(suc);
					continue;
				}
			
				suc.setAppcount(0);
				suc.setDevicecount(0);
				suc.setTime(new Date());
				suc.setIp(ip);
				suc.setRemark("success");
				if(res.getStatus().getCode()==StatusCode.OK.getValue()){
					if(res.getData().get("channelMapUsers")!=null&&res.getData().get("channelMapUsers").length()>1){
						String userIds = res.getData().get("channelMapUsers");
						String userIdArray[] = userIds.split(",");
						List<String> idList=Arrays.asList(userIdArray);
						Long appnumber=ejlUserDaoImpl.getAppConnectNumber(idList);
						Long deviceNumber=ejlUserDaoImpl.getDeviceConnectNumber(idList);
						suc.setAppcount(appnumber.intValue());
						suc.setDevicecount(deviceNumber.intValue());
					}
					
				}
				statisticUserConnectDaoImpl.insert(suc);
			}
			
		}catch(Exception e){
			log.error("更新用户状态错误", e);
		}
	}


	
	
}
