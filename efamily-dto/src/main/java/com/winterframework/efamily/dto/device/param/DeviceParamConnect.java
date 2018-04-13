package com.winterframework.efamily.dto.device.param;

public class DeviceParamConnect {
		private Integer beat;	//心跳频率
		private Integer timeout;	//连网超时
		private Integer retry;	//连网重试次数
		private Integer restart;	//网络重启次数
		
		public static void coverNewValue(DeviceParamConnect oldObj,DeviceParamConnect newObj){
			if(newObj.getBeat()!=null){
				oldObj.setBeat(newObj.getBeat());
			}
			if(newObj.getTimeout()!=null){
				oldObj.setTimeout(newObj.getTimeout());
			}
			if(newObj.getRetry()!=null){
				oldObj.setRetry(newObj.getRetry());
			}
			if(newObj.getRestart()!=null){
				oldObj.setRestart(newObj.getRestart());
			}
		}
		public Integer getBeat() {
			return beat;
		}
		public void setBeat(Integer beat) {
			this.beat = beat;
		}
		public Integer getTimeout() {
			return timeout;
		}
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		public Integer getRetry() {
			return retry;
		}
		public void setRetry(Integer retry) {
			this.retry = retry;
		}
		public Integer getRestart() {
			return restart;
		}
		public void setRestart(Integer restart) {
			this.restart = restart;
		}
		
		
		
}
