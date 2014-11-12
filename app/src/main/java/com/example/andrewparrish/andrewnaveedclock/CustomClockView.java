package com.example.andrewparrish.andrewnaveedclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
import android.view.View;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class CustomClockView extends View {

    //variables needed for onDraw
    private Bitmap mBitmap;
    private Paint mTextPaint;
    private Paint mBorderPaint;
    private int mWidth;
    private int mHeight;
    private String dateTime;


    public CustomClockView(Context context, Bitmap mBitmap) {
        super(context);
        this.mBitmap = mBitmap.createScaledBitmap(mBitmap, 600, 800, false);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(80);
        mTextPaint.setTextAlign(Paint.Align.CENTER);


        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.GRAY);
        mBorderPaint.measureText("00:00:00");


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    //method to actually draw on the canvas
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

     //draw bitmap
        // center the image
        int cx = (mWidth - mBitmap.getWidth()) >> 1; // same as width / 2
        int cy = (mHeight - mBitmap.getHeight()) >> 1;

        canvas.drawBitmap(mBitmap, cx, cy, null);


    // draw border Rect
    canvas.drawRect(mWidth / 2 , mHeight / 2, mWidth + mTextPaint.getTextSize(), mHeight + mTextPaint.getTextSize(), mBorderPaint);

        Time currentTime = new Time();
        currentTime.setToNow();

        dateTime = currentTime.format("%k:%M:%S");
        canvas.drawText(dateTime, (mWidth / 2) , (mHeight - 100), mTextPaint);
    }
}
