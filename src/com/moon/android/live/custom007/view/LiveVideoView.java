package com.moon.android.live.custom007.view;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.VideoView;

public class LiveVideoView extends VideoView{

	public LiveVideoView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public LiveVideoView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public LiveVideoView(Context paramContext) {
		super(paramContext);
	}
	
	@Override
	protected void onMeasure(int paramInt1, int paramInt2) {
//		super.onMeasure(paramInt1, paramInt2);
		int width = getDefaultSize(mScreenWidth, paramInt1);
		int height = getDefaultSize(mScreenHeight, paramInt2);
		setMeasuredDimension(width,height);
	}
	
	private int mScreenWidth;
	private int mScreenHeight;
	public void setScreenSize(int width,int height){
		mScreenWidth = width;
		mScreenHeight = height;
	}
}
