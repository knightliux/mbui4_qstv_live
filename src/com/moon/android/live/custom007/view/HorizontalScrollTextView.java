package com.moon.android.live.custom007.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class HorizontalScrollTextView extends TextView implements
		View.OnClickListener {
	public static final String TAG = HorizontalScrollTextView.class
			.getSimpleName();
	public boolean isStarting = false;
	private Paint paint = null;
	private float step = 0.0F;
	private float temp_view_plus_text_length = 0.0F;
	private float temp_view_plus_two_text_length = 0.0F;
	private String text = "";
	private float textLength = 0.0F;
	private float viewWidth = 0.0F;
	private float y = 0.0F;

	public HorizontalScrollTextView(Context paramContext) {
		super(paramContext);
		initView();
	}

	public HorizontalScrollTextView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initView();
	}

	public HorizontalScrollTextView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		initView();
	}

	private void initView() {
		setOnClickListener(this);
	}

	public void init(WindowManager paramWindowManager) {
		this.paint = getPaint();
		this.text = getText().toString();
		this.textLength = this.paint.measureText(this.text);
		this.viewWidth = getWidth();
		if ((this.viewWidth == 0.0F) && (paramWindowManager != null))
			this.viewWidth = paramWindowManager.getDefaultDisplay().getWidth();
		this.step = this.textLength;
		this.temp_view_plus_text_length = (this.viewWidth + this.textLength);
		this.temp_view_plus_two_text_length = (this.viewWidth + 4.0F * this.textLength);
		this.y = getPaddingTop();
	}

	public void onClick(View paramView) {
		if (this.isStarting) {
			stopScroll();
			return;
		}
		startScroll();
	}

	public void onDraw(Canvas paramCanvas) {
		paramCanvas.drawText(this.text, this.temp_view_plus_text_length
				- this.step, this.y, this.paint);
		if (!this.isStarting)
			return;
		this.step = ((float) (1.5D + this.step));
		if (this.step > this.temp_view_plus_two_text_length)
			this.step = this.textLength;
		invalidate();
	}

	public void onRestoreInstanceState(Parcelable paramParcelable) {
		if (!(paramParcelable instanceof SavedState)) {
			super.onRestoreInstanceState(paramParcelable);
			return;
		}
		SavedState localSavedState = (SavedState) paramParcelable;
		super.onRestoreInstanceState(localSavedState.getSuperState());
		this.step = localSavedState.step;
		this.isStarting = localSavedState.isStarting;
	}

	public Parcelable onSaveInstanceState() {
		SavedState localSavedState = new SavedState(super.onSaveInstanceState());
		localSavedState.step = this.step;
		localSavedState.isStarting = this.isStarting;
		return localSavedState;
	}

	public void setTextColor(int paramInt) {
		this.paint.setColor(paramInt);
	}

	public void setY(int paramInt) {
		this.y += paramInt;
	}

	public void startScroll() {
		this.isStarting = true;
		invalidate();
	}

	public void stopScroll() {
		this.isStarting = false;
		invalidate();
	}

	// public static class SavedState extends View.BaseSavedState
	// {
	// public static final Parcelable.Creator<SavedState> CREATOR = new
	// Parcelable.Creator()
	// {
	// public SavedState createFromParcel(Parcel paramAnonymousParcel)
	// {
	// return new SavedState(paramAnonymousParcel, null);
	// }
	//
	// public HorizontalScrollTextView.SavedState[] newArray(int
	// paramAnonymousInt)
	// {
	// return new HorizontalScrollTextView.SavedState[paramAnonymousInt];
	// }
	// };
	// public boolean isStarting = false;
	// public float step = 0.0F;
	//
	// private SavedState(Parcel paramParcel)
	// {
	// super();
	// boolean[] arrayOfBoolean = (boolean[])null;
	// paramParcel.readBooleanArray(arrayOfBoolean);
	// if ((arrayOfBoolean != null) && (arrayOfBoolean.length > 0))
	// this.isStarting = arrayOfBoolean[0];
	// this.step = paramParcel.readFloat();
	// }
	//
	// SavedState(Parcelable paramParcelable)
	// {
	// super();
	// }
	//
	// public void writeToParcel(Parcel paramParcel, int paramInt)
	// {
	// super.writeToParcel(paramParcel, paramInt);
	// boolean[] arrayOfBoolean = new boolean[1];
	// arrayOfBoolean[0] = this.isStarting;
	// paramParcel.writeBooleanArray(arrayOfBoolean);
	// paramParcel.writeFloat(this.step);
	// }
	// }
	// }

	public static class SavedState extends android.view.View.BaseSavedState {

		public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

			public SavedState createFromParcel(Parcel parcel) {
				return new SavedState(parcel, null);
			}

			public SavedState[] newArray(int i) {
				return new SavedState[i];
			}

		};
		public boolean isStarting;
		public float step;

		public void writeToParcel(Parcel parcel, int i) {
			super.writeToParcel(parcel, i);
			boolean aflag[] = new boolean[1];
			aflag[0] = isStarting;
			parcel.writeBooleanArray(aflag);
			parcel.writeFloat(step);
		}

		private SavedState(Parcel parcel) {
			super(parcel);
			isStarting = false;
			step = 0.0F;
			boolean aflag[] = (boolean[]) null;
			parcel.readBooleanArray(aflag);
			if (aflag != null && aflag.length > 0)
				isStarting = aflag[0];
			step = parcel.readFloat();
		}

		SavedState(Parcel parcel, SavedState savedstate) {
			this(parcel);
		}

		SavedState(Parcelable parcelable) {
			super(parcelable);
			isStarting = false;
			step = 0.0F;
		}
	}
}
