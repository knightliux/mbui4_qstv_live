package com.moon.android.live.custom007.OSD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moon.android.live.custom007.AudioManage;
import com.moon.android.live.custom007.Configs;
import com.moon.android.live.custom007.LiveActivity;
import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.model.LiveData;
import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.view.CustomToast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

@SuppressLint({ "NewApi", "CutPasteId", "HandlerLeak" })
public class OSDListProgram extends OSD
						implements OnKeyListener{
	private static Logger log = Logger.getInstance();
	private View mContainer;
	private ListView mListViewProgramListView;
	private ProgramAdapter mProgramAdapter;
	private List<LiveData> mListVodVideo;
	private Context mContext;
	private TextView mTextCurrentPos;
	private LiveActivity mMainActivity;
	private RelativeLayout mProgramListContainer;
	private Map<String,List<LiveData>> mMapPrograms; 
	private DisplayImageOptions options;
	private TextView mTextViewTotalProg;
	private List<String> mListClassifyName = new ArrayList<String>();
	
	private int mClassifySelect = 0;
	private TextView mTextClassifyName;
	//then show the program list, redirect to playing program
	private int mInwhichClassify;
	private int mInWhichSelectPos = -1;
	private ImageView mProArrLeft,mProArrRight;
	private boolean isShouldUpdateProgram = false;
	public OSDListProgram(Context context, LiveActivity main,View container
			,Map<String,List<LiveData>> mapPrograms){
		setProperity(PROPERITY_LEVEL_3);
		mMapPrograms = mapPrograms;
		mMainActivity = main;
		mContainer = container;
		mContext = context;
		mInwhichClassify = 0;
		
		Iterator<?> it = mapPrograms.keySet().iterator();
		while(it.hasNext()){                        
            String key = (String) it.next();   
            mListClassifyName.add(key);
        }
		Collections.sort(mListClassifyName);
		mListVodVideo = mMapPrograms.get(mListClassifyName.get(0));
		
		mListViewProgramListView = (ListView) mContainer.findViewById(R.id.main_program_listview);
		mProgramListContainer = (RelativeLayout) mContainer.findViewById(R.id.main_program_list);
		mTextViewTotalProg = (TextView) mContainer.findViewById(R.id.program_all_num);
		mTextClassifyName = (TextView) mContainer.findViewById(R.id.clssify_name);
		mProArrLeft = (ImageView) mContainer.findViewById(R.id.program_arr_left);
		mProArrRight = (ImageView) mContainer.findViewById(R.id.program_arr_right);
		
		if(!Configs.isShowClassify){
			mProArrLeft.setVisibility(View.GONE);
			mProArrRight.setVisibility(View.GONE);
		}
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_transport)
		.showImageForEmptyUri(R.drawable.icon_transport)
		.showImageOnFail(R.drawable.icon_transport)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		
		if(null != mListClassifyName && mListClassifyName.size() > 0 ){
			String firstClassifyName = mListClassifyName.get(0);
			setClassifyName(firstClassifyName);
		}
		
		mProgramAdapter = new ProgramAdapter(mContext, mListVodVideo,options);
		mListViewProgramListView.setAdapter(mProgramAdapter);
		mTextCurrentPos = (TextView) mContainer.findViewById(R.id.program_current_position);
		mListViewProgramListView.setOnItemClickListener(mProgramItemClickListener);
		mListViewProgramListView.setOnItemSelectedListener(mProgramItemSelectListener);
		mListViewProgramListView.setOnHoverListener(mOnHoverListener);
		
		mListViewProgramListView.setOnKeyListener(this);
		
	}
	
	public void updateList(List<LiveData> listVodVideo){
//		cancelAnimation();
//		mListViewProgramListView.setLayoutAnimation(getAnimationController());
		mProgramAdapter = new ProgramAdapter(mContext, listVodVideo,options);
		mListViewProgramListView.setAdapter(mProgramAdapter);
		int inAllProgramPosition = mMainActivity.getProgramId();
		mProgramAdapter.setSelectPos(inAllProgramPosition);
	}
	
	@SuppressLint("NewApi")
	private OnHoverListener mOnHoverListener = new OnHoverListener() {
		@Override
		public boolean onHover(View v, MotionEvent event) {
			osdProgramReTime();
			return false;
		}
	};
	
	public void setSelectProgramPos(int inAllProgramPos){
		mProgramAdapter.setSelectPos(inAllProgramPos);
	}
	
	private void setTotalPrograms(){
		mTextViewTotalProg.setText("/"+mListVodVideo.size());
	}
	
	public void setProgramId(String id){
		mTextCurrentPos.setText(id);
	}
	
	private ImageView mImageSelectIcon;
	private OnItemClickListener mProgramItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			try{
				log.i("--------ChangeChannel start ---");
				LiveData data = mListVodVideo.get(position);
				log.i(" " + data.getUrl());
				int inAllProgramPos = Integer.valueOf(data.getInAllProgramPos());
				log.i("MainActivity programId = " + mMainActivity.getProgramId());
				log.i("click program in all program pos = " + inAllProgramPos);
				if(mMainActivity.getProgramId() != inAllProgramPos){
					mMainActivity.changeChannel(inAllProgramPos);
					setSelectProgramPos(inAllProgramPos);
					changeProgramListIcon(arg1);
					mInwhichClassify = mClassifySelect;
					mInWhichSelectPos = position;
					setVisibility(View.GONE);
				} else {
					log.i("Is playing the program");
					new CustomToast(mContext, mContext.getString(R.string.is_playing), 24).show();
				}
				log.i("------- ChangeChannel end ---");
			}catch(Exception e){
				log.e("Change channel err [" + e.toString() +"]");
			} 
		}
	};
	
	public void recordProgramPoint(int classify,int position){
		mInwhichClassify = classify;
		mInWhichSelectPos = position;
	}
	
	/**
	 * change select program list icon then change channel
	 * ��̨��ʱ���л�����ʱ��̨��
	 * @param view
	 */
	public void changeProgramListIcon(View view){
		try{
			ImageView image = (ImageView) view.findViewById(R.id.program_item_play_image);
			image.setVisibility(View.VISIBLE);
			if(null != mImageSelectIcon){
				log.i("mImageSelectIcon is not null");
				mImageSelectIcon.setVisibility(View.INVISIBLE);
			}
			else {
				log.i("mImageSelectIcon is null");
				mProgramAdapter.hiddenFirstShowImage();
			}
			mImageSelectIcon = image;
		}catch(Exception e){
			log.e(e.toString());
		}
	}
	
	public void changeProgramListIcon(int position){
		try{
			mListViewProgramListView.setSelection(position);
			View view = mListViewProgramListView.getChildAt(position);
			
			if(null != view){
				log.i("view is not null");
				changeProgramListIcon(view);
			} else log.i("view is null");
		}catch(Exception e){
			log.e(e.toString());
		}
	}
	
	private OnItemSelectedListener mProgramItemSelectListener = new OnItemSelectedListener(){
		@Override
		public void onItemSelected(AdapterView<?> arg0, View view, int position,
				long arg3) {
			mTextCurrentPos.setText(String.valueOf(position+1));
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};
	
	private Timer mTimerListProgram;
	public static final int HIDE_PROGRAM_LIST = 0x2001;
	@Override
	public void setVisibility(int visibility) {
		if(mInWhichSelectPos == -1)
			mInWhichSelectPos = mMainActivity.getProgramId();
		mProgramListContainer.setVisibility(visibility);
		if(visibility == View.VISIBLE){
			mListViewProgramListView.requestFocus();
			mListViewProgramListView.requestFocusFromTouch();
			mListViewProgramListView.setSelection(mMainActivity.getProgramId());
		}
		showPlayingProgram();
		osdProgramReTime();
	}
	
	private void showPlayingProgram() {
		try{
			if(mClassifySelect != mInwhichClassify || isShouldUpdateProgram){
				isShouldUpdateProgram = false;
				changeClassify(mInwhichClassify);
			}
			mListViewProgramListView.setSelection(mInWhichSelectPos);
			log.i("Program visibiligy item selection = " + mInWhichSelectPos);
		} catch (Exception e){
			log.e(e.toString());
		}
	}

	private void osdProgramReTime() {
		if(null != mTimerListProgram)
			mTimerListProgram.cancel();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(HIDE_PROGRAM_LIST);
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, OSDManager.OSD_LISTPROGRAM_SHOW_TIME * 1000);
		mTimerListProgram = timer;
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case HIDE_PROGRAM_LIST:
				setVisibility(View.GONE);
				break;
			}
		};
	};

	@Override
	public int getVisibility() {
		return mProgramListContainer.getVisibility();
	}
	
	public void setProgramSelection(int position){
		mListViewProgramListView.setSelection(position);
	}
	
	
	
	
	private void changeClassify(boolean isLeft){
		int classifyLastPos = mListClassifyName.size() - 1;
		if(null == mListClassifyName && mListClassifyName.size() <= 0) return;
		if(isLeft){
			if(mClassifySelect == 0)
				mClassifySelect = classifyLastPos;
			else mClassifySelect --;
		} else{
			if(mClassifySelect == classifyLastPos)
				mClassifySelect = 0;
			else mClassifySelect ++;
		}
		changeClassify(mClassifySelect);
	}
	
	private void changeClassify(int position){
		if(null == mListClassifyName || mListClassifyName.size() <= position) return;
		String key = mListClassifyName.get(position);
		List<LiveData> list = mMapPrograms.get(key);
		mListVodVideo = list;
		updateList(list);
		setTotalPrograms();
		setClassifyName(key);
	}
	
	private void setClassifyName(String key){
		try{
			mTextClassifyName.setText(key.split("\\|")[1]);
		}catch(Exception e){
			log.e(e.toString());
		}
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		AudioManage audioManage = AudioManage.getInstance();
		if(v == mListViewProgramListView && event.getAction() == KeyEvent.ACTION_DOWN){
			osdProgramReTime();
			switch(keyCode){
			case KeyEvent.KEYCODE_BACK:
				if(getVisibility() == View.VISIBLE){
					setVisibility(View.GONE);
					return true;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_UP :
				if(mListViewProgramListView.getSelectedItemPosition() == 0)
					mListViewProgramListView.setSelection(mListVodVideo.size() - 1);
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if(mListViewProgramListView.getSelectedItemPosition() == mListVodVideo.size() - 1)
					mListViewProgramListView.setSelection(0);
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if(Configs.isShowClassify){
					changeClassify(true);
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if(Configs.isShowClassify){
					changeClassify(false);
				}
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				audioManage.lowerVolume(mContext);
				mMainActivity.setVolume();
				return true;
			case KeyEvent.KEYCODE_VOLUME_UP:
				audioManage.upVolume(mContext);
				mMainActivity.setVolume();
				return true;
			}
		}
		return false;
	}

	public boolean isShouldUpdateProgram() {
		return isShouldUpdateProgram;
	}

	public void setShouldUpdateProgram(boolean isShouldUpdateProgram) {
		this.isShouldUpdateProgram = isShouldUpdateProgram;
	}

//	AnimationSet set = new AnimationSet(true);
//	protected LayoutAnimationController getAnimationController() {
//		int duration=50;
//
//		Animation animation = new AlphaAnimation(0.0f, 1.0f);
//		animation.setDuration(duration);
//		set.addAnimation(animation);
//
//		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//		animation.setDuration(duration);
//		set.addAnimation(animation);
//
//		LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
//		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//		return controller;
//	}
//	
//	protected void cancelAnimation() { 
//		set.cancel();
//	}
	
	
}
