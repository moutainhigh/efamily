package com.winterframework.efamily.enums;

public enum DeviceOnffAdapter {
	MOVE_ONFF(400022);
	private int _code;
	DeviceOnffAdapter(int code){
		this._code=code;
	}
	public int getCode() {
		return _code;
	} 
	public static String get(int code,String value){
		for(DeviceOnffAdapter param:DeviceOnffAdapter.values()){
			if(code==param.getCode()){
				return Integer.valueOf(value)==0?"1":"0";
			}
		}
		return value;
	}
}
