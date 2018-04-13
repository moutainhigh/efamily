package com.winterframework.efamily.enums;

public enum Lunar {
	Lunar();
	public enum Zodiac {
		RAT("rat"), 
		OX("ox"),
		TIGGER("tigger"),
		HARE("hare"),
		DRAGON("dragon"),
		SNAKE("snake"),
		HORSE("horse"),
		SHEEP("sheep"),
		MONKEY("monkey"),
		COCK("cock"),
		DOG("dog"),
		BOAR("boar");
		 
		private String _value;
		Zodiac(String value){
			this._value=value;
		}
		public String getValue(){
			return _value;
		}
	}
	public enum Week {
		MONDAY("monday"), 
		TUESDAY("tuesday"),
		WEDNESDAY("wednesday"),
		THURSDAY("thursday"),
		FRIDAY("friday"),
		SATURDAY("saturday"),
		SUNDAY("sunday");
		 
		private String _value;
		Week(String value){
			this._value=value;
		}
		public String getValue(){
			return _value;
		}
	}
}
