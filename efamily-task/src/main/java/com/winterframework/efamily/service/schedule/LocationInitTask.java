package com.winterframework.efamily.service.schedule;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamConnect;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;
import com.winterframework.efamily.entity.CityJsonObject;
import com.winterframework.efamily.entity.DeviceMobile;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EfLocationGps;
import com.winterframework.efamily.entity.EfLocationWifi;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.GaoDeLocationOperator;
import com.winterframework.efamily.entity.GaoDeLocationStruc;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.service.IDeviceMobileService;
import com.winterframework.efamily.service.IDeviceSignalRecordService;
import com.winterframework.efamily.service.IEfComLocationGpsService;
import com.winterframework.efamily.service.IEfComUserWeatherService;
import com.winterframework.efamily.service.IEfComWeatherService;
import com.winterframework.efamily.service.IEfLocationWifiService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.IEjlComUserExtendInfoService;
import com.winterframework.efamily.service.IWeatherService;
import com.winterframework.efamily.utils.BaiDuGetCityNameUtil;
import com.winterframework.efamily.utils.GaoDeLocationGetUtil;
import com.winterframework.efamily.utils.GpsLocationTransformGede;
import com.winterframework.efamily.utils.LocationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

public class LocationInitTask {

	private Logger log = LoggerFactory.getLogger(LocationInitTask.class);
	
	@Resource(name = "deviceMobileServiceImpl")
	private IDeviceMobileService deviceMobileServiceImpl;
	
	@Resource(name = "efLocationWifiServiceImpl")
	private IEfLocationWifiService efLocationWifiServiceImpl;
	
	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao ejlComDeviceDaoImpl;
	
	@Resource(name = "deviceSignalRecordServiceImpl")
	private IDeviceSignalRecordService deviceSignalRecordServiceImpl;

