package com.winterframework.efamily.notification.model;

import java.util.Map;

import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;

/**
 * 推送任务
 * @ClassName
 * @Description
 * @author ibm
 * 2015年9月4日
 */
public class NotyTask {
	private Long id;	//任务ID
	private NotyRule rule;	//推送规则
	private NotyTarget target;	//任务推送目标
	private int command;	//命令码
	private NotyMessage.Type notyType;	//消息类型
	private Map<String,String> Data;	//消息内容
	private Long taskTime;	//任务产生时间
	private boolean isOffline;	//是否离线发送过(终端使用，在线再推时标识消息已经离线推送过）
	private boolean isSentOffline;	//离线已经发送
	private String offlineTitle;	//离线推送消息标题
	private Map<String,String> offlineData;	//离线推送消息体内容
	
	public enum Status{
		UN_SEND(0),SENT(1),ARRIVED(2);
		private int _code;
		Status(int code){
			_code=code;
		}
		public int getCode(){
			return _code;
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NotyRule getRule() {
		return rule;
	}
	public void setRule(NotyRule rule) {
		this.rule = rule;
	}
	public NotyTarget getTarget() {
		return target;
	}
	public void setTarget(NotyTarget target) {
		this.target = target;
	}
	
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	
	public NotyMessage.Type getNotyType() {
		return notyType;
	}
	public void setNotyType(NotyMessage.Type notyType) {
		this.notyType = notyType;
	}
	public Map<String, String> getData() {
		return Data;
	}
	public void setData(Map<String, String> data) {
		Data = data;
	}
	public Long getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Long taskTime) {
		this.taskTime = taskTime;
	}
	public boolean isOffline() {
		return isOffline;
	}
	public void setOffline(boolean isOffline) {
		this.isOffline = isOffline;
	}
	public boolean isSentOffline() {
		return isSentOffline;
	}
	public void setSentOffline(boolean isSentOffline) {
		this.isSentOffline = isSentOffline;
	}
	public String getOfflineTitle() {
		return offlineTitle;
	}
	public void setOfflineTitle(String offlineTitle) {
		this.offlineTitle = offlineTitle;
	}
	public Map<String, String> getOfflineData() {
		return offlineData;
	}
	public void setOfflineData(Map<String, String> offlineData) {
		this.offlineData = offlineData;
	}
	
}
