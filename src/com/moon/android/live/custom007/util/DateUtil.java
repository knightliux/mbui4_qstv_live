package com.moon.android.live.custom007.util;

public class DateUtil {

	public static String second2Hour(int seconds) {
		String retStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if(seconds <= 0) {
            return "00:00:00";
		}
		else {
			minute = seconds/60;
			if(minute < 60) {
				second = seconds%60;
				retStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			}
			else {
				hour = minute/60;
				if (hour > 99)
					return "99:59:59";
				minute = minute%60;
				second = seconds%60;
				retStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return retStr;
	}
	
	private static String unitFormat(int i) {
		String retStr = null;
		if(i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = Integer.toString(i);
		return retStr;
    }
}
