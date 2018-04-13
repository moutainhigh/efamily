 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * 定位处理辅助表
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月8日
 */
public class EfLocationAssist extends FmlBaseEntity implements Cloneable{
	  
	//date formats
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -2730712346707614290L;
	//columns START
	private Long locationId;	//坐标ID
	private Double speed;	//速度
	private Double acceleration;	//加速度
	private String direction;	//方向(NN EN EE ES SS WS WW WN)
	private Integer slopeDegree;	//斜率角度数
	private Integer moveMode;	//移动模式

	public enum Acceleration {
		//加速度
		WALK()
	}
	public enum MoveMode {
		
		//步行速度 1~5km/h		0.21	
		//骑行速度 8~12km/h		0.7
		//公车速度 30~130km/h	2.3
		//火车速度 100~300km/h	13.9
		//飞机速度 500~1100km/h
		STAY(0,0,1,0.01),WALK(1,3,2,0.05),BIKE(2,10,5,0.15),BUS(3,70,55,1.1),TRAIN(4,225,100,3.0),PLANE(5,800,480,11.95);
		
		private int _v;	//模式值
		private int _s;	//模式速度
		private int _p;	//模式精度
		private double _a;	//模式加速度	m/s30s
		MoveMode(int v,int s,int p,double a){
			_v=v;
			_s=s;
			_p=p;
			_a=a;
		}
		public int getValue(){
			return _v;
		}
		public int getSpeed(){
			return _s;
		}
		public int getPrecision(){
			return _p;
		}
		public double getAcceleration(){
			return _a;
		}
		public static MoveMode getMoveMode(int value){
			for(MoveMode moveMode:MoveMode.values()){
				if(value==moveMode.getValue()){
					return moveMode;
				}
			}
			return MoveMode.STAY;
		} 
	}
	//columns END
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Integer getSlopeDegree() {
		return slopeDegree;
	}
	public void setSlopeDegree(Integer slopeDegree) {
		this.slopeDegree = slopeDegree;
	}
	public Integer getMoveMode() {
		return moveMode;
	}
	public void setMoveMode(Integer moveMode) {
		this.moveMode = moveMode;
	}	
	 
	public Double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(Double acceleration) {
		this.acceleration = acceleration;
	}
	@Override
	public EfLocationAssist clone() throws CloneNotSupportedException {
		return (EfLocationAssist)super.clone();
	}
	
}

