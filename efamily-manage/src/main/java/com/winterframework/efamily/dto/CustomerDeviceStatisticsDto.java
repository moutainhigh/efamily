package com.winterframework.efamily.dto;

public class CustomerDeviceStatisticsDto {

	
	private String  customerName;//*** 客户名称
	private Integer customerNumber;	//*** 客户编号
	private Integer produceDeviceData;	//*** 已生产数量
	private String  model;//*** 设备型号
	private Integer statisticsData;//*** 统计数据
	private Integer status;//*** 状态   0解绑：1绑定
	
	public Integer getProduceDeviceData() {
		return produceDeviceData;
	}
	public void setProduceDeviceData(Integer produceDeviceData) {
		this.produceDeviceData = produceDeviceData;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(Integer customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getStatisticsData() {
		return statisticsData;
	}
	public void setStatisticsData(Integer statisticsData) {
		this.statisticsData = statisticsData;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CustomerDeviceStatisticsDto [customerName=" + customerName
				+ ", customerNumber=" + customerNumber + ", produceDeviceData="
				+ produceDeviceData + ", model=" + model + ", statisticsData="
				+ statisticsData + ", status=" + status + "]";
	}

}
