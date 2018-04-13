package com.winterframework.efamily.utils;

import java.io.Serializable;

/** SendObject概要说明：商户给平台的参数对象
 * <br>@author Administrator
 */
public class SendObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String order = "";
	private String transtime = "";
	private String amount = "";
	private String productcategory = "";
	private String productname = "";
	private String productdesc = "";
	private String productprice = "";
	private String productcount = "";
	private String merrmk = "";
	private String userua = "";
	private String userip = "";
	private String areturl = "";
	private String sreturl = "";
	private String orderSubOpenid = "";
	private String pnc = "";
	private String authCode = "";
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProductcategory() {
		return productcategory;
	}
	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	public String getProductprice() {
		return productprice;
	}
	public void setProductprice(String productprice) {
		this.productprice = productprice;
	}
	public String getProductcount() {
		return productcount;
	}
	public void setProductcount(String productcount) {
		this.productcount = productcount;
	}
	public String getMerrmk() {
		return merrmk;
	}
	public void setMerrmk(String merrmk) {
		this.merrmk = merrmk;
	}
	public String getUserua() {
		return userua;
	}
	public void setUserua(String userua) {
		this.userua = userua;
	}
	public String getUserip() {
		return userip;
	}
	public void setUserip(String userip) {
		this.userip = userip;
	}
	public String getAreturl() {
		return areturl;
	}
	public void setAreturl(String areturl) {
		this.areturl = areturl;
	}
	public String getSreturl() {
		return sreturl;
	}
	public void setSreturl(String sreturl) {
		this.sreturl = sreturl;
	}
	public String getOrderSubOpenid() {
		return orderSubOpenid;
	}
	public void setOrderSubOpenid(String orderSubOpenid) {
		this.orderSubOpenid = orderSubOpenid;
	}
	public String getPnc() {
		return pnc;
	}
	public void setPnc(String pnc) {
		this.pnc = pnc;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	
}
