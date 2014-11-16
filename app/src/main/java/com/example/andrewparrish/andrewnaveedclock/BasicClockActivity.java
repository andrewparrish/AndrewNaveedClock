package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BasicClockActivity extends Activity {

    public TextView textViewDate;
    public TextView textViewTime;
    public RelativeLayout parentFrame;

    private static int SETTINGS_REQUEST = 100;

    private boolean militaryTime = false;
    private int timeColor = Color.BLACK;

    private static final String MILITARY_TIME_BOOLEAN = "military_time_boolean";
    private static final String TIME_COLOR_INTEGER = "time_color_integer";
    private static final String TAG = "clock-activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clock);
        parentFrame = (RelativeLayout) findViewById(R.id.parentFrame);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

        if (savedInstanceState != null){
            savedInstanceState.getBoolean(MILITARY_TIME_BOOLEAN);
            savedInstanceState.getInt(TIME_COLOR_INTEGER);
        }
        //instantiate the time object necessary for the clock
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                // update TextView here!
                                setTime(today, timeColor);
                                setDate(today, timeColor);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
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
            settingsIntent.putExtra(MILITARY_TIME_BOOLEAN, militaryTime);
            settingsIntent.putExtra(TIME_COLOR_INTEGER, timeColor);

            startActivityForResult(settingsIntent, SETTINGS_REQUEST);
            return true;
        }
        else if (id == R.id.clock_view_2){
            Intent customClockIntent = new Intent(this, CustomClockActivity.class);
            customClockIntent.putExtra(MILITARY_TIME_BOOLEAN, militaryTime);
            customClockIntent.putExtra(TIME_COLOR_INTEGER, timeColor);

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
               Bundle bundle = data.getExtras();
               militaryTime = bundle.getBoolean(MILITARY_TIME_BOOLEAN);

                if(bundle.getInt(TIME_COLOR_INTEGER) != 0) {
                    timeColor = bundle.getInt(TIME_COLOR_INTEGER);
                }

            }
        }



    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save state information with a collection of key-value pairs
        savedInstanceState.putBoolean(MILITARY_TIME_BOOLEAN, militaryTime);
        savedInstanceState.putInt(TIME_COLOR_INTEGER, timeColor);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setTime(Time today, int color){
        String time = null;

        if(militaryTime == true) {
             time = today.format("%k:%M:%S");
        }
        else{
             time = today.format("%I:%M:%S %p");
        }

        textViewTime.setText(time);  // Current time
        textViewTime.setTextColor(color);
    }

    private void setDate(Time today, int color){
        String date = today.month + "/" + today.monthDay + "/" + today.year + "";
        textViewDate.setTextColor(color);
        textViewDate.setText(date);
    }
}
