package com.moon.android.live.custom007.OSD;

import java.util.List;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.model.Ad;
import com.moon.android.live.custom007.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OSDAd extends OSD {
	
	private View mContainer;
	private ImageView mAdImage;
	private List<Ad> mListAd;
	private RelativeLayout mAdContainer;
	private TextView mTextCountdown;
	
	private Logger log = Logger.getInstance();
	public OSDAd(Context context,View container){
		mContainer = container;
		mAdContainer = (RelativeLayout) mContainer.findViewById(R.id.program_ad);
		mAdImage = (ImageView) mContainer.findViewById(R.id.program_ad_image);
		mTextCountdown  = (TextView) mContainer.findViewById(R.id.program_ad_countdown);
		setProperity(OSD.PROPERITY_LEVEL_3);
	}
	
	@Override
	public void setVisibility(int visibility) {
		mAdContainer.setVisibility(visibility);
	}
	
	public void setAd(List<Ad> list,DisplayImageOptions options){
		mListAd = list;
		if(null != list && list.size() > 0){
			Ad ad = list.get(0);
			if(null == ad)return;
			String url = ad.getAdurl();
			int time = 10;
			try{
				time = Integer.valueOf(ad.getSec());
			}catch(Exception e){
				log.e(e.toString());
			}
			ImageLoader.getInstance().displayImage(url, mAdImage, options);
			new CountDownTimer(time * 1000,1000) {
				@Override
				public void onTick(long millisUntilFinished) {
					String time = String.valueOf(millisUntilFinished / 1000);
					mTextCountdown.setText(time);
				}
				
				@Override
				public void onFinish() {
					
				}
			}.start();
		}
	}
	
	
	@Override
	public int getVisibility() {
		return mAdContainer.getVisibility();
	}
	
	public boolean isOn(){
		return View.VISIBLE == mAdContainer.getVisibility();
	}
}