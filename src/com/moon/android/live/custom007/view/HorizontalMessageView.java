package com.moon.android.live.custom007.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.moon.android.live.custom007.R;

public class HorizontalMessageView extends LinearLayout {
	private HorizontalScrollTextView hTextView = (HorizontalScrollTextView) LayoutInflater
			.from(getContext()).inflate(R.layout.horizontalmessage, this)
			.findViewById(R.id.messagetxt);

	public HorizontalMessageView(Context paramContext,
			AttributeSet paramAttributeSet, String paramString) {
		super(paramContext, paramAttributeSet);
		this.hTextView.setText(paramString);
		this.hTextView.init((WindowManager) paramContext
				.getSystemService("window"));
	}

	public HorizontalMessageView(Context paramContext, String paramString) {
		this(paramContext, null, paramString);
	}

	public void setBackColor(int paramInt) {
		setBackgroundColor(paramInt);
	}

	public void setTextColor(int paramInt) {
		this.hTextView.setTextColor(paramInt);
	}

	public void setTextSize(int paramInt) {
		this.hTextView.setTextSize(paramInt);
		this.hTextView.setY(paramInt);
	}

	public void startScroll() {
		this.hTextView.startScroll();
	}
}
