package com.moon.android.live.custom007.view;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class MyMarquee {
	private Context mContext;
	private int mGravity = 85;
	float mVerticalMargin;
	View mView;
	WindowManager mWindowManager;
	float mHorizontalMargin;
	private int mX;
	private int mY;
	private int mWidth;
	final Handler mHandler = new Handler();
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
	
	final Runnable mHide = new Runnable() {
		public void run() {
			MyMarquee.this.handleHide();
		}
	};
	
	final Runnable mShow = new Runnable() {
		public void run() {
			MyMarquee.this.handleShow();
		}
	};

	public MyMarquee(Context paramContext) {
		mContext = paramContext;
		mWindowManager = ((WindowManager) paramContext
				.getSystemService("window"));
		WindowManager.LayoutParams localLayoutParams = mParams;
		localLayoutParams.height = -2;
		localLayoutParams.width = -2;
		localLayoutParams.type = 2007;
		localLayoutParams.flags = 40;
		localLayoutParams.format = -3;
		localLayoutParams.setTitle("Toast");
	}

	public void handleHide() {
		if (mView != null) {
			if (mView.getParent() != null)
				mWindowManager.removeView(mView);
			mView = null;
		}
	}

	public void handleShow() {
		if (mView != null) {
			int i = mGravity;
			mParams.gravity = i;
			mParams.width = mWidth;
			if ((i & 0x7) == 7)
				mParams.horizontalWeight = 1.0F;
			if ((i & 0x70) == 112)
				mParams.verticalWeight = 1.0F;
			mParams.x = mX;
			mParams.y = mY;
			mParams.verticalMargin = mVerticalMargin;
			mParams.horizontalMargin = mHorizontalMargin;
			if (mView.getParent() != null)
				mWindowManager.removeView(mView);
			mWindowManager.addView(mView, mParams);
		}
	}

	public void hide() {
		mHandler.post(this.mHide);
	}

	public void remove() {
		if (mView != null) {
			mWindowManager.removeView(mView);
			mView = null;
		}
	}

	public void setFocusable(boolean paramBoolean) {
		if (paramBoolean) {
			mParams.flags = 0;
			return;
		}
		mParams.flags = 40;
	}

	public void setGravity(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4) {
		mGravity = paramInt1;
		mX = paramInt3;
		mY = paramInt4;
		mWidth = paramInt2;
	}

	public void setMargin(float paramFloat1, float paramFloat2) {
		mHorizontalMargin = paramFloat1;
		mVerticalMargin = paramFloat2;
	}

	public void setView(int paramInt) {
		mView = ((LayoutInflater) mContext
				.getSystemService("layout_inflater")).inflate(paramInt, null);
	}

	public void setView(View paramView) {
		mView = paramView;
	}

	public void show() {
		mHandler.post(mShow);
	}
}

