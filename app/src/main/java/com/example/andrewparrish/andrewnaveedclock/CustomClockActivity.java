package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

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

        // TODO: this bitmap should use decodeStream when HTTP GET is done -> MIGHT NEED TO BE REFACTORED
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_map_image);

        // ind the view
        final RelativeLayout frame = (RelativeLayout) findViewById(R.id.custom_frame);
        final CustomClockView customClock = new CustomClockView(getApplicationContext(), mBitmap);

        frame.addView(customClock);

        //instantiate the time object necessary for the clock
        callAsynchronousTask();

// TODO: DELETE THIS WHEN EVERYTHING HAS BEEN TESTED
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                    while(true) {
//                        customClock.postInvalidate();
//                        try {
//                            Thread.sleep(60000);
//                        } catch (InterruptedException e) {
//                            Log.i(TAG, "InterruptedException");
//                        }
//                    }
//            }
//        }).start();
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            GoogleJSONAsyncBackgroundTask performBackgroundTask = new GoogleJSONAsyncBackgroundTask();
                            // PerformBackgroundTask this class is the class that extends AsyncTask
                            performBackgroundTask.execute();
                        } catch (Exception e) {
                            Log.e(TAG, "EXCEPTION THROWN");
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 60000); //execute in every 60000 ms
    }
}


