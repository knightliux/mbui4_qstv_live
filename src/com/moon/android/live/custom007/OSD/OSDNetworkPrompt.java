package com.moon.android.live.custom007.OSD;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.moon.android.live.custom007.R;

public class OSDNetworkPrompt extends OSD{

	private View mContainer;
	private LinearLayout mLayoutNetwork;
	
	public OSDNetworkPrompt(Context context,View container){
		mContainer = container;
		mLayoutNetwork = (LinearLayout) mContainer.findViewById(R.id.osd_network_prompt);
		setProperity(PROPERITY_LEVEL_3);
	}
	
	@Override
	public void setVisibility(int visibility) {
		mLayoutNetwork.setVisibility(visibility);
	}

	@Override
	public int getVisibility() {
		return mLayoutNetwork.getVisibility();
	}

}
