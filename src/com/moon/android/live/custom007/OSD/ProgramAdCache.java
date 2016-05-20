package com.moon.android.live.custom007.OSD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;

import com.moon.android.live.custom007.Configs;
import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.util.MACUtils;
import com.moon.utils.AESSecurity;
import com.moon.utils.MD5Util;

public class ProgramAdCache {
static Logger logger = Logger.getInstance();
	
	public static boolean isExist(String tvid,Context ctx){
		logger.i("Program cache file = " + Configs.getCacheFile(ctx)+tvid);
		File file = new File(Configs.getCacheFile(ctx)+tvid);
		return file.exists();
	}
	
	public static boolean isDirectory(String tvid,Context ctx){
		File file = new File(Configs.getCacheFile(ctx)+tvid);
		return file.isDirectory();
	}
	
	public static boolean delFile(String tvid,Context ctx){
		File file = new File(Configs.getCacheFile(ctx)+tvid);
		if(file.exists())
			return file.delete();
		return false;
	}
	
	
	public static void save(String saveGsonStr, String tvid,Context ctx){
		File file = new File(Configs.getCacheFile(ctx) + tvid);
		logger.i("Program cache file = " + Configs.getCacheFile(ctx)+tvid);
		if(file.isDirectory()) return;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			String ciphertext = AESSecurity.encrypt(saveGsonStr, MD5Util.getStringMD5_16(MACUtils.getMac()));
			fos.write(ciphertext.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			if(null != fos)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	@SuppressWarnings("resource")
	public static String getGsonString(String tvid,Context ctx){
		File file = new File(Configs.getCacheFile(ctx)+tvid);
		if(file.isDirectory()) return "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(Configs.getCacheFile(ctx)+tvid));
			String resultStr = "";
			String data = br.readLine(); 
			if(null != data)
				resultStr += data;
			while(data != null){  
				data = br.readLine(); 
				if(null != data)
					resultStr += data;
			} 
			String expressly = AESSecurity.decrypt(resultStr, MD5Util.getStringMD5_16(MACUtils.getMac()));
			return expressly;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}  catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static boolean checkIsSame(String gsonStr,String tvid,Context ctx){
		File file = new File(Configs.getCacheFile(ctx)+tvid);
		String md5_01 = MD5Util.getFileMD5String(file);
		String cipherText = AESSecurity.encrypt(gsonStr, 
							MD5Util.getStringMD5_16(MACUtils.getMac()));
		String md5_02 = MD5Util.getStringMD5_32(cipherText);
		logger.i("md51 = " + md5_01 + "  md52 = " + md5_02);
		if(null == md5_01) return false;
		if(null == md5_02) return false;
		if(md5_01.equals(md5_02)) return true;
		return false;
	}
}
