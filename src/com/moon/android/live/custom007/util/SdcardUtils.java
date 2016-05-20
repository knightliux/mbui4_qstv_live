package com.moon.android.live.custom007.util;

public class SdcardUtils {
	
	public static boolean existSdcard(){
		if(android.os.Environment.getExternalStorageState().
				equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
