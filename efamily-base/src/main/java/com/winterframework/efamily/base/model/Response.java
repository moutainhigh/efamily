package com.winterframework.efamily.base.model;




public class  Response<T>{
	private Context ctx;	//上下文
	private Status status;
	public T data;
	
	public Response(){}
	public Response(Request req){
		ctx=req.getCtx();
	}
	public Long getUserId() {
		return ctx.getUserId();
	}
	public Long getDeviceId() {
		return ctx.getDeviceId();
	}
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public Context getCtx() {
		return ctx;
	}
	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

}
