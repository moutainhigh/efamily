package com.winterframework.efamily.dto;

public class CustomerStatisticsDataInfo {

	private String  customerName;//客户名称
	private Integer customerNumber;	//客户编码
	private String  model;//手表型号
	
	private Integer bindDeviceData=0;//线上绑定设备数量
	private Integer unbindDeviceData=0;//线上解绑设备数量
	private Integer storageDeviceData=0;//入库的数据
	private Integer produceDeviceData=0;//已经生产数量  此数据由松果提供
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
	public Integer getBindDeviceData() {
		return bindDeviceData;
	}
	public void setBindDeviceData(Integer bindDeviceData) {
		this.bindDeviceData = bindDeviceData;
	}
	public Integer getUnbindDeviceData() {
		return unbindDeviceData;
	}
	public void setUnbindDeviceData(Integer unbindDeviceData) {
		this.unbindDeviceData = unbindDeviceData;
	}
	public Integer getStorageDeviceData() {
		return storageDeviceData;
	}
	public void setStorageDeviceData(Integer storageDeviceData) {
		this.storageDeviceData = storageDeviceData;
	}
	public Integer getProduceDeviceData() {
		return produceDeviceData;
	}
	public void setProduceDeviceData(Integer produceDeviceData) {
		this.produceDeviceData = produceDeviceData;
	}
	@Override
	public String toString() {
		return "CustomerStatisticsDataInfo [customerName=" + customerName
				+ ", customerNumber=" + customerNumber + ", model=" + model
				+ ", bindDeviceData=" + bindDeviceData + ", unbindDeviceData="
				+ unbindDeviceData + ", storageDeviceData=" + storageDeviceData
				+ ", produceDeviceData=" + produceDeviceData + "]";
	}
	
	
}
