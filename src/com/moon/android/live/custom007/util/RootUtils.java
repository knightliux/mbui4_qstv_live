package com.moon.android.live.custom007.util;

import java.io.File;

public class RootUtils {

	public static boolean checkRoot() {
		boolean bool = false;
		try {
			File file = new File("/system/bin/mnote");
//			File file2 = new File("/system/xbin/su");
			if (file.exists())
				return true;
//			if(file2.exists()){
//				return true;
//			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return bool;
	}
}
