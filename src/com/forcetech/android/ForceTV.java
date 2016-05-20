package com.forcetech.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.os.Environment;
import android.util.Log;

public class ForceTV {
	private boolean p2pIsStart = false;
	private String path = "";
//	private String path = Environment.getExternalStorageDirectory().getPath();
	private byte[] pathbyte = new byte[path.length()+1];

	public void initForceClient() {
//		System.out.println("start Force P2P.........");
		try {
			Process process = Runtime.getRuntime().exec("netstat");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()), 1024);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("0.0.0.0:9906"))
					p2pIsStart = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.arraycopy(path.getBytes(),0,pathbyte,0,path.length());
		if (!p2pIsStart)Log.d("jni", String.valueOf(startWithLog(9906, 20 * 1024 * 1024, pathbyte)));
//		if (!p2pIsStart)Log.d("jni", String.valueOf(start(9906, 20 * 1024 * 1024)));
//			Log.d("jni", String.valueOf(start(9906, 20 * 1024 * 1024)));
// 		Log.d("jni", String.valueOf(start()));
	}

	public native int start(int port, int size);
	public native int startWithLog(int port, int size, byte[] path);

	public native int stop();


	static {
		System.loadLibrary("forcetv");
	}
}
