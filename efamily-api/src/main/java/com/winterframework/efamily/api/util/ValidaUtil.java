package com.winterframework.efamily.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaUtil {
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][0-9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	public static void main(String[] s){
		System.out.println(isMobile("13567890987"));
		System.out.println(isMobile("17267890987"));
	}

}
