package com.moon.android.live.custom007.OSD;

import java.util.HashMap;
import java.util.Map;

import android.view.View;


public class OSDManager {
	
	public static final String OSD_LIST_PROGARM = "com.moonlive.android.iptv.listprogram";
	public static final String OSD_CACHE = "com.moonlive.android.iptv.CACHE";
	public static final String OSD_PROGRAM_NUM = "com.moonlive.android.iptv.programnum";
	public static final String OSD_VOLUME = "com.moonlive.android.iptv.volume";
	public static final String OSD_NETWORK_PROMPT = "com.moonlive.android.iptv.networkprompt";
	public static final String OSD_NO_SIGNAL = "com.moonlive.android.iptv.nosignal";
	public static final String OSD_PROGRAM_PROMPT = "com.moonlive.android.iptv.program_prompt";
	public static final String OSD_PROGRAM_AD = "com.moonlive.android.iptv.program_ad";
	
	public static final int OSD_SHOW_TIME = 10;
	public static final int OSD_LISTPROGRAM_SHOW_TIME = 60;
	
	private static Map<String,OSD> OSDList = new HashMap<String,OSD>();
	
	public OSD getOSD(String osdTag){
		return OSDList.get(osdTag);
	}
	
	public void addOSD(String osdTags,OSD osd){
		OSDList.put(osdTags,osd);
	}
	
	public void closeOSD(OSD osd){
		osd.setVisibility(View.GONE);
	}
	
	 public void closeAllOSD(){
		for(String key : OSDList.keySet())
		{
			OSD osd = OSDList.get(key);
			osd.setVisibility(View.GONE);
		}
	}
	
	public void showOSD(OSD osd){
		for(String key : OSDList.keySet()){
			OSD osdTemp = OSDList.get(key);
			if(osdTemp == osd) continue;
			int showOsdProperity = osd.getProperity();
			int tempOsdProperity = osdTemp.getProperity();
			OSD osdClose = showOsdProperity > tempOsdProperity ? osdTemp : osd;
			osdClose.setVisibility(View.GONE);
		}
		int visibility = osd.getVisibility() == View.VISIBLE?View.GONE : View.VISIBLE;
		osd.setVisibility(visibility);
	}
	
	
	public void showOSD(OSD osd,boolean show){
		showOSD(osd);
		if(show)
			osd.setVisibility(View.VISIBLE);
	}
	
}
