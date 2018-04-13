/**   
* @Title: DeviceParamStruc.java 
* @Package com.winterframework.efamily.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-21 上午11:02:53 
* @version V1.0   
*/
package com.winterframework.efamily.dto;

import java.io.Serializable;

/** 
* @ClassName: DeviceParamStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-21 上午11:02:53 
*  
*/
public class DeviceParamStruc implements Serializable{
	private static final long serialVersionUID = 232121312312l;
	private String connect_beat;
	private String connect_timeout;
	private String net_retry;
	private String net_repeat;
	private String bright_max;
	private String bright;
	private String sound_max;
	private String sound;
	private String signal_max;
	private String signal_upload_freq;
	private String signal;
	private String battery_max;
	private String battery_upload_freq;
	private String location_onff;
	private String location_gather_freq;
	private String location_upload_freq;
	private String health_heart_onff;
	private String health_heart_gather_freq;
	private String health_heart_upload_freq;
	private String health_walk_onff;
	private String health_walk_upload_freq;
	private String vibrate_onff;
	private String silent_onff;
	public String getConnect_beat() {
		return connect_beat;
	}
	public void setConnect_beat(String connect_beat) {
		this.connect_beat = connect_beat;
	}
	public String getConnect_timeout() {
		return connect_timeout;
	}
	public void setConnect_timeout(String connect_timeout) {
		this.connect_timeout = connect_timeout;
	}
	public String getNet_retry() {
		return net_retry;
	}
	public void setNet_retry(String net_retry) {
		this.net_retry = net_retry;
	}
	public String getNet_repeat() {
		return net_repeat;
	}
	public void setNet_repeat(String net_repeat) {
		this.net_repeat = net_repeat;
	}
	public String getBright_max() {
		return bright_max;
	}
	public void setBright_max(String bright_max) {
		this.bright_max = bright_max;
	}
	public String getBright() {
		return bright;
	}
	public void setBright(String bright) {
		this.bright = bright;
	}
	public String getSound_max() {
		return sound_max;
	}
	public void setSound_max(String sound_max) {
		this.sound_max = sound_max;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getSignal_max() {
		return signal_max;
	}
	public void setSignal_max(String signal_max) {
		this.signal_max = signal_max;
	}
	public String getSignal_upload_freq() {
		return signal_upload_freq;
	}
	public void setSignal_upload_freq(String signal_upload_freq) {
		this.signal_upload_freq = signal_upload_freq;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getBattery_max() {
		return battery_max;
	}
	public void setBattery_max(String battery_max) {
		this.battery_max = battery_max;
	}
	public String getBattery_upload_freq() {
		return battery_upload_freq;
	}
	public void setBattery_upload_freq(String battery_upload_freq) {
		this.battery_upload_freq = battery_upload_freq;
	}
	public String getLocation_onff() {
		return location_onff;
	}
	public void setLocation_onff(String location_onff) {
		this.location_onff = location_onff;
	}
	public String getLocation_gather_freq() {
		return location_gather_freq;
	}
	public void setLocation_gather_freq(String location_gather_freq) {
		this.location_gather_freq = location_gather_freq;
	}
	public String getLocation_upload_freq() {
		return location_upload_freq;
	}
	public void setLocation_upload_freq(String location_upload_freq) {
		this.location_upload_freq = location_upload_freq;
	}
	public String getHealth_heart_onff() {
		return health_heart_onff;
	}
	public void setHealth_heart_onff(String health_heart_onff) {
		this.health_heart_onff = health_heart_onff;
	}
	public String getHealth_heart_gather_freq() {
		return health_heart_gather_freq;
	}
	public void setHealth_heart_gather_freq(String health_heart_gather_freq) {
		this.health_heart_gather_freq = health_heart_gather_freq;
	}
	public String getHealth_heart_upload_freq() {
		return health_heart_upload_freq;
	}
	public void setHealth_heart_upload_freq(String health_heart_upload_freq) {
		this.health_heart_upload_freq = health_heart_upload_freq;
	}
	public String getHealth_walk_onff() {
		return health_walk_onff;
	}
	public void setHealth_walk_onff(String health_walk_onff) {
		this.health_walk_onff = health_walk_onff;
	}
	public String getHealth_walk_upload_freq() {
		return health_walk_upload_freq;
	}
	public void setHealth_walk_upload_freq(String health_walk_upload_freq) {
		this.health_walk_upload_freq = health_walk_upload_freq;
	}
	public String getVibrate_onff() {
		return vibrate_onff;
	}
	public void setVibrate_onff(String vibrate_onff) {
		this.vibrate_onff = vibrate_onff;
	}
	public String getSilent_onff() {
		return silent_onff;
	}
	public void setSilent_onff(String silent_onff) {
		this.silent_onff = silent_onff;
	}
}
