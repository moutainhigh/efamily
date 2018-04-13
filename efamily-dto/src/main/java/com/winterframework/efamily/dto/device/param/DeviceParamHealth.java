package com.winterframework.efamily.dto.device.param;

public class DeviceParamHealth {
		private Integer sex;	//性别
		private Integer age;	//年龄
		private Integer height;	//身高
		private Double weight;	//体重
		private Integer arm;	//臂长
		private Integer heartTime;	//测量心率时长（单位：秒）
		private Integer sittingTime;	//久坐时间（单位：分钟）
		private String[] sittingSpan;	//久坐监测时间区间
		private String[] sleepSpan;	//睡眠监测时间区间
		
		public static void coverNewValue(DeviceParamHealth oldObj,DeviceParamHealth newObj){
			if(newObj.getSex()!=null){
				oldObj.setSex(newObj.getSex());
			}
			if(newObj.getAge()!=null){
				oldObj.setAge(newObj.getAge());
			}
			if(newObj.getHeight()!=null){
				oldObj.setHeight(newObj.getHeight());
			}
			if(newObj.getWeight()!=null){
				oldObj.setWeight(newObj.getWeight());
			}
			if(newObj.getArm()!=null){
				oldObj.setArm(newObj.getArm());
			}
			if(newObj.getHeartTime()!=null){
				oldObj.setHeartTime(newObj.getHeartTime());
			}
			if(newObj.getSittingTime()!=null){
				oldObj.setSittingTime(newObj.getSittingTime());
			}
			if(newObj.getSittingSpan()!=null){
				oldObj.setSittingSpan(newObj.getSittingSpan());
			}
			if(newObj.getSleepSpan()!=null){
				oldObj.setSleepSpan(newObj.getSleepSpan());
			}
		}
		
		public Integer getSex() {
			return sex;
		}
		public void setSex(Integer sex) {
			this.sex = sex;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public Integer getHeight() {
			return height;
		}
		public void setHeight(Integer height) {
			this.height = height;
		}
		public Double getWeight() {
			return weight;
		}
		public void setWeight(Double weight) {
			this.weight = weight;
		}
		
		public Integer getArm() {
			return arm;
		}
		public void setArm(Integer arm) {
			this.arm = arm;
		}
		
		public Integer getHeartTime() {
			return heartTime;
		}
		public void setHeartTime(Integer heartTime) {
			this.heartTime = heartTime;
		}
		public Integer getSittingTime() {
			return sittingTime;
		}
		public void setSittingTime(Integer sittingTime) {
			this.sittingTime = sittingTime;
		} 
		
		public String[] getSittingSpan() {
			return sittingSpan;
		}
		public void setSittingSpan(String[] sittingSpan) {
			this.sittingSpan = sittingSpan;
		}
		public String[] getSleepSpan() {
			return sleepSpan;
		}
		public void setSleepSpan(String[] sleepSpan) {
			this.sleepSpan = sleepSpan;
		}
		
}
