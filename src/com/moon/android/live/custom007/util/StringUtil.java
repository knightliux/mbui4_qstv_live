package com.moon.android.live.custom007.util;

public class StringUtil {
	
	
	public static final String NULL_STRING = "";
	
	/**
	 * is null or is ""
	 * @param parameter
	 * @return
	 */
	public static boolean isBlank(String parameter){
		return (null == parameter) || ("".equals(parameter.trim()));
	}
}
