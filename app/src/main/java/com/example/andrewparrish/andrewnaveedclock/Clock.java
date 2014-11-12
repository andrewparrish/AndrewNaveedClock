package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class Clock extends Activity {

    public TextView textViewDate;
    public TextView textViewTime;
    public RelativeLayout parentFrame;
    private Timer timer;
    private TimerTask timerTask;
    private static final String LOG = "clock-activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clock);
        parentFrame = (RelativeLayout) findViewById(R.id.parentFrame);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

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
                                setTime(today);
                                setDate(today);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTime(Time today){
        String time = today.format("%k:%M:%S");
        textViewTime.setText(today.format("%k:%M:%S"));  // Current time
        Log.i(LOG, time);
    }

    private void setDate(Time today){
        String date = today.month + "/" + today.monthDay + "/" + today.year + "";
        textViewDate.setText(date);
        Log.i(LOG, date);
    }
}
