package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tuxedo on 11/12/14.
 */

public class CustomClockActivity extends Activity {

    private static int SETTINGS_REQUEST = 100;

    private static final String MILITARY_TIME_BOOLEAN = "military_time_boolean";
    private static final String TIME_COLOR_INTEGER = "time_color_integer";
    private static final String TAG = "clock-activity";

    private boolean militaryTime = false;
    private int timeColor = Color.BLACK;

    public CustomClockView customClock = null;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_clock_view);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        militaryTime = preferences.getBoolean(MILITARY_TIME_BOOLEAN, false);
        timeColor = preferences.getInt(TIME_COLOR_INTEGER, Color.BLACK);

        // find the view
        final RelativeLayout frame = (RelativeLayout) findViewById(R.id.custom_frame);
        customClock = new CustomClockView(getApplicationContext());

        frame.addView(customClock);


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

        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 60000 ms
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.persistent_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(settingsIntent, SETTINGS_REQUEST);
            return true;
        }
        else if (id == R.id.clock_view_2){
            Intent customClockIntent = new Intent(this, BasicClockActivity.class);
            startActivity(customClockIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == SETTINGS_REQUEST) {
            if(resultCode == RESULT_OK) {
                militaryTime = preferences.getBoolean(MILITARY_TIME_BOOLEAN, false);
                timeColor = preferences.getInt(TIME_COLOR_INTEGER, Color.BLACK);
            }
        }
    }
}


