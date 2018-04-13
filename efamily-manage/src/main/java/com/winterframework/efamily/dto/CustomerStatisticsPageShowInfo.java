package com.winterframework.efamily.dto;

import java.util.Map;
import java.util.TreeMap;

public class CustomerStatisticsPageShowInfo {

	private Map<String,CustomerStatisticsDataInfo> customerStatisticsDataInfoMap = new TreeMap<String,CustomerStatisticsDataInfo>();
	private Integer bindDeviceDataTotal=0;//线上绑定设备总数量
	private Integer unbindDeviceDataTotal=0;//线上解绑设备总数量
	private Integer storageDeviceDataTotal=0;//入库的总数量
	private Integer produceDeviceDataTotal=0;//已生产的总数量
	
	public Integer addBindDeviceDataTotal(Integer bindDeviceData){
		return bindDeviceDataTotal+=bindDeviceData;
	}
	
	public Integer addUnbindDeviceDataTotal(Integer unbindDeviceData){
		return unbindDeviceDataTotal+=unbindDeviceData;
	}
	
	public Integer addStorageDeviceDataTotal(Integer storageDeviceData){
		return storageDeviceDataTotal+=storageDeviceData;
	}

	public Integer addProduceDeviceDataTotal(Integer produceDeviceData){
		return produceDeviceDataTotal+=produceDeviceData;
	}
	
	public Integer getProduceDeviceDataTotal() {
		return produceDeviceDataTotal;
	}

	public void setProduceDeviceDataTotal(Integer produceDeviceDataTotal) {
		this.produceDeviceDataTotal = produceDeviceDataTotal;
	}

	public Map<String, CustomerStatisticsDataInfo> getCustomerStatisticsDataInfoMap() {
		return customerStatisticsDataInfoMap;
	}
	public void setCustomerStatisticsDataInfoMap(
			Map<String, CustomerStatisticsDataInfo> customerStatisticsDataInfoMap) {
		this.customerStatisticsDataInfoMap = customerStatisticsDataInfoMap;
	}
	public Integer getBindDeviceDataTotal() {
		return bindDeviceDataTotal;
	}
	public void setBindDeviceDataTotal(Integer bindDeviceDataTotal) {
		this.bindDeviceDataTotal = bindDeviceDataTotal;
	}
	public Integer getUnbindDeviceDataTotal() {
		return unbindDeviceDataTotal;
	}
	public void setUnbindDeviceDataTotal(Integer unbindDeviceDataTotal) {
		this.unbindDeviceDataTotal = unbindDeviceDataTotal;
	}
	public Integer getStorageDeviceDataTotal() {
		return storageDeviceDataTotal;
	}
	public void setStorageDeviceDataTotal(Integer storageDeviceDataTotal) {
		this.storageDeviceDataTotal = storageDeviceDataTotal;
	}

	@Override
	public String toString() {
		return "CustomerStatisticsPageShowInfo [customerStatisticsDataInfoMap="
				+ customerStatisticsDataInfoMap + ", bindDeviceDataTotal="
				+ bindDeviceDataTotal + ", unbindDeviceDataTotal="
				+ unbindDeviceDataTotal + ", storageDeviceDataTotal="
				+ storageDeviceDataTotal + ", produceDeviceDataTotal="
				+ produceDeviceDataTotal + "]";
	}

	
	
}
