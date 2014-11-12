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
//TODO -> ADD CODE TO PERSIST SETTINGS IN ALL LIFECYCLE METHODS
//TODO -> CREATE/ORGANIZE PACKAGES IN THE DIRECTORY SO THAT THINGS MAKE SENSE
public class CustomClockActivity extends Activity {

    private static final String TAG = "clock-activity";
    public CustomClockView customClock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_clock_view);


        // find the view
        final RelativeLayout frame = (RelativeLayout) findViewById(R.id.custom_frame);
        customClock = new CustomClockView(getApplicationContext());

        frame.addView(customClock);

        //TODO -> ADD A MENU INFLATER HERE SO WE CAN TRAVERSE BETWEEN THE DIFFERENT VIEWS


        //instantiate the time object necessary for the clock
        callAsynchronousTask();
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
                            customClock.invalidate();
                        } catch (Exception e) {
                            Log.e(TAG, "EXCEPTION THROWN");
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 60000 ms
    }
}


