package com.winterframework.efamily.shardStrategy;

import java.util.Date;
import java.util.Map;

import com.google.code.shardbatis.strategy.ShardStrategy;
import com.winterframework.efamily.base.utils.DateUtils;
public class ShardStrategyImpl  implements ShardStrategy{
	private static int type=2;//1 week 2month
	@Override
	public String getTargetTableName(String arg0, Object arg1, String arg2) {
		String i="";
		if(arg1!=null){
			if(arg1 instanceof Map){
				Map ad = (Map)arg1;
			Object timeObject = ad.get("time")==null?ad.get("fromTime")==null?ad.get("startQueryTime"):ad.get("fromTime"):ad.get("time");
			if(timeObject==null){
				return arg0;
			} 
			if(timeObject instanceof Date){
				Date time = (Date)timeObject;
				i = getTableName( arg0,  time);
			}else if(timeObject instanceof Long){
				Date time = DateUtils.convertLong2Date((Long)timeObject);
				i = getTableName( arg0,  time);
			}
			return i;
			}
		}
		return arg0;
	}
	
	
	public String getTableName(String arg0, Date time){
		String tableName=arg0;
		if(type==2){
			if(DateUtils.getYear(DateUtils.currentDate())!=DateUtils.getYear(time)||DateUtils.getMonth(DateUtils.currentDate())!=DateUtils.getMonth(time)){
				   String dateString = DateUtils.format(time);
				   String[] dateArray = dateString.split("-");
				   tableName+="_"+dateArray[0]+dateArray[1];
				}
		}else if(type ==1){
			if(DateUtils.getYear(DateUtils.currentDate())!=DateUtils.getYear(time)||DateUtils.getMonth(DateUtils.currentDate())!=DateUtils.getMonth(time)
					||DateUtils.getDate(DateUtils.currentDate())==DateUtils.getDate(time)){
				   String dateString = DateUtils.format(time);
				   String[] dateArray = dateString.split("-");
				   tableName+="_"+dateArray[0]+dateArray[1]+DateUtils.getDate(time);
				}
		}
		return tableName;
	}
}
