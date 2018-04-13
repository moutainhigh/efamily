package com.winterframework.efamily.dto;

import java.util.HashMap;
import java.util.Map;

public class UpdateWatchOwnerResponse { 

	private Map<String, String> paramMapResult = new HashMap<String,String>();

	public Map<String, String> getParamMapResult() {
		return paramMapResult;
	}

	public void setParamMapResult(Map<String, String> paramMapResult) {
		this.paramMapResult = paramMapResult;
	}
	
	
}