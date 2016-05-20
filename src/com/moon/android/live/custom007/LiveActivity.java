package com.moon.android.live.custom007;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudtv.sdk.WhiteListControl;
import com.cloudtv.sdk.http.JsonHttpResponseHandler;
import com.cloudtv.sdk.utils.AlarmUtil;
import com.forcetech.android.ForceTV;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moon.android.live.custom007.R;
import com.moon.android.live.custom007.OSD.OSDAd;
import com.moon.android.live.custom007.OSD.OSDCache;
import com.moon.android.live.custom007.OSD.OSDListProgram;
import com.moon.android.live.custom007.OSD.OSDManager;
import com.moon.android.live.custom007.OSD.OSDNetworkPrompt;
import com.moon.android.live.custom007.OSD.OSDProgramNum;
import com.moon.android.live.custom007.OSD.OSDVolume;
import com.moon.android.live.custom007.OSD.ProgramAdCache;
import com.moon.android.live.custom007.OSD.ProgramCache;
import com.moon.android.live.custom007.model.Ad;
import com.moon.android.live.custom007.model.ForceLoadItem;
import com.moon.android.live.custom007.model.LiveData;
import com.moon.android.live.custom007.model.UpdateData;
import com.moon.android.live.custom007.util.Logger;
import com.moon.android.live.custom007.util.MACUtils;
import com.moon.android.live.custom007.util.NetworkUtil;
import com.moon.android.live.custom007.util.RequestUtil;
import com.moon.android.live.custom007.util.ScreenUtils;
import com.moon.android.live.custom007.util.StringUtil;
import com.moon.android.live.custom007.util.VersionUtil;
import com.moon.android.live.custom007.view.CustomToast;
import com.moon.android.live.custom007.view.GGTextView;
import com.moon.android.live.custom007.view.MarqueeTextView;
import com.moon.android.live.custom007.view.VideoView;
import com.moon.android.live.custom007.white.ChianPull;
import com.moon.android.live.custom007.white.Pull;
import com.moon.utils.AESSecurity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class LiveActivity extends Activity 
								implements OnKeyListener{
	
	public static final Logger log = Logger.getInstance();
	
	//player 
	public static final String PERIOD = ".";
	public static final String PLAY_SERVER = "http://127.0.0.1:9906/";
	public static final String FORCE_CMD = "switch_chan";
	public static final String SYS_MUSIC_COMMAND = "com.android.music.musicservicecommand.pause";
	public static final String S_COMMAND = "command";
	public static final String S_STOP = "stop";  
	public static final int START_INSTALL = 0x11111;
	
	private VideoView mVideoView;
	private LinearLayout mClickLinearlayout;
	private RelativeLayout mContainer;
	private MarqueeTextView mTextLoadPrompt;
	private RelativeLayout mLoadAnimation;
	private RelativeLayout mLoadLayout;
	private TextView mTextLoadInMsg;
	private LinearLayout mLayoutReload;
	private Button mBtnReload;
	private Button mBtnReloadExit;
	private TextView mTextProgramName;
	private TextView mTextAllProgram;
	private TextView mTextViewVersionName;
	
	private boolean isInputProgramNum = false;	 //�Ƿ�������̨�Ž��е�̨
	private boolean isChangeChannel = true;		
	private boolean loadPlayDataSucc = false;    //�Ƿ�������ݼ��ؽ���
	private long mPreiousTime = 0;
	private int mCurrentPlayPos;
	private String mVideoPath;
	private List<LiveData> mListLiveDataAll; 
	
	public static final String FORCE_CHANNEL_INFO = "http://127.0.0.1:9906/api?func=query_chan_info&id=";
	public static final String FORCE_CHAN_DATA_INFO = "http://127.0.0.1:9906/api?func=query_chan_data_info&id=";
	public static final int START_CHANGE_CHANNEL = 0x1101;
	public static final int CHANGE_CHANNEL_TIME = 2;
	public static final long GET_CACHE_PEROID = 1;
	public static final long START_CACHE_TIME = 1;
	public static final int MAX_TRAFFIC = 1000;
	public static final int CAN_PLAY_CACHE_TIME = 1;
	public static final int CHECK_SIGNAL_PEROID = 5 * 60 * 1000;
	public static final int HAS_SIGNAL = 0x80;
	public static final int SINAL_POOR = 0x81;
	public static final int IS_FIRST_VOD_OK = 0x801;
	public static final int PALY_HTTP_VOD = 0x802;
	private static final int FIRST_VOD_READY_TIME = 20 * 1000;
	
	private Timer mTimerGetPlayPos = new Timer();
	private Timer mTimerChangeChannel;
	private Timer mTimerCache = new Timer();
	private Timer mTimerFisrtCanPlay; 
	
	private OSDListProgram mOsdListProgram;
	private OSDCache mOsdCache;
	private OSDProgramNum mOsdProgramNum;
	private OSDManager mOsdManager;
	private OSDVolume mOsdVolume;
	private OSDNetworkPrompt mOsdNetwork;
	private OSDAd mOsdAd;
	
	private DisplayImageOptions mOptions;
	int parseTime = 0;
	
	private Map<String,String> mMapCheckProgramRepeat = new HashMap<String,String>();
	
	private Map<String, List<LiveData>> mMapClassifyPrograms =
			new HashMap<String, List<LiveData>>();
	
	private GGTextView mScrollTextview;
	
	private AudioManage mAudioManage=AudioManage.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live);
		initWidget();
		new Thread(new Runnable() {
			//��Ϊԭ����������ĳЩ�����ϻ�ܾ�
			//���߳���Ϊ�˱����������
			public void run() {
				startForceTv();
				mHandler.sendEmptyMessage(Configs.FORCE_SO_LOADED);
			}
		}).start();
		
	}
	
	private void startForceTv() {
		new ForceTV().initForceClient();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		log.i("onresume");
//		remember = getSharedPreferences("fileInfoRemember", 0);
//		Configs.chip = remember.getString("chip", "");
//		new GetHeartChipThread().start();
//		registerMyReceiver();
	}
	
	private void initWidget() {
		mOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_transport)
		.showImageForEmptyUri(R.drawable.icon_transport)
		.showImageOnFail(R.drawable.icon_transport)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		
		mContainer = (RelativeLayout) findViewById(R.id.container);
		mVideoView = (VideoView) findViewById(R.id.main_videoview);
		mTextLoadPrompt = (MarqueeTextView) findViewById(R.id.load_prompt);
		mLoadAnimation = (RelativeLayout) findViewById(R.id.vod_loading_img);
		mLoadLayout = (RelativeLayout) findViewById(R.id.load_layout);
		mTextLoadInMsg = (TextView) findViewById(R.id.load_in_msg);
		mTextAllProgram = (TextView) findViewById(R.id.program_all_num);
		mLayoutReload = (LinearLayout) findViewById(R.id.reload_prompt);
		mBtnReload = (Button) findViewById(R.id.vod_button_reload);
		mBtnReloadExit = (Button) findViewById(R.id.vod_button_exit);
		mClickLinearlayout = (LinearLayout) findViewById(R.id.click_linearlayout);
		mTextProgramName = (TextView) findViewById(R.id.welcome_program_name);
		mTextViewVersionName = (TextView) findViewById(R.id.versionname);
		mVideoView.setOnPreparedListener(mVideoPrepareListener);
		mVideoView.setOnKeyListener(this);
		mClickLinearlayout.setOnClickListener(mVideoViewClickListener);
		mScrollTextview = (GGTextView) findViewById(R.id.marquee_text);
		mScrollTextview.init(getWindowManager());
		
		mLoadLayout.setOnClickListener(mVideoViewClickListener);
		mBtnReload.setOnClickListener(mBtnReloadClick);
		mBtnReloadExit.setOnClickListener(mBtnReloadClick);
		
		mTextViewVersionName.setText(getText(R.string.version)+VersionUtil.getVerName(this));
	}
	
	private void loadVodMsg() {
		if(checkNetworkConnected()){
			mHandler.sendEmptyMessage(Configs.NETWORK_CONNECT);
		}else{
			mHandler.sendEmptyMessage(Configs.NETWORK_NOT_CONNECT);
		}
	};
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Configs.AD_SHOW_FINISH:
				mVideoView.start();
				log.i("mhandler get ad show finish");
