package com.moon.android.live.custom007.OSD;


public abstract class OSD{
	
	public static final int PROPERITY_LEVEL_1 = 1;
	public static final int PROPERITY_LEVEL_2 = 2;
	public static final int PROPERITY_LEVEL_3 = 3;
	public static final int PROPERITY_LEVEL_4 = 4;
	public static final int PROPERITY_LEVEL_5 = 5;
	public static final int PROPERITY_LEVEL_6 = 6;
	
	private int mProperity = PROPERITY_LEVEL_2;
	
	public void setProperity(int properity){
		mProperity = properity;
	};

	public int getProperity(){
		return mProperity;
	}
	
	public abstract void setVisibility(int visibility);
	
	public abstract int getVisibility();
}
