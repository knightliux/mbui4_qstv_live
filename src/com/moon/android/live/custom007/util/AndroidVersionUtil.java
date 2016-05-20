package com.moon.android.live.custom007.util;

import android.os.Environment;

public class AndroidVersionUtil {

	 public static boolean isFroyoOrLater()
	    {
	        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;
	    }

	    public static boolean isGingerbreadOrLater()
	    {
	        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;
	    }

	    public static boolean isHoneycombOrLater()
	    {
	        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
	    }

	    public static boolean isICSOrLater()
	    {
	        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	    }

	    public static boolean isJellyBeanOrLater()
	    {
	        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
	    }

	    public static boolean hasExternalStorage() {
	        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	    }
}
