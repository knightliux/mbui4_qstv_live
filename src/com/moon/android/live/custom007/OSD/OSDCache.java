package com.moon.android.live.custom007.OSD;

import android.content.Context;
import android.view.View;

import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.view.LoadView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class OSDCache extends OSD {
	
	private View mContainer;
	private LoadView mLoadView;
	
	public OSDCache(Context context,View container){
		mContainer = container;
		mLoadView = (LoadView) mContainer.findViewById(R.id.program_loadview);
		setProperity(OSD.PROPERITY_LEVEL_3);
	}
	
	@Override
	public void setVisibility(int visibility) {
		mLoadView.setVisibility(visibility);
	}
	
	public void setIcon(String iconPath,DisplayImageOptions options){
		mLoadView.setIcon(iconPath,options);
	}
	
	@Override
	public int getVisibility() {
		return mLoadView.getVisibility();
	}
	
	public void setTraffic(String traffic){
		mLoadView.setText(traffic);
	}
	
	public boolean isOn(){
		return View.VISIBLE == mLoadView.getVisibility();
	}
}