package com.moon.android.live.custom007.view;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.moon.android.live.custom007.util.Logger;

@SuppressLint("NewApi")
public class VideoView extends SurfaceView implements
		MediaController.MediaPlayerControl {
	
	private Logger logger = Logger.getInstance();
	public static final int SHOW_MODE_4_3 = 0;
	public static final int SHOW_MODE_16_9 = 1;
	public static final int SHOW_MODE_FULL_SCREEN = 2;
	
	public void setShowMode(int width, int height ,int mode){
		int widthScreen;
		int heightScreen;
		heightScreen = height;
		if(mode == SHOW_MODE_4_3){
			widthScreen = height * 4 / 3;
		} else if(mode == SHOW_MODE_16_9){
			widthScreen = height * 16 / 9;
		} else {
			widthScreen = width;
		}
		setVideoScale(widthScreen, heightScreen);
	}
	
	private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
		public void onBufferingUpdate(MediaPlayer paramAnonymousMediaPlayer,
				int paramAnonymousInt) {
			mCurrentBufferPercentage = paramAnonymousInt;
		}
	};
	
	private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer paramAnonymousMediaPlayer) {
			if (mMediaController != null)
				mMediaController.hide();
			if (mOnCompletionListener != null)
				mOnCompletionListener.onCompletion(mMediaPlayer);
		}
	};
	
	private Context mContext;
	private int mCurrentBufferPercentage;
	private int mDuration;
	private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
		public boolean onError(MediaPlayer paramAnonymousMediaPlayer,
				int paramAnonymousInt1, int paramAnonymousInt2) {
			logger.d("Error: " + paramAnonymousInt1 + ","
					+ paramAnonymousInt2);
			if (mMediaController != null)
				mMediaController.hide();
			if ((mOnErrorListener != null)
					&& (mOnErrorListener.onError(mMediaPlayer,
							paramAnonymousInt1, paramAnonymousInt2)));
			while (getWindowToken() == null)
				return true;
			mContext.getResources();
			return true;
		}
	};
	private boolean mIsPrepared;
	private MediaController mMediaController;
	private MediaPlayer mMediaPlayer = null;
	private MySizeChangeLinstener mMyChangeLinstener;
	private MediaPlayer.OnCompletionListener mOnCompletionListener;
	private MediaPlayer.OnErrorListener mOnErrorListener;
	private MediaPlayer.OnPreparedListener mOnPreparedListener;
	MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
		public void onPrepared(MediaPlayer paramAnonymousMediaPlayer) {
			mIsPrepared = true;
			if (mOnPreparedListener != null)
				mOnPreparedListener.onPrepared(mMediaPlayer);
			if (mMediaController != null)
				mMediaController.setEnabled(true);
			mVideoWidth = paramAnonymousMediaPlayer.getVideoWidth();
			mVideoHeight = paramAnonymousMediaPlayer.getVideoHeight();
			if ((mVideoWidth != 0) && (mVideoHeight != 0)) {
				getHolder().setFixedSize(mVideoWidth, mVideoHeight);
				if ((mSurfaceWidth == mVideoWidth)
						&& (mSurfaceHeight == mVideoHeight)) {
					if (mSeekWhenPrepared != 0) {
						mMediaPlayer.seekTo(mSeekWhenPrepared);
						mSeekWhenPrepared = 0;
					}
					if (!mStartWhenPrepared) {
						if (!isPlaying()
								&& (mSeekWhenPrepared != 0 || getCurrentPosition() > 0)
								&& mMediaController != null) {
							mMediaController.show(0);
							return;
						}
					} else {
						mMediaPlayer.start();
						mStartWhenPrepared = false;
						if (mMediaController != null)
							mMediaController.show();
					}
				}
			} else {
				if (mSeekWhenPrepared != 0) {
					mMediaPlayer.seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				if (mStartWhenPrepared) {
					mMediaPlayer.start();
					mStartWhenPrepared = false;
					return;
				}
			}
		}
	};
	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder,
				int paramAnonymousInt1, int paramAnonymousInt2,
				int paramAnonymousInt3) {
			mSurfaceWidth = paramAnonymousInt2;
			mSurfaceHeight = paramAnonymousInt3;
			if ((mMediaPlayer != null) && (mIsPrepared)
					&& (mVideoWidth == paramAnonymousInt2)
					&& (mVideoHeight == paramAnonymousInt3)) {
				if (mSeekWhenPrepared != 0) {
					mMediaPlayer.seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				mMediaPlayer.start();
				if (mMediaController != null)
					mMediaController.show();
			}
		}

		public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder) {
			mSurfaceHolder = paramAnonymousSurfaceHolder;
			openVideo();
		}

		public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder) {
			mSurfaceHolder = null;
			if (mMediaController != null)
				mMediaController.hide();
			if (mMediaPlayer != null) {
				mMediaPlayer.reset();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
		}
	};
	
	private int mSeekWhenPrepared;
	MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(MediaPlayer paramAnonymousMediaPlayer,
				int paramAnonymousInt1, int paramAnonymousInt2) {
			mVideoWidth = paramAnonymousMediaPlayer.getVideoWidth();
			mVideoHeight = paramAnonymousMediaPlayer.getVideoHeight();
			if (mMyChangeLinstener != null)
				mMyChangeLinstener.doMyThings();
			if ((mVideoWidth != 0) && (mVideoHeight != 0))
				getHolder().setFixedSize(mVideoWidth, mVideoHeight);
		}
	};
	
	private boolean mStartWhenPrepared;
	private int mSurfaceHeight;
	private SurfaceHolder mSurfaceHolder = null;
	private int mSurfaceWidth;
	private Uri mUri;
	private int mVideoHeight;
	private int mVideoWidth;
	private String path = null;

	public VideoView(Context paramContext) {
		super(paramContext);
		this.mContext = paramContext;
		initVideoView();
	}

	public VideoView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
		this.mContext = paramContext;
		initVideoView();
	}

	public VideoView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		this.mContext = paramContext;
		initVideoView();
	}

	private void attachMediaController() {
		if (mMediaPlayer != null && mMediaController != null) {
			mMediaController.setMediaPlayer(this);
			Object obj;
			if (getParent() instanceof View)
				obj = (View) getParent();
			else
				obj = this;
			mMediaController.setAnchorView(((View) (obj)));
			mMediaController.setEnabled(mIsPrepared);
		}
	}

	private void initVideoView() {
		this.mVideoWidth = 0;
		this.mVideoHeight = 0;
		getHolder().addCallback(this.mSHCallback);
		getHolder().setType(3);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

	private void openVideo() {
		System.out.println("***********************");
		if (((this.mUri == null) || (this.mSurfaceHolder == null))
				&& ((this.path == null) || (this.mSurfaceHolder == null)))
			return;
		Intent localIntent = new Intent("com.android.music.musicservicecommand");
		localIntent.putExtra("command", "pause");
		this.mContext.sendBroadcast(localIntent);
		if (this.mMediaPlayer != null) {
			this.mMediaPlayer.reset();
			this.mMediaPlayer.release();
			this.mMediaPlayer = null;
		}
		try {
			this.mMediaPlayer = new MediaPlayer();
			this.mMediaPlayer.reset();
			this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
			this.mMediaPlayer
					.setOnVideoSizeChangedListener(this.mSizeChangedListener);
			this.mIsPrepared = false;
			logger.v("reset duration to -1 in openVideo");
			this.mDuration = -1;
			this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
			this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
			this.mMediaPlayer
					.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
			this.mCurrentBufferPercentage = 0;
			if (this.mUri == null) {
				this.mMediaPlayer.setDataSource(this.path);
			} else {
				this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
			}
			this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
			this.mMediaPlayer.setAudioStreamType(3);
			this.mMediaPlayer.setScreenOnWhilePlaying(true);
			this.mMediaPlayer.prepareAsync();
			attachMediaController();
			return;
		} catch (IOException localIOException) {
			logger.w("Unable to open content: " + this.mUri);
			return;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			logger.w("Unable to open content: " + this.mUri);
		}
	}

	private void toggleMediaControlsVisiblity() {
		if (this.mMediaController.isShowing()) {
			this.mMediaController.hide();
			return;
		}
		this.mMediaController.show();
	}

	public boolean canPause() {
		return true;
	}

	public boolean canSeekBackward() {
		return true;
	}

	public boolean canSeekForward() {
		return true;
	}

	public int getBufferPercentage() {
		if (this.mMediaPlayer != null)
			return this.mCurrentBufferPercentage;
		return 0;
	}

	public int getCurrentPosition() {
		if ((this.mMediaPlayer != null) && (this.mIsPrepared))
			return this.mMediaPlayer.getCurrentPosition();
		return 0;
	}

	public int getDuration() {
		if ((this.mMediaPlayer != null) && (this.mIsPrepared)) {
			if (this.mDuration > 0)
				return this.mDuration;
			this.mDuration = this.mMediaPlayer.getDuration();
			return this.mDuration;
		}
		this.mDuration = -1;
		return this.mDuration;
	}

	public int getVideoHeight() {
		return this.mVideoHeight;
	}

	public int getVideoWidth() {
		return this.mVideoWidth;
	}

	public boolean isPlaying() {
		if ((this.mMediaPlayer != null) && (this.mIsPrepared))
			try {
				boolean bool = this.mMediaPlayer.isPlaying();
				return bool;
			} catch (Exception localException) {
			}
		return false;
	}

	public boolean onKeyDown(int i, KeyEvent paramKeyEvent) {
		if (mIsPrepared && i != 4 && i != 24 && i != 25 && i != 82 && i != 5
				&& i != 6 && mMediaPlayer != null && mMediaController != null) {
			if (i == 79 || i == 85) {
				if (mMediaPlayer.isPlaying()) {
					pause();
					mMediaController.show();
				} else {
					start();
					mMediaController.hide();
				}
				return true;
			}
			if (i == 86 && mMediaPlayer.isPlaying()) {
				pause();
				mMediaController.show();
			} else {
				toggleMediaControlsVisiblity();
			}
		}
		return super.onKeyDown(i, paramKeyEvent);
	}

	protected void onMeasure(int width, int height) {
		setMeasuredDimension(getDefaultSize(this.mVideoWidth, width),
				getDefaultSize(this.mVideoHeight, height));
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if ((this.mIsPrepared) && (this.mMediaPlayer != null)
				&& (this.mMediaController != null))
			toggleMediaControlsVisiblity();
		return false;
	}

	public boolean onTrackballEvent(MotionEvent paramMotionEvent) {
		if ((this.mIsPrepared) && (this.mMediaPlayer != null)
				&& (this.mMediaController != null))
			toggleMediaControlsVisiblity();
		return false;
	}

	public void pause() {
		if ((this.mMediaPlayer != null) && (this.mIsPrepared)
				&& (this.mMediaPlayer.isPlaying()))
			this.mMediaPlayer.pause();
		this.mStartWhenPrepared = false;
	}

	public int resolveAdjustedSize(int paramInt1, int paramInt2) {
		int i = View.MeasureSpec.getMode(paramInt2);
		int j = View.MeasureSpec.getSize(paramInt2);
		switch (i) {
		default:
			return paramInt1;
		case 0:
			return paramInt1;
		case -2147483648:
			return Math.min(paramInt1, j);
		case 1073741824:
			return j;
		}
	}

	public void seekTo(int paramInt) {
		if ((this.mMediaPlayer != null) && (this.mIsPrepared)) {
			this.mMediaPlayer.seekTo(paramInt);
			return;
		}
		this.mSeekWhenPrepared = paramInt;
	}


	public void setMediaController(MediaController paramMediaController) {
		if (this.mMediaController != null)
			this.mMediaController.hide();
		this.mMediaController = paramMediaController;
		attachMediaController();
	}

	public void setMySizeChangeLinstener(
			MySizeChangeLinstener paramMySizeChangeLinstener) {
		this.mMyChangeLinstener = paramMySizeChangeLinstener;
	}

	public void setOnCompletionListener(
			MediaPlayer.OnCompletionListener paramOnCompletionListener) {
		this.mOnCompletionListener = paramOnCompletionListener;
	}

	public void setOnErrorListener(
			MediaPlayer.OnErrorListener paramOnErrorListener) {
		this.mOnErrorListener = paramOnErrorListener;
	}

	public void setOnPreparedListener(
			MediaPlayer.OnPreparedListener paramOnPreparedListener) {
		this.mOnPreparedListener = paramOnPreparedListener;
	}

	public void setVideoPath(String paramString) {
		setVideoURI(Uri.parse(paramString));
	}

	public void setVideoScale(int width, int heigth) {
//		ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
//		localLayoutParams.height = paramInt2;
//		localLayoutParams.width = paramInt1;
//		setLayoutParams(localLayoutParams);
		LayoutParams param = new RelativeLayout.LayoutParams(width, heigth);
		getHolder().setFixedSize(width, heigth);
		setLayoutParams(param);
//		if(null != mMediaPlayer)
//			mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
	}

	public void setVideoURI(Uri paramUri) {
		this.mUri = paramUri;
		this.mStartWhenPrepared = false;
		this.mSeekWhenPrepared = 0;
		openVideo();
		requestLayout();
		invalidate();
	}

	public void setVideoUrl(String paramString) {
		this.path = paramString;
		this.mStartWhenPrepared = false;
		this.mSeekWhenPrepared = 0;
		openVideo();
		requestLayout();
		invalidate();
	}

	public void start() {
		if ((this.mMediaPlayer != null) && (this.mIsPrepared)) {
			this.mMediaPlayer.start();
			this.mStartWhenPrepared = false;
			return;
		}
		this.mStartWhenPrepared = true;
	}

	public void stopPlayback() {
		if (this.mMediaPlayer != null) {
			this.mMediaPlayer.stop();
			this.mMediaPlayer.release();
			this.mMediaPlayer = null;
		}
	}

	public static abstract interface MySizeChangeLinstener {
		public abstract void doMyThings();
	}

	@Override
	public int getAudioSessionId() {
		return 0;
	}
}
