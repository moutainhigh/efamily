package com.winterframework.efamily.base.model;


public class  Request<T>{
	private Context ctx=new Context();	//上下文
	public T data;

	public Long getUserId() {
		return null==ctx?null:ctx.getUserId();
	}
	public Long getDeviceId() {
		return null==ctx?null:ctx.getDeviceId();
	}
	public Context getCtx() {
		return ctx;
	}
	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
