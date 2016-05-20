package com.moon.android.live.custom007.util;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtils {
	
	
	public static final Logger log = Logger.getInstance();
	public static int[] getScreenSize2(Activity activity) {
		// ��ȡ��Ļ��ߣ�����1��
		int screenWidth = activity.getWindowManager().getDefaultDisplay()
				.getWidth(); // ��Ļ�?���أ��磺480px��
		int screenHeight = activity.getWindowManager().getDefaultDisplay()
				.getHeight(); // ��Ļ�ߣ����أ��磺800p��
		// ��ȡ��Ļ�ܶȣ�����2��
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getResources().getDisplayMetrics();
		float density = dm.density; // ��Ļ�ܶȣ����ر���0.75/1.0/1.5/2.0��
		int densityDPI = dm.densityDpi; // ��Ļ�ܶȣ�ÿ�����أ�120/160/240/320��
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		screenWidth = dm.widthPixels; // ��Ļ�?���أ��磺480px��
		screenHeight = dm.heightPixels; // ��Ļ�ߣ����أ��磺800px��
		// ��ȡ��Ļ�ܶȣ�����3��
		dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density; // ��Ļ�ܶȣ����ر���0.75/1.0/1.5/2.0��
		densityDPI = dm.densityDpi; // ��Ļ�ܶȣ�ÿ�����أ�120/160/240/320��
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		int screenWidthDip = dm.widthPixels; // ��Ļ�?dip���磺320dip��
		int screenHeightDip = dm.heightPixels; // ��Ļ�?dip���磺533dip��
		screenWidth = (int) (dm.widthPixels * density + 0.5f); // ��Ļ�?px���磺480px��
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // ��Ļ�ߣ�px���磺800px��
		log.i("Screen width = " + screenWidth +" screenHeight = " + screenHeight);
		return new int[] {screenWidth, screenHeight + 50};
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static int[] getScreenSize(Activity activity) {
		int[] screen = new int[2];
		DisplayMetrics metrics = new DisplayMetrics();
		Display display = activity.getWindowManager().getDefaultDisplay();
		display.getMetrics(metrics);
		int ver = Build.VERSION.SDK_INT;
		if (ver < 13) {
			screen[1] = metrics.heightPixels;
		} else if (ver == 13) {
			try {
				Method method = display.getClass().getMethod("getRealHeight");
				screen[1] = (Integer) method.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(ver == 17){
			try {
				Method method = display.getClass().getMethod("getHeight");
				screen[1] = (Integer) method.invoke(display) + 50;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ver > 13 && ver != 17) {
			try {
				Method method = display.getClass().getMethod("getRawHeight");
				screen[1] = (Integer) method.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		screen[0] = display.getWidth();
		return screen;
	}
}
