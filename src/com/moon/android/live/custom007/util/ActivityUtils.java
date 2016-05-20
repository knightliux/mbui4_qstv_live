package com.moon.android.live.custom007.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivityUtils {
	
	public static List<Activity> list = new ArrayList<Activity>();
	
	public static void finishAllActivity(){
		for(Activity activity : list){
			if(null != activity){
				try{
					activity.finish();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void startActivity(Activity context, Class<?> classs){
		Intent intent = new Intent();
    	intent.setClass(context, classs);
    	list.add(context);
    	context.startActivity(intent);
	}
	
	public static void startActivity(Context context, Class<?> classs){
		Intent intent = new Intent();
    	intent.setClass(context, classs);
    	context.startActivity(intent);
	}
	
	public static void startActivityForResult(Activity context, Class<?> classs, String strName, String result){
		list.add(context);
		Intent intent = new Intent();
		intent.setClass(context, classs);
		Bundle  budle = new Bundle();
		budle.putString(strName, result);
		intent.putExtras(budle);
    	context.startActivityForResult(intent, 0);
	}
	
	public static void startActivityForResult(Activity context, Class<?> classs, int resultFlag){
		list.add(context);
		Intent intent = new Intent(context, classs);  
		context.startActivityForResult(intent, resultFlag);  
	}
	
	public static void startActivity(Activity context, Class<?> classs, String key, List<?> result){
		list.add(context);
		Intent intent = new Intent();
		intent.setClass(context, classs);
		intent.putExtra(key, result.toArray());
    	context.startActivity(intent);
	}
	
	public static void startActivity(Activity context, Class<?> classs, String key, Serializable  result){
		list.add(context);
		Intent intent = new Intent();
		intent.setClass(context, classs);
		intent.putExtra(key, result);
    	context.startActivity(intent);
	}

}
