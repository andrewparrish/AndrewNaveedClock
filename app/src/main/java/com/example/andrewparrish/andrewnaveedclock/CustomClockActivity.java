package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class CustomClockActivity extends Activity {

    private Timer timer;
    private TimerTask timerTask;
    private static final String TAG = "clock-activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_clock_view);

        // this bitmap should use decodeStream when HTTP GET is done
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_map_image);

        // ind the view
        final RelativeLayout frame = (RelativeLayout) findViewById(R.id.custom_frame);
        final CustomClockView customClock = new CustomClockView(getApplicationContext(), mBitmap);

        frame.addView(customClock);

        //instantiate the time object necessary for the clock

        new Thread(new Runnable() {
            @Override
            public void run() {
                    while(true) {
                        customClock.postInvalidate();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.i(TAG, "InterruptedException");
                        }
                    }
            }
        }).start();
    }
}


