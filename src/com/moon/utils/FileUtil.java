package com.moon.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

public class FileUtil {

	/**
	 * ����ļ��Ƿ����
	 * @param file
	 * @return
	 */
	public static boolean isExist(File file){
		
		return false;
	}
	
	
	public static void saveToSdcard(String s){
		try{
		 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
             File sdCardDir = Environment.getExternalStorageDirectory();//��ȡSDCardĿ¼,2.2��ʱ��Ϊ:/mnt/sdcart  2.1��ʱ��Ϊ��/sdcard������ʹ�þ�̬�����õ�·�����һ�㡣  
             File saveFile = new File(sdCardDir, "abc.txt");  
             FileOutputStream outStream = new FileOutputStream(saveFile);  
             outStream.write(s.getBytes());  
             outStream.close();  
         }  
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
