package com.moon.android.live.custom007.OSD;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moon.android.live.custom007.R;

@SuppressLint("HandlerLeak")
public class OSDProgramNum extends OSD{
	
	private View mContainer;
	private TextView mTextProgramId;
	private TextView mTextProgramName;
	private LinearLayout mLLProgram;
	
	public OSDProgramNum(Context context,View container){
		mContainer = container;
		mLLProgram = (LinearLayout) mContainer.findViewById(R.id.program_name_container);
		mTextProgramId = (TextView) mContainer.findViewById(R.id.program_id);
		mTextProgramName = (TextView) mContainer.findViewById(R.id.program_name);
		setProperity(PROPERITY_LEVEL_3);
		
        TextPaint tp1 = mTextProgramId.getPaint(); 
        tp1.setStrokeWidth(10);
        tp1.setStyle(Style.FILL);
        tp1.setFakeBoldText(false);
	}
	
	private Timer mTimerProgramNum;
	public static final int HIDE_PROGRAM_NUM = 0x1001;
	
	@Override
	public void setVisibility(int visibility) {
		mLLProgram.setVisibility(visibility);
		
		if(null != mTimerProgramNum)
			mTimerProgramNum.cancel();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(HIDE_PROGRAM_NUM);
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, OSDManager.OSD_SHOW_TIME * 1000);
		mTimerProgramNum = timer;
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case HIDE_PROGRAM_NUM:
				setVisibility(View.GONE);
				break;
			}
		};
	};
	
	@Override
	public int getVisibility() {
		return mLLProgram.getVisibility();
	}
	
	public int getProgramText(){
		try{
			return Integer.valueOf(mTextProgramId.getText().toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	public void setProgramText(String programId,String programName){
		mTextProgramId.setText(programId);
		mTextProgramName.setText(programName);
	}
}
