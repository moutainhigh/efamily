package com.winterframework.efamily.web.intercept;

import java.util.Locale;
import java.util.ResourceBundle;


public class ResourceInteration {

	   public static String getMessage(String key, Locale locale)
	    {
	        
	       /* ResourceBundle resourceBundle = ResourceBundle.getBundle(Global.RESOURCES_PATH,
	                locale);
	        
	        if (resourceBundle.containsKey(key))
	        {
	            return resourceBundle.getString(key);
	        }*/
	        
	        return key;
	    } 
	
	
	
}
