
package com.winterframework.efamily.base.utils;

import java.util.UUID;

public class KeyUtil
{
	public static String getKey(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] s){
		System.out.println(getKey());
	}
}