//				Bundle bundleAd = msg.getData();
//				int position = bundleAd.getInt(Configs.INTENT_PARAM);
//				int inAllPos = bundleAd.getInt(Configs.INTENT_PARAM02);
//				log.i("finish ad show program position = " + position);
//				changeChannelToPlay(position,inAllPos);
				mAudioManage.setNormal(getApplicationContext());//����������Ϊ��
				mOsdManager.closeOSD(mOsdAd);//�رչ��
				break;
			case Configs.NETWORK_NOT_CONNECT:
				showPromptMsg(R.string.network_not_connect);
				showReloadBtn();
				break;
			case Configs.NETWORK_CONNECT:
				mTextLoadInMsg.setText(R.string.check_auth);
				//���ذ�����
				//loginWhiteServer();
				//��һ�ν����ʱ��ֱ��ȡ���棬�����е����ͨ��Ļ��Ļ�������ֱ��
				//ͬʱ��ȡ������֤�� ��֤��ͨ����û�������  ʾ��Ȼ���˳�����֤ͨ����»��棬������Թۿ�
//				String link = VodHistory.getLink(getApplicationContext());
//				String key = VodHistory.getKey(getApplicationContext());
//				if(VodHistory.getAuthCacheStatus(LiveActivity.this) && !StringUtil.isBlank(link) && !StringUtil.isBlank(key)){
//					Configs.key = key;
//					Configs.link = link;
//					sendEmptyMessage(Configs.READ_CACHE);
//				} 
				//�����Ƿ���ȷ�Ķ�ȡ���˻���Ľ�Ŀ�б?����Ҫ������Ȩ��Ȼ���ڸ����Ȩ����������ȡ���
				//���ȡ������Ȩ��ʧ�ܵģ���ʾ��Ȩʧ�ܣ�ֱ�Ӹ����ʾ�������˳�Ӧ��
				//ˢ����Ȩ�������״̬
				//ȡ����Ȩ�Ժ�ֱ��ȡ��Ŀ�б?���жԱȣ����ͬ�����½�Ŀ�б?�����滺��
				Message message = new Message();
				message.what = Configs.AUTH_SUCCESS_CACHE;
				Configs.key = "001122xxnb";
				Configs.link = "11";
				Bundle b = new Bundle();
//				bundle.putString(Configs.INTENT_PARAM, vodUrl.getUrl());//�Ϸ�ʽ����Ȩ�ɹ�,��̨���ػ�ȡ��Ŀ�б��URL
				b.putString(Configs.INTENT_PARAM, Configs.getProgramListApi(Configs.AllServer));
				message.setData(b);
			//	message.setData(new Bundle().putString(Configs.INTENT_PARAM, Configs.getProgramListApi(MyApplication.serverAdd)));
				mHandler.sendMessage(message);
