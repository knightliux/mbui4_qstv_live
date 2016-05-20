package com.moon.android.live.custom007.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.os.Environment;

public class LogcatUtil {

	private Logger log = Logger.getInstance();
	private PrintStream ps;
	public void outputLog(){
		String logDir = Environment.getExternalStorageDirectory().toString() + File.separator + "moon" + File.separator;
		File logDirFile = new File(logDir);
		if(!logDirFile.exists()) {
			logDirFile.mkdir();
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = dateFormat.format(new java.util.Date());
		
		String logFileName = logDir + date + ".txt";
		File logFile = new File(logFileName); 
		if(!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				log.e(e.toString());
			}
		}
		try {
			ps = new PrintStream(new FileOutputStream(logFile));
		} catch (FileNotFoundException e1) {
			log.e(e1.toString());
		}
			
		ArrayList<String> cmdLine = new ArrayList<String>();
		cmdLine.add("logcat");
		
		ArrayList<String> clearLog = new ArrayList<String>();
		clearLog.add("logcat");
		clearLog.add("-c");
		
		try {
			Process process = Runtime.getRuntime().exec(cmdLine.toArray(new String[cmdLine.size()]));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String str = null;
			while((str=bufferedReader.readLine()) != null) {
				ps.println(str);
			}
		} catch (IOException e) {
			ps.flush();
			ps.close();
		}
		ps.flush();
		ps.close();
	}
}
