package com.winterframework.efamily.dto.device.param;

import java.lang.reflect.Field;

public class DeviceParamCommon {
		private Integer bright;	//亮度（百分比=设置值/最大值）
		private Integer sound;	//声音（百分比=设置值/最大值）
		private Integer shake;	//振动模式
		private Integer quiet;	//静音模式
		private String[] powerAuto;	//自动开关机
		private String version;	//软件版本
		private Integer locationOnff;	//定位开关 
		private Integer heartOnff;	//心率开关 
		private Integer walkOnff;	//计步开关 
		private Integer sittingOnff;	//久坐开关
		private Integer sleepOnff;	//睡眠开关
		private String timezone;	//时区
		
		public static void coverNewValue(DeviceParamCommon oldObj,DeviceParamCommon newObj){
			if(newObj.getBright()!=null){
				oldObj.setBright(newObj.getBright());
			}
			if(newObj.getSound()!=null){
				oldObj.setSound(newObj.getSound());
			}
			if(newObj.getShake()!=null){
				oldObj.setShake(newObj.getShake());
			}
			if(newObj.getQuiet()!=null){
				oldObj.setQuiet(newObj.getQuiet());
			}
			if(newObj.getPowerAuto()!=null){
				oldObj.setPowerAuto(newObj.getPowerAuto());
			}
			if(newObj.getVersion()!=null){
				oldObj.setVersion(newObj.getVersion());
			}
			if(newObj.getLocationOnff()!=null){
				oldObj.setLocationOnff(newObj.getLocationOnff());
			}
			if(newObj.getHeartOnff()!=null){
				oldObj.setHeartOnff(newObj.getHeartOnff());
			}
			if(newObj.getWalkOnff()!=null){
				oldObj.setWalkOnff(newObj.getWalkOnff());
			}
			
			if(newObj.getSittingOnff()!=null){
				oldObj.setSittingOnff(newObj.getSittingOnff());
			}
			if(newObj.getSleepOnff()!=null){
				oldObj.setSleepOnff(newObj.getSleepOnff());
			}
			if(newObj.getTimezone()!=null){
				oldObj.setTimezone(newObj.getTimezone());
			}
		}
		public Integer getBright() {
			return bright;
		}
		public void setBright(Integer bright) {
			this.bright = bright;
		}
		public Integer getSound() {
			return sound;
		}
		public void setSound(Integer sound) {
			this.sound = sound;
		}
		public Integer getShake() {
			return shake;
		}
		public void setShake(Integer shake) {
			this.shake = shake;
		}
		public Integer getQuiet() {
			return quiet;
		}
		public void setQuiet(Integer quiet) {
			this.quiet = quiet;
		}
		  
		public String[] getPowerAuto() {
			return powerAuto;
		}
		public void setPowerAuto(String[] powerAuto) {
			this.powerAuto = powerAuto;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public Integer getLocationOnff() {
			return locationOnff;
		}
		public void setLocationOnff(Integer locationOnff) {
			this.locationOnff = locationOnff;
		}
		public Integer getHeartOnff() {
			return heartOnff;
		}
		public void setHeartOnff(Integer heartOnff) {
			this.heartOnff = heartOnff;
		}
		public Integer getWalkOnff() {
			return walkOnff;
		}
		public void setWalkOnff(Integer walkOnff) {
			this.walkOnff = walkOnff;
		}
		public Integer getSittingOnff() {
			return sittingOnff;
		}
		public void setSittingOnff(Integer sittingOnff) {
			this.sittingOnff = sittingOnff;
		}
		public Integer getSleepOnff() {
			return sleepOnff;
		}
		public void setSleepOnff(Integer sleepOnff) {
			this.sleepOnff = sleepOnff;
		}
		public String getTimezone() {
			return timezone;
		}
		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}
		
		
}
