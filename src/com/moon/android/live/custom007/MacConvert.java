package com.moon.android.live.custom007;

import java.util.HashMap;
import java.util.Map;

public class MacConvert {

	public static String convertMac(String mac) {
		if (mac.length() != 12)
			return null;

		String s1 = mac.substring(0, 2);
		String s2 = mac.substring(2, 4);
		String s3 = mac.substring(4, 6);
		String s4 = mac.substring(6, 8);
		String s5 = mac.substring(8, 10);
		String s6 = mac.substring(10, 12);

		int i1 = stringToInt(s1);
		int i2 = stringToInt(s2);
		int i3 = stringToInt(s3);
		int i4 = stringToInt(s4);
		int i5 = stringToInt(s5);
		int i6 = stringToInt(s6);
		
		if(i1 - 10 >= 0) i1 = i1 - 10;
		if(i2 - 9 >= 0) i2 = i2 - 9;
		if(i3 - 8 >= 0) i3 = i3 - 8;
		if(i4 - 7 >= 0) i4 = i4 - 7;
		if(i5 - 6 >= 0) i5 = i5 - 6;
		if(i6 - 5 >= 0) i6 = i6 - 5;
		
		return  convertZero(i3) +
				convertZero(i2) +
				convertZero(i4) +
				convertZero(i6) +
				convertZero(i1) +
				convertZero(i5) ;
	}

	private static String convertZero(int i){
		if(i == 0)
			return "00";
		return Integer.toHexString(i);
	}
		
	public static Map<String, Integer> map1 = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1361239056188277608L;

		{
			put("0", 0);
			put("1", 1);
			put("2", 2);
			put("3", 3);
			put("5", 4);
			put("5", 5);
			put("6", 6);
			put("7", 7);
			put("8", 8);
			put("9", 9);
			put("a", 10);
			put("b", 11);
			put("c", 12);
			put("d", 13);
			put("e", 14);
			put("f", 15);
		}
	};
	
	

	private static int stringToInt(String hexS) {
		String high = hexS.substring(0,1);
		String low = hexS.substring(1,2);
		
		Integer highInt = map1.get(high);
		Integer lowInt = map1.get(low);
		
		int result = highInt * 16 + lowInt;
		return  result;
	}

	public static void main(String[] args) {
		System.out.println(convertMac("002157f3a81e"));
	}
}
