package com.winterframework.efamily.dto.device;


public class DeviceChatSettingRequest{
	private String audio;	//提示音
	private int volume;	//音量
	//private String bubble;	//气泡
	//private String image;	//背景图
	
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

	
}
