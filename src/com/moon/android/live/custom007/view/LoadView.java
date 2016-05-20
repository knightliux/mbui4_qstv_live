package com.moon.android.live.custom007.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LoadView extends RelativeLayout{
	
	Logger logger = Logger.getInstance();
	private ImageView mImgVod;
	private TextView mTextTraffic;
	private TextView mTextTrafficBKS;
	public static final String KBS = " kb/s";
	private ImageView mImageProgram;
	private Context mContext;
	public LoadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.p_vod_cache, this);
		Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.p_vod_anima);  
		LinearInterpolator lin = new LinearInterpolator();  
		operatingAnim.setInterpolator(lin);  
		
		mImgVod = (ImageView) view.findViewById(R.id.rotate_image);
		mTextTraffic = (TextView) view.findViewById(R.id.text_traffic);
		mTextTrafficBKS = (TextView) view.findViewById(R.id.text_traffic_kbs);
		mImageProgram = (ImageView) view.findViewById(R.id.program_icon);
		mImgVod.startAnimation(operatingAnim);
	}
	
	public void setIcon(String iconPath,DisplayImageOptions options){
//		FinalBitmap fb = FinalBitmap.create(mContext);
		mImageProgram.setVisibility(View.GONE);
		if(!isNull(iconPath)){
			//mImageProgram.setVisibility(View.VISIBLE);
//			fb.display(mImageProgram, iconPath);
			//ImageLoader.getInstance().displayImage(iconPath, mImageProgram, options);
		} 
	}
	
	private boolean isNull(String path){
		return null == path || "".equals(path);
	}
	
	public LoadView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LoadView(Context context) {
		this(context, null);
	}
	
	public void stopAnimation(){
		mImgVod.clearAnimation();
	}

	public void setText(String traffic){
		mTextTraffic.setText(traffic);
		mTextTrafficBKS.setText(KBS);
	}
}
