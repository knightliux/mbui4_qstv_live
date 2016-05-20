package com.moon.android.live.custom007;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.util.MACUtils;
import com.moon.utils.AESSecurity;
import com.moon.utils.MD5Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class VodHistory {
	
	public static final Logger log = Logger.getInstance();
	
	/**
	 * link start 
	 */
	public static final String LAST_LINK = "last_link";
	public static final String LAST_LINK_VALUE = "last_link_value";
	public static void saveLink(Context context, String value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_LINK, Context.MODE_PRIVATE);
		Editor addEditor = sharedPreferences.edit();
		addEditor.putString(LAST_LINK_VALUE, value);
		addEditor.commit();
	}
	
	public static String getLink(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_LINK, Context.MODE_PRIVATE);
		String value = sharedPreferences.getString(LAST_LINK_VALUE, null);
		return value;
	}
	/**
	 * link end
	 */
	
	/**
	 * key start 
	 */
	public static final String LAST_KEY = "last_some";
	public static final String LAST_KEY_VALUE = "last_value";
	public static void saveKey(Context context, String value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_KEY, Context.MODE_PRIVATE);
		Editor addEditor = sharedPreferences.edit();
		addEditor.putString(LAST_KEY_VALUE, value);
		addEditor.commit();
	}
	
	public static String getKey(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_KEY, Context.MODE_PRIVATE);
		String value = sharedPreferences.getString(LAST_KEY_VALUE, null);
		return value;
	}
	/**
	 * key end
	 */
	
	public static final String VOD_HISTORY_SHARE = "vod_history";
	public static final String PLAY_POS = "play_pos";
	public static void add(Context context, long playPos){
		if(playPos < 0) return ;
		SharedPreferences sharedPreferences = context.getSharedPreferences(VOD_HISTORY_SHARE, Context.MODE_PRIVATE);
		Editor delEditor = sharedPreferences.edit();
		delEditor.remove(PLAY_POS);
		delEditor.commit();
		Editor addEditor = sharedPreferences.edit();
		String value = String.valueOf(playPos);
		addEditor.putString(PLAY_POS, value);
		addEditor.commit();
	}
	
	public static Integer getHistory(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(VOD_HISTORY_SHARE, Context.MODE_PRIVATE);
		String value = sharedPreferences.getString(PLAY_POS, null);
		if(null == value) return 0;
		int returnValue;
		try{
			returnValue = Integer.valueOf(value); 
		}catch(Exception e){
			returnValue = 0;
		}
		return returnValue;
	}
	
	public static final String VOD_HISTORY_TIME = "vod_history2";
	public static final String PLAY_STATUS = "PLAY_STATUS";
	public static final String LAST_PLAY_TIME = "LAST_PLAY_TIME";
	public static final String CHECK_PASS = "1001";
	public static final String CHECK_NOT_PASS = "1002";
	public static void saveCache(Context context,boolean playStatus){
		SharedPreferences sharedPreferences = context.getSharedPreferences(VOD_HISTORY_TIME, Context.MODE_PRIVATE);
		String checkStr = playStatus ? CHECK_PASS : CHECK_NOT_PASS;
		String storageStr = AESSecurity.encrypt(checkStr, MD5Util.getStringMD5_16(MACUtils.getMac()));
		Editor addEditor = sharedPreferences.edit();
		addEditor.putString(PLAY_STATUS, storageStr);
		addEditor.putLong(LAST_PLAY_TIME, System.currentTimeMillis());
		addEditor.commit();
	}
	
	/**
	 * 1001 ��֤ͨ��   1002 ��֤��ͨ��
	 * @param context
	 * @return
	 */
	public static final long ONE_DAY = 24 * 60 * 60 * 1000;
	public static final long CACHE_TIME_OUT = ONE_DAY * 180;
	public static boolean getAuthCacheStatus(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(VOD_HISTORY_TIME, Context.MODE_PRIVATE);
		String status = sharedPreferences.getString(PLAY_STATUS, null);
		long time = sharedPreferences.getLong(LAST_PLAY_TIME,0);
		long hasPassTime = Math.abs(System.currentTimeMillis() - time);
		log.i("last time = " + time + "  cache time = " + hasPassTime);
		if(null != status){
			 String statusCode = "null";
			try {
				statusCode = AESSecurity.decrypt(status, MD5Util.getStringMD5_16(MACUtils.getMac()));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			 if(CHECK_PASS.equals(statusCode)
					 && hasPassTime < CACHE_TIME_OUT)
				 return true;
		}
		return false;
	}
	
	public static long getCacheTime(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_KEY, Context.MODE_PRIVATE);
		long time = sharedPreferences.getLong(LAST_PLAY_TIME,0);
		return time;
	}
	
}
