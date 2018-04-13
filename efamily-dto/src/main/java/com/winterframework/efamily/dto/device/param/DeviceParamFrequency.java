package com.winterframework.efamily.dto.device.param;

public class DeviceParamFrequency {
		private Integer signalGfreq;	//信号采集频率
		private Integer signalUfreq;	//信号上传频率
		private Integer batteryGfreq;	//电量采集频率
		private Integer batteryUfreq;	//电量上传频率
		private Integer heartGfreq;	//心率采集频率
		private Integer heartUfreq;	//心率上传频率
		private Integer walkUfreq;	//计步上传频率
		private Integer locationGfreq;	//定位采集频率
		private Integer locationUfreq;	//定位上传频率
		
		public static void coverNewValue(DeviceParamFrequency oldObj,DeviceParamFrequency newObj){
			if(newObj.getSignalGfreq()!=null){
				oldObj.setSignalGfreq(newObj.getSignalGfreq());
			}
			if(newObj.getSignalUfreq()!=null){
				oldObj.setSignalUfreq(newObj.getSignalUfreq());
			}
			if(newObj.getBatteryGfreq()!=null){
				oldObj.setBatteryGfreq(newObj.getBatteryGfreq());
			}
			if(newObj.getBatteryUfreq()!=null){
				oldObj.setBatteryUfreq(newObj.getBatteryUfreq());
			}
			if(newObj.getHeartGfreq()!=null){
				oldObj.setHeartGfreq(newObj.getHeartGfreq());
			}
			if(newObj.getHeartUfreq()!=null){
				oldObj.setHeartUfreq(newObj.getHeartUfreq());
			}
			if(newObj.getWalkUfreq()!=null){
				oldObj.setWalkUfreq(newObj.getWalkUfreq());
			}
			if(newObj.getLocationGfreq()!=null){
				oldObj.setLocationGfreq(newObj.getLocationGfreq());
			}
			if(newObj.getLocationUfreq()!=null){
				oldObj.setLocationUfreq(newObj.getLocationUfreq());
			}
		}
		
		public Integer getSignalGfreq() {
			return signalGfreq;
		}
		public void setSignalGfreq(Integer signalGfreq) {
			this.signalGfreq = signalGfreq;
		}
		public Integer getSignalUfreq() {
			return signalUfreq;
		}
		public void setSignalUfreq(Integer signalUfreq) {
			this.signalUfreq = signalUfreq;
		}
		public Integer getBatteryGfreq() {
			return batteryGfreq;
		}
		public void setBatteryGfreq(Integer batteryGfreq) {
			this.batteryGfreq = batteryGfreq;
		}
		public Integer getBatteryUfreq() {
			return batteryUfreq;
		}
		public void setBatteryUfreq(Integer batteryUfreq) {
			this.batteryUfreq = batteryUfreq;
		}
		public Integer getHeartGfreq() {
			return heartGfreq;
		}
		public void setHeartGfreq(Integer heartGfreq) {
			this.heartGfreq = heartGfreq;
		}
		public Integer getHeartUfreq() {
			return heartUfreq;
		}
		public void setHeartUfreq(Integer heartUfreq) {
			this.heartUfreq = heartUfreq;
		}
		public Integer getWalkUfreq() {
			return walkUfreq;
		}
		public void setWalkUfreq(Integer walkUfreq) {
			this.walkUfreq = walkUfreq;
		}
		public Integer getLocationGfreq() {
			return locationGfreq;
		}
		public void setLocationGfreq(Integer locationGfreq) {
			this.locationGfreq = locationGfreq;
		}
		public Integer getLocationUfreq() {
			return locationUfreq;
		}
		public void setLocationUfreq(Integer locationUfreq) {
			this.locationUfreq = locationUfreq;
		}
		

		
		
}