	@Resource(name = "ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationServiceImpl;
	
	
	@Resource(name = "efComLocationGpsServiceImpl")
	private IEfComLocationGpsService efComLocationGpsServiceImpl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@PropertyConfig(value = "gaode.location.key")
	private String key;
	
	
	@Resource(name="ejlComLocationDaoImpl")
	private IEjlComLocationDao ejlComLocationDaoImpl;
	
	@Resource(name ="ejlComUserExtendInfoServiceImpl")
	private IEjlComUserExtendInfoService ejlComUserExtendInfoDaoImpl;
	
	@Resource(name = "ejlComWeatherServiceImpl")
	private IEfComWeatherService ejlComWeatherServiceImpl;
	
	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherServiceImpl;
	
	@Resource(name = "efComUserWeatherServiceImpl")
	private IEfComUserWeatherService efComUserWeatherServiceImpl;
	
	
	
	@SuppressWarnings("unchecked")
	public void execute() throws Exception {
		log.info("过滤 GPS WIFI 基站 数据，获取轨迹数据：-------------------------------------------------------START ---------------");
		List<DeviceMobile> deviceMobileList = deviceMobileServiceImpl.getByOpTag(0L,null,null,null,null);
		List<EfLocationWifi>  efLocationWifiList =efLocationWifiServiceImpl.getByOpTag(0L,null,null,null,null);
		EfLocationGps efLocationGpsQuery = new EfLocationGps();
		efLocationGpsQuery.setOpTag(0);
		List<EfLocationGps> efLocationGpsList = efComLocationGpsServiceImpl.selectListObjByAttribute(null,efLocationGpsQuery);
		String baseUrl = "http://apilocate.amap.com/position?key="+key+"&";
		
		//********** 传递过来的定位数据中 已经有 userId deviceId ,如果设备被更换用户了，之前的定位数据还是之前用户的轨迹。
		Set<Long> deviceIdSet = new HashSet<Long>();
		Map<Long,Long> deviceUserMap = new HashMap<Long,Long>();
		for(DeviceMobile deviceMobile:deviceMobileList){
			deviceIdSet.add(deviceMobile.getDeviceId());
		}
		for(EfLocationWifi efLocationWifi:efLocationWifiList ){
			deviceIdSet.add(efLocationWifi.getDeviceId());
			deviceUserMap.put(efLocationWifi.getDeviceId(), efLocationWifi.getUserId());
		}
		for(EfLocationGps efLocationGps:efLocationGpsList){
			deviceIdSet.add(efLocationGps.getDeviceId());
			if(deviceUserMap.get(efLocationGps.getDeviceId())==null){
				deviceUserMap.put(efLocationGps.getDeviceId(),efLocationGps.getUserId());
			}
		}
		Map<Long,EjlDevice> deviceQrcodeMap = new HashMap<Long,EjlDevice>();
		//if(!deviceMobileList.isEmpty()||!efLocationWifiList.isEmpty()){
			for(Long deviceId:deviceIdSet){
				EjlUserDevice eEjlUserDevice = new EjlUserDevice();
				eEjlUserDevice.setStatus(1l);
				eEjlUserDevice.setDeviceId(deviceId);
				List<EjlUserDevice> list = ejlComUserDeviceDaoImpl.getAllByEntity(eEjlUserDevice);
				if(!list.isEmpty()){
					if(deviceUserMap.get(deviceId)==null){
					deviceUserMap.put(deviceId,list.get(0).getUserId());
					}
				}
				EjlDevice ejlDevice = ejlComDeviceDaoImpl.getById(deviceId);
				deviceQrcodeMap.put(deviceId, ejlDevice);
				
			}
		//}
		//*********** 基站转换为 高德 的数据 
		List<EjlLocation> cellSourceList = new ArrayList<EjlLocation>();
		List<EjlLocation> cellList = getCellGbrsLocation(deviceMobileList,baseUrl,deviceUserMap,deviceQrcodeMap);
		cellSourceList.addAll(cellList);
		//*********** wifi数据转换为高德数据
		cellList.addAll(getWiftLocation(efLocationWifiList,baseUrl,deviceUserMap,deviceQrcodeMap));
		locationListSort(cellList);
		log.info("==============================this locaiton source size:"+cellList.size());
		//基站 wifi 可以获取具体地址，可以根据具体地址去重
		cellList.removeAll(this.removeRepeatPoint(cellList));
		
		//根据基站数据去除移动wift的数据（wift跟基站距离相差10公里的wift为移动的）
		this.removeDynamicWiftByCell(cellList, cellSourceList);
		
		//** GPS无法获取高德具体地址 只可以自己去除GPS的重复数据
		List<EjlLocation> gpsList = this.getGpsLocation(efLocationGpsList);
		gpsList.removeAll(this.removeRepeatPoint(gpsList));
		cellList.addAll(gpsList);
		log.info("this locaiton removeRepeat size:"+cellList.size());
		locationListSort(cellList);
		//删除时间上重复
		cellList = this.cellAndWiftLocationRepeatHander(cellList);
		log.info("this locaiton removeTime size:"+cellList.size());
		locationListSort(cellList);
		
		
		//*** 上面的步骤都是时间和地址的去重   去除异常点 
		Long a1 = System.currentTimeMillis();
		cellList = distanceExceptionLocationHander(cellList);
		Long a2 = System.currentTimeMillis();
		System.out.println("[jason_location]: 去除异常点耗时 time = "+(a2-a1)/1000);
		
		log.info("==================================this locaiton distanceException size:"+cellList.size());
		
		//*** 聚合 ： 例如 120米以内 聚合为一个点
		Long b1 = System.currentTimeMillis();
		cellList = this.juheEjlLocation(cellList);
		Long b2 = System.currentTimeMillis();
		System.out.println("[jason_location]: 聚合120米的点耗时 time = "+(b2-b1)/1000);
		
		log.info("==================================this locaiton juhe size:"+cellList.size());
		Long c1 = System.currentTimeMillis();
		locationListSort(cellList);
		Long c2 = System.currentTimeMillis();
		System.out.println("[jason_location]: 1排序耗时耗时 time = "+(c2-c1)/1000);
		Context ctx=new Context();
		ctx.set("userId", -1L);
		log.info("==================================this locaiton parse size:"+cellList.size());
		Map<String,List<EjlLocation>> locationMap = new HashMap<String,List<EjlLocation>>();
		EjlLocation ejlLocationLast = null;
		for(EjlLocation cell:cellList){
			Long d1 = System.currentTimeMillis();
			if(cell.getUserId()==null)
			{
				cell.setUserId(0L);
			}
			if(ejlLocationLast == null){
				Date time=DateUtils.addHours(DateUtils.currentDate(), -1);
				ejlLocationLast = ejlComLocationDaoImpl.getUserLatestLocation(cell.getUserId(), cell.getDeviceId(),YesNo.YES.getValue(),time);
			}else{
				ejlLocationLast = cell;
			}
			List<EjlLocation> locationList =  locationMap.get(cell.getUserId()+"@"+cell.getDeviceId());
			if(locationList == null){
				Date selectDate = DateUtils.addMinutes(cell.getTime(), -(24*60));
				locationList =  ejlComLocationDaoImpl.getUserLocationDesc(cell.getUserId(), cell.getDeviceId(), selectDate);
				if(locationList != null){
					locationMap.put(cell.getUserId()+"@"+cell.getDeviceId(), locationList);
				}else{
					locationList = new ArrayList<EjlLocation>();
				}
			}
			
			
			//****  【去除基站的坐标】条件：前面或者后面10分钟 存在wifi或者gps的坐标
			boolean flag = true;
			Iterator<EjlLocation> iter = locationList.iterator();
			while(iter.hasNext()){
				EjlLocation locationTemp = iter.next();
				if(locationTemp.getDeviceId().longValue()==cell.getDeviceId().longValue() && DateUtils.calcMinutesBetween(locationTemp.getTime(), cell.getTime()) < 5 ){
					 //source : 1:GPS 2:基站 3:WIFI 4:其它  calcMinutesBetween
					 if(cell.getSource()==2 && locationTemp.getSource()!=2){
						 flag = false;
						 break;
					 }/*else if(cell.getSource()!=2 && locationTemp.getSource()==2){
						 ejlComLocationDaoImpl.delete(locationTemp.getId());
						 iter.remove();
					 }*/
				}else{
					 break;
				}
			}

            if(!flag){
            	continue;
            }
            
            //****  【5个点比较，去除异常点】条件：取出5个点，4段距离，如果   第2段和第3段    是   第1段和第4段   的3倍以上，则认为异常点。
			Date selectDate = DateUtils.addMinutes(cell.getTime(), -(24*60));
			List<EjlLocation> ejlLocationLatelyCountList = ejlComLocationDaoImpl.getUserLocationLatelyCount(cell.getUserId(), cell.getDeviceId(), selectDate, 1L);
            if(ejlLocationLatelyCountList!=null && ejlLocationLatelyCountList.size()>0){
            	EjlLocation ejlLocationLastTemp = ejlLocationLatelyCountList.get(0);
            	Double distance = this.getDistance(
						Double.valueOf((ejlLocationLastTemp.getLocation().split(",")[1])),Double.valueOf((ejlLocationLastTemp.getLocation().split(",")[0])),
						Double.valueOf((cell.getLocation().split(",")[1])),Double.valueOf((cell.getLocation().split(",")[0]))
						);
            	cell.setRemark("与前一点:id="+ejlLocationLastTemp.getId()+";distance="+distance+";");
            	/*if(ejlLocationLatelyCountList.size()==4){
            		ejlLocationLatelyCountList.add(0, cell);
                	ejlLocationLatelyCountList = removeDistance(ejlLocationLatelyCountList);
                	if(ejlLocationLatelyCountList.size()>0){
                		ejlComLocationDaoImpl.delete(ejlLocationLatelyCountList.get(0).getId());
                	}
            	}*/
            }
            
			Double juheDistance = 80D;//100米
			for(EjlLocation locationTemp:locationList){
				//*** 当前入库的点 与 之前24小时内的点 做比较 ，如果在60米以内，则以之前的点为标准（也就是：120米以内 只能出现一个点）。
				Double distance = this.getDistance(
						Double.valueOf((locationTemp.getLocation().split(",")[1])),Double.valueOf((locationTemp.getLocation().split(",")[0])),
						Double.valueOf((cell.getLocation().split(",")[1])),Double.valueOf((cell.getLocation().split(",")[0]))
						);
				if(distance.doubleValue()<=juheDistance.doubleValue()&&locationTemp.getDeviceId().longValue()==cell.getDeviceId().longValue())
				{
					//*** 替换当前点的坐标 ***
					cell.setLocation(locationTemp.getLocation());
					
					/*EjlLocation lastLocation =  ejlComLocationServiceImpl.getUserLatestLocation(cell.getUserId(), cell.getDeviceId());
					if(lastLocation!=null){

	                  if(getDistance(Double.valueOf((cell.getLocation().split(",")[1])),Double.valueOf((cell.getLocation().split(",")[0])),
						Double.valueOf((lastLocation.getLocation().split(",")[1])),Double.valueOf((lastLocation.getLocation().split(",")[0]))) <60)
							try {
								log.info("this first is remove 00000000000000：lastLocation = "+lastLocation.toString());
								//ejlComLocationServiceImpl.remove(null, lastLocation.getId());
							} catch (BizException e) {
								log.error("delete first point because of 60 inside");
							}
					 }*/
					break;
				}
			}
			
			locationList.add(0,cell);
			ejlComLocationServiceImpl.save(ctx,cell);
			try{
				weatherServiceImpl.userWeatherCityHander(cell);
			}catch(Exception e){
				log.error("[loation_change_weather]: location = "+cell.toString(),e.getMessage());
			}
			try{
			//去掉突然跳开的点 比如3个点  中间那个点突然跳开，前后点相同。时间在10分钟之内
			for(int i=0;i<30;i++){
				if(i>0){
					if(locationList.size()>i-1){
					EjlLocation first = locationList.get(i-1);
					if(locationList.size()>i+1){
						EjlLocation mid = locationList.get(i);
						EjlLocation last = locationList.get(i+1);
						if(!mid.getLocation().equals(last.getLocation())&& first.getLocation().equals(last.getLocation())&&DateUtils.calcMinutesBetween(first.getTime(), last.getTime())<5){
							if(mid.getId()!= null){
								mid.setLocation(last.getLocation());
								ejlComLocationServiceImpl.save(null, mid);
							}
						}
					}}
				}
			}}catch(Exception e){
				log.error("[loation_change_weather]: location = "+cell.toString(),e.getMessage());
			}
			Long d2 = System.currentTimeMillis();
			System.out.println("[jason_location]: 一个点处理耗时 time = "+(d2-d1)/1000);
		}
		
	}
	
	//获取30分钟之内与wift最近的基站点，用于过滤移动wift
	public EjlLocation getNearCellForDate(EjlLocation ejlWiftLocation,List<EjlLocation> cellSourceList){
		Long minTime = Long.MAX_VALUE;
		EjlLocation cellEjlLocation = null;
		for(EjlLocation ejlLocation:cellSourceList){
			Date cellDate = ejlLocation.getTime();
			if(ejlWiftLocation.getDeviceId().longValue()==ejlLocation.getDeviceId().longValue() && Math.abs(DateUtils.calcMinutesBetween(ejlWiftLocation.getTime(), cellDate))<minTime){
				minTime = Math.abs(DateUtils.calcMinutesBetween(ejlWiftLocation.getTime(), cellDate));
				cellEjlLocation = ejlLocation;
			}
		}
		if(minTime<30){
			return cellEjlLocation;
		}else{
			log.info("wiftDate:"+DateUtils.format(ejlWiftLocation.getTime(),DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN)+"30分钟内没有基站数据");
			return null;
		}
	}
	
	public void removeDynamicWiftByCell(List<EjlLocation> cellList,List<EjlLocation> cellSourceList){
		List<EjlLocation> removeList = new ArrayList<EjlLocation>();
		for(int i=0;i<cellList.size();i++){
			EjlLocation ejlLocation = cellList.get(i);
			//获取wift数据
			if(ejlLocation.getSource()==3){
				Date wiftDate = ejlLocation.getTime();
				//获取wift时间最近的基站数据
				EjlLocation cellEjlLocation = getNearCellForDate(ejlLocation,cellSourceList);
				if(cellEjlLocation!=null){
					double distance = this.getDistance(Double.valueOf(ejlLocation.getLocation().split(",")[1]), Double.valueOf(ejlLocation.getLocation().split(",")[0]), Double.valueOf(cellEjlLocation.getLocation().split(",")[1]), Double.valueOf(cellEjlLocation.getLocation().split(",")[0]));
					if(getDistance(Double.valueOf((ejlLocation.getLocation().split(",")[1])),Double.valueOf((ejlLocation.getLocation().split(",")[0])),
							Double.valueOf((cellEjlLocation.getLocation().split(",")[1])),Double.valueOf((cellEjlLocation.getLocation().split(",")[0]))) >15000){
						removeList.add(ejlLocation);
					}
					
				}
			}
		}
		
		cellList.removeAll(removeList);
	}
	
	public void locationListSort(List<EjlLocation> cellList){
		Collections.sort(cellList,new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				EjlLocation e1 = (EjlLocation)o1;
				EjlLocation e2 = (EjlLocation)o2;
				if(e1.getDeviceId().longValue()>e2.getDeviceId().longValue()){
					return 1;
				}else if(e1.getDeviceId().longValue()<e2.getDeviceId().longValue()){
					return -1;
				}else{
					return e1.getTime().compareTo(e2.getTime());
				}
			}
		});
	}
	
	public void  getJuheList(List<EjlLocation> ejlLocationList,List<List<EjlLocation>> resultList,int index){
		if(index>ejlLocationList.size()-1){
			return;
		}else{
			Double juheDistance = 80d;
			EjlLocation begin = ejlLocationList.get(index);
			List<EjlLocation> juheList = new ArrayList<EjlLocation>();
			juheList.add(begin);
			resultList.add(juheList);
			if(index==ejlLocationList.size()-1){
				return;
			}else{
				for(int i=index+1;i<ejlLocationList.size();i++){
					EjlLocation ejlLocation = ejlLocationList.get(i);
					Double distance = this.getDistance(Double.valueOf((begin.getLocation().split(",")[1])),Double.valueOf((begin.getLocation().split(",")[0])),
							Double.valueOf((ejlLocationList.get(i).getLocation().split(",")[1])),Double.valueOf((ejlLocationList.get(i).getLocation().split(",")[0])));
					if(distance.doubleValue()<=juheDistance.doubleValue()&&begin.getDeviceId().longValue()==ejlLocation.getDeviceId().longValue())
					{
						juheList.add(ejlLocationList.get(i));
					}
					else
					{
						getJuheList(ejlLocationList,resultList,i);
						break;
					}
				}
			}
		}
	}
	
	public List<EjlLocation> juheEjlLocation(List<EjlLocation> ejlLocationList){		
		List<EjlLocation> resultList = new ArrayList<EjlLocation>();
		List<List<EjlLocation>> juheList = new ArrayList<List<EjlLocation>>();
		this.getJuheList(ejlLocationList, juheList, 0);
		if(juheList.size()>0){
			for(List<EjlLocation> unitList:juheList){
				resultList.add(unitList.get(unitList.size()/2));
			}
		}
		return resultList;
	}
	
	
	public List<EjlLocation> removeRepeatPoint(List<EjlLocation> ejlLocationList){
		
		List<EjlLocation> removeList = new ArrayList<EjlLocation>();
		try{
			for(int i=0;i<ejlLocationList.size()-1;i++){
				EjlLocation first = ejlLocationList.get(i);
				EjlLocation last = ejlLocationList.get(i+1);
				if(first.getDes()!=null&&last.getDes()!=null&&first.getDes().equals(last.getDes())&&first.getDeviceId().longValue()==last.getDeviceId().longValue()){
					removeList.add(last);
				}
				
			}
		}catch(Exception e){
			log.error("get location desc error",e);
		}
		return removeList;
	}
	
	/**
	 * 功能：5个点，比较出一个异常点。@--@--@--@--@
	 * @param ejlLocationList
	 * @return
	 */
	private List<EjlLocation> removeDistance(List<EjlLocation> ejlLocationList){
		List<EjlLocation> removeList = new ArrayList<EjlLocation>();
		try{
			if(ejlLocationList.size()>=5){
				List<Double> distancePoints = new ArrayList<Double>();
				for(int i=0;i<ejlLocationList.size()-1;i++){
					EjlLocation first = ejlLocationList.get(i);
					EjlLocation last = ejlLocationList.get(i+1);
					Double distance = getDistance(Double.valueOf((first.getLocation().split(",")[1])),Double.valueOf((first.getLocation().split(",")[0])),
							Double.valueOf((last.getLocation().split(",")[1])),Double.valueOf((last.getLocation().split(",")[0])));
					distancePoints.add(distance);
				}
				Double maxDistance=0d;
				int maxPoint=0;
				int i=0;
				for(Double distancePoint:distancePoints){
					if(distancePoint.doubleValue()>maxDistance.doubleValue()){
						maxDistance = distancePoint;
						maxPoint = i;
					}
					i++;
				}
				Double secondMaxDistance=0d;
				int secondMaxPoint=0;
				i=0;
				for(Double distancePoint:distancePoints){
					if(distancePoint.doubleValue()>secondMaxDistance.doubleValue()&&i!=maxPoint){
						secondMaxDistance = distancePoint;
						secondMaxPoint = i;
					}
					i++;
				} 
				int count=0;
				if(Math.abs(secondMaxPoint-maxPoint)==1){
					for(int j=0;j<distancePoints.size();j++){
						if(j!=maxPoint&&j!=secondMaxPoint){
							if(distancePoints.get(j).doubleValue()!=0.0&&secondMaxDistance>distancePoints.get(j).doubleValue()*3){
								count++;
							}
						}
					}
				}
				if(count >=2){
					log.info("this locaiton remove exception point "+ejlLocationList.get(maxPoint>secondMaxPoint?maxPoint:secondMaxPoint).getTime());
					removeList.add(ejlLocationList.get(maxPoint>secondMaxPoint?maxPoint:secondMaxPoint));
				}
				
			}
		}catch(Exception e){
			log.error("removeDistance error", e);
		}
		return removeList;
	}
	
	private void getTwoLocationException(EjlLocation firstLocation,EjlLocation lastLocation,List<EjlLocation> removeList){
		if(firstLocation.getDeviceId().longValue() == lastLocation.getDeviceId().longValue()){
			log.info("this locaiton removeDistance:"+getDistance(Double.valueOf((lastLocation.getLocation().split(",")[1])),Double.valueOf((lastLocation.getLocation().split(",")[0])),
					Double.valueOf((firstLocation.getLocation().split(",")[1])),Double.valueOf((firstLocation.getLocation().split(",")[0]))));
		if(DateUtils.addMinutes(firstLocation.getTime(), 1).compareTo(lastLocation.getTime())>0){
			if(getDistance(Double.valueOf((lastLocation.getLocation().split(",")[1])),Double.valueOf((lastLocation.getLocation().split(",")[0])),
					Double.valueOf((firstLocation.getLocation().split(",")[1])),Double.valueOf((firstLocation.getLocation().split(",")[0]))) >3500){
				removeList.add(lastLocation);
			}
		}
		else if(DateUtils.addMinutes(firstLocation.getTime(), 2).compareTo(lastLocation.getTime())>0){
			if(getDistance(Double.valueOf((lastLocation.getLocation().split(",")[1])),Double.valueOf((lastLocation.getLocation().split(",")[0])),
					Double.valueOf((firstLocation.getLocation().split(",")[1])),Double.valueOf((firstLocation.getLocation().split(",")[0]))) >6200){
				removeList.add(lastLocation);
			}
		}
		else if(DateUtils.addMinutes(firstLocation.getTime(), 5).compareTo(lastLocation.getTime())>0){
			if(getDistance(Double.valueOf((lastLocation.getLocation().split(",")[1])),Double.valueOf((lastLocation.getLocation().split(",")[0])),
					Double.valueOf((firstLocation.getLocation().split(",")[1])),Double.valueOf((firstLocation.getLocation().split(",")[0]))) >18000){
				removeList.add(lastLocation);
			}
		}
		else if(DateUtils.addMinutes(firstLocation.getTime(), 30).compareTo(lastLocation.getTime())>0){
			if(getDistance(Double.valueOf((lastLocation.getLocation().split(",")[1])),Double.valueOf((lastLocation.getLocation().split(",")[0])),
					Double.valueOf((firstLocation.getLocation().split(",")[1])),Double.valueOf((firstLocation.getLocation().split(",")[0]))) >80000){
				removeList.add(lastLocation);
			}
		}}
	}
	
	private List<EjlLocation> distanceExceptionLocationHander(List<EjlLocation> ejlLocationList){
		List<EjlLocation> removeList = new ArrayList<EjlLocation>();
		List<EjlLocation> referList = new ArrayList<EjlLocation>();
		List<EjlLocation> currentList = new ArrayList<EjlLocation>();
		EjlLocation first = null;
		for(EjlLocation ejlLocation:ejlLocationList){
			if(first == null){
				first = ejlLocation;
			}
			if(first.getDeviceId().longValue() != ejlLocation.getDeviceId().longValue()){
				referList.clear();
				first = ejlLocation;
			}
			currentList.clear();
			if(first.equals(ejlLocation)){
				
				//********* 如果数据库中的最后一个点  跟当前点 比较 如果小于 60米 ，则删除当前点，最后一个点时间变为当前点。
				Date time=DateUtils.addHours(DateUtils.currentDate(), -1);
				first =  ejlComLocationServiceImpl.getUserLatestLocation(first.getUserId(), first.getDeviceId(),YesNo.YES.getValue(),time);
				if(first!=null){
					//log.info("this first is ok");
					if(getDistance(Double.valueOf((ejlLocation.getLocation().split(",")[1])),Double.valueOf((ejlLocation.getLocation().split(",")[0])),
							Double.valueOf((first.getLocation().split(",")[1])),Double.valueOf((first.getLocation().split(",")[0]))) <60){
						try {
							//log.info("this first is remove");
							first.setTime(ejlLocation.getTime());
							ejlComLocationServiceImpl.save(null, first);
							currentList.add(ejlLocation);
						} catch (BizException e) {
							//log.error("delete first point because of 60 inside");
						}
					}else if(ejlLocation.getSource().intValue()==2){
						Date selectDate = DateUtils.addMinutes(ejlLocation.getTime(), -10);
						List<EjlLocation> locationList = ejlComLocationDaoImpl.getUserLocation(ejlLocation.getUserId(), ejlLocation.getDeviceId(), selectDate);
						//log.info("this first is source 2 and size:"+locationList.size());
						for(EjlLocation l:locationList){
							if(l.getSource().intValue()!=2){
								currentList.add(l);
								//log.info("this first is source 2 remove");
								break;
							}
						}
					}
				}
				else
				{
					first = ejlLocation;
				}
				
			}
			//****** 前后两个点比对 去除异常点  
			this.getTwoLocationException(first, ejlLocation, currentList);
			if(currentList.size()>=1){
				removeList.add(ejlLocation);
				//log.info("this locaiton removeDistance size:"+ejlLocation.getTime());
				continue;
			}
			//*********** 
			if(referList.size()<5){
				if(!referList.contains(ejlLocation)){
				referList.add(ejlLocation);}
			}else{
				referList.remove(0);
				referList.add(ejlLocation);
				removeList.addAll(removeDistance(referList));
				//referList.removeAll(removeList);
			}
			if(!removeList.contains(ejlLocation)){
				first = ejlLocation;
			}
		}
		
		ejlLocationList.removeAll(removeList);
		return ejlLocationList;
	}
	
	@Deprecated
	/// <summary>
    /// 给定的经度1，纬度1；经度2，纬度2. 计算2个经纬度之间的距离。
    /// </summary>
    /// <param name="lat1">经度1</param>
    /// <param name="lon1">纬度1</param>
    /// <param name="lat2">经度2</param>
    /// <param name="lon2">纬度2</param>
    /// <returns>距离（米）</returns>
	public static double Distance(double lat1,double lon1, double lat2,double lon2)
    {
        //用haversine公式计算球面两点间的距离。
        //经纬度转换成弧度
        lat1 = ConvertDegreesToRadians(lat1);
        lon1 = ConvertDegreesToRadians(lon1);
        lat2 = ConvertDegreesToRadians(lat2);
        lon2 = ConvertDegreesToRadians(lon2);

        //差值
        double  vLon = Math.abs(lon1 - lon2);
        double  vLat = Math.abs(lat1 - lat2);

        //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double  h = HaverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * HaverSin(vLon);

        double  distance = 2 * 6371.0 * Math.asin(Math.sqrt(h));

        return distance*1000; 	
    }
	
	
	
	public static double HaverSin(double theta)
    {
        double v = Math.sin(theta / 2);
        return v * v;
    }


    /// <summary>
    /// 将角度换算为弧度。
    /// </summary>
    /// <param name="degrees">角度</param>
    /// <returns>弧度</returns>
    public static double ConvertDegreesToRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }

    public static double ConvertRadiansToDegrees(double radian)
    {
        return radian * 180.0 / Math.PI;
    }
	
	//gps wift和基站重复数据过滤 gps>wift>cell
	private List cellAndWiftLocationRepeatHander(List<EjlLocation> locationList){
		List<EjlLocation> deviceMobileListRemove = new ArrayList<EjlLocation>();
		List<EjlLocation> efLocationWifiListRemove = new ArrayList<EjlLocation>();
		List<EjlLocation> efLocationWifiList = new ArrayList<EjlLocation>();
		List<EjlLocation> deviceMobileList = new ArrayList<EjlLocation>();
		List<EjlLocation> efLocationGpsList = new ArrayList<EjlLocation>();
		for(EjlLocation ejlLocation:locationList){
			if(ejlLocation.getSource()==1){
				efLocationGpsList.add(ejlLocation);
			}
			else if(ejlLocation.getSource()==3){
				efLocationWifiList.add(ejlLocation);
			}else if(ejlLocation.getSource() ==2){
				deviceMobileList.add(ejlLocation);
			}
		}
		for(EjlLocation efLocationGps:efLocationGpsList){
			for(EjlLocation efLocationWifi:efLocationWifiList ){
				Date date1Begin = DateUtils.addMinutes(efLocationGps.getTime(), -1);
				Date date1End = DateUtils.addMinutes(efLocationGps.getTime(), 1);
				Date date2 = efLocationWifi.getTime();
				if(date2.compareTo(date1Begin)>0&&date2.compareTo(date1End)<0&&efLocationGps.getDeviceId().longValue()==efLocationWifi.getDeviceId().longValue()){
					efLocationWifiListRemove.add(efLocationWifi);
				}
			}
		}
		
		for(EjlLocation efLocationGps:efLocationGpsList){
			for(EjlLocation deviceMobile:deviceMobileList){
				Date date1Begin = DateUtils.addMinutes(efLocationGps.getTime(), -5);
				Date date1End = DateUtils.addMinutes(efLocationGps.getTime(), 5);
				Date date2 = deviceMobile.getTime();
				if(date2.compareTo(date1Begin)>0&&date2.compareTo(date1End)<0
						&&efLocationGps.getDeviceId().longValue()==deviceMobile.getDeviceId().longValue()){
					deviceMobileListRemove.add(deviceMobile);
				}
			}
		}
		
		
		for(EjlLocation efLocationWifi:efLocationWifiList ){
			for(EjlLocation deviceMobile:deviceMobileList){
				if(efLocationWifi.getDeviceId().equals(deviceMobile.getDeviceId())){
					Date date1Begin = DateUtils.addMinutes(efLocationWifi.getTime(), -5);
					Date date1End = DateUtils.addMinutes(efLocationWifi.getTime(), 5);
					Date date2 = deviceMobile.getTime();
					if(date2.compareTo(date1Begin)>0&&date2.compareTo(date1End)<0
							&&efLocationWifi.getDeviceId().longValue()==deviceMobile.getDeviceId().longValue()){
					deviceMobileListRemove.add(deviceMobile);
					}
				}
				
			}
		}
		locationList.removeAll(deviceMobileListRemove);
		locationList.removeAll(efLocationWifiListRemove);
		return locationList;
	}
	
	
	private List<EjlLocation> getGpsLocation(List<EfLocationGps> efLocationGpsList){
		List<EjlLocation> list = new ArrayList<EjlLocation>();
		for(EfLocationGps efLocationGps:efLocationGpsList){
			try{
				efLocationGps.setOpTag(1);
				Context ctx = new Context();
				ctx.set("userId", 1);
				efComLocationGpsServiceImpl.save(ctx, efLocationGps);
				EjlLocation ejlLocation = new EjlLocation();
				ejlLocation.setDeviceId(efLocationGps.getDeviceId());
				LatLng latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(Double.valueOf(efLocationGps.getLocation().split(",")[0]),Double.valueOf(efLocationGps.getLocation().split(",")[1])));
				ejlLocation.setLocation(latLng.latitude+","+latLng.longitude);
				ejlLocation.setUserId(efLocationGps.getUserId());
				ejlLocation.setTime(efLocationGps.getTime());
				ejlLocation.setSource(1);
				ejlLocation.setType(2);
				try {
					CityJsonObject cityJsonObject =BaiDuGetCityNameUtil.getCityNameByLocation(ejlLocation.getLocation());
					if(cityJsonObject!=null){
						ejlLocation.setDes(cityJsonObject.getFormatted_address());
						ejlLocation.setCity(cityJsonObject.getAddressComponent().getCity());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list.add(ejlLocation);
			} catch (BizException e) {
				log.error("update gps location error", e);
			}
		}
		return list;
	} 
	private List<EjlLocation> getWiftLocation(List<EfLocationWifi>  efLocationWifiList,String baseUrl,Map<Long,Long> deviceUserMap,Map<Long,EjlDevice> deviceQrcodeMap){
		List<EjlLocation> resultList = new ArrayList<EjlLocation>();
		for(EfLocationWifi efLocationWifi:efLocationWifiList){
			try{
				EjlDevice qrcode = deviceQrcodeMap.get(efLocationWifi.getDeviceId());
				StringBuffer url = new StringBuffer();
				url.append(baseUrl);
				url.append("&accesstype=1");
				if(qrcode != null && qrcode.getCode()!=null)
				url.append("&imei="+qrcode.getCode());
				
				if(efLocationWifi.getMac1() != null){
					String mac = efLocationWifi.getMac1().replace("-",":");
					url.append("&macs="+mac+","+efLocationWifi.getSignal1()+","+efLocationWifi.getMacName1());
					if(efLocationWifi.getMac2()!= null){
						String mac2 = efLocationWifi.getMac2().replace("-",":");
						url.append("|"+mac2+","+efLocationWifi.getSignal2()+","+efLocationWifi.getMacName2());
					}
					if(efLocationWifi.getMac3()!= null){
						String mac3 = efLocationWifi.getMac3().replace("-",":");
						url.append("|"+mac3+","+efLocationWifi.getSignal3()+","+efLocationWifi.getMacName3());
					}
					if(efLocationWifi.getMac4()!= null){
						String mac4 = efLocationWifi.getMac4().replace("-",":");
						url.append("|"+mac4+","+efLocationWifi.getSignal4()+","+efLocationWifi.getMacName4());
					}
					if(efLocationWifi.getMac5()!= null){
						String mac5 = efLocationWifi.getMac5().replace("-",":");
						url.append("|"+mac5+","+efLocationWifi.getSignal5()+","+efLocationWifi.getMacName5());
					}
				}
				url.append("&output=json");
				GaoDeLocationStruc result = GaoDeLocationGetUtil.getGaoDeLocation(url.toString());
				if(result != null){
					efLocationWifi.setOpTag(1);
					efLocationWifiServiceImpl.update(efLocationWifi);
					EjlLocation ejlLocation = new EjlLocation();
					ejlLocation.setGmtCreated(DateUtils.convertLong2Date(efLocationWifi.getTime()));
					ejlLocation.setDeviceId(efLocationWifi.getDeviceId());
					ejlLocation.setLocation(getWaiduJinduConverter(result.getLocation()));
					ejlLocation.setUserId(deviceUserMap.get(efLocationWifi.getDeviceId()));
					ejlLocation.setTime(DateUtils.convertLong2Date(efLocationWifi.getTime()));
					ejlLocation.setSource(3);
					ejlLocation.setType(2);
					ejlLocation.setCity(result.getCity());
					try{
						String des = result.getDesc().replace(" " , "");
						ejlLocation.setDes(des);
					}catch(Exception e){
						e.printStackTrace();
					}
					//ejlComLocationServiceImpl.save(ejlLocation);
					resultList.add(ejlLocation);
				}
			}catch(Exception e){
				log.error("get location error", e);
			}
			
			
		}
		return resultList;
	}
	
	/**
	 * 根据组合获取一条wifi记录的多个坐标
	 * @param baseUrl
	 * @param deviceQrcodeMap
	 * @param efLocationWifi
	 * @return
	 * @throws Exception
	 */
	private List<GaoDeLocationStruc> getLocationList(String baseUrl,
			Map<Long, EjlDevice> deviceQrcodeMap, EfLocationWifi efLocationWifi)
			throws Exception {
		EjlDevice qrcode = deviceQrcodeMap.get(efLocationWifi.getDeviceId());
		String url = baseUrl+"&accesstype=1"; 
		if(qrcode != null && qrcode.getCode()!=null){
			url+="&imei="+qrcode.getCode();
		}
		
		Map<String,String> macsMap=new HashMap<String,String>();
		List<String> macsList=new ArrayList<String>();
		if(null!=efLocationWifi.getMac1()){
			String mac=efLocationWifi.getMac1().replace("-",":");
			macsMap.put(mac, efLocationWifi.getSignal1()+","+efLocationWifi.getMacName1());
			macsList.add(mac);
		}
		if(null!=efLocationWifi.getMac2()){
			String mac=efLocationWifi.getMac2().replace("-",":");
			macsMap.put(mac,efLocationWifi.getSignal2()+","+efLocationWifi.getMacName2());
			macsList.add(mac);
		}
		if(null!=efLocationWifi.getMac3()){
			String mac=efLocationWifi.getMac3().replace("-",":");
			macsMap.put(mac,efLocationWifi.getSignal3()+","+efLocationWifi.getMacName3());			
			macsList.add(mac);
		}
		if(null!=efLocationWifi.getMac4()){
			String mac=efLocationWifi.getMac4().replace("-",":");
			macsMap.put(mac,efLocationWifi.getSignal4()+","+efLocationWifi.getMacName4());
			macsList.add(mac);
		}
		if(null!=efLocationWifi.getMac5()){
			String mac=efLocationWifi.getMac5().replace("-",":");
			macsMap.put(mac,efLocationWifi.getSignal5()+","+efLocationWifi.getMacName5());
			macsList.add(mac);
		}
		List<String> macCombList=new ArrayList<String>();
		//macsList必定大于等于2
		if(macsList.size()>2){	//两个以上的mac才组合
			macCombList=getCombinationsMacs(macsList,macsList.size()-1);
			StringBuffer sb=new StringBuffer();
			for(String mac:macsList){	
				sb.append(mac).append(",");
			}
			macCombList.add(sb.substring(0,sb.length()-1));
		}else{
			macCombList.add(macsList.get(0)+","+macsList.get(1));
		}
		List<GaoDeLocationStruc> locationList=new ArrayList<GaoDeLocationStruc>();
		for(String macComb:macCombList){
			StringBuffer sb=new StringBuffer(url);
			String[] macArr=macComb.split(",");
			for(int i=0;i<macArr.length;i++){
				String mac=macArr[i];
				if(i==0){
					sb.append("&macs="+mac+","+macsMap.get(mac));
				}else{
					sb.append("|"+mac+","+macsMap.get(mac));
				}
			}
			sb.append("&output=json");
			GaoDeLocationStruc result = GaoDeLocationGetUtil.getGaoDeLocation(url.toString());
			if(null!=result){
				locationList.add(result);
			}
		}
		return locationList;
	}
	public static List<String> getCombinationsMacs(List<String> macsSet, int length){
		String[] str=new String[macsSet.size()];
		List<String> strs=new ArrayList<String>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(macsSet.toArray(str));
		Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector,length);
		for (ICombinatoricsVector<String> combination : gen) {
			String abc="";
			for(String st:combination.getVector()){
				abc+=(","+st);
			}
			strs.add(abc.substring(1));
		}
		return strs;
	}
	private List<EjlLocation> getCellGbrsLocation(List<DeviceMobile> deviceMobileList,String baseUrl,Map<Long,Long> deviceUserMap,Map<Long,EjlDevice> deviceQrcodeMap){
		List<EjlLocation> resultList = new ArrayList<EjlLocation>();
		for(DeviceMobile deviceMobile:deviceMobileList){
			try{
			EjlDevice qrcode = deviceQrcodeMap.get(deviceMobile.getDeviceId());	
			StringBuffer url = new StringBuffer();
			url.append(baseUrl);
			url.append("&accesstype=0");
			if(qrcode != null && qrcode.getCode()!=null)
			url.append("&imei="+qrcode.getCode());
			url.append("&cdma=0");
			/*if(qrcode != null && qrcode.getImsi()!=null)
			url.append("&imsi="+qrcode.getImsi());*/
			url.append("&bts="+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac1()+","+deviceMobile.getCi1()+","+deviceMobile.getRssi1());
			if(deviceMobile.getLac2()!=null){
				
			url.append("&nearbts="+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac2()+","+deviceMobile.getCi2()+","+deviceMobile.getRssi2());
			if(deviceMobile.getLac3()!=null){
				url.append("|"+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac3()+","+deviceMobile.getCi3()+","+deviceMobile.getRssi3());
				if(deviceMobile.getLac4()!=null){
					url.append("|"+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac4()+","+deviceMobile.getCi4()+","+deviceMobile.getRssi4());
					if(deviceMobile.getLac5()!=null){
						url.append("|"+deviceMobile.getMcc()+","+deviceMobile.getMnc()+","+deviceMobile.getLac5()+","+deviceMobile.getCi5()+","+deviceMobile.getRssi5());
					}
				}
			}
			
			}
			url.append("&output=json");
			GaoDeLocationStruc result = GaoDeLocationGetUtil.getGaoDeLocation(url.toString());
			if(result != null){
				deviceMobile.setOpTag(1);
				deviceMobileServiceImpl.update(deviceMobile);
				EjlLocation ejlLocation = new EjlLocation();
				ejlLocation.setGmtCreated(DateUtils.convertLong2Date(deviceMobile.getTime()));
				ejlLocation.setDeviceId(deviceMobile.getDeviceId());
				ejlLocation.setLocation(getWaiduJinduConverter(result.getLocation()));
				ejlLocation.setTime(DateUtils.convertLong2Date(deviceMobile.getTime()));
				ejlLocation.setUserId(deviceUserMap.get(deviceMobile.getDeviceId()));
				ejlLocation.setSource(2);
				ejlLocation.setType(2);
				ejlLocation.setCity(result.getCity());
				try{
				String des = result.getDesc().replace(" " , "");
				ejlLocation.setDes(des);
				}catch(Exception e){
					e.printStackTrace();
				}
				//ejlComLocationServiceImpl.save(ejlLocation);
				resultList.add(ejlLocation);
				
			}
			}catch(Exception e){
				log.error("get location error", e);
			}
		}
		return resultList;
	}
	
	private String getWaiduJinduConverter(String location){
		String[] jinduWeidus =  location.split(",");
		return jinduWeidus[1]+","+jinduWeidus[0];
	}
	/// <summary>  
	/// 获取两个经纬度之间的距离  
	/// </summary>  
	/// <param name="LonA">经度A</param>  
	/// <param name="LatA">纬度A</param>  
	/// <param name="LonB">经度B</param>  
	/// <param name="LatB">经度B</param>  
	/// <returns>距离（米）</returns> 
	public static double getDistance(double LonA, double LatA, double LonB, double LatB)  
	{   
		if(LonA==LonB && LatA==LatB) return 0.0;
	    // 东西经，南北纬处理，只在国内可以不处理(假设都是北半球，南半球只有澳洲具有应用意义)  
	    double MLonA = LonA;  
	    double MLatA = rad(LatA);  
	    double MLonB = LonB;  
	    double MLatB = rad(LatB);  
	    
	    double C = Math.sin(MLatA) * Math.sin(MLatB) + Math.cos(MLatA) * Math.cos(MLatB) * Math.cos(rad(MLonA - MLonB));
	    if(C>1){	//经纬度很接近时 C可能大于1 此时默认距离0
	    	C=1;
	    }
	    return (6371.004 * Math.acos(C))*1000; 
	}  
	  
	private static double rad(double d)  
	{  
	    return d * Math.PI / 180.0;  
	}
	
	public static void main(String[] args){
		//22.5485124,113.9441802
/*		System.out.println(Distance(113.9489052,22.5432739,113.9429319,22.5372898));
		
		System.out.println(Distance(113.9437191,22.5487606,113.9429319,22.5372898));
		
		System.out.println(Distance(113.9437191,22.5487606,113.9489052,22.5432739));*/

		
		/*
		 * | 127246 |   1060 | 22.5489448,113.9431209 |      330 |    2 | 2015-12-21 21:59:52 |      3 | NULL   |        -1 | 2015-12-21 22:03:51 |      NULL | NULL       |
| 127248 |   1060 | 22.5487408,113.9439147 |      330 |    2 | 2015-12-21 22:03:52 |      3 | NULL   |        -1 | 2015-12-21 22:06:50 |      NULL | NULL       |
| 127249 |   1060 | 22.5431863,113.948903  |      330 |    2 | 2015-12-21 22:05:52 |      3 | NULL   |        -1 | 2015-12-21 22:06:50 |      NULL | NULL       |
| 127253 |   1060 | 22.5489448,113.9431209 |      330 |    2 | 2015-12-21 22:13:53 |      3 | NULL   |        -1 | 2015-12-21 22:15:50 |      NULL | NULL       |
| 127254 |   1060 | 22.5487408,113.9439147 |      330 |    2 | 2015-12-21 22:14:53 |      3 | NULL   |        -1 | 2015-12-21 22:15:50 |      NULL | NULL       |
		 */
		/*
		System.out.println(Distance(113.948903,22.5431863,113.9431209,22.5489448));
		System.out.println(Distance(113.948903,22.5431863,113.9439147,22.5487408));
		System.out.println(Distance(113.9431209,22.5489448,113.9439147,22.5487408));
		System.out.println(Distance(113.9431209,22.5489448,113.9439147,22.5487408));*/ 
		NumberFormat nf = NumberFormat.getNumberInstance();  
        nf.setMaximumFractionDigits(LocationUtil.PREC_DEGREE); 
		System.out.println(nf.format((Math.atan(-1)*180/Math.PI)));
		LatLng latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(22.69684,113.841038));
		System.out.println(latLng.getLongitude()+","+latLng.getLatitude());
		latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(22.683645,113.833598));
		System.out.println(latLng.getLongitude()+","+latLng.getLatitude());
		latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(22.66916,113.828515));
		System.out.println(latLng.getLongitude()+","+latLng.getLatitude());
		latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(22.712975,113.84982));
		System.out.println(latLng.getLongitude()+","+latLng.getLatitude());
		latLng = GpsLocationTransformGede.transformFromWGSToGCJ(new LatLng(22.715177,113.85035));
		System.out.println(latLng.getLongitude()+","+latLng.getLatitude());
		System.out.println(Distance(113.94337380,22.54892930,113.943876,22.549848));
		 
		 
		GaoDeLocationOperator s2=JsonUtils.fromJson("{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"result\":{\"type\":\"4\",\"location\":\"121.9641081,30.0969048\",\"radius\":\"550\",\"desc\":\"G9211甬舟高速 靠近桃夭门大桥\",\"province\":\"[]\",\"city\":\"[]\",\"adcode\":\"900000\",\"road\":\"G9211甬舟高速\",\"street\":\"册南路\",\"poi\":\"桃夭门大桥\"}}",GaoDeLocationOperator.class);
		
		EfDeviceSetting deviceSetting=new EfDeviceSetting();
		deviceSetting.setDeviceId(222L);
		DeviceParamCommon cc=new DeviceParamCommon();
		cc.setBright(2);
		cc.setSound(3);
		cc.setShake(1);
		cc.setQuiet(1);
		cc.setPowerAuto(new String[]{"8:00|14:00","12:00|21:00"});
		cc.setHeartOnff(1);
		cc.setLocationOnff(1);
		cc.setSleepOnff(1);
		cc.setWalkOnff(1);
		cc.setSittingOnff(1);
		
		
		DeviceParamHealth hh=new DeviceParamHealth();
		hh.setSittingTime(2);
		hh.setHeight(172);
		hh.setSleepSpan(new String[]{"20:00-08:00","13:00-15:00"});
		DeviceParamFrequency ff=new DeviceParamFrequency();
		ff.setBatteryGfreq(60);
		ff.setBatteryUfreq(60);
		ff.setSignalGfreq(180);
		ff.setSignalUfreq(300);
		ff.setHeartGfreq(300);
		ff.setHeartGfreq(600);
		ff.setLocationGfreq(30);
		ff.setLocationUfreq(300);
		ff.setWalkUfreq(30);
		
		DeviceParamConnect cn=new DeviceParamConnect();
		cn.setBeat(240);
		cn.setTimeout(60);
		cn.setRetry(3);
		cn.setRestart(1);
		
		deviceSetting.setCommon(JsonUtils.toJson(cc));
		deviceSetting.setHealth(JsonUtils.toJson(hh));
		deviceSetting.setFrequency(JsonUtils.toJson(ff));
		deviceSetting.setConnect(JsonUtils.toJson(cn));
		 
		System.out.println(JsonUtils.toJson(new String[]{"108|66","108|66"}));
		
		LatLng ll1=new LatLng(39.941715,116.407287);
		LatLng ll2=bd_decrypt(ll1.getLatitude(), ll1.getLongitude());
		LatLng ll=bd_encrypt(ll2.getLatitude(),ll2.getLongitude());
		System.out.println("SSSSSSS1:"+ll1.getLongitude()+","+ll1.getLatitude());
		System.out.println("SSSSSSS2:"+ll2.getLongitude()+","+ll2.getLatitude());
		System.out.println(getDistance(116.368904,39.923423,116.387271,39.922501));
		System.out.println(getDistance(ll1.getLongitude(),ll1.getLatitude(),ll.getLongitude(),ll.getLatitude())+"s3s");
		System.out.println(getDistance(114.05726100,22.55992590,114.05404160,22.56144860));
		System.out.println(getDistance(114.05726100,22.55992590,114.05345160,22.56224120));
		//System.out.println(LocationUtil.getDistance(113.94337380,22.54892930,113.943876,22.549848)); 
		List<String> s=new ArrayList<String>();
		s.add("8a:f2:28:db:1d:f2");
		s.add("c8-3a-35-19-35-c8"); 
		List<String> l =getCombinationsMacs(s, 2);
		for(String ss:l){
			System.out.println(ss);
		}  		
	}
	
	
	public static LatLng bd_encrypt(double gg_lat, double gg_lon)
	{
		LatLng s=new LatLng();
	    double x = gg_lon, y = gg_lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI); 
	    s.setLongitude(z * Math.cos(theta) + 0.0065);
	    s.setLatitude(z * Math.sin(theta) + 0.006);
	    return s;
	}
	 
	public static LatLng bd_decrypt(double bd_lat, double bd_lon)
	{
		LatLng s=new LatLng();
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
	    s.setLongitude(z * Math.cos(theta));
	    s.setLatitude(z * Math.sin(theta));
	    return s;
	}
	  
}
