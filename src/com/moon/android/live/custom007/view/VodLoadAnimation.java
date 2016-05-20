package com.moon.android.live.custom007.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.moon.android.live.custom007.R;

@SuppressLint("NewApi")
public class VodLoadAnimation extends LinearLayout{

	public VodLoadAnimation(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public VodLoadAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public VodLoadAnimation(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  progressBarItemView=(View)inflater.inflate(R.layout.vod_load_anim, null);
        addView(progressBarItemView);
        ImageView progressImageView=(ImageView)this.findViewById(R.id.vod_load_img);
        AnimationDrawable animationDrawable = (AnimationDrawable) progressImageView.getDrawable();
        animationDrawable.start();
    }
}
	
