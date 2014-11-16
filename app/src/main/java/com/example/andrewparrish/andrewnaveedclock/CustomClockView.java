package com.example.andrewparrish.andrewnaveedclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.view.View;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class CustomClockView extends View {

    private static final String TAG = "clock-activity";

    //preferences
    SharedPreferences preferences;
    private boolean militaryTime;
    private int colorTime;

    //variables needed for onDraw
    private static Bitmap mBitmap;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private String dateTime;

    private static final String MILITARY_TIME_BOOLEAN = "military_time_boolean";
    private static final String TIME_COLOR_INTEGER = "time_color_integer";

    public CustomClockView(Context context) {
        super(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        init();
    }
   private void init(){

       //get the preferences
       militaryTime = preferences.getBoolean(MILITARY_TIME_BOOLEAN, false);
       colorTime = preferences.getInt(TIME_COLOR_INTEGER, Color.BLACK);

       // get the test image first
        this.mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_map_image);

       //set the paint object for the text field
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(colorTime);
        mTextPaint.setTextSize(80);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    //static setter because there will only ever be one BitMap in existence for the app
    public static void setBitMap(Bitmap bMap){
        mBitmap = mBitmap.createScaledBitmap(bMap, 600, 800, false);
    }

    //method that measures the size of the device and returns the dimensions for the canvas
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
        int cx = (mWidth - mBitmap.getWidth()) >> 1; // same as width * .5
        int cy = (mHeight - mBitmap.getHeight()) >> 1;

        canvas.drawBitmap(mBitmap, cx, cy, null);


        Time currentTime = new Time();
        currentTime.setToNow();
        dateTime = setTime(currentTime);

        colorTime = preferences.getInt(TIME_COLOR_INTEGER, Color.BLACK);
        mTextPaint.setColor(colorTime);

        Log.e(TAG, "CUSTOM VIEW COLOR = "+ colorTime);
        canvas.drawText(dateTime, (mWidth / 2), (mHeight - 100), mTextPaint);

    }

    private String setTime(Time today){
        String time = null;
        if(militaryTime == true) {
           return time = today.format("%k:%M:%S");
        }
        else{
           return time = today.format("%I:%M:%S %p");
        }

    }
}
