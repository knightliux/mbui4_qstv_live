package com.moon.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

public class FileUtil {

	/**
	 * 检查文件是否存在
	 * @param file
	 * @return
	 */
	public static boolean isExist(File file){
		
		return false;
	}
	
	
	public static void saveToSdcard(String s){
		try{
		 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
             File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录,2.2的时候为:/mnt/sdcart  2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。  
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
