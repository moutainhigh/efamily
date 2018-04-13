package com.winterframework.efamily.dto.device;




/**
 * 设备农历请求对象
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月9日
 */
public class DeviceLunarRequest{
	private String date;	//日期
	private String week;	//星期
	private String lunar;	//农历
	private String ganzhi;	//干支
	private String zodiac;	//生肖
	private String fitavoid;	//宜忌
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getLunar() {
		return lunar;
	}
	public void setLunar(String lunar) {
		this.lunar = lunar;
	}
	public String getGanzhi() {
		return ganzhi;
	}
	public void setGanzhi(String ganzhi) {
		this.ganzhi = ganzhi;
	}
	public String getZodiac() {
		return zodiac;
	}
	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}
	public String getFitavoid() {
		return fitavoid;
	}
	public void setFitavoid(String fitavoid) {
		this.fitavoid = fitavoid;
	}


	
}
