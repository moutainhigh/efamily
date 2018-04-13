package com.winterframework.efamily.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.MD5Util;
import com.winterframework.efamily.common.DoubleSpan;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.LatLng;

/**
 * 定位工具类
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月8日
 */
public class LocationUtil {
	public final static double R=6371.004;	// 地球半径（千米）
	public final static double KM=1000;	// 千米
	public final static int PREC_SPEED=3;	//速度精度 3位（km/h）
	public final static int PREC_ACCELERATION=2;	//加速度精度 2位（km/h）
	public final static int PREC_DEGREE=0;	//角度精度 0位
	public final static int PREC_LATLON=3;	//经纬度精度 3位
	public final static int OFFSET_LATLON=3;	//经纬度偏差 3
	public final static int OFFSET_NEAR_MINS=2;	//时间偏差2分钟
	public final static Double DYNAMICWIFT_DISTANCE = 5000d;//动态wift偏移最大值（m）大于时速120 计算
	public final static double MIN_VALUE=0.0000001;	//无限小正值

	
	public final static Integer POINT_TIME_SPLIT = 1; //每种地址类型的时间间隔（m），指定时间内只有一种类型的数据
	

	public final static double JUHEDISTANCE = 80;//聚合数据的精度
	public final static int TIME_STAY=3;	//停留时间 5min

	/// <summary>  
	/// 获取两个经纬度之间的距离  
	/// </summary>  
	/// <param name="LonA">经度A</param>  
	/// <param name="LatA">纬度A</param>  
	/// <param name="LonB">经度B</param>  
	/// <param name="LatB">经度B</param>  
	/// <returns>距离（米）</returns> 
	public static double getDistance(LatLng llA,LatLng llB)  
	{  
		double lonA=llA.getLongitude();
		double latA=llA.getLatitude();
		double lonB=llB.getLongitude();
		double latB=llB.getLatitude();
		
		if(lonA==lonB && latA==latB) return 0.0;
	    // 东西经，南北纬处理，只在国内可以不处理(假设都是北半球，南半球只有澳洲具有应用意义)  
	    double MLonA = lonA;  
	    double MLatA = rad(latA);  
	    double MLonB = lonB;  
	    double MLatB = rad(latB);  
	    
	    double C = Math.sin(MLatA) * Math.sin(MLatB) + Math.cos(MLatA) * Math.cos(MLatB) * Math.cos(rad(MLonA - MLonB));
	    if(C>1){	//经纬度很接近时 C可能大于1 此时默认距离0
	    	C=1;
	    }
	    return (R * Math.acos(C))*KM;
	}  
	/**
	 * 计算速度 km/h
	 * @param distance
	 * @param timeSpan
	 * @return
	 */
	public static double calcSpeed(double distance,long timeSpan){
		final int h=1000*3600;
		if(Math.abs(timeSpan)==0){
			timeSpan=h;
		}
		return format((distance/1000)/(timeSpan*1.0/h),PREC_SPEED);
	}
	public static double calcAcceleration(double speed,long timeSpan){ 
		if(Math.abs(timeSpan)==0){
			timeSpan=1;
		}
		return format((speed*1000/3600)/(timeSpan*1.0/1000),PREC_ACCELERATION);
	}
	public static String getDirection(LatLng llA,LatLng llB){
		double lonA=llA.getLongitude();
		double latA=llA.getLatitude();
		double lonB=llB.getLongitude();
		double latB=llB.getLatitude();
		double interLat=latB-latA;
		double interLon=lonB-lonA;
		
		StringBuffer sb=new StringBuffer();
		if(interLon>0){
			sb.append("E");
			if(interLat>0){
				sb.append("N");
			}else if(interLat==0){
				sb.append("E");
			}else{
				sb.append("S");
			}
		}else if(interLon==0){
			if(interLat>0){
				sb.append("NN");
			}else{
				sb.append("SS");
			}
		}else{
			sb.append("W");
			if(interLat>0){
				sb.append("N");
			}else if(interLat==0){
				sb.append("W");
			}else{
				sb.append("S");
			}
		}
		return sb.toString();
	}
	public static int calcSlopeDegree(LatLng ll1,LatLng ll2){ 
		double latSpan=ll2.getLatitude()-ll1.getLatitude();
		double lonSpan=ll2.getLongitude()-ll1.getLongitude();
		if(Math.abs(lonSpan)<MIN_VALUE){	//如果经度差很小 则在当前精度规则内 设置7位精度
			lonSpan=(lonSpan<0?-1:1)*MIN_VALUE;
		}		 
		return (int)(format((Math.atan((latSpan)/(lonSpan))*180/Math.PI),PREC_DEGREE));
	}
	/**小数精度处理
	 * @param d 数值
	 * @param precision	精度
	 * @return
	 */
	public static double format(double d,int precision){
		NumberFormat nf = NumberFormat.getNumberInstance();  
        nf.setMaximumFractionDigits(precision); 
        return Double.valueOf(nf.format(d).replace(",", ""));
	}
	/**
	 * 计算经纬度相对赤道原点的弧度
	 * @param d
	 * @return
	 */
	private static double rad(double d)  
	{  
	    return d * Math.PI / 180.0;  
	}
	
