package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BasicClockActivity extends Activity {

    public TextView textViewDate;
    public TextView textViewTime;
    public RelativeLayout parentFrame;
    SharedPreferences preferences;

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

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        militaryTime = preferences.getBoolean(MILITARY_TIME_BOOLEAN, false);
        timeColor = preferences.getInt(TIME_COLOR_INTEGER, Color.BLACK);

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
            startActivityForResult(settingsIntent, SETTINGS_REQUEST);
            return true;
        }
        else if (id == R.id.clock_view_2){
            Intent customClockIntent = new Intent(this, CustomClockActivity.class);
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
