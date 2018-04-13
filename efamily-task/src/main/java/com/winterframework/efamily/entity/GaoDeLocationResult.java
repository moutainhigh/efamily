package com.winterframework.efamily.entity;

public class GaoDeLocationResult {

	private int status;//返回结果状态值  值为0或1,0表示false；1表示true
	private String info;//返回状态说明 status为0时，info返回错误原因，否则返回“OK”。详情参阅info状态表
	private String infocode;
	private Regeocode regeocode;//逆地理编码列表 batch=true返回此标签，标签下为regeocode对象列表；batch=false直接返回regeocode对象；regeocode对象包含的数据如下
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getInfocode() {
		return infocode;
	}
	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}
	public Regeocode getRegeocode() {
		return regeocode;
	}
	public void setRegeocode(Regeocode regeocode) {
		this.regeocode = regeocode;
	}
	@Override
	public String toString() {
		return "GaoDeLocationResult [status=" + status + ", info=" + info
				+ ", infocode=" + infocode + ", regeocode=" + regeocode + "]";
	}
	
}