	public static String getWeiduJinduConverter(String location){
		String[] jinduWeidus =  location.split(",");
		return jinduWeidus[1]+","+jinduWeidus[0];
	}
	public static List<List<Date>> dateSplit(Date startDate, Date endDate,int minutes){
		List<List<Date>> splitList = new ArrayList<List<Date>>();
		if(startDate.compareTo(endDate)==0){
			List<Date> list = new ArrayList<Date>();
			list.add(startDate);
			list.add(endDate);
			splitList.add(list);
			return splitList;
		}
		Date begin = startDate;
		while (begin.compareTo(endDate) < 0) {
			List<Date> list = new ArrayList<Date>();
			list.add(begin);
			list.add(DateUtils.addMinutes(begin, minutes));
			begin = DateUtils.addMinutes(begin, minutes);
			splitList.add(list);
		}
		return splitList;
	}
	
	public static List<List<Date>> dateSplitSecond(Date startDate, Date endDate,int seconds){
		List<List<Date>> splitList = new ArrayList<List<Date>>();
		if(startDate.compareTo(endDate)==0){
			List<Date> list = new ArrayList<Date>();
			list.add(startDate);
			list.add(endDate);
			splitList.add(list);
			return splitList;
		}
		Date begin = startDate;
		while (begin.compareTo(endDate) < 0) {
			List<Date> list = new ArrayList<Date>();
			list.add(begin);
			list.add(DateUtils.addSeconds(begin, seconds));
			begin = DateUtils.addSeconds(begin, seconds);
			splitList.add(list);
		}
		return splitList;
	}
	
	private final static int MAXDEVICENUM=1;//一次处理的设备个数
	
	public static List<List<Long>> getSplitsDeviceIds(List<Long> deviceIds) throws Exception{
		List<List<Long>> deviceIdSplits = new ArrayList<List<Long>>();
		for(Long deviceId:deviceIds){
			List<Long> deviceSplitList = null;
			if(deviceIdSplits.size()>0){
			deviceSplitList = deviceIdSplits.get(deviceIdSplits.size()-1);
			}else{
				deviceSplitList = new ArrayList<Long>();
				deviceIdSplits.add(deviceSplitList);
			}
			if(deviceSplitList.size()<MAXDEVICENUM){
				deviceSplitList.add(deviceId);
			}else{
				List<Long> deviceSplitListNew = new ArrayList<Long>();
				deviceSplitListNew.add(deviceId);
				deviceIdSplits.add(deviceSplitListNew);
			}
		}
		return deviceIdSplits;
	}
	
	public static void locationListSort(List<EfLocationSemi> cellList){
		Collections.sort(cellList,new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				EfLocationSemi e1 = (EfLocationSemi)o1;
				EfLocationSemi e2 = (EfLocationSemi)o2;
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
	
	public static void main(String[] a){
		LatLng llA=new LatLng("22.54892930,113.94337380");
		LatLng llB=new LatLng("22.549848,113.943876");
		SendObject so=new SendObject();
		so.setAmount("1");
		System.out.println(JsonUtils.fromJson(jsonStr, clazz);
		System.out.println(getMd5TokenKey(6697L,1483L));
		DoubleSpan s=new DoubleSpan("11.22~33.33");
		System.out.println(MD5Util.getMD5Format("ANVO"+"1373417"));
		
		
	}
	
	public static String getMd5TokenKey(Long userId,Long deviceId){
		if(null==userId) return null;
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append(Separator.vertical);
		if(null==deviceId){
			sb.append("");
		}else{
			sb.append(deviceId);
		}
		return MD5Util.getMD5Format(sb.toString().getBytes());
	}
}
