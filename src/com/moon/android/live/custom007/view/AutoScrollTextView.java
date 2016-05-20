package com.moon.android.live.custom007.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoScrollTextView extends TextView {

	private float mTextLength = 0f;
	private float mViewWidth = 0f;
	private float mStep = 0f;
	private float mYPos = 0f;
	private float mXPos = 0f;
	private float temp_view_plus_text_length = 0.0f;
	private float temp_view_plus_two_text_length = 0.0f;
	private boolean isStarting = false;
	private Paint mPaint = null;
	private String mText = "";
	private boolean isFirst = true;
	private boolean isSingleLineScrool = true;
	
	public static final float SPEED_lEVEL_1=1.0f;
	public static final float SPEED_lEVEL_1_PLUS=1.5f;
	public static final float SPEED_lEVEL_2=2.0f;
	public static final float SPEED_lEVEL_2_PLUS=2.5f;
	public static final float SPEED_lEVEL_3=3.0f;
	public static final float SPEED_lEVEL_3_PLUS=3.5f;
	public static final float SPEED_lEVEL_4=4.0f;
	public static final float SPEED_lEVEL_4_PLUS=4.5f;
	public static final float SPEED_lEVEL_5=5.0f;
	public static final float SPEED_lEVEL_5_PLUS=5.5f;
	
	private float mSpeed = SPEED_lEVEL_2_PLUS;
	
	
	public AutoScrollTextView(Context context) {
		super(context);
//		this.setFocusable(true);
		init();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		this.setFocusable(true);
		init();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		this.setFocusable(true);
		init();
	}

	public void setSpeed(float speed){
		mSpeed=speed;
	}
	
	private void init() {
		mPaint = getPaint();
		mPaint.setARGB(255, 255, 250,250);
		startScroll();
	}

	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.step = mStep;
		ss.isStarting = isStarting;
		return ss;

	}

	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		mStep = ss.step;
		isStarting = ss.isStarting;

	}

	public static class SavedState extends BaseSavedState {
		public boolean isStarting = false;
		public float step = 0.0f;

		SavedState(Parcelable superState) {
			super(superState);
		}

		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeBooleanArray(new boolean[] { isStarting });
			out.writeFloat(step);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}
		};

		private SavedState(Parcel in) {
			super(in);
			boolean[] b = null;
			in.readBooleanArray(b);
			if (b != null && b.length > 0)
				isStarting = b[0];
			step = in.readFloat();
		}
	}

	/**
	 * 寮�婊氬姩
	 */
	public void startScroll() {
		isStarting = true;
		invalidate();
	}

	/**
	 * 鍋滄婊氬姩
	 */
	public void stopScroll() {
		isStarting = false;
		invalidate();
	}
	@Override
	public void onDraw(Canvas canvas) {
		if (isFirst) {
			mViewWidth = getWidth();
			mTextLength = mPaint.measureText(mText);
			
			if (mTextLength < mViewWidth && !isSingleLineScrool)
				isStarting = false;
			
			mStep = mTextLength;
			temp_view_plus_text_length = mViewWidth + mTextLength;
			temp_view_plus_two_text_length = mViewWidth + mTextLength * 2;
			mYPos = getTextSize() + getPaddingTop();
			mXPos = getPaddingLeft();
			isFirst = false;
		}
		if (!isStarting) {
			canvas.drawText(mText, mXPos, mYPos, mPaint);
			return;
		}
		canvas.drawText(mText, temp_view_plus_text_length - mStep, mYPos, mPaint);
		mStep += mSpeed;
		if (mStep > temp_view_plus_two_text_length)
			mStep = mTextLength;
		invalidate();
	}

	public void setText(String text) {
		this.mText = text;
	}

	public void setSingleLineScrool(boolean isSingleLineScrool) {
		this.isSingleLineScrool = isSingleLineScrool;
	}
	
}
