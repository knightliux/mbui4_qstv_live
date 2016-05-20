package com.moon.android.live.custom007.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.moon.android.live.custom007.R;

public class CircularSeekBar extends View {

	private final  Paint mCirclePaint;
	private final  Paint mTextPaint;
	private final Context mContext;
	private final  Paint mPaintDot;
	public static int ANGLE_DEFAULT = 270;
	public static int SCROLL_SPEED = 3;
	public static int PER_ANGLER = 24;
	private Integer[] volumeDegree = {270,294,318,342,6,30,54,78,102,126,150,174,198,222,246,270};
	private int volumePos = 0;
	public CircularSeekBar(Context context) {
		this(context, null);
	}

	public CircularSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mCirclePaint = new Paint();
		mTextPaint = new Paint();
		mPaintDot = new Paint();
		mCirclePaint.setAntiAlias(true); 
		mCirclePaint.setStyle(Paint.Style.STROKE); 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int center = getWidth()/2;
		int innerCircle = dip2px(mContext, 82); 
		int ringWidth = dip2px(mContext, 6); 
		
		mCirclePaint.setARGB(255, 255 ,228, 0);
		mCirclePaint.setStrokeWidth(ringWidth);
		
		RectF rect2 = new RectF(center-(innerCircle + 1 +ringWidth/2),center-(innerCircle + 1 +ringWidth/2), 
				center+(innerCircle + 1 +ringWidth/2), center+(innerCircle + 1 +ringWidth/2));
		canvas.drawArc(rect2, ANGLE_DEFAULT, volumePos * PER_ANGLER , false, mCirclePaint);
		
		if(0 != volumePos % volumeDegree.length){
			mTextPaint.setARGB(255, 212 ,225, 233);
			mTextPaint.setTextSize(60);
			mTextPaint.setTextAlign(Align.CENTER);
			canvas.drawText(String.valueOf(volumePos), center, center + 15, mTextPaint);
		} else {
			Bitmap bitmap = drawableToBitmap(getResources().getDrawable(R.drawable.p_volume_slient));	
			canvas.drawBitmap(bitmap, center - 20, center - 20, mPaintDot);
		}
		
		int dotXpos = getXpos(innerCircle - 13,center - 8,volumeDegree[volumePos]);
		int dotYpos = getYpos(innerCircle - 13, center - 8, volumeDegree[volumePos]);
		mPaintDot.setARGB(255, 212 ,225, 233);
		Bitmap bitmap = drawableToBitmap(getResources().getDrawable(R.drawable.p_volume_dot));	
		canvas.drawBitmap(bitmap, dotXpos, dotYpos, mPaintDot);
		super.onDraw(canvas);
	}
	
	public void setVolume(int pos){
		volumePos = pos ;
		invalidate();
	}
	
	private static Bitmap drawableToBitmap(Drawable drawable) {       
		BitmapDrawable bd = (BitmapDrawable)drawable;
        return bd.getBitmap();
	}
	
	private int getXpos(int r,int center,int angle){
		double xPlusDouble = r * Math.cos(Math.toRadians(angle));
		Double reseveXplus = Double.valueOf(xPlusDouble);
	    int xPlusInt =reseveXplus.intValue();
		int xPos = center + xPlusInt;
		return xPos;
	}
	
	private int getYpos(int r,int center,int angle){
		double yPlusDouble = r * Math.sin(Math.toRadians(angle));
		Double reseveXplus = Double.valueOf(yPlusDouble);
	    int yPlusInt =reseveXplus.intValue();
		int yPos = center + yPlusInt;
		return yPos;
	}
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
