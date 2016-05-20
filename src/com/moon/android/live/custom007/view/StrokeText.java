package com.moon.android.live.custom007.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.widget.TextView;

public class StrokeText extends TextView{

	private float mBigFontBottom;  
    private float mBigFontHeight;  
  
    private String text;  
    private Paint mPaint;  
    private int strokeSize = 1;  
  
    public StrokeText(Context context) {  
        super(context);  
        init();  
    }  
  
    public StrokeText(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();  
    }  
  
    public StrokeText(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();  
    }  
  
    private void init() {  
        mPaint = new Paint();  
        mPaint.setAntiAlias(true);  
        mPaint.setTextSize(getTextSize());  
//        mPaint.setColor(Color.rgb(128, 138, 135)); 
        mPaint.setColor(Color.rgb(0, 0, 0)); 
        FontMetrics fm = mPaint.getFontMetrics();  
        mBigFontBottom = fm.bottom;  
        mBigFontHeight = fm.bottom - fm.top;  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {  
        if (strokeSize > 0 && strokeSize < 4) {  
            float y = getPaddingTop() + mBigFontHeight - mBigFontBottom;  
            canvas.drawText(text, 0, y - strokeSize, mPaint);  
            canvas.drawText(text, 0, y + strokeSize, mPaint);  
            canvas.drawText(text, 0 + strokeSize, y, mPaint);  
            canvas.drawText(text, 0 + strokeSize, y + strokeSize, mPaint);  
            canvas.drawText(text, 0 + strokeSize, y - strokeSize, mPaint);  
            canvas.drawText(text, 0 - strokeSize, y, mPaint);  
            canvas.drawText(text, 0 - strokeSize, y + strokeSize, mPaint);  
            canvas.drawText(text, 0 - strokeSize, y - strokeSize, mPaint);  
        }  
  
        super.onDraw(canvas);  
    }  
  
    @Override  
    public void setText(CharSequence text, BufferType type) {  
        super.setText(text, type);  
        this.text = text.toString();  
        invalidate();  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        if (strokeSize > 0 && strokeSize < 4) {  
            setMeasuredDimension(getMeasuredWidth() + strokeSize, getMeasuredHeight());  
        }  
    }
	
}
