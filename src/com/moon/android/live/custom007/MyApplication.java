package com.moon.android.live.custom007;


import android.app.Application;
import android.content.Context;

import com.moon.android.live.custom007.model.UpdateData;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application 
{
	private static MyApplication iptvAppl1ication;
	public static String appMsg = "";
	protected static UpdateData updateData;

	public static String serverAdd;//����Ȩ�ɹ��󣬰ѿ��õ���Ȩ��������ַ���浽Application��Χ
	@Override
	public void onCreate() {
		super.onCreate();
		iptvAppl1ication = this;

		initImageLoader(getApplicationContext());

	}
	
	public static void initImageLoader(Context context) {
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

	public static MyApplication getApplication() {
		return iptvAppl1ication;
	}
	
	static{
		System.loadLibrary("moon");
	}
}
