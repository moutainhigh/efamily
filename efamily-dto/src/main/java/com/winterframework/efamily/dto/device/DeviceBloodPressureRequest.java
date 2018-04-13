package com.winterframework.efamily.dto.device;


/**
 * 血压
 * @ClassName
 * @Description
 * @author ibm
 * 2016年8月22日
 */
public class DeviceBloodPressureRequest{
	private Integer high;
	private Integer low;
	private Long fromTime;
	private Long toTime;
	private Integer rate;
	
	public Long getFromTime() {
		return fromTime;
	}
	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}
	public Long getToTime() {
		return toTime;
	}
	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}
	public Integer getHigh() {
		return high;
	}
	public void setHigh(Integer high) {
		this.high = high;
	}
	public Integer getLow() {
		return low;
	}
	public void setLow(Integer low) {
		this.low = low;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	
}
