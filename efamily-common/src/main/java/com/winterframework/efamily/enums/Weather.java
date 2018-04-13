package com.winterframework.efamily.enums;

public enum Weather {
	SUNNY("sunny"), 
	RAINY("rainy"),
	SNOW("snow");
	 
	private String _value;
	Weather(String value){
		this._value=value;
	}
	public String getValue(){
		return _value;
	}
	public enum Wind {
		WEAK("weak"), 
		MIDDLE("middle"),
		STRONG("strong");
		 
		private String _value;
		Wind(String value){
			this._value=value;
		}
		public String getValue(){
			return _value;
		}
	}
	public enum Dress {
		SUMMER("summer"), 
		SPRING("spring"),
		WINTER("winter"),
		COLD("cold");
		 
		private String _value;
		Dress(String value){
			this._value=value;
		}
		public String getValue(){
			return _value;
		}
	}
}
