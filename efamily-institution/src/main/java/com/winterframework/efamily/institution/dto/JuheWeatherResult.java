package com.winterframework.efamily.institution.dto;


public class JuheWeatherResult<T> {
	 
	private WeatherData<T> data;

	public WeatherData<T> getData() {
		return data;
	}

	public void setData(WeatherData<T> data) {
		this.data = data;
	}
}
