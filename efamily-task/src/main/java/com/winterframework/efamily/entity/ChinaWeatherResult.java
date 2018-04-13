package com.winterframework.efamily.entity;

public class ChinaWeatherResult {
	private ChinaWeather weather;
	private String temperature;
	private String ganmao;
	private String pollution;
	private String dress;

	public String getGanmao() {
		return ganmao;
	}

	public void setGanmao(String ganmao) {
		this.ganmao = ganmao;
	}

	public String getPollution() {
		return pollution;
	}

	public void setPollution(String pollution) {
		this.pollution = pollution;
	}

	public String getDress() {
		return dress;
	}

	public void setDress(String dress) {
		this.dress = dress;
	}


	public ChinaWeather getWeather() {
		return weather;
	}

	public void setWeather(ChinaWeather weather) {
		this.weather = weather;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

}
