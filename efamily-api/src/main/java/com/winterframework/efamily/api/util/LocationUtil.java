package com.winterframework.efamily.api.util;


public class LocationUtil {
	public static double getLogitude(String location){
		return Double.valueOf(location.split(",")[1]);
	}
	public static double getLatitude(String location){
		return Double.valueOf(location.split(",")[0]);
	}
	public static void main(String[] a){
		
	}
}
