package com.winterframework.efamily.dto.device;



/**
 * 设备定位星信噪比请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceLocationSatelliteRequest{
	private int count;	//参与定位卫星数
	private int rate;	//星信噪比
	private Long time;	//时间
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
}
