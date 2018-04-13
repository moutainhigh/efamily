package com.winterframework.efamily.dto;

import java.util.List;

/** 
* @ClassName: HealthyMonitorDataSummaryStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午2:57:45 
*  
*/
public class HealthyMonitorDataStruc {
	private List<HealthyMonitorDataUnitStruc> unitDatas;
	private Long totalTime;
	
	private long topValue;
	private long bottomValue;
	private long middleValue;
	
	private Long rateRangeLt;
	private Long rateRangeGt;
	public List<HealthyMonitorDataUnitStruc> getUnitDatas() {
		return unitDatas;
	}

	public void setUnitDatas(List<HealthyMonitorDataUnitStruc> unitDatas) {
		this.unitDatas = unitDatas;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public long getTopValue() {
		return topValue;
	}

	public void setTopValue(long topValue) {
		this.topValue = topValue;
	}

	public long getBottomValue() {
		return bottomValue;
	}

	public void setBottomValue(long bottomValue) {
		this.bottomValue = bottomValue;
	}

	public long getMiddleValue() {
		return middleValue;
	}

	public void setMiddleValue(long middleValue) {
		this.middleValue = middleValue;
	}

	public Long getRateRangeLt() {
		return rateRangeLt;
	}

	public void setRateRangeLt(Long rateRangeLt) {
		this.rateRangeLt = rateRangeLt;
	}

	public Long getRateRangeGt() {
		return rateRangeGt;
	}

	public void setRateRangeGt(Long rateRangeGt) {
		this.rateRangeGt = rateRangeGt;
	}
	
	
	
}
