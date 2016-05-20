package com.moon.android.live.custom007.util;

import android.content.Context;
import android.content.pm.PackageManager;

import com.moon.android.live.custom007.Configs;

public class VersionUtil {

	
	public static int getVerCode(Context paramContext) {
		try {
			int i = paramContext.getPackageManager().getPackageInfo(
					Configs.PKG_NAME, 0).versionCode;
			return i;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return -1;
	}

	public static String getVerName(Context paramContext) {
		try {
			String str = paramContext.getPackageManager().getPackageInfo(
					Configs.PKG_NAME, 0).versionName;
			return str;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return "";
	}
}
