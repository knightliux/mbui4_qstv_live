package com.moon.android.live.custom007.OSD;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.moon.android.live.custom007.AudioManage;
import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.view.CircularSeekBar;

public class OSDVolume extends OSD{

	private View mView;
	private View mViewVolume;
	private CircularSeekBar mVolumeSeekBar;
	private Context mContext;
	public OSDVolume(Context context,View view){
		mView = view; 
		mContext = context;
		mVolumeSeekBar = (CircularSeekBar) mView.findViewById(R.id.circle_seek_bar);
		mViewVolume = mView.findViewById(R.id.volume_container);
		setVolume();
		setProperity(PROPERITY_LEVEL_3);
	}
	
	public void setVolume(){
		mVolumeSeekBar.setVolume(AudioManage.getInstance().getCurrentAudio(mContext));
	}

	private Timer mVolumeOldTimer;
	@Override
	public void setVisibility(int visible) {
		mViewVolume.setVisibility(visible);
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mViewVolume.setVisibility(View.GONE);
			}
		};
		if(null != mVolumeOldTimer)
			mVolumeOldTimer.cancel();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, 5000);
		mVolumeOldTimer = timer;
	}

	@Override
	public int getVisibility() {
		return mViewVolume.getVisibility();
	}
	
}