//				new FinalRequestDAO().checkAuth(LiveActivity.this,this);
				break;
			case Configs.AUTH_STOP:
				showPromptMsg(R.string.auth_fail);
				showReloadBtn();
				break;
			case Configs.AUTH_FAIL:
				if(loadPlayDataSucc){
					//�Ѿ����뵽�˽�Ŀ��Ȼ����Ȩʧ���ˣ�5s��رճ���
					new CustomToast(getApplicationContext(),getString(R.string.exit_5s_later)).show();
					sendEmptyMessageDelayed(Configs.EXIT, 5000);
				} else {
					showPromptMsg(R.string.auth_fail);
					showReloadBtn();
				}
				break;
			case Configs.AUTH_FAIL_FINISH:
				
				break;
				//��ȡ�����е���Ȩ�ɹ�����ʼ�����б�
			case Configs.EXIT:
				exit();
				break;
			case Configs.READ_CACHE:
				/***
				 * 2014.7.22
				 * ��ѡ��ȡ���ػ�����б?�����ػ�����б��У���ֱ��ʹ�ñ��ص��б�
				 * ͬʱ��ȡ�����б?ȡ���Ժ�����и��£����滻���ػ���
				 * ͬʱ�����б�
				 */
				mTextLoadInMsg.setText(R.string.load_list);
				if(ProgramCache.isExist(LiveActivity.this) && !ProgramCache.isDirectory(LiveActivity.this)){
					//�����б�
					log.i("��ȡ�����е��б�");
					String gsonCacheStr = ProgramCache.getGsonString(LiveActivity.this);
					//��ȡ�����е��б?�����е��б�������?������ȥ�б?ȥ���б������
					//��������б�δ���?��ȡ��ݣ�ȡ����ݺ���жԱ� MD5ֵ����ͬ�����±��ػ�����ݣ�Ȼ��ˢ���б�
					try{
						log.i("�������ػ���Ľ�Ŀ�б�ɹ�");
						parseXmlAndPlay(gsonCacheStr, true, Configs.key);
					}catch(Exception e){
						log.i(e.toString());
					}
				} 
				break;
			case Configs.AUTH_SUCCESS_CACHE:
				//new UpgradeAndRequest(LiveActivity.this).sendRequest();
				Bundle bundle2 = msg.getData();
				String subcatUrl2 = bundle2.getString(Configs.INTENT_PARAM);
				Log.d("dd",subcatUrl2);
				FinalHttp finalHttp = new FinalHttp();
				log.i(subcatUrl2);
				
				KeyStore trustStore = null;
				SSLSocketFactory sf = null;
				try {
					trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
					trustStore.load(null, null);
					sf = new SSLSocketFactoryEx(trustStore);
					sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
				} catch (Exception e){
					log.e(e.toString());
				}
		        
		        if(null != sf){
		        	finalHttp.configSSLSocketFactory(sf);
		        }
		        if(NetworkUtil.isConnectingToInternet(LiveActivity.this)){
		        	subcatUrl2 += "&jsontype=2";
		        	finalHttp.post(subcatUrl2,mParseVodCallBack);
		        }
				break;
			case Configs.AUTH_SUCCESS_NETWORK:
				
				break;
			case Configs.SUCCESS:
				break;
			case Configs.NETWORK_EXCEPTION:
				showPromptMsg(R.string.network_exception);
				showReloadBtn();
				break;
			case Configs.CONTENT_IS_NULL:
				showPromptMsg(R.string.content_is_null);
				showReloadBtn();
				break;
			case Configs.LOAD_DATA_SUCCESS:
				mTextLoadInMsg.setText(R.string.ready_play);
				initOSD();
				mLoadLayout.setVisibility(View.GONE);
				loadPlayDataSucc = true;
				
				//then ready play, start calculate traffics
				mTextAllProgram.setText("/"+mListLiveDataAll.size());
				getLastLookAndPlay();
				mOsdListProgram.setProgramId(String.valueOf(mCurrentPlayPos+1));
				try{
					test.schedule(testTask, START_CACHE_TIME * 1000, GET_CACHE_PEROID * 1000);
					mTimerCache.schedule(mTimerTaskTraffic, START_CACHE_TIME * 1000, GET_CACHE_PEROID * 1000);
				}catch(Exception e){
					log.e(e.toString());
				}
				break;
			case Configs.VOD_CAN_PLAY:
				startPlay(); 
				break;
			case START_INSTALL:
				log.i("/*** download update apk ,and installed "); 
				update(); 
				break;
			case IS_FIRST_VOD_OK:
				log.e("still check first vod ok");
				if(mCurrentPlayPos + 1 >= mListLiveDataAll.size() || mCurrentPlayPos + 1 <= 0){
					mOsdListProgram.setSelectProgramPos(0);
					changeChannel(0);
				} else {
					changeChannel(mCurrentPlayPos + 1);
					mOsdListProgram.setSelectProgramPos(mCurrentPlayPos);
				}
				break;
			case PALY_HTTP_VOD:
				Bundle bundle = msg.getData();
				mVideoPath = bundle.getString(Configs.INTENT_PARAM);
				startPlay();
				break;
			case Configs.FORCE_SO_LOADED:
				loadVodMsg();
				break;
			default:
				break;
			}
		}

		private void startPlay() {
			log.i("start play video");
			mTextProgramName.setText(mListLiveDataAll.get(mCurrentPlayPos).getDname());
			int screenSize[] = ScreenUtils.getScreenSize2(LiveActivity.this);
			mVideoView.setVideoScale(screenSize[0], screenSize[1]);
			mVideoView.setVideoPath(mVideoPath);
			mVideoView.start();

			/*
			 * ����һ����ʱ��������ڵ�½�����ʱ������ϴβ��ŵĽ�Ŀ������Ļ����Զ���ת����һ����Ŀ
			 */
			if(!loadPlayDataSucc){
				if(null != mTimerFisrtCanPlay){
					mTimerFisrtCanPlay.cancel();
					mTimerFisrtCanPlay = null;
					mTimerTaskFirstCanPlay.cancel();
					mTimerTaskFirstCanPlay = null;
				}
				mTimerFisrtCanPlay = new Timer();
				mTimerTaskFirstCanPlay = new TimerTask() {
					@Override
					public void run() {
						if(isChangeChannel){
							mHandler.sendEmptyMessage(IS_FIRST_VOD_OK);
						}	
					}
				}; 
				mTimerFisrtCanPlay.schedule(mTimerTaskFirstCanPlay, FIRST_VOD_READY_TIME);
			}
		}
	}; 
	
	
	/*
	 * ͨ��ӿ�   http://127.0.0.1:9906/api?func=query_chan_info&id= ��ȡ�����ٶ�
	 * ����ٶ� <= 0 ���ж��Ƿ���������
	 */
	private long mTrafficOldBytes = 0L;
	private long mTrffaicBytes = 0;
	private TimerTask mTimerTaskTraffic = new TimerTask() {
		@Override
		public void run() {
			if(mTrafficOldBytes == 0)
				mTrafficOldBytes = getUidRxBytes();
			else {
				long mTrafficNewBytes = getUidRxBytes();
				mTrffaicBytes = mTrafficNewBytes - mTrafficOldBytes;
				mTrafficOldBytes = mTrafficNewBytes;
			}
			mTrafficHandler.sendEmptyMessage(0);
		}
	};
	
	private long getUidRxBytes() {
		PackageManager pm = getPackageManager();
		ApplicationInfo ai = null;
		try {
			ai = pm.getApplicationInfo(Configs.PKG_NAME,
					PackageManager.GET_ACTIVITIES);
		} catch (Exception e1) {
			log.e(e1.toString());
		}
		return TrafficStats.getUidRxBytes(ai.uid) == TrafficStats.UNSUPPORTED ? 0
				: (TrafficStats.getTotalRxBytes() / 1024);
	}
	
	private TimerTask mTimerTaskGetPlayPos = new TimerTask() {
		@Override
		public void run() {
			mHandlerCheckCache.sendEmptyMessage(0);
		}
	};
	
	
	/**
	 * ��ʱ����1sǰMediaPlayer��ʱ������жԱȣ����ʱ�����δ��
	 * ���ʱ�����ͬ��������˿��٣���ʾcache����.
	 */
	private int stopIndex=0;
	private Handler mHandlerCheckCache = new Handler(){
		public void handleMessage(Message msg) {
			int currentPlayPos = (int)mVideoView.getCurrentPosition();
			if(currentPlayPos <= 3) return;  //ǰ3�벻�ж��Ƿ���Կ���
			if(isChangeChannel) return;  //������̨��ȡ����
			
			
			if(mPreiousTime == currentPlayPos){
				stopIndex+=1;
			
				if(stopIndex<5)return;
				if(View.GONE == mOsdCache.getVisibility()){
					mOsdManager.showOSD(mOsdCache);
					
				}
			} else {
				if(View.VISIBLE == mOsdCache.getVisibility()){
					mOsdManager.closeOSD(mOsdCache);
				
					stopIndex=0;
				}
			}
			mPreiousTime = currentPlayPos;
		};
	};
	
	private Handler mTrafficHandler = new Handler(){
		public void handleMessage(Message msg) {
			long traffic = mTrffaicBytes;
			mOsdCache.setTraffic(String.valueOf(traffic));
			if(traffic <= 0){
				//�������
				if(!checkNetworkConnected()){
					if(View.GONE == mOsdNetwork.getVisibility())
						mOsdManager.showOSD(mOsdNetwork);
				} else mOsdManager.closeOSD(mOsdNetwork); 
			} else mOsdManager.closeOSD(mOsdNetwork);
		};
	};
	
	private void update() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Configs.APP_DATA_PATH + "/files/",
				Configs.APK_NAME)),
                        "application/vnd.android.package-archive");
        startActivity(intent);
	}
	
	private void stopUpdatAndGetMsgService() {
		Intent intent = new Intent(this,MsgService.class);
		stopService(intent);
	}
	
	private void registerMyReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Configs.BroadCast.APP_GET_MSG);
		intentFilter.addAction(Configs.BroadCast.UPDATE_MSG);
		registerReceiver(mMsgReceiver, intentFilter);
	}
	
	private BroadcastReceiver mMsgReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			if(Configs.BroadCast.APP_GET_MSG.equals(intent.getAction())){
				showMsg(MyApplication.appMsg);
			} 
			if(Configs.BroadCast.UPDATE_MSG.equals(intent.getAction())){
				UpdateData updata = MyApplication.updateData;
				String appUrl = updata.getUrl();
				new FinalRequestDAO().initPopWindow(appUrl, updata,LiveActivity.this,mHandler);
			} 
		}
	};
	
	private void showMsg(String paramString) {
		mScrollTextview.setText(paramString);
		mScrollTextview.init(getWindowManager());
		mScrollTextview.startScroll();
	}
	
	private OnClickListener mBtnReloadClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v == mBtnReload){
				mLayoutReload.setVisibility(View.INVISIBLE);
				loadVodMsg(); 
				showLoadProgress();
			}
			else if(v == mBtnReloadExit) 
				System.exit(0);
		}
	};
	
	private OnClickListener mVideoViewClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			log.i("video view onclick listener");
			if(loadPlayDataSucc)
				if(mOsdListProgram.getVisibility() == View.GONE)
					mOsdManager.showOSD(mOsdListProgram);
				else mOsdManager.closeOSD(mOsdListProgram);
		}
	};
	
	private OnPreparedListener mVideoPrepareListener = new OnPreparedListener() {
		@Override
		public void onPrepared(MediaPlayer mp) {
			if(loadPlayDataSucc == false)
				loadPlayDataSucc = true;
			mLoadLayout.setVisibility(View.GONE);
			if(mOsdCache.getVisibility() == View.VISIBLE)
				mOsdManager.closeOSD(mOsdCache);
			isChangeChannel = false;
			if(null != mTimerFisrtCanPlay){
				mTimerFisrtCanPlay.cancel();
			}
			try{
				mTimerGetPlayPos.schedule(mTimerTaskGetPlayPos, 0, 1000);
			}catch(Exception e){
				log.i(e.toString());
			}
		}
	};
	
	private AjaxCallBack<Object> mParseVodCallBack = new AjaxCallBack<Object>() {
		@Override
		public void onSuccess(Object t) {
			super.onSuccess(t);
			String gsonStr = (String)t;
			log.i(gsonStr);
			if(loadPlayDataSucc){
				if(true){
					log.i("��ȡ���б����ݿ��е��б?ͬ������Ƿ����");
					try{
						//���Գɹ������ٽ���ݸ���
						Gson gson = new Gson();
						gson.fromJson(gsonStr, new TypeToken<List<LiveData>>(){}.getType());
						new CustomToast(getApplicationContext(),getString(R.string.data_is_update)).show();
						log.i("���µ������������������");
						ProgramCache.save(gsonStr,LiveActivity.this);
					}catch(Exception e){
						log.e(e.toString());
						log.i("���µ�����޷���ȷ������������");
					}
				} else log.i("��ȡ���б����ݿ��е��б���ͬ����������ȷȡ���б?����Ҫ����ô");
			} else {
				log.i("���Ǽ��ر��أ����߼���ʧ���ˣ���ֱ�ӻ�ȡ���ҽ����б?�����ٴα������");
				parseXmlAndPlay(gsonStr,false,Configs.key);
			}
		}
		
		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			log.i("�����б�ʧ��");
			super.onFailure(t, errorNo, strMsg);
		}
	};
	
	
	/**
	 * �ѻ����е�gson��ת��List<LiveData>����ͨ��Handler���Ͳ�������
	 * @param gsonString
	 * @param isLoadCache
	 * @param key
	 */
	private void parseXmlAndPlay(String gsonString,boolean isLoadCache,String key){
		boolean isParseSuccess = parseListXML(gsonString,isLoadCache,key);
	    if(isParseSuccess){
	    	ProgramCache.save(gsonString,LiveActivity.this);
	    	mHandler.sendEmptyMessage(Configs.LOAD_DATA_SUCCESS);
	    }
	}
	
	@SuppressWarnings("unchecked")
	private boolean parseListXML(String gsonString,boolean isLoadCache,String key){
		Gson gson = new Gson();
		List<LiveData> listLiveData = null;
		try{
			listLiveData = (List<LiveData>)gson.fromJson(gsonString, new TypeToken<List<LiveData>>(){}.getType());
		}catch(Exception e){
			log.e(e.toString());
			return false;
		}
		
	    if(null == listLiveData || listLiveData.size() == 0){
	    	mHandler.sendEmptyMessage(Configs.CONTENT_IS_NULL);
	    	return false;
	    } 
	    
	    int programPostion = 0;
	    List<LiveData> listAll = new ArrayList<LiveData>();
	    
	    //ѭ�����еĽ�Ŀ
	    for(LiveData data : listLiveData){
	    	//������URL��ַ������Ϊ�գ����߽������?����ȡ���
	    	//��ʧ�ܣ��˽�Ŀ����ʾ
	    	String url = data.getUrl();
	    	String urlDecode = null;
			try {
				urlDecode = AESSecurity.decrypt(url, key);
				if(null == urlDecode || "".equals(urlDecode))
					continue;
				if(urlDecode.startsWith("force://")
						||urlDecode.startsWith("forcetv://")){
					String [] arrayOfString = urlDecode
							.split("://")[1].split("/");
					data.setStreamIp(arrayOfString[0]);
					data.setChannelId(arrayOfString[1]);
				}
				data.setUrl(urlDecode);

		    	//����Ŀ�Ƿ��Ѿ�����
		    	boolean isExist = mMapCheckProgramRepeat.containsKey(data.getId());
		    	if(isExist){
		    		data.setInAllProgramPos(mMapCheckProgramRepeat.get(data.getId()));
		    	} else {
		    		data.setInAllProgramPos(String.valueOf(programPostion));
		    		mMapCheckProgramRepeat.put(data.getId(), String.valueOf(programPostion));
		    		programPostion++;
		    		listAll.add(data);
		    	}
		    	putProgramToClassify(data);
			} catch (InvalidKeyException e2) {
				reLoad();
				log.i(e2.toString());
			} catch (IllegalBlockSizeException e2) {
				reLoad();
				log.i(e2.toString());
			} catch (BadPaddingException e2) {
				reLoad();
				log.i(e2.toString());
			} catch (NoSuchAlgorithmException e2) {
				reLoad();
				log.i(e2.toString());
			} catch (NoSuchPaddingException e2) {
				reLoad();
				log.i(e2.toString());
			} catch(Exception e){
				log.e(e.toString());
				log.e("���Ϸ���ַ " +urlDecode);
				continue;
	    	}
	    }
	    mListLiveDataAll = listAll;
	    mMapClassifyPrograms.put("0|"+getString(R.string.program_title), mListLiveDataAll);
	    return true;
	    }
	    
	
	private void putProgramToClassify(LiveData data){
		String classifyName = data.getClassname();
		String orderid = data.getOrderid();
		if(null == classifyName || "".equals(classifyName) ||
				null == orderid || "".equals(orderid)) return ;
		String key = orderid + "|" + classifyName;
		boolean isExist = mMapClassifyPrograms.containsKey(key);
		if(isExist){
			List<LiveData> list = mMapClassifyPrograms.get(key);
			if(null != list){
				list.add(data);
				mMapClassifyPrograms.put(key, list);
			} else {
				List<LiveData> list2 = new ArrayList<LiveData>();
				list2.add(data);
				mMapClassifyPrograms.put(key, list2);
			}
		} else {
			List<LiveData> list = new ArrayList<LiveData>();
			list.add(data);
			mMapClassifyPrograms.put(key, list);
		}
	}
	
	private void reLoad(){
		//������ݳ��?ɾ��棬���¼���
//		File file = new File(Configs.CONTENT_CACHE_FILE);
//		log.i("�������ػ������ʱ��keyֵ�쳣��ɾ��壬���¼����������");
		try{
//			file.delete();
			loadVodMsg();
		} catch (Exception e1){
			log.i(e1.toString());
		}
	}

	private void showPromptMsg(int strRes){
		mLoadAnimation.setVisibility(View.GONE);
		mTextLoadPrompt.setText(strRes);
		mTextLoadPrompt.setVisibility(View.VISIBLE);
	}
	
	private void showLoadProgress(){
		mLoadAnimation.setVisibility(View.VISIBLE);
		mTextLoadPrompt.setVisibility(View.GONE);
	}
	
	private void showReloadBtn(){
		mLayoutReload.setVisibility(View.VISIBLE);
		mBtnReload.requestFocus();
		mBtnReload.requestFocusFromTouch();
	}

	
	private void initOSD() {
		mOsdManager = new OSDManager();
		mOsdListProgram = new OSDListProgram(getApplicationContext(), this,mContainer,mMapClassifyPrograms);
		mOsdCache = new OSDCache(getApplicationContext(),mContainer);
		mOsdProgramNum = new OSDProgramNum(getApplicationContext(), mContainer);
		mOsdVolume = new OSDVolume(getApplicationContext(), mContainer);
		mOsdNetwork = new OSDNetworkPrompt(getApplicationContext(), mContainer);
		mOsdAd = new OSDAd(getApplicationContext(), mContainer);
		
		mOsdManager.addOSD(OSDManager.OSD_CACHE, mOsdCache);
		mOsdManager.addOSD(OSDManager.OSD_LIST_PROGARM, mOsdListProgram);
		mOsdManager.addOSD(OSDManager.OSD_PROGRAM_NUM, mOsdProgramNum);
		mOsdManager.addOSD(OSDManager.OSD_VOLUME, mOsdVolume);
		mOsdManager.addOSD(OSDManager.OSD_NETWORK_PROMPT, mOsdNetwork);
		mOsdManager.addOSD(OSDManager.OSD_PROGRAM_AD, mOsdAd);
	}

	private Timer test = new Timer();
	private TimerTask testTask = new TimerTask() {
		@Override
		public void run() {
			    String mForcePlayInfo;
			    mForcePlayInfo = RequestUtil.getInstance().request("http://127.0.0.1:9906/api?func=query_chan_info&id=" + mListLiveDataAll.get(mCurrentPlayPos).getChannelId());
                log.d("--------123-----"+mForcePlayInfo);
		}
	};
	private TimerTask mTimerTaskFirstCanPlay = new TimerTask() {
		@Override
		public void run() {
			if(isChangeChannel){
				mHandler.sendEmptyMessage(IS_FIRST_VOD_OK);
			}
		}
	};
	
	/*
	 * ��ȡ�ϴβ��ŵĽ�Ŀ����
	 */
	private void getLastLookAndPlay() {
		int previousPlayPos = VodHistory.getHistory(getApplicationContext());
		if(previousPlayPos < 0 || previousPlayPos >= mListLiveDataAll.size())
			previousPlayPos = 0;
		
		changeChannel(previousPlayPos);
		mOsdListProgram.setSelectProgramPos(previousPlayPos);
	}
	
	private void play(LiveData liveData) {
		String url = liveData.getUrl();
		if(null != liveData && null != url){
			if(url.startsWith("forcetv://") || url.startsWith("force://")){
				FinalHttp finalHttp = new FinalHttp();
				String requestUrl = getVideoHttp(liveData.getChannelId(), liveData.getStreamIp(), FORCE_CMD, Configs.link);
				finalHttp.get(requestUrl,mStartPlayCallBack);
			} else {
				Message msg = new Message();
				msg.what = PALY_HTTP_VOD;
				Bundle bundle = new Bundle();
				bundle.putString(Configs.INTENT_PARAM, url);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			}
		} else {
			new CustomToast(this, "Program url is null").show();
		} 
	}
	
	private String getVideoHttp(String channelId, String streamIp, String cmd, String link) {
		StringBuilder s4 = new StringBuilder(PLAY_SERVER + "cmd.xml?cmd=");
		s4.append(cmd).append("&id=").append(channelId).append("&server=").append(streamIp);
		if (link != null && !link.equals("")) {
			s4.append("&link=").append(link);
		}
		return s4.toString();
	}

	public void changeChannel(int position){
		prarseAdAndShow(position);
	}
	
	/*
	 * ��ȡ��沢�Ҳ���
	 * �����ĳ������ֱ����Ŀ��ʱ�򣬻���ȡ���ӽ�Ŀ�Ĺ�滺�棬����л�������ʾ�����û�У�����ʾ
	 * 
	 * ÿ�ε���󣬲�����û�л��涼�����´Ӻ�̨����ȡ�����ݣ����б��ظ��£��´ε����̨��ʱ����ʾ��
	 * ע:ÿ�ν�Ӧ��ֻ�ڴ�̨��һ�ε����ʱ��ȡ��棬�ٴε����ʱ�򣬲�����ȡ (���isHasGetAd�����ж�)
	 */
	private void prarseAdAndShow(int position) {
		//get cache 
		LiveData liveData = mListLiveDataAll.get(position);
		String tvid = liveData.getId();
		boolean isAdExist = ProgramAdCache.isExist(tvid,LiveActivity.this);
		int sec = 0;
		
		int inAllPos = 0;
		try{
			inAllPos = Integer.valueOf(liveData.getInAllProgramPos());
		}catch(Exception e){
			log.e(e.toString()); 
		}
		mCurrentPlayPos = inAllPos;
		
		if(isAdExist){
			log.i("ad exist");
			String content = ProgramAdCache.getGsonString(tvid,LiveActivity.this);
			try{
				Type type = new TypeToken<List<Ad>>(){}.getType();
				List<Ad> list = new Gson().fromJson(content, type);
				if(null != list && list.size() > 0){
					mOsdAd.setAd(list, mOptions);
					mOsdManager.showOSD(mOsdAd);
					sec = getAdTime(list);
				}
			}catch(Exception e){
				log.e(e.toString());
			}
			log.i("ad seconds = " + sec);
		}
//		mVideoView.stopPlayback();
		changeChannelToPlay(position, mCurrentPlayPos);
		mAudioManage.setSlience(getApplicationContext());
		mHandler.removeMessages(Configs.AD_SHOW_FINISH);
		Message msg = new Message();
//		Bundle bundle = new Bundle();
//		bundle.putInt(Configs.INTENT_PARAM, position);
//		bundle.putInt(Configs.INTENT_PARAM02, mCurrentPlayPos);
//		msg.setData(bundle);
		msg.what = Configs.AD_SHOW_FINISH;
		mHandler.sendMessageDelayed(msg, sec * 1000);
		
		if(!liveData.isHasGetAd())
			new FinalRequestDAO().getProgramAd(liveData,0,LiveActivity.this);
	}

	private int getAdTime(List<Ad> list) {
		int times = 0;
		for(Ad ad : list){
			try{
				int time = Integer.valueOf(ad.getSec());
				log.i(ad.toString());
				times =+ time;
			}catch(Exception e){
				log.e(e.toString()); 
			}
		}
		return times;
	}

	private void changeChannelToPlay(int position,int inAllPos){
		isChangeChannel = true;
		mOsdManager.showOSD(mOsdCache,true);
		LiveData liveData = mListLiveDataAll.get(position);
		mOsdCache.setIcon(liveData.getIco(),mOptions);
		liveData.getChannelId();
		play(liveData);
		showProgramNum(inAllPos);
		VodHistory.add(getApplicationContext(), inAllPos);
	}
	
	private AjaxCallBack<Object> mStartPlayCallBack = new AjaxCallBack<Object>() {
		@Override
		public void onSuccess(Object t) {
			super.onSuccess(t);
			ForceLoadItem forceLoadItem = ForceDataParse.vodCanPlay((String)t);
			//����ʧ��
			if(null == forceLoadItem || forceLoadItem.getFlag() != 0){
				log.i("program start fail, reson = " + forceLoadItem.getReason());
				mOsdManager.closeOSD(mOsdCache);
				if(!loadPlayDataSucc){
					toPlay();
				}
			} else { //�����ɹ�
				log.i("program start success,can play now");
				toPlay();
			}
		}
		
		private void toPlay(){
			LiveData liveData = mListLiveDataAll.get(mCurrentPlayPos);
			mVideoPath = new StringBuilder(PLAY_SERVER).append(liveData.getChannelId())
					.append(PERIOD).append(liveData.getType()).toString();
			log.i("play videoPath = " + mVideoPath);
			mHandler.sendEmptyMessage(Configs.VOD_CAN_PLAY);
		}
		
		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			//start fail, can't play
			log.i("program start fail,can't play now");
		}
	};
	
	public void onBackPressed() {
//		Log.i("log", "back()");
//		if(loadPlayDataSucc)
//			new FinalRequestDAO().showExitWindow(this,mHandler);
//		else System.exit(0);
	};
	
	@Override
	protected void onStop() {
		super.onStop();
		exit();
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		mAudioManage = AudioManage.getInstance();
		if(event.getAction() == KeyEvent.ACTION_DOWN && 
				v == mVideoView && loadPlayDataSucc){
			switch(keyCode){
			case KeyEvent.KEYCODE_DPAD_UP:
				mCurrentPlayPos--;
				if(mCurrentPlayPos < 0)
					mCurrentPlayPos = mListLiveDataAll.size() - 1;
				mOsdListProgram.setSelectProgramPos(mCurrentPlayPos);
				changeChannel(mCurrentPlayPos);
				mOsdListProgram.recordProgramPoint(0,mCurrentPlayPos);
				return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				mCurrentPlayPos++;
				if(mCurrentPlayPos >= mListLiveDataAll.size())
					mCurrentPlayPos = 0;
				mOsdListProgram.setSelectProgramPos(mCurrentPlayPos);
				changeChannel(mCurrentPlayPos);
				mOsdListProgram.recordProgramPoint(0,mCurrentPlayPos);
				return true;
			case KeyEvent.KEYCODE_MENU:
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				try{
					mOsdManager.showOSD(mOsdListProgram);
				}catch(Exception e){
					log.e(e.toString());
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				try{
					mAudioManage.lowerVolume(getApplicationContext());
					mOsdVolume.setVolume();
					mOsdVolume.setVisibility(View.VISIBLE);
				}catch(Exception e){
					log.e(e.toString());
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
			case KeyEvent.KEYCODE_VOLUME_UP:
				try{
					mAudioManage.upVolume(getApplicationContext());
					mOsdVolume.setVolume();
					mOsdVolume.setVisibility(View.VISIBLE);
				}catch(Exception e){
					log.e(e.toString());
				}
				return true;
			case KeyEvent.KEYCODE_VOLUME_MUTE:
				try{
					mAudioManage.setSlience(getApplicationContext());
					mOsdVolume.setVolume();
					mOsdVolume.setVisibility(View.VISIBLE);
				}catch(Exception e){
					log.e(e.toString());
				}
				return true; 
			}
			
			//change channel by input number 
			if(keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9){
				if(!isInputProgramNum){
					int programCode = keyCode - KeyEvent.KEYCODE_0 - 1;
					mOsdProgramNum.setProgramText(
								String.valueOf(programCode + 1),getProgramName(programCode));
					mOsdProgramNum.setVisibility(View.VISIBLE);
					isInputProgramNum = true;
					reTimingTVNumInput();
				} else {
					mOsdProgramNum.setVisibility(View.VISIBLE);
					String oldText = String.valueOf(mOsdProgramNum.getProgramText());
					if(oldText.length() >= 3){
						oldText = "";
					}
					int programCode = keyCode - KeyEvent.KEYCODE_0 - 1;
					String newText = String.valueOf(programCode + 1);
					mOsdProgramNum.setProgramText(oldText+newText,getProgramName(Integer.valueOf(oldText+newText) - 1));
					reTimingTVNumInput();
				}
			}
			
			//backspace button
			if(keyCode == KeyEvent.KEYCODE_DEL && isInputProgramNum){
				String oldText = String.valueOf(mOsdProgramNum.getProgramText());
				int oldTextLength = oldText.length();
				if(oldTextLength >= 1){
					String newText = oldText.substring(0, oldTextLength - 1);
					if(null == newText || StringUtil.NULL_STRING.equals(newText)){
						cancelTVNumInputTimer();
					} else
						mOsdProgramNum.setProgramText(newText,getProgramName(Integer.valueOf(newText)));
					reTimingTVNumInput();
				}
			}
		}
		return false;
	}
	
	public String getProgramName(int position){
		if(mListLiveDataAll.size() > position && position >= 0)
			return mListLiveDataAll.get(position).getDname();
		return getText(R.string.no_program).toString();
	}
	
	private void cancelTVNumInputTimer(){
		if(null != mTimerChangeChannel)
			mTimerChangeChannel.cancel();
	}
	
	private void reTimingTVNumInput() {
		if(null != mTimerChangeChannel)
			mTimerChangeChannel.cancel();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				mChangeHandler.sendEmptyMessage(START_CHANGE_CHANNEL);
				isInputProgramNum = false;
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, CHANGE_CHANNEL_TIME * 1000);
		mTimerChangeChannel = timer;
	}

	private Handler mChangeHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case START_CHANGE_CHANNEL:
				int destProgramId = mOsdProgramNum.getProgramText()-1;
				if(destProgramId != mCurrentPlayPos){
					if(destProgramId >= 0 && destProgramId < mListLiveDataAll.size()){
//						changeChannelWithInputNum(destProgramId);
						prarseAdAndShow(destProgramId);
						mOsdListProgram.recordProgramPoint(0,destProgramId);
						mOsdListProgram.setShouldUpdateProgram(true);
					}
					else mOsdProgramNum.setProgramText(String.valueOf(destProgramId + 1),getString(R.string.no_program));
					isInputProgramNum = false;
				} else {
					mOsdProgramNum.setProgramText(String.valueOf(destProgramId + 1),getString(R.string.is_playing));
				}
				break;
			}
		};
	};
	
	public void setVolume(){
		mOsdVolume.setVolume();
		mOsdVolume.setVisibility(View.VISIBLE);
	}
	
	private void showProgramNum(int inAllPos) {
		int programPos = inAllPos <= 0 || inAllPos >= mListLiveDataAll.size() ? 0 : inAllPos;
		mOsdProgramNum.setProgramText(String.valueOf(inAllPos + 1),mListLiveDataAll.get(programPos).getDname());
		mOsdProgramNum.setVisibility(View.VISIBLE);
	}

	public int getProgramId(){
		int inAllPos = 0;
		try{
			LiveData liveData = mListLiveDataAll.get(mCurrentPlayPos);
			inAllPos = Integer.valueOf(liveData.getInAllProgramPos());
		}catch(Exception e){
			log.e(e.toString());
		}
		return inAllPos;
	}
	
	public boolean isChangeChannel(){
		return isChangeChannel;
	}
	
	private boolean checkNetworkConnected() {
		return NetworkUtil.isConnectingToInternet(getApplicationContext());
	}
	
	public void exit(){
		new FinalRequestDAO().dismisExitWindow();
		finish();
		try{
			stopUpdatAndGetMsgService();
			unregisterReceiver(mMsgReceiver);
			//�ر������
			new FinalRequestDAO().dismissMarquee();
			mTimerTaskTraffic.cancel();
			mVideoView.stopPlayback();
		}catch(Exception e){
			log.i("stop update and get msg service error |OR| unregisterReceiver error");
		} finally {
			System.exit(0);
		}
    }
	
	private int serverPort = 0;
	private String serverAddress =null;
	private SharedPreferences remember;
	// ������
	private JsonHttpResponseHandler loginHandler = new JsonHttpResponseHandler() {

		public void onFailure(Throwable error, String content) {
			log.e(content);
			log.d("wlc : this is error");
		}

		@Override
		public void onFailure(Throwable error, JSONObject errorResponse) {
			super.onFailure(error, errorResponse);
			log.e(errorResponse.toString());
			log.d("wlc : this is error2");
		}

		@Override
		public void onSuccess(JSONObject response) {
			super.onSuccess(response);
			int error_code;
			try {
				String[] str = Configs.chip.split("[:]");
				serverAddress = str[0];
				serverPort = Integer.parseInt(str[1]);
				error_code = response.getInt("error_code");
				String message = response.getString("message");
				if (0 == error_code) {
					log.d("wlc : this is ok");
					WhiteListControl.setHeartBeat(LiveActivity.this, MACUtils.getMac(),
							serverAddress, serverPort);
					AlarmUtil.addAlarm(LiveActivity.this);
				} else {
					log.e(message);
				}
			} catch (JSONException e) {
				log.e(e.toString());
			} catch(Exception e){
				log.e(e.toString());
			}
		}
	};
	
	
	public class GetHeartChipThread extends Thread {
		public void run() {
			getHeartChip();	
		}
	}
	
	public void getHeartChip(){
		Pull p=new Pull();
		  try {
			URL ur = new URL(Configs.getHBSApi(MyApplication.serverAdd));
			if(ur==null||ur.equals("")){
				log.v("Heart : Not get url");
			}else{			
			InputStream input = ur.openStream();
			List<ChianPull> materials = p.getPersons(input);
			String ip=null;
			for(ChianPull ch:materials){
				ip = ch.ip;
				log.v("Heart : url = " + ip);
			}
			remember.edit().putString("chip",ip).commit();
			Configs.chip = remember.getString("chip", "");
			}
		} catch (Exception e) {
			log.e(e.toString());
		}
	}
	
	//LOGIN WHITE COTROL SERVER
	private void loginWhiteServer(){
		String mac = MACUtils.getMac();
		if((mac != null) && !(mac.equals(""))) {
			log.d("login whiteList Server!");
			log.d("deviceID="+mac);
			log.d("loginURL="+Configs.WHITE_URL);
			WhiteListControl.sendLogin(mac, Configs.WHITE_URL, loginHandler);
		}
	}
}
