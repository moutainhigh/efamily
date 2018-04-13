/**   
* @Title: EjlRemindServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午4:30:28 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEfComLocationOriginDao;
import com.winterframework.efamily.entity.EfLocationOrigin;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.service.IEfComLocationOriginService;
import com.winterframework.efamily.service.IEfLocationSemiService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.ITskLocationSemiService;
import com.winterframework.efamily.utils.LocationUtil;



/** 
* @ClassName: EfLocationOriginServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午4:30:28 
*  
*/
@Service("efLocationSemiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfLocationSemiServiceImpl extends EfComLocationSemiServiceImpl implements IEfLocationSemiService {
	private Logger log = LoggerFactory.getLogger(EfLocationSemiServiceImpl.class);
	@Resource(name="efComLocationOriginDaoImpl")
	private IEfComLocationOriginDao efComLocationOriginDaoImpl;
	@Resource(name="ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationService;
	@Resource(name="efComLocationOriginServiceImpl")
	private IEfComLocationOriginService efComLocationOriginService;
	@Resource(name="tskLocationSemiServiceImpl")
	private ITskLocationSemiService tskLocationSemiService;
	
	@Override
	public void initLocationSemi() throws Exception {
		/*List<Long> deviceIds = efComLocationOriginDaoImpl.getLocationOriginDeviceId();
		Context ctx = new Context();
		ctx.set("userId", -1);
		//List<List<Long>> deviceIdSplits = LocationUtil.getSplitsDeviceIds(deviceIds);
		for(Long deviceId:deviceIds){
			List<Long> deviceList = new ArrayList<Long>();
			deviceList.add(deviceId);
			List<EfLocationOrigin> list = efComLocationOriginDaoImpl.getLocationOriginByDevice(deviceList);
			
			if(list==null||list.size()==0){
				continue;
			}
			for(EfLocationOrigin efLocationOrigin:list){
				efLocationOrigin.setStatus(YesNo.YES.getValue());
			}
			efComLocationOriginService.save(ctx, list);
			
			*//**
			 * 1.处理当前未处理的list
			 * 2.走数据库处理(所有坐标点)
			 *//*
			List<EfLocationSemi> lists = this.removeLocationSourceWifiCellPointForTime(getInitLocationSemi(list),deviceId);
			//聚合数据入库
			this.save(ctx, lists);
			//处理表数据时间不一致的情况，重新去除3种数据源数据
			//this.removeSemiSourceWifiCellPoint(deviceId);
			
			//按指定的距离聚合数据
			juheEjlLocation(deviceId);
		}*/
	}
	
	
	
	private void removeSemiSourceWifiCellPoint(Long deviceId) throws Exception{
		List<EfLocationSemi> unDoList = this.getEntityDao().getAllListByNewUnhander(deviceId);
		Context ctx = new Context();
		ctx.set("userId", -1);
		boolean isDoRepeat = false;
		for(EfLocationSemi efLocationSemi:unDoList){
			if(efLocationSemi.getFlag().intValue()==EfLocationSemi.Flag.FINISH.getValue()){
				EjlLocation location=ejlComLocationService.getBySourceId(efLocationSemi.getId());
				if(null!=location){
					ejlComLocationService.remove(ctx, location.getId());
				}
				isDoRepeat = true;
			}else if(efLocationSemi.getFlag().intValue() != EfLocationSemi.Flag.UNHANDLE.getValue()){
				isDoRepeat = true;
			}
			efLocationSemi.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());
		}
		if(isDoRepeat){
			List<EfLocationSemi> resultList = removeLocationSourceWifiCellPointForTime(unDoList,deviceId);
			for(EfLocationSemi efLocationSemi:unDoList){
				if(!resultList.contains(efLocationSemi)){
					efLocationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
				}
			}
			this.save(ctx, unDoList);
		}
	}
	
	private List<EfLocationSemi> getInitLocationSemi(List<EfLocationOrigin> list) throws Exception{
		List<EfLocationSemi> semiList = new ArrayList<EfLocationSemi>();
		for(EfLocationOrigin efLocationOrigin:list){
			semiList.add(this.converterLocationOriginToSemi(efLocationOrigin));
		}
		return semiList;
	}
	
	
	private void removeByTime(List<EfLocationSemi> list,Date time,int source){
		List<EfLocationSemi> removeList = new ArrayList<EfLocationSemi>();
		for(int i=list.size()-1;i>=0;i--){
			EfLocationSemi last = list.get(i);
			Long between =DateUtils.calcMinutesBetween(last.getTime(), time);
			if(between<LocationUtil.POINT_TIME_SPLIT&&last.getSource().intValue()==source){
				removeList.add(last);
			}
		}
		list.removeAll(removeList);
	}
	
	
	private List<EfLocationSemi> removeLocationSourceWifiCellPointForTime(List<EfLocationSemi> list,Long deviceId) throws Exception{
		List<EfLocationSemi> resultList = new ArrayList<EfLocationSemi>();
		for(int i=0;i<list.size();i++){
			EfLocationSemi efLocationSemi = list.get(i);
			if(resultList.size()==0){
				if(list.get(i).getSource()!=3||!this.isRemoveWifi(efLocationSemi, list)){
					resultList.add(list.get(i));
				}
			}else{
				Long time =DateUtils.calcMinutesBetween(resultList.get(resultList.size()-1).getTime(), list.get(i).getTime());
				if(time>=LocationUtil.POINT_TIME_SPLIT){
					resultList.add(list.get(i));
				}else{
					EfLocationSemi first = resultList.get(resultList.size()-1);
					if(efLocationSemi.getSource().intValue()==1){
						/*if(first.getSource().intValue()!=1){
							resultList.remove(first);
						}*/
						this.removeByTime(resultList, efLocationSemi.getTime(), 2);
						this.removeByTime(resultList, efLocationSemi.getTime(), 3);
						resultList.add(efLocationSemi);
					}else if(efLocationSemi.getSource().intValue()==3 && !this.isRemoveWifi(efLocationSemi, list)){
						if(first.getSource().intValue()==1){
							continue;
						}else if(first.getSource().intValue()==3){
							resultList.add(efLocationSemi);
						}else if(first.getSource().intValue()==2){
							/*resultList.remove(first);*/
							this.removeByTime(resultList, efLocationSemi.getTime(), 2);
							resultList.add(efLocationSemi);
						}
					}else if(efLocationSemi.getSource().intValue()==2){
						if(first.getSource().intValue()==1||first.getSource().intValue()==3){
							continue;
						}
						resultList.add(efLocationSemi);
					}
				}
			}
		}
		Context ctx = new Context();
		ctx.set("userId", -1);
		List<EfLocationSemi> firstList = this.getEntityDao().getFirstLocationByNewUnhanderTime(deviceId, resultList.get(0).getTime(),LocationUtil.POINT_TIME_SPLIT);
		if(!firstList.isEmpty()){
			EfLocationSemi first = firstList.get(firstList.size()-1);
			if(resultList.get(0).getSource()==1){
				for(EfLocationSemi efLocationSemi:firstList){
					efLocationSemi.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());
					if(efLocationSemi.getSource().intValue()==2||efLocationSemi.getSource().intValue()==3){
						if(efLocationSemi.getFlag().intValue()==EfLocationSemi.Flag.FINISH.getValue()){ 
							EjlLocation location=ejlComLocationService.getBySourceId(efLocationSemi.getId());
							if(null!=location){
								ejlComLocationService.remove(ctx, location.getId());
							}
						}
						efLocationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
						efLocationSemi.setRemark("当前gps，去除前面的非gps数据");
						EfLocationSemi firstFirst = this.getEntityDao().getLast(efLocationSemi.getDeviceId(), efLocationSemi.getTime());
						if(firstFirst != null){
						firstFirst.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());
						this.save(ctx, firstFirst);}
					}
				}
			}else{
				if(resultList.get(0).getSource()==3){
					if(first.getSource().intValue()==1){
						resultList.get(0).setFlag(EfLocationSemi.Flag.DELETE.getValue());
						resultList.get(0).setRemark("当前wifi，去除当前点wifi数据");
						first.setPreviousId(resultList.size()>=2?resultList.get(1).getId():null);
					}else if(first.getSource().intValue()==2){
						for(EfLocationSemi efLocationSemi:firstList){
							efLocationSemi.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());
							if(efLocationSemi.getSource().intValue()==2||efLocationSemi.getSource().intValue()==3){
								if(efLocationSemi.getFlag().intValue()==EfLocationSemi.Flag.FINISH.getValue()){
									EjlLocation location=ejlComLocationService.getBySourceId(efLocationSemi.getId());
									if(null!=location){
										ejlComLocationService.remove(ctx, location.getId());
									}
								}
								efLocationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
								efLocationSemi.setRemark("当前wifi,去除前面的非gps数据");
								EfLocationSemi firstFirst = this.getEntityDao().getLast(efLocationSemi.getDeviceId(), efLocationSemi.getTime());
								if(firstFirst != null){
								firstFirst.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());
								this.save(ctx, firstFirst);}
							}
						}
					}
				}else if(resultList.get(0).getSource().intValue()==2){
					if(first.getSource().intValue()==1||first.getSource().intValue()==3){
						resultList.get(0).setFlag(EfLocationSemi.Flag.DELETE.getValue());
						resultList.get(0).setRemark("当前基站，去除当前点基站数据");
						first.setPreviousId(resultList.size()>=2?resultList.get(1).getId():null);
					}
				}
			}
			this.save(ctx, firstList);
		}
		return resultList;
	}
	
	/*private List<EfLocationOrigin> removeLocationSourceWiftCellPointForTime(List<EfLocationOrigin> list){
			Date beginDate = list.get(0).getTime();
			Date endDate = list.get(list.size()-1).getTime();
			beginDate = DateUtils.parse(DateUtils.format(beginDate,DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN);
			endDate = DateUtils.parse(DateUtils.format(DateUtils.addMinutes(endDate, 1),DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN);
			List<List<Date>> dateSplitList = LocationUtil.dateSplitSecond(beginDate, endDate, LocationUtil.POINT_TIME_SPLIT);
			List<EfLocationOrigin> resultList = new ArrayList<EfLocationOrigin>();
			for(List<Date> dateList : dateSplitList){
				 beginDate = dateList.get(0);
				 endDate = dateList.get(1);
				 List<EfLocationOrigin> splitLocaitonList = new ArrayList<EfLocationOrigin>();
				 for(EfLocationOrigin efLocationOrigin:list){
					 if(efLocationOrigin.getTime().compareTo(beginDate)>=0&&efLocationOrigin.getTime().compareTo(endDate)<0){
						 splitLocaitonList.add(efLocationOrigin);
					 }
				 }
				 resultList.addAll(this.getResultList(splitLocaitonList, list));
			}
			return resultList;
	}*/
	
	

	public EfLocationSemi getNearCellForDate(EfLocationSemi ejlWiftLocation,List<EfLocationSemi> cellSourceList){
		Long minTime = Long.MAX_VALUE;
		EfLocationSemi cellEjlLocation = null;
		for(EfLocationSemi ejlLocation:cellSourceList){
			Date cellDate = ejlLocation.getTime();
			if(ejlWiftLocation.getDeviceId().longValue()==ejlLocation.getDeviceId().longValue() && Math.abs(DateUtils.calcMinutesBetween(ejlWiftLocation.getTime(), cellDate))<minTime){
				minTime = Math.abs(DateUtils.calcMinutesBetween(ejlWiftLocation.getTime(), cellDate));
				cellEjlLocation = ejlLocation;
			}
		}
		if(minTime<2){
			return cellEjlLocation;
		}else{
			log.info("wiftDate:"+DateUtils.format(ejlWiftLocation.getTime(),DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN)+"30分钟内没有基站数据");
			return null;
		}
	}
	
	private boolean isRemoveWifi(EfLocationSemi efLocationOriginWifi,List<EfLocationSemi> allList){
		EfLocationSemi cellEjlLocation = getNearCellForDate(efLocationOriginWifi,getListBySource(allList,2));
		if(cellEjlLocation!=null){
			LatLng l1 = new LatLng(efLocationOriginWifi.getLocation());
			LatLng l2 = new LatLng(cellEjlLocation.getLocation());
			if(LocationUtil.getDistance(l1, l2)>LocationUtil.DYNAMICWIFT_DISTANCE){
				return true;
			}
		}
		return false;
	}
	
	
	private  List<EfLocationSemi> getResultList(List<EfLocationSemi> list,List<EfLocationSemi> allList){
		List<EfLocationSemi> resultList = new ArrayList<EfLocationSemi>();
		//存在gps，只留gps
		if(isExist(list,1)){
			resultList.addAll(getListBySource(list,1));
			return resultList;
		}
		//存在wift
		else if(isExist(list,3)){
			resultList.addAll(getListBySource(list,3));
			List<EfLocationSemi> removeList = new ArrayList<EfLocationSemi>();
			if(resultList.size()>0){
				EfLocationSemi cellEjlLocationRemove = null;
				for(EfLocationSemi efLocationOrigin:resultList){
					EfLocationSemi cellEjlLocation = getNearCellForDate(efLocationOrigin,getListBySource(allList,2));
					if(cellEjlLocation!=null){
						LatLng l1 = new LatLng(efLocationOrigin.getLocation());
						LatLng l2 = new LatLng(cellEjlLocation.getLocation());
						if(LocationUtil.getDistance(l1, l2)>LocationUtil.DYNAMICWIFT_DISTANCE){
							removeList.add(efLocationOrigin);
							cellEjlLocationRemove = cellEjlLocation;
						}
						
					}
				}
				resultList.removeAll(removeList);
				if(resultList.isEmpty()){
					resultList.add(cellEjlLocationRemove);
				}
			}
			return resultList;
		}else if(isExist(list,2)){
			resultList.addAll(getListBySource(list,2));
			return resultList;
		}
		return resultList;
	}

	private List<EfLocationSemi> getListBySource(List<EfLocationSemi> list,int source){
		List<EfLocationSemi> resultList = new ArrayList<EfLocationSemi>();
		for(EfLocationSemi efLocationOrigin:list){
			if(efLocationOrigin.getSource().intValue()==source){
				resultList.add(efLocationOrigin);
			}
		}
		return resultList;
	}
	
	private boolean isExist(List<EfLocationSemi> list,int source){
		for(EfLocationSemi efLocationOrigin:list){
			if(efLocationOrigin.getSource().intValue()==source){
				return true;
			}
		}
		return false;
	}
	
	
	private  void  getJuheList(List<EfLocationSemi> ejlLocationList,List<List<EfLocationSemi>> resultList,int index){
		if(index>ejlLocationList.size()-1){
			return;
		}else{
			EfLocationSemi begin = ejlLocationList.get(index);
			List<EfLocationSemi> juheList = new ArrayList<EfLocationSemi>();
			juheList.add(begin);
			resultList.add(juheList);
			if(index==ejlLocationList.size()-1){
				return;
			}else{
				for(int i=index+1;i<ejlLocationList.size();i++){
					EfLocationSemi ejlLocation = ejlLocationList.get(i);
					Double distance =LocationUtil.getDistance(new LatLng(begin.getLocation()), new LatLng(ejlLocationList.get(i).getLocation()));
					if(distance.doubleValue()<=LocationUtil.JUHEDISTANCE&&begin.getDeviceId().longValue()==ejlLocation.getDeviceId().longValue())
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
	
	private EfLocationSemi converterLocationOriginToSemi(EfLocationOrigin efLocationOrigin){
		EfLocationSemi efLocationSemi = new EfLocationSemi();
		efLocationSemi.setDeviceId(efLocationOrigin.getDeviceId());
		efLocationSemi.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());//未处理
		efLocationSemi.setLocation(efLocationOrigin.getLocation());
		efLocationSemi.setRemark(efLocationOrigin.getRemark());
		efLocationSemi.setSource(efLocationOrigin.getSource());
		efLocationSemi.setSourceId(efLocationOrigin.getId());
		efLocationSemi.setStatus(YesNo.YES.getValue());//可用
		efLocationSemi.setTime(efLocationOrigin.getTime());
		efLocationSemi.setType(efLocationOrigin.getType());
		efLocationSemi.setUserId(efLocationOrigin.getUserId());
		efLocationSemi.setTimeEnd(efLocationOrigin.getTime());
		efLocationSemi.setTimeBegin(efLocationOrigin.getTime());
		efLocationSemi.setAddress(efLocationOrigin.getAddress());
		efLocationSemi.setCity(efLocationOrigin.getCity());
		return efLocationSemi;
	}
	
	@Override
	public void juheEjlLocation(List<EfLocationSemi> ejlLocationList,boolean isInit) throws Exception{
		List<EfLocationSemi> resultList = new ArrayList<EfLocationSemi>();
		List<List<EfLocationSemi>> juheList = new ArrayList<List<EfLocationSemi>>();
		LocationUtil.locationListSort(ejlLocationList);
		getJuheList(ejlLocationList, juheList, 0);
		if(juheList.size()>0){
			for(List<EfLocationSemi> unitList:juheList){
				EfLocationSemi efLocationSemi = unitList.get(unitList.size()-1);
				efLocationSemi.setTimeBegin(unitList.get(0).getTimeBegin()==null?unitList.get(0).getTime():unitList.get(0).getTimeBegin());
				efLocationSemi.setTimeEnd(unitList.get(unitList.size()-1).getTimeEnd()==null?unitList.get(unitList.size()-1).getTime():unitList.get(unitList.size()-1).getTimeEnd());
				if(unitList.size()>1){
					//纬度
					BigDecimal latitude = new BigDecimal(0);
					//经度
					BigDecimal longitude = new BigDecimal(0);
					for(EfLocationSemi efLocationOrigin : unitList){
						LatLng latLng = new LatLng(efLocationOrigin.getLocation());
						latitude = latitude.add(new BigDecimal(latLng.getLatitude()));
						longitude = longitude.add(new BigDecimal(latLng.getLongitude()));
					}
					StringBuffer sp = new StringBuffer();
					sp.append(latitude.divide(new BigDecimal(unitList.size()), 7,BigDecimal.ROUND_HALF_UP)).append(Separator.comma).append(longitude.divide(new BigDecimal(unitList.size()), 7,BigDecimal.ROUND_HALF_UP));
					efLocationSemi.setLocation(sp.toString());
					efLocationSemi.setSourceId(null);
					efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				}else{
					efLocationSemi.setFlag(efLocationSemi.getFlag().intValue()==EfLocationSemi.Flag.UNHANDLE.getValue()?EfLocationSemi.Flag.JUHEFINISH.getValue():efLocationSemi.getFlag().intValue());
				}
				if(isInit){
					efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				}else{
					efLocationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
				}
				resultList.add(efLocationSemi);
			}
		}
		///聚合后原来被删除的数据改变状态
		List<EfLocationSemi> deleteList = new ArrayList<EfLocationSemi>();
		for(EfLocationSemi efLocationSemi:ejlLocationList){
			if(!resultList.contains(efLocationSemi)){
				efLocationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
				deleteList.add(efLocationSemi);
			}
		}
		Context ctx = new Context();
		ctx.set("userId", -1);
		this.save(ctx, deleteList);
		//重新设置前后点之后入库
		this.setPreNextPoint(resultList);
	}
	
	private void juheEjlLocation(Long deviceId) throws Exception{	
		//地址描叙相同的点认为是同一个点
		//this.setRepeatPoint(ejlLocationList);
		List<EfLocationSemi> ejlLocationList = this.getDeviceLocationSemiByStatus(null, deviceId, EfLocationSemi.Flag.UNHANDLE.getValue());
		juheEjlLocation(ejlLocationList,true);
	}
	
	private void setPreNextPoint(List<EfLocationSemi> list) throws Exception{
		//List<EfLocationSemi> list = this.getDeviceLocationSemiByStatus(null, null, EfLocationSemi.Flag.UNHANDLE.getValue());
		EfLocationSemi first = null;
		List<EfLocationSemi> firstList = new ArrayList<EfLocationSemi>();
		//List<EfLocationSemi> removeList = new ArrayList<EfLocationSemi>();
		int i=0;
		for(EfLocationSemi location : list){
			if(first == null||first.getDeviceId().longValue()!=location.getDeviceId().longValue()){
				first = location;
			}
			if(first.equals(location)){
				first = this.getEntityDao().getLast(first.getDeviceId(), first.getTime());
				if(first!=null){
					first.setNextId(location.getId());
					if(LocationUtil.getDistance(new LatLng(first.getLocation()), new LatLng(location.getLocation()))<=LocationUtil.JUHEDISTANCE){
						//纬度
						BigDecimal latitude = new BigDecimal(0);
						//经度
						BigDecimal longitude = new BigDecimal(0);
						latitude = latitude.add(new BigDecimal(new LatLng(first.getLocation()).getLatitude())).add(new BigDecimal(new LatLng(location.getLocation()).getLatitude()));
						longitude = longitude.add(new BigDecimal(new LatLng(first.getLocation()).getLongitude())).add(new BigDecimal(new LatLng(location.getLocation()).getLongitude()));
						//前后点聚合之后只改变前一点时间和坐标，后一点删除
						StringBuffer sp = new StringBuffer();
						sp.append(latitude.divide(new BigDecimal(2), 7,BigDecimal.ROUND_HALF_UP)).append(Separator.comma).append(longitude.divide(new BigDecimal(2), 7,BigDecimal.ROUND_HALF_UP));
						first.setLocation(sp.toString());
						first.setFlag(EfLocationSemi.Flag.DELETE.getValue());
						first.setRemark("跟数据库上一点比较，需要聚合去除上一点");
						
						//去掉上一点，上上一点的下一点坐标有变化
						EfLocationSemi firstFirst = this.getEntityDao().getLast(first.getDeviceId(), first.getTime());
						first.setTime(location.getTime());
						if(firstFirst!=null){
							firstFirst.setNextId(location.getId());
							firstList.add(firstFirst);
						}
						location.setLocation(sp.toString());
						if(first.getTimeBegin()!=null){
							location.setTimeBegin(first.getTimeBegin());
						}
						//removeList.add(location);
					}
					if(first.getFlag()==EfLocationSemi.Flag.UNHANDLE.getValue()){
						first.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
					}
					firstList.add(first);
				}else{
					//location.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
					location.setPreviousId(null);
				}
			}
			if(first!=null){
				location.setPreviousId(first.getFlag().intValue()!=EfLocationSemi.Flag.DELETE.getValue()?first.getId():first.getPreviousId());
				//location.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				//location.setRemark(location.getRemark()+" 距离上一点：" + LocationUtil.getDistance(new LatLng(first.getLocation()), new LatLng(location.getLocation()))+"米");
				location.setDistance(first.getFlag().intValue()!=EfLocationSemi.Flag.DELETE.getValue()?LocationUtil.getDistance(new LatLng(first.getLocation()), new LatLng(location.getLocation())):
					first.getDistance());
				
			}
			i++;
			if(list.size()>i){
				EfLocationSemi last = list.get(i);
				if(location.getDeviceId().longValue()==last.getDeviceId().longValue()){
					location.setNextId(last.getId());
					//location.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				}else{
					location.setNextId(null);
				}
			}
			first = location;
		}
		
		list.addAll(firstList);
		Context ctx = new Context();
		ctx.set("userId", -1);
		this.save(ctx, list);
	}
	
	/*private void setRepeatPoint(List<EfLocationOrigin> ejlLocationList){
		try{
			for(int i=0;i<ejlLocationList.size()-1;i++){
				EfLocationOrigin first = ejlLocationList.get(i);
				EfLocationOrigin last = ejlLocationList.get(i+1);
				if(first.getRemark()!=null&&last.getRemark()!=null&&first.getRemark().equals(last.getRemark())&&first.getDeviceId().longValue()==last.getDeviceId().longValue()){
					last.setLocation(first.getLocation());
				}
				
			}
		}catch(Exception e){
			log.error("get location desc error",e);
		}
	}*/
	
	
}